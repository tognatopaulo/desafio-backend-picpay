package tech.tognati.picpay.service;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;
import tech.tognati.picpay.controller.TransferDTO;
import tech.tognati.picpay.entity.Transfer;
import tech.tognati.picpay.entity.Wallet;
import tech.tognati.picpay.entity.WalletType;
import tech.tognati.picpay.exception.InsufficientBalanceException;
import tech.tognati.picpay.exception.TranferNotAuthorizedException;
import tech.tognati.picpay.exception.TransferNotAllowedForWalletTypeException;
import tech.tognati.picpay.exception.WalletNotFoundException;
import tech.tognati.picpay.repository.TransferRepository;
import tech.tognati.picpay.repository.WalletRepository;
import tech.tognati.picpay.repository.WalletTypeRepository;

import java.util.concurrent.CompletableFuture;

@Service
public class TransferService {

    private final NotificationService notificationService;
    private final AuthorizationService authorizationService;
    private final TransferRepository transferRepository;
    private final WalletRepository walletRepository;

    public TransferService(NotificationService notificationService, AuthorizationService authorizationService, TransferRepository transferRepository, WalletRepository walletRepository) {
        this.notificationService = notificationService;
        this.authorizationService = authorizationService;
        this.transferRepository = transferRepository;
        this.walletRepository = walletRepository;
    }


    @Transactional
    public Transfer transfer(@Valid TransferDTO transferDTO) {

        var sender = walletRepository.findById(transferDTO.payer())
                .orElseThrow(() -> new WalletNotFoundException(transferDTO.payer()));

        var receiver = walletRepository.findById(transferDTO.payee())
                .orElseThrow(() -> new WalletNotFoundException(transferDTO.payee()));

        validateTransfer(transferDTO, sender);

        sender.debit(transferDTO.value());
        receiver.credit(transferDTO.value());

        var transfer = new Transfer(sender, receiver, transferDTO.value());
        walletRepository.save(sender);
        walletRepository.save(receiver);
        var transferResult = transferRepository.save(transfer);

        CompletableFuture.runAsync(() -> notificationService.sendNotification(transferResult));

        return transferResult;
    }

    private void validateTransfer( TransferDTO transferDTO, Wallet sender) {
        if(!sender.isTransferAllowedForWalletType()) {
            throw new TransferNotAllowedForWalletTypeException();
        }

        if(!sender.isBalancerEqualOrGreatherThan(transferDTO.value())){
            throw new InsufficientBalanceException(sender.getBalance());
        }

        if(!authorizationService.isAuthorized(transferDTO)){
            throw new TranferNotAuthorizedException();
        }


    }
}
