package com.sander.picpay_simplificado.service;

import com.sander.picpay_simplificado.dto.WalletDto;
import com.sander.picpay_simplificado.entity.TypeUser;
import com.sander.picpay_simplificado.entity.Wallet;
import com.sander.picpay_simplificado.exception.ValidationException;
import com.sander.picpay_simplificado.repository.WalletRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

@ExtendWith(MockitoExtension.class)
class WalletServiceTest {
    @Mock
    private WalletRepository repository;
    @Mock
    private UniqueFields uniqueFields;
    @Mock
    private WalletDto dto;
    @Mock
    private Wallet wallet;


    @Test
    @DisplayName("Campos mapeados corretamente")
    void registerWalletSuccess() {
        WalletDto dto = new WalletDto(
                "Alexsander",
                "12323",
                "ander@gmail.com",
                new BigDecimal(1000.0), TypeUser.Merchant);

        Wallet wallet = new Wallet(dto);


        Assertions.assertEquals(wallet.getNameFull(), dto.nameFull());
        Assertions.assertEquals(wallet.getCpf(), dto.cpf());
        Assertions.assertEquals(wallet.getEmail(),dto.email());
        Assertions.assertEquals(wallet.getBalance(),dto.balance());
        Assertions.assertEquals(wallet.getTypeUser(),dto.typeUser());
    }
    @Test
    @DisplayName("Campo repetido no mapeamento")
    void registerWalletErrorValidation() {
        WalletDto dto = new WalletDto(
                "Alexsander",
                "12323",
                "ander@gmail.com",
                new BigDecimal(1000.0), TypeUser.Merchant);

        Wallet wallet = new Wallet(dto);

        BDDMockito.doThrow(
                new ValidationException(String.format("Cpf: '%s' já foi cadastrado",dto.cpf())))
                .when(uniqueFields).checkFields(dto);

        ValidationException ex = Assertions.assertThrows(ValidationException.class, () -> uniqueFields.checkFields(dto));

        Assertions.assertEquals(wallet.getNameFull(), dto.nameFull());
        Assertions.assertEquals(wallet.getCpf(), dto.cpf());
        Assertions.assertEquals(wallet.getEmail(),dto.email());
        Assertions.assertEquals(wallet.getBalance(),dto.balance());
        Assertions.assertEquals(wallet.getTypeUser(),dto.typeUser());

        Mockito.verify(uniqueFields).checkFields(dto);
    }

}