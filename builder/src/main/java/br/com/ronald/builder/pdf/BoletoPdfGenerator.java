package br.com.ronald.builder.pdf;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import br.com.ronald.builder.boleto.*; // Importa todas as classes de boleto

public class BoletoPdfGenerator {

    public static void gerarPdf(Boleto boleto, String caminhoArquivo) {
        Document document = new Document(PageSize.A4, 30, 30, 30, 30);

        try (FileOutputStream fos = new FileOutputStream(caminhoArquivo)) {
            PdfWriter writer = PdfWriter.getInstance(document, fos);
            document.open();

            // --- LÓGICA DE SELEÇÃO VISUAL ---
            BaseColor corBanco = new BaseColor(255, 100, 0); // Padrão Itaú
            String textoLogo = "Itaú";
            String codCompensacao = "341-7";

            if (boleto instanceof NubankBoleto) {
                corBanco = new BaseColor(138, 43, 226); // Roxo Nubank
                textoLogo = "nu";
                codCompensacao = "260-7";
            } else if (boleto instanceof BradescoBoleto) {
                corBanco = new BaseColor(200, 0, 0); // Vermelho Bradesco
                textoLogo = "Bradesco";
                codCompensacao = "237-2";
            }

            Font fontLabel = new Font(Font.FontFamily.HELVETICA, 6, Font.NORMAL);
            Font fontDado = new Font(Font.FontFamily.HELVETICA, 9, Font.BOLD);
            Font fontBranca = new Font(Font.FontFamily.HELVETICA, 14, Font.BOLD, BaseColor.WHITE);

            // --- CABEÇALHO ---
            PdfPTable header = new PdfPTable(3);
            header.setWidthPercentage(100);
            header.setWidths(new float[]{1.5f, 0.7f, 4.5f});

            PdfPCell cellLogo = new PdfPCell(new Phrase(textoLogo, fontBranca));
            cellLogo.setBackgroundColor(corBanco);
            cellLogo.setHorizontalAlignment(Element.ALIGN_CENTER);
            header.addCell(cellLogo);

            header.addCell(new PdfPCell(new Phrase(codCompensacao, fontDado)));

            PdfPCell cellLinha = new PdfPCell(new Phrase(boleto.getLinhaDigitavel(), new Font(Font.FontFamily.HELVETICA, 11, Font.BOLD)));
            cellLinha.setBorder(Rectangle.NO_BORDER);
            cellLinha.setHorizontalAlignment(Element.ALIGN_RIGHT);
            header.addCell(cellLinha);

            document.add(header);

            // --- CORPO (LAYOUT FEBRABAN) ---
            PdfPTable corpo = new PdfPTable(6);
            corpo.setWidthPercentage(100);
            corpo.setSpacingBefore(5);

            addCell(corpo, "Local de Pagamento", "Em qualquer banco até o vencimento", fontLabel, fontDado, 5);
            addCell(corpo, "Vencimento", new SimpleDateFormat("dd/MM/yyyy").format(boleto.getVencimento().getTime()), fontLabel, fontDado, 1);

            addCell(corpo, "Beneficiário", boleto.getCedente(), fontLabel, fontDado, 5);
            addCell(corpo, "Agência / Código Beneficiário", "0001 / 1234567-8", fontLabel, fontDado, 1);

            addCell(corpo, "Data Documento", new SimpleDateFormat("dd/MM/yyyy").format(Calendar.getInstance().getTime()), fontLabel, fontDado, 1);
            addCell(corpo, "Nº Documento", "REQ-" + boleto.getNossoNumero(), fontLabel, fontDado, 1);
            addCell(corpo, "Espécie Doc.", "DV", fontLabel, fontDado, 1);
            addCell(corpo, "Aceite", "N", fontLabel, fontDado, 1);
            addCell(corpo, "Data Processamento", new SimpleDateFormat("dd/MM/yyyy").format(Calendar.getInstance().getTime()), fontLabel, fontDado, 1);
            addCell(corpo, "Nosso Número", String.format("%011d", boleto.getNossoNumero()), fontLabel, fontDado, 1);

            addCell(corpo, "Uso do Banco", "", fontLabel, fontDado, 1);
            addCell(corpo, "Carteira", "00", fontLabel, fontDado, 1);
            addCell(corpo, "Espécie Moeda", "R$", fontLabel, fontDado, 1);
            addCell(corpo, "Quantidade", "", fontLabel, fontDado, 1);
            addCell(corpo, "Valor Moeda", "", fontLabel, fontDado, 1);
            addCell(corpo, "(=) Valor do Documento", "R$ " + String.format("%.2f", boleto.getValor()), fontLabel, fontDado, 1);

            PdfPCell cellInstr = new PdfPCell(new Phrase("Instruções: Não aceitar pagamento em cheque. Após o vencimento, cobrar multa de 2% e juros de 1% ao mês.", fontLabel));
            cellInstr.setColspan(5);
            cellInstr.setMinimumHeight(50);
            corpo.addCell(cellInstr);
            addCell(corpo, "(-) Descontos", "0,00", fontLabel, fontDado, 1);

            document.add(corpo);

            // --- PAGADOR ---
            document.add(new Paragraph(" "));
            PdfPTable pagadorTable = new PdfPTable(1);
            pagadorTable.setWidthPercentage(100);
            PdfPCell cellPag = new PdfPCell(new Phrase("Pagador: " + boleto.getSacado() + "\nEndereço: Rua do Cliente, 123 - Centro, Eunápolis-BA", fontDado));
            cellPag.setPadding(8);
            pagadorTable.addCell(cellPag);
            document.add(pagadorTable);

            // --- CÓDIGO DE BARRAS ---
            document.add(new Paragraph(" "));
            String codigoLimpo = boleto.getCodigoDeBarras().replaceAll("\\D", "");
            if (codigoLimpo.length() % 2 != 0) codigoLimpo = "0" + codigoLimpo;

            BarcodeInter25 code25 = new BarcodeInter25();
            code25.setGenerateChecksum(false);
            code25.setCode(codigoLimpo);
            code25.setBarHeight(45f);
            code25.setX(1.1f);

            Image img = code25.createImageWithBarcode(writer.getDirectContent(), null, null);
            img.setAlignment(Element.ALIGN_LEFT);
            document.add(img);

            document.add(new Paragraph("FICHA DE COMPENSAÇÃO", fontLabel));

            document.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void addCell(PdfPTable table, String label, String value, Font fLabel, Font fDado, int colspan) {
        Paragraph p = new Paragraph();
        p.add(new Phrase(label + "\n", fLabel));
        p.add(new Phrase(value, fDado));
        PdfPCell cell = new PdfPCell(p);
        cell.setColspan(colspan);
        cell.setPadding(3);
        table.addCell(cell);
    }
}