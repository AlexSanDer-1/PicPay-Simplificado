package com.sander.picpay_simplificado.service;

import com.sander.picpay_simplificado.dto.WalletDto;
import com.sander.picpay_simplificado.dto.Walletdetail;
import com.sander.picpay_simplificado.entity.Wallet;
import com.sander.picpay_simplificado.repository.WalletRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class WalletService {
    @Autowired
    private WalletRepository repository;
    @Autowired
    private UniqueFields uniqueFields;

    public Wallet registerWallet(WalletDto dto) {

        uniqueFields.checkFields(dto);

        Wallet wallet = new Wallet(dto);

        return repository.save(wallet);
    }
    public Page<Walletdetail>  listWallet(Pageable pageable){
       return  repository.findAll(pageable)
               .map(Walletdetail::new);
    }
}
