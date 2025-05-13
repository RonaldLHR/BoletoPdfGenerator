package br.com.ronald.builder.pdf;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import java.io.FileOutputStream;
import br.com.ronald.builder.boleto.Boleto;
import java.text.SimpleDateFormat;

public class BoletoPdfGenerator {

    public static void gerarPdf(Boleto boleto, String caminhoArquivo) {
        Document document = new Document();

        try {
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(caminhoArquivo));
            document.open();

            Font fontTitulo = new Font(Font.FontFamily.HELVETICA, 16, Font.BOLD);
            Font fontPadrao = new Font(Font.FontFamily.HELVETICA, 12);

            document.add(new Paragraph("Boleto Bancário", fontTitulo));
            document.add(new Paragraph(" "));

            document.add(new Paragraph("Sacado: " + boleto.getSacado(), fontPadrao));
            document.add(new Paragraph("Cedente: " + boleto.getCedente(), fontPadrao));
            document.add(new Paragraph("Valor: R$ " + boleto.getValor(), fontPadrao));

            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            document.add(new Paragraph("Vencimento: " + sdf.format(boleto.getVencimento().getTime()), fontPadrao));
            document.add(new Paragraph("Nosso Número: " + boleto.getNossoNumero(), fontPadrao));
            document.add(new Paragraph("Linha Digitável: " + boleto.getLinhaDigitavel(), fontPadrao));
            document.add(new Paragraph("Código de Barras (numérico): " + boleto.getCodigoDeBarras(), fontPadrao));

            document.add(new Paragraph(" ")); // espaço

            // Gerar imagem do código de barras
            BarcodeInter25 barcode = new BarcodeInter25();
            barcode.setCode(boleto.getCodigoDeBarras());
            barcode.setBarHeight(40f); // altura das barras
            barcode.setX(1.2f); // largura das barras
            barcode.setChecksumText(true); // exibe o código abaixo das barras

            Image barcodeImage = barcode.createImageWithBarcode(writer.getDirectContent(), null, null);
            barcodeImage.setAlignment(Image.ALIGN_CENTER);
            document.add(barcodeImage);

            document.close();
            System.out.println("✅ PDF gerado com sucesso em: " + caminhoArquivo);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
