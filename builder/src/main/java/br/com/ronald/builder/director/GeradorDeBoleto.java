package br.com.ronald.builder.director;

import java.util.Calendar;
import br.com.ronald.builder.boleto.Boleto;
import br.com.ronald.builder.builder.BoletoBuilder;

public class GeradorDeBoleto {
    private BoletoBuilder boletoBuilder;

    public GeradorDeBoleto(BoletoBuilder boletoBuilder) {
        this.boletoBuilder = boletoBuilder;
    }

    public Boleto geraBoleto() {
        boletoBuilder.buildSacado("João da Silva");
        boletoBuilder.buildCedente("Empresa XYZ");
        boletoBuilder.buildValor(1500.75);
        Calendar vencimento = Calendar.getInstance();
        vencimento.add(Calendar.DATE, 10);
        boletoBuilder.buildVencimento(vencimento);
        boletoBuilder.buildNossoNumero(123456);
        return boletoBuilder.getBoleto();
    }
}
