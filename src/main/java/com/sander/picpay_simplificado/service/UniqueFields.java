package com.sander.picpay_simplificado.service;

import com.sander.picpay_simplificado.dto.WalletDto;
import com.sander.picpay_simplificado.exception.ValidationException;
import com.sander.picpay_simplificado.repository.WalletRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UniqueFields {
    @Autowired
    private WalletRepository repository;
    public void checkFields(WalletDto dto){
        var cpfExists = repository.findByCpf(dto.cpf());
        var emailExits = repository.findByEmail(dto.email());

        if (cpfExists.isPresent()){
            throw new ValidationException(String.format("Cpf: '%s' já foi cadastrado",dto.cpf()));
        }
        else if (emailExits.isPresent()){
            throw new ValidationException(String.format("Email: '%s' já foi cadastrado",dto.email()));
        }
    }
}
