package com.sander.picpay_simplificado.service;

import com.sander.picpay_simplificado.dto.TransFerDto;
import com.sander.picpay_simplificado.entity.TypeUser;
import com.sander.picpay_simplificado.entity.Wallet;
import com.sander.picpay_simplificado.exception.ValidationException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
@Service
public class ValidationTransfer {
    public void validate(Wallet wallet, TransFerDto transFerDto){
        if (wallet.getTypeUser() != (TypeUser.Merchant)){
            throw new ValidationException("Somente usuarios do tipo lojista podem efetuar transferencia");
        }
        if (wallet.getBalance().compareTo(BigDecimal.ZERO) <= 0){
           throw new ValidationException("Saldo insuficiente");
        }
        if (transFerDto.value().compareTo(wallet.getBalance()) > 0){
            throw new ValidationException("Valor de transferência é maior que o saldo");
        }
    }
}
