package com.sander.picpay_simplificado.entity;

import com.sander.picpay_simplificado.dto.TransFerDto;
import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity(name = "transfer")
@Table(name = "transfers")
public class Transfer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private BigDecimal value;
    @ManyToOne
    @JoinColumn(name = "payer_wallet_id")
    private Wallet payer;
    @ManyToOne
    @JoinColumn(name = "payee_wallet_id")
    private Wallet payee;


    public Transfer(TransFerDto transFerDto,Wallet payerWallet,Wallet payeeWallet){
        this.value = transFerDto.value();
        this.payer = payerWallet;
        this.payee = payeeWallet;
    }
    public Transfer() {
    }

    public Transfer(BigDecimal value, Wallet payer, Wallet payee) {
        this.value = value;
        this.payer = payer;
        this.payee = payee;
    }




    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public Wallet getPayer() {
        return payer;
    }

    public void setPayer(Wallet payer) {
        this.payer = payer;
    }

    public Wallet getPayee() {
        return payee;
    }

    public void setPayee(Wallet payee) {
        this.payee = payee;
    }
}
