package br.com.ronald.builder.boleto;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class ItauBoleto implements Boleto {
    private String sacado, cedente;
    private double valor;
    private Calendar vencimento;
    private int nossoNumero;

    public ItauBoleto(String sacado, String cedente, double valor, Calendar vencimento, int nossoNumero) {
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
        String banco = "341";
        String moeda = "9";
        String fixoVenc = "9999";
        String valorFmt = String.format("%010d", (int)(this.valor * 100));

        // Campo livre: 25 dígitos para fechar 44 totais (3+1+4+10 + 26 = 44)
        // Usamos 10 do nosso número + 16 zeros
        String campoLivre = String.format("%010d", this.nossoNumero) + "0000000000000000";

        return banco + moeda + fixoVenc + valorFmt + campoLivre;
    }

    @Override
    public String getLinhaDigitavel() {
        String cb = getCodigoDeBarras();
        // Formatação padrão: 00000.00000 00000.000000 00000.000000 0 00000000000000
        return cb.substring(0, 5) + "." + cb.substring(5, 10) + " " +
                cb.substring(10, 15) + "." + cb.substring(15, 21) + " " +
                cb.substring(21, 26) + "." + cb.substring(26, 32) + " 1 " +
                cb.substring(32);
    }
}