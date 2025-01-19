package com.sander.picpay_simplificado.repository;

import com.sander.picpay_simplificado.entity.Transfer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransferRepository extends JpaRepository<Transfer,Long> {
}
