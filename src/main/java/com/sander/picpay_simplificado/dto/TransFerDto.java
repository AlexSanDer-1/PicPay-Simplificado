package com.sander.picpay_simplificado.dto;

import com.sander.picpay_simplificado.entity.Wallet;

import java.math.BigDecimal;

public record TransFerDto(BigDecimal value, Long  payer, Long payee) {
}
