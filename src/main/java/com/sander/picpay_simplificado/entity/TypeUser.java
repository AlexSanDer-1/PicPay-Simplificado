package com.sander.picpay_simplificado.entity;

public enum TypeUser {
    Merchant(1,"Usúario do tipo comerciante"),
    Common(2,"Usúario comum");

    private final int id;
    private final String message;

     TypeUser(int id,String message){
        this.id = id;
        this.message = message;
    }

}
