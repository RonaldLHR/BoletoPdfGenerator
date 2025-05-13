package br.com.ronald.builder.boleto;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class BBBoleto implements Boleto {
    private String sacado;
    private String cedente;
    private double valor;
    private Calendar vencimento;
    private int nossoNumero;

    public BBBoleto(String sacado, String cedente, double valor, Calendar vencimento, int nossoNumero) {
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
        return "00190.00009 01234.567890 12345.678901 1 12345678901234"; // Exemplo fixo
    }

    public String getCodigoDeBarras() {
        return "00191234567890123456789012345678901234567890"; // Exemplo fixo
    }

    public String toString() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        return "Boleto BB\nSacado: " + sacado +
                "\nCedente: " + cedente +
                "\nValor: " + valor +
                "\nVencimento: " + sdf.format(vencimento.getTime()) +
                "\nNosso Número: " + nossoNumero +
                "\nLinha Digitável: " + getLinhaDigitavel() +
                "\nCódigo de Barras: " + getCodigoDeBarras();
    }
}
