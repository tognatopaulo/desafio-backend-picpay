package tech.tognati.picpay.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tech.tognati.picpay.entity.WalletType;

public interface WalletTypeRepository extends JpaRepository<WalletType, Long> {
}
