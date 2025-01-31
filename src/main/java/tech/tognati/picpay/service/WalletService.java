package tech.tognati.picpay.service;

import org.springframework.stereotype.Service;
import tech.tognati.picpay.controller.CreateWalletDTO;
import tech.tognati.picpay.entity.Wallet;
import tech.tognati.picpay.exception.WalletDataAlreadyExistException;
import tech.tognati.picpay.repository.WalletRepository;

@Service
public class WalletService {

    private final WalletRepository walletRepository;

    public WalletService(WalletRepository walletRepository) {
        this.walletRepository = walletRepository;
    }

    public Wallet createWallet(CreateWalletDTO dto) {
        var wallerDb = walletRepository.findByCpfCnpjOrEmail(dto.cpfCnpj(), dto.email());
        if (wallerDb.isPresent()) {
            throw new WalletDataAlreadyExistException("CPF/CNPJ or email already exists");
        }
        return walletRepository.save(dto.toEntity());
    }
}
