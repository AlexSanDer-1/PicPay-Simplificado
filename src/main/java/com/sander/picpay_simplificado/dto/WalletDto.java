package com.sander.picpay_simplificado.dto;

import com.sander.picpay_simplificado.entity.TypeUser;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record WalletDto(@NotBlank String nameFull,@NotBlank String cpf, @NotBlank String email,@NotNull BigDecimal balance,@NotNull TypeUser typeUser){
}
