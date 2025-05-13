package br.com.ronald.builder.main;

import br.com.ronald.builder.builder.BBBoletoBuilder;
import br.com.ronald.builder.director.GeradorDeBoleto;
import br.com.ronald.builder.boleto.Boleto;
import br.com.ronald.builder.pdf.BoletoPdfGenerator;

public class TestaGeradorDeBoleto {
    public static void main(String[] args) {
        BBBoletoBuilder builder = new BBBoletoBuilder();
        GeradorDeBoleto gerador = new GeradorDeBoleto(builder);
        Boleto boleto = gerador.geraBoleto();

        System.out.println("Dados do Boleto:");
        System.out.println("Sacado: " + boleto.getSacado());
        System.out.println("Cedente: " + boleto.getCedente());
        System.out.println("Valor: " + boleto.getValor());

        String userHome = System.getProperty("user.home");
        String caminho = userHome + "/Downloads/boleto_bb.pdf";

        System.out.println("Gerando PDF em: " + caminho);
        BoletoPdfGenerator.gerarPdf(boleto, caminho);
    }
}
