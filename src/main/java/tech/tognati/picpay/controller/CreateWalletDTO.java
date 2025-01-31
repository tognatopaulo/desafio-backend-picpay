package tech.tognati.picpay.controller;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import tech.tognati.picpay.entity.Wallet;
import tech.tognati.picpay.entity.WalletType;

public record CreateWalletDTO(
        @NotBlank String fullName,
        @NotBlank String cpfCnpj,
        @NotBlank String email,
        @NotBlank String password,
        @NotNull WalletType.Enum walletType
) {
    public Wallet toEntity() {
        return new Wallet(fullName, cpfCnpj, email, password, walletType.get());
    }
}
