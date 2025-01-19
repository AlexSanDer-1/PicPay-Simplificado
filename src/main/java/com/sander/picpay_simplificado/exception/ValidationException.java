package com.sander.picpay_simplificado.exception;

public class ValidationException extends RuntimeException {

   public ValidationException (String mensagem) {
       super(mensagem);
   }

}
