package com.sander.picpay_simplificado.service;

import com.sander.picpay_simplificado.dto.AuthorizaData;
import com.sander.picpay_simplificado.dto.AuthorizeDto;
import com.sander.picpay_simplificado.dto.TransFerDto;
import com.sander.picpay_simplificado.entity.Transfer;
import com.sander.picpay_simplificado.entity.TypeUser;
import com.sander.picpay_simplificado.entity.Wallet;
import com.sander.picpay_simplificado.exception.NotificationException;
import com.sander.picpay_simplificado.exception.ValidationException;
import com.sander.picpay_simplificado.repository.TransferRepository;
import com.sander.picpay_simplificado.repository.WalletRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.util.Assert;

import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TransferSeviceTest {
    @InjectMocks
    private TransferSevice service;
    @Mock
    private Wallet wallet;
    @Mock
    private TransferRepository transferRepository;
    @Mock
    private WalletRepository walletRepository;
    @Mock
    private TransFerDto dto;
    @Mock
    private Transfer transfer;
    @Mock
    private ValidationTransfer validationTransfer;
    @Mock
    private AuthorizationService authorization;
    @Mock
    private AuthorizeDto authorizeDto;
    @Mock
    private NotificationService notificationService;

    @Test
    @DisplayName("Deveria salvar Transferencia com todos campos validos")
    void transferSave() {
        String message = "Notificação enviada com sucesso!";
        TransFerDto dto = new TransFerDto(new BigDecimal(100.0), 1l, 2l);

        Wallet payerWallet = new Wallet("Joao", "2323", "@gmail.com", new BigDecimal(1000.0), TypeUser.Merchant);
        Wallet payeeWallet = new Wallet("Maria", "12323", "maria@gmail.com", new BigDecimal(100), TypeUser.Common);
        AuthorizaData authorizaData = new AuthorizaData(true);
        AuthorizeDto authorizeDto = new AuthorizeDto("success", authorizaData);

        when(authorization.authorizar(payerWallet,dto.value())).thenReturn(authorizeDto);
        authorization.authorizar(payerWallet,dto.value());

        ValidationTransfer validationTransfer = new ValidationTransfer();
        validationTransfer.validate(payerWallet, dto);

        payerWallet.debitWallet(dto.value());
        payeeWallet.credtWallet(dto.value());

        Transfer transfer = new Transfer(dto, payerWallet, payeeWallet);

        walletRepository.save(payerWallet);
        walletRepository.save(payeeWallet);
        transferRepository.save(transfer);

        notificationService.notification(payerWallet,message);

        verify(transferRepository, Mockito.times(1)).save(any(Transfer.class));
        verify(walletRepository, Mockito.times(2)).save(any(Wallet.class));
        verify(notificationService,Mockito.times(1)).notification(any(),eq(message));


        Assertions.assertEquals(new BigDecimal(900), payerWallet.getBalance());
        Assertions.assertEquals(new BigDecimal(200), payeeWallet.getBalance());
        Assertions.assertDoesNotThrow(() -> validationTransfer.validate(payerWallet, dto));

    }

    @Test
    @DisplayName("Validação falhou")
    void transferBadValidation() {

        TransFerDto dto = new TransFerDto(new BigDecimal(100.0), 1l, 2l);

        Wallet payerWallet = new Wallet("Joao", "2323", "@gmail.com", new BigDecimal(1000.0), TypeUser.Merchant);
        Wallet payeeWallet = new Wallet("Maria", "12323", "maria@gmail.com", new BigDecimal(100), TypeUser.Common);


        BDDMockito.doThrow(
                new ValidationException(String.format("Somente usuarios do tipo lojista podem efetuar transferencia"))
        ).when(validationTransfer).validate(payerWallet, dto);

        then(transferRepository).shouldHaveNoInteractions();
        then(walletRepository).shouldHaveNoMoreInteractions();

        Assertions.assertThrows(ValidationException.class, () -> validationTransfer.validate(payerWallet, dto));

    }
}