package com.sander.picpay_simplificado.service;

import com.sander.picpay_simplificado.dto.TransFerDto;
import com.sander.picpay_simplificado.entity.Transfer;
import com.sander.picpay_simplificado.exception.ValidationException;
import com.sander.picpay_simplificado.repository.TransferRepository;
import com.sander.picpay_simplificado.repository.WalletRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TransferSevice {
    @Autowired
    private WalletRepository walletRepository;
    @Autowired
    private TransferRepository transferRepository;
    @Autowired
    private ValidationTransfer validationWallet;
    @Autowired
    private AuthorizationService authorization;
    @Autowired
    private NotificationService notificationService;

    @Transactional
    public synchronized Transfer transfer(TransFerDto transFerDto) {

        var payer = walletRepository.findById(transFerDto.payer())
                .orElseThrow(() -> new ValidationException("Usuario tipo lojista não existe"));

        var payee = walletRepository.findById(transFerDto.payee())
                .orElseThrow(() -> new ValidationException("Usuario tipo comum não existe"));

        validationWallet.validate(payer,transFerDto);
        authorization.authorizar(payer, transFerDto.value());

        payer.debitWallet(transFerDto.value());
        payee.credtWallet(transFerDto.value());
        Transfer transfer = new Transfer(transFerDto,payer,payee);

        walletRepository.save(payee);
        walletRepository.save(payer);

        notificationService.notification(payer,"Transação realizada com sucesso");
        notificationService.notification(payee,"Transação recebida com sucesso");

        return transferRepository.save(transfer);

    }

}
