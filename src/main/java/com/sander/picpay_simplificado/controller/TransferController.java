package com.sander.picpay_simplificado.controller;

import com.sander.picpay_simplificado.dto.TransFerDto;
import com.sander.picpay_simplificado.dto.WalletSaldoDetail;
import com.sander.picpay_simplificado.service.TransferSevice;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/transfer")
public class TransferController {
    @Autowired
    private TransferSevice transferSevice;
    @PostMapping
    public ResponseEntity transfer(@RequestBody @Valid TransFerDto dto, UriComponentsBuilder uriBuilder) {
        var transfer = transferSevice.transfer(dto);
        var uri = uriBuilder.path("/transfer/{id}").buildAndExpand(transfer.getId()).toUri();

        return ResponseEntity.created(uri).body(new WalletSaldoDetail(transfer));
    }
}
