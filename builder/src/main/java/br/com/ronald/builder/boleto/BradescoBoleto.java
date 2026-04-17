package br.com.ronald.builder.boleto;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class BradescoBoleto implements Boleto {
    private String sacado;
    private String cedente;
    private double valor;
    private Calendar vencimento;
    private int nossoNumero;

    public BradescoBoleto(String sacado, String cedente, double valor, Calendar vencimento, int nossoNumero) {
        this.sacado = sacado;
        this.cedente = cedente;
        this.valor = valor;
        this.vencimento = vencimento;
        this.nossoNumero = nossoNumero;
    }

    public String getSacado() { return this.sacado; }
    public String getCedente() { return this.cedente; }
    public double getValor() { return this.valor; }
    public Calendar getVencimento() { return this.vencimento; }
    public int getNossoNumero() { return this.nossoNumero; }

    public String getLinhaDigitavel() {
        // 237 é o código do Bradesco
        return "23790.00000 00000.000000 00000.000000 9 12345678901234";
    }

    public String getCodigoDeBarras() {
        return "23791234567890123456789012345678901234567890";
    }

    @Override
    public String toString() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        return "Boleto BRADESCO\nSacado: " + sacado + " | Valor: R$ " + valor;
    }
}