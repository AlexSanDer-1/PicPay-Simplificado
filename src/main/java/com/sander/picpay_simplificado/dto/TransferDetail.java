package com.sander.picpay_simplificado.dto;

import com.sander.picpay_simplificado.entity.Transfer;

import java.math.BigDecimal;

public record TransferDetail(BigDecimal value,Long payer,Long  payee) {

    public TransferDetail(Transfer transfer){
        this(transfer.getValue(),transfer.getPayer().getId(),transfer.getPayee().getId());
    }
}
