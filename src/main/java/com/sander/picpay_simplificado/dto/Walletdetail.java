package com.sander.picpay_simplificado.dto;

import com.sander.picpay_simplificado.entity.TypeUser;
import com.sander.picpay_simplificado.entity.Wallet;

public record Walletdetail(Long id,String nameFull, String cpf, String email, TypeUser typeUser) {

   public Walletdetail(Wallet wallet){
         this(wallet.getId(),wallet.getNameFull(), wallet.getCpf(), wallet.getEmail(),wallet.getTypeUser());
   }
}
