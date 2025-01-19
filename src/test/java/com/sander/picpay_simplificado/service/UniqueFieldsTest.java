package com.sander.picpay_simplificado.service;

import com.sander.picpay_simplificado.dto.WalletDto;
import com.sander.picpay_simplificado.entity.Wallet;
import com.sander.picpay_simplificado.exception.ValidationException;
import com.sander.picpay_simplificado.repository.WalletRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class UniqueFieldsTest {
    @InjectMocks
    private UniqueFields uniqueFields;
    @Mock
    private WalletRepository repository;
    @Mock
    private WalletDto dto;
    @Mock
    private Wallet wallet;

    @Test
    @DisplayName("Campo Cpf não deve ser repetido")
    void cpfNaoDeveSerRepetido() {

        BDDMockito.given(dto.cpf()).willReturn("12323");
        BDDMockito.given(repository.findByCpf(dto.cpf())).willReturn(Optional.empty());


        Assertions.assertDoesNotThrow(() -> uniqueFields.checkFields(dto));

    }

    @Test
    @DisplayName("Campo cpf  repetido")
    void CpfRepetido() {

        BDDMockito.given(dto.cpf()).willReturn("teste@gmail.com");
        BDDMockito.given(repository.findByCpf(dto.cpf())).willReturn(Optional.of(wallet));


       ValidationException ex = Assertions.assertThrows(ValidationException.class, () -> uniqueFields.checkFields(dto));
       Assertions.assertEquals(String.format("Cpf: '%s' já foi cadastrado",dto.cpf()),ex.getMessage());

    }

    @Test
    @DisplayName("Campo email não deve ser repetido")
    void EmailNaoDeveSerRepetido() {

        BDDMockito.given(dto.cpf()).willReturn("teste@gmail.com");
        BDDMockito.given(repository.findByEmail(dto.email())).willReturn(Optional.empty());


        Assertions.assertDoesNotThrow(() -> uniqueFields.checkFields(dto));

    }

    @Test
    @DisplayName("Campo email  repetido")
    void EmailRepetido() {

        BDDMockito.given(dto.email()).willReturn("teste@gmail.com");
        BDDMockito.given(repository.findByEmail(dto.email())).willReturn(Optional.of(wallet));


        ValidationException ex =   Assertions.assertThrows(ValidationException.class, () -> uniqueFields.checkFields(dto));
        Assertions.assertEquals(String.format("Email: '%s' já foi cadastrado",dto.email()),ex.getMessage());


    }


}