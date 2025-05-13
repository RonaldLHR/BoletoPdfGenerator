package br.com.ronald.builder.boleto;

import java.util.Calendar;

public interface Boleto {
    String getSacado();
    String getCedente();
    double getValor();
    Calendar getVencimento();
    int getNossoNumero();
    String getLinhaDigitavel();
    String getCodigoDeBarras();
    String toString();
}
