package com.sander.picpay_simplificado.service;

import com.sander.picpay_simplificado.dto.TransFerDto;
import com.sander.picpay_simplificado.entity.TypeUser;
import com.sander.picpay_simplificado.entity.Wallet;
import com.sander.picpay_simplificado.exception.ValidationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mock;

import java.math.BigDecimal;

import static org.mockito.Mockito.when;

class ValidationTransferTest {

    @Mock
    private Wallet wallet;
    @Mock
    private TransFerDto dto;
    @Mock
    private ValidationTransfer validationTransfer;


    @Test
    @DisplayName("Usuario deve ser tipo lojista")
    public void validateBad() {

        TransFerDto dto = new TransFerDto(new BigDecimal(100.0), 1l, 2l);

        Wallet wallet = new Wallet(
                "Joao",
                "2323",
                "@gmail.com",
                new BigDecimal(1000.0),
                TypeUser.Merchant);

        ValidationTransfer validationTransfer = new ValidationTransfer();


        Assertions.assertDoesNotThrow(() -> validationTransfer.validate(wallet,dto));

    }
    @Test
    @DisplayName("Usuario  tipo comum não deve passar")
    public void validateSuccess() {

        TransFerDto dto = new TransFerDto(new BigDecimal(100.0), 1l, 2l);

        Wallet wallet = new Wallet(
                "Joao",
                "2323",
                "@gmail.com",
                new BigDecimal(1000.0),
                TypeUser.Common);

        ValidationTransfer validationTransfer = new ValidationTransfer();


        Assertions.assertThrows(ValidationException.class,() -> validationTransfer.validate(wallet,dto));

    }
}