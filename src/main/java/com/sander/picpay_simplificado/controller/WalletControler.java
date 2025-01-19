package com.sander.picpay_simplificado.controller;

import com.sander.picpay_simplificado.dto.WalletDto;
import com.sander.picpay_simplificado.dto.Walletdetail;
import com.sander.picpay_simplificado.service.WalletService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/register")
public class WalletControler {
    @Autowired
    private WalletService walletService;

    @PostMapping
    @Transactional
    public ResponseEntity registerWallet(@RequestBody @Valid WalletDto dto, UriComponentsBuilder uriBuilder) {

        var wallet = walletService.registerWallet(dto);

        var uri = uriBuilder.path("/wallet/{id}").buildAndExpand(wallet.getId()).toUri();

        return ResponseEntity.created(uri).body(new Walletdetail(wallet));

    }
    @GetMapping
    @Transactional
    public ResponseEntity<Page<Walletdetail>> listWallet(Pageable pageable){

        var page = walletService.listWallet(pageable);
        return  ResponseEntity.ok(page);
    }
}
