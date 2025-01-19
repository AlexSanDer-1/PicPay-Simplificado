package com.sander.picpay_simplificado.repository;

import com.sander.picpay_simplificado.dto.TransFerDto;
import com.sander.picpay_simplificado.entity.TypeUser;
import com.sander.picpay_simplificado.entity.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WalletRepository extends JpaRepository<Wallet,Long> {

   Optional<Wallet> findByCpf(String cpf);


    boolean findByTypeUser(TypeUser typeUser);


    Optional<Wallet> findByEmail(String email);
}
