package com.jmquijano.ejemplos.models;

import com.jmquijano.ejemplos.exceptions.NotEnoughMoneyException;

import java.math.BigDecimal;

public class Cuenta {

    private String persona;
    private BigDecimal saldo;

    private Banco banco;

    public Cuenta(String persona, BigDecimal saldo) {
        this.persona = persona;
        this.saldo = saldo;
    }

    public String getPersona() {
        return persona;
    }

    public void setPersona(String persona) {
        this.persona = persona;
    }

    public BigDecimal getSaldo() {
        return saldo;
    }

    public void setSaldo(BigDecimal saldo) {
        this.saldo = saldo;
    }

    public Banco getBanco() {
        return banco;
    }

    public void setBanco(Banco banco) {
        this.banco = banco;
    }

    public void debito(BigDecimal ammount) {
        BigDecimal nuevoSaldo = this.saldo.subtract(ammount);
        if (nuevoSaldo.compareTo(BigDecimal.ZERO) < 0) {
            throw new NotEnoughMoneyException("Dinero Insuficiente");
        }

        this.saldo = nuevoSaldo;
    }

    protected void credito(BigDecimal ammount) {
        this.saldo = this.saldo.add(ammount);
    }

    @Override
    public boolean equals(Object obj) {

        if (!(obj instanceof Cuenta)) {
            return false;
        }

        Cuenta c = (Cuenta) obj;

        if (this.persona == null || this.saldo == null) {
            return false;
        }

        return this.persona.equals(c.getPersona()) && this.saldo.equals(c.getSaldo());
    }
}
