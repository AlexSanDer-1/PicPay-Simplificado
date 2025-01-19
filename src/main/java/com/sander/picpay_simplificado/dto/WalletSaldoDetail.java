package com.sander.picpay_simplificado.dto;

import com.sander.picpay_simplificado.entity.Transfer;

import java.math.BigDecimal;

public record WalletSaldoDetail(String nameFullPayer, BigDecimal balancePayer, String nameFullPayee, BigDecimal balancePayee){

  public WalletSaldoDetail(Transfer wallet){
        this(wallet.getPayer().getNameFull(),wallet.getPayer().getBalance(),
                wallet.getPayee().getNameFull(),wallet.getPayee().getBalance());
    }
}
