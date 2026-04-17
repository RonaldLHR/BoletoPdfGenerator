package br.com.ronald.builder.boleto;

import java.util.Calendar;

public class NubankBoleto implements Boleto {
    private String sacado, cedente;
    private double valor;
    private Calendar vencimento;
    private int nossoNumero;

    public NubankBoleto(String sacado, String cedente, double valor, Calendar vencimento, int nossoNumero) {
        this.sacado = sacado;
        this.cedente = cedente;
        this.valor = valor;
        this.vencimento = vencimento;
        this.nossoNumero = nossoNumero;
    }

    public String getSacado() { return sacado; }
    public String getCedente() { return cedente; }
    public double getValor() { return valor; }
    public Calendar getVencimento() { return vencimento; }
    public int getNossoNumero() { return nossoNumero; }

    @Override
    public String getCodigoDeBarras() {
        String banco = "260"; // Nubank
        String moeda = "9";
        String fatorVenc = "9999";
        String valorFmt = String.format("%010d", (int)(this.valor * 100));
        String campoLivre = String.format("%010d", this.nossoNumero) + "0000000000000000";
        return banco + moeda + fatorVenc + valorFmt + campoLivre;
    }

    @Override
    public String getLinhaDigitavel() {
        String cb = getCodigoDeBarras();
        return cb.substring(0, 5) + "." + cb.substring(5, 10) + " " +
                cb.substring(10, 15) + "." + cb.substring(15, 21) + " " +
                cb.substring(21, 26) + "." + cb.substring(26, 32) + " 1 " +
                cb.substring(32);
    }
}