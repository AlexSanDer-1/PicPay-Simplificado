package com.sander.picpay_simplificado.entity;

import com.sander.picpay_simplificado.dto.WalletDto;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.Objects;

@Entity(name = "wallet")
@Table(name = "wallet")

public class Wallet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nameFull;
    @JoinColumn(unique = true)
    private String cpf;
    @JoinColumn(unique = true)
    private String email;
    private BigDecimal balance;
    private TypeUser typeUser;

    public Wallet() {
    }

    public Wallet(String nameFull, String cpf, String email, BigDecimal balance, TypeUser typeUser) {
        this.nameFull = nameFull;
        this.cpf = cpf;
        this.email = email;
        this.typeUser = typeUser;
        this.balance = balance;
    }

    public Wallet(WalletDto dto){
        this.nameFull = dto.nameFull();
        this.cpf = dto.cpf();
        this.email = dto.email();
        this.balance = dto.balance();
        this.typeUser = dto.typeUser();

    }

    public void  debitWallet(BigDecimal value){
       this.balance = balance.subtract(value);
    }
    public void credtWallet(BigDecimal value){
        this.balance = balance.add(value);
    }

    public String getNameFull() {
        return nameFull;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Wallet wallet = (Wallet) object;
        return Objects.equals(balance, wallet.balance);
    }

    @Override
    public int hashCode() {
        return Objects.hash(balance);
    }

    public void setNameFull(String nameFull) {
        this.nameFull = nameFull;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public TypeUser getTypeUser() {
        return typeUser;
    }

    public void setTypeUser(TypeUser typeUser) {
        this.typeUser = typeUser;
    }
}
