package br.com.ronald.builder.gui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Calendar;
import br.com.ronald.builder.builder.*;
import br.com.ronald.builder.boleto.Boleto;
import br.com.ronald.builder.pdf.BoletoPdfGenerator;

public class TelaGeradorBoleto extends JFrame {

    // Componentes de entrada
    private JTextField txtSacado, txtDocSacado, txtEndereco, txtCedente, txtDocCedente, txtValor, txtNossoNumero;
    private JTextArea txtInstrucoes;
    private JComboBox<String> comboBancos;
    private JButton btnGerar;

    // Cores do Tema
    private final Color COR_FUNDO = new Color(245, 245, 247); // Cinza claro estilo Mac
    private final Color COR_BOTAO = new Color(39, 174, 96);   // Verde Esmeralda moderno
    private final Color COR_TEXTO_BOTAO = Color.WHITE;

    public TelaGeradorBoleto() {
        // Configurações Básicas
        setTitle("Emissor de Boletos Profissional - Ronald Xavier");
        setSize(650, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);
        getContentPane().setBackground(COR_FUNDO);

        // Painel Principal com Padding generoso
        JPanel painelPrincipal = new JPanel();
        painelPrincipal.setLayout(new BoxLayout(painelPrincipal, BoxLayout.Y_AXIS));
        painelPrincipal.setBackground(COR_FUNDO);
        painelPrincipal.setBorder(new EmptyBorder(30, 30, 30, 30));

        // --- SEÇÃO 1: DADOS DO TÍTULO ---
        JPanel pnlTitulo = criarPainelSecao("1. Dados do Banco e Valor");

        comboBancos = new JComboBox<>(new String[]{"Itaú", "Nubank", "Bradesco"});
        comboBancos.setFont(new Font("Arial", Font.PLAIN, 13));
        adicionarCampoForm(pnlTitulo, "Banco:", comboBancos);

        txtValor = criarTextField();
        adicionarCampoForm(pnlTitulo, "Valor (R$):", txtValor);

        txtNossoNumero = criarTextField();
        adicionarCampoForm(pnlTitulo, "Nosso Número:", txtNossoNumero);

        painelPrincipal.add(pnlTitulo);
        painelPrincipal.add(Box.createVerticalStrut(20)); // Espaçador

        // --- SEÇÃO 2: DADOS DO BENEFICIÁRIO ---
        JPanel pnlCedente = criarPainelSecao("2. Sua Empresa (Beneficiário)");

        txtCedente = criarTextField();
        txtCedente.setText("RPS Contabilidade"); // Valor padrão
        adicionarCampoForm(pnlCedente, "Razão Social:", txtCedente);

        txtDocCedente = criarTextField();
        txtDocCedente.setText("00.000.000/0001-00"); // Valor padrão
        adicionarCampoForm(pnlCedente, "CNPJ:", txtDocCedente);

        painelPrincipal.add(pnlCedente);
        painelPrincipal.add(Box.createVerticalStrut(20)); // Espaçador

        // --- SEÇÃO 3: DADOS DO PAGADOR ---
        JPanel pnlSacado = criarPainelSecao("3. Dados do Cliente (Pagador)");

        txtSacado = criarTextField();
        adicionarCampoForm(pnlSacado, "Nome/Razão Social:", txtSacado);

        txtDocSacado = criarTextField();
        adicionarCampoForm(pnlSacado, "CPF/CNPJ:", txtDocSacado);

        txtEndereco = criarTextField();
        adicionarCampoForm(pnlSacado, "Endereço Completo:", txtEndereco);

        painelPrincipal.add(pnlSacado);
        painelPrincipal.add(Box.createVerticalStrut(20)); // Espaçador

        // --- SEÇÃO 4: INSTRUÇÕES ---
        JPanel pnlInstrucoes = criarPainelSecao("4. Instruções do Caixa");
        pnlInstrucoes.setLayout(new BorderLayout());

        txtInstrucoes = new JTextArea(4, 20);
        txtInstrucoes.setFont(new Font("Consolas", Font.PLAIN, 12)); // Fonte monoespaçada para instruções
        txtInstrucoes.setText("Não aceitar após o vencimento.\nCobrar juros de 1% ao mês.");
        txtInstrucoes.setLineWrap(true);
        txtInstrucoes.setWrapStyleWord(true);
        txtInstrucoes.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        JScrollPane scrollInstrucoes = new JScrollPane(txtInstrucoes);
        scrollInstrucoes.setBorder(BorderFactory.createLineBorder(new Color(210, 210, 210)));
        pnlInstrucoes.add(scrollInstrucoes, BorderLayout.CENTER);

        painelPrincipal.add(pnlInstrucoes);

        // --- BOTÃO GERAR ---
        JPanel pnlBotao = new JPanel(new BorderLayout());
        pnlBotao.setBackground(COR_FUNDO);
        pnlBotao.setBorder(new EmptyBorder(20, 30, 30, 30));

        btnGerar = new JButton("GERAR BOLETO COMPLETO");
        btnGerar.setFont(new Font("Arial", Font.BOLD, 16));
        btnGerar.setBackground(COR_BOTAO);
        btnGerar.setForeground(COR_TEXTO_BOTAO);
        btnGerar.setOpaque(true);
        btnGerar.setBorderPainted(false); // Remove a borda padrão feia
        btnGerar.setFocusPainted(false);   // Remove o retângulo de foco
        btnGerar.setPreferredSize(new Dimension(0, 55));
        btnGerar.setCursor(new Cursor(Cursor.HAND_CURSOR)); // Mãozinha ao passar o mouse

        btnGerar.addActionListener(this::gerar);
        pnlBotao.add(btnGerar, BorderLayout.CENTER);

        // Adicionando tudo à janela
        JScrollPane scrollPrincipal = new JScrollPane(painelPrincipal);
        scrollPrincipal.setBorder(null);
        add(scrollPrincipal, BorderLayout.CENTER);
        add(pnlBotao, BorderLayout.SOUTH);
    }

    // --- MÉTODOS AUXILIARES DE ESTILIZAÇÃO ---

    private JPanel criarPainelSecao(String titulo) {
        JPanel pnl = new JPanel(new GridLayout(0, 2, 15, 15));
        pnl.setBackground(Color.WHITE); // Fundo branco nas seções
        pnl.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(230, 230, 230), 1), // Borda externa sutil
                new EmptyBorder(15, 20, 15, 20) // Padding interno
        ));

        // Título da seção em negrito e azul escuro
        TitledBorder tb = BorderFactory.createTitledBorder(
                BorderFactory.createEmptyBorder(), // Borda vazia para o titledborder não desenhar linha
                titulo,
                TitledBorder.LEFT,
                TitledBorder.TOP,
                new Font("Arial", Font.BOLD, 14),
                new Color(44, 62, 80)
        );
        pnl.setBorder(BorderFactory.createCompoundBorder(tb, pnl.getBorder()));

        return pnl;
    }

    private JTextField criarTextField() {
        JTextField tf = new JTextField();
        tf.setFont(new Font("Arial", Font.PLAIN, 13));
        tf.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(210, 210, 210)), // Borda cinza claro
                BorderFactory.createEmptyBorder(5, 8, 5, 8) // Padding interno do texto
        ));
        return tf;
    }

    private void adicionarCampoForm(JPanel painel, String label, Component componente) {
        JLabel lbl = new JLabel(label);
        lbl.setFont(new Font("Arial", Font.PLAIN, 13));
        lbl.setForeground(new Color(70, 70, 70));
        painel.add(lbl);
        painel.add(componente);
    }

    private void gerar(ActionEvent e) {
        // Lógica de geração permanece a mesma, só mudou o visual
        try {
            BoletoBuilder builder;
            String sel = (String) comboBancos.getSelectedItem();

            if (sel.equals("Itaú")) builder = new ItauBoletoBuilder();
            else if (sel.equals("Nubank")) builder = new NubankBoletoBuilder();
            else builder = new BradescoBoletoBuilder();

            builder.buildSacado(txtSacado.getText());
            builder.buildCedente(txtCedente.getText());
            builder.buildValor(Double.parseDouble(txtValor.getText().replace(",", ".")));
            builder.buildNossoNumero(Integer.parseInt(txtNossoNumero.getText()));

            Calendar v = Calendar.getInstance();
            v.add(Calendar.DATE, 10);
            builder.buildVencimento(v);

            Boleto b = builder.getBoleto();
            String path = System.getProperty("user.home") + "/Downloads/boleto_" + sel.toLowerCase() + ".pdf";

            BoletoPdfGenerator.gerarPdf(b, path);

            JOptionPane.showMessageDialog(this, "✅ Boleto Completo Gerado em Downloads!");

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro: " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        // Tenta usar o visual do Windows/Sistema
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            // Pequeno ajuste para garantir que os campos fiquem bonitos no Windows
            UIManager.put("TextField.border", BorderFactory.createEmptyBorder());
        } catch (Exception ignored) {}

        SwingUtilities.invokeLater(() -> new TelaGeradorBoleto().setVisible(true));
    }
}