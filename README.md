# 📑 Emissor de Boletos Multi-Banco (Java)

Sistema desktop profissional desenvolvido em Java para emissão de boletos bancários no padrão **Febraban**. O projeto utiliza engenharia de software avançada para permitir a troca dinâmica de layouts entre diferentes instituições financeiras como **Itaú**, **Nubank** e **Bradesco**.

---

## 🚀 Tecnologias e Ferramentas

* **Java 25+**: Utilização das versões mais recentes da linguagem.
* **Java Swing**: Interface Gráfica (GUI) customizada com visual moderno e UX aprimorada.
* **iText PDF (v5.5.13)**: Biblioteca robusta para geração e manipulação de documentos PDF.
* **Maven**: Gerenciamento de dependências e build do projeto.

---

## 🧠 Arquitetura e Design Patterns

O projeto foi estruturado utilizando o **Design Pattern Builder**, garantindo que a complexidade de criação de cada banco seja isolada da interface.

### Principais conceitos aplicados:
* **Builder Pattern**: Separação total entre a lógica de construção do objeto (`Boleto`) e a sua representação final.
* **Polimorfismo Dinâmico**: O `BoletoPdfGenerator` utiliza `instanceof` para identificar o banco em tempo de execução e aplicar automaticamente a identidade visual (cores, logos e instruções).
* **Blindagem de Dados**: Algoritmo que garante que o código de barras (**Intercalado 2 de 5**) possua sempre comprimento par (44 dígitos), evitando exceções comuns de formatação.

---

## 📸 Funcionalidades da Interface

A interface foi projetada para ser intuitiva e completa, organizada em quatro seções principais:
1.  **Dados do Título**: Seleção de banco, valor e ID (Nosso Número).
2.  **Dados do Beneficiário**: Informações de quem emite o boleto (Ex: **RPS Contabilidade**).
3.  **Dados do Pagador**: Cadastro completo do cliente (Nome, CPF/CNPJ e Endereço).
4.  **Instruções de Caixa**: Área para mensagens personalizadas sobre multas e juros.

---

## 📂 Estrutura de Pacotes

```text
src/main/java/br/com/ronald/builder/
│
├── boleto/    # Interfaces e classes concretas dos bancos (Itau, Nubank, etc)
├── builder/   # Lógica do Design Pattern Builder para cada instituição
├── gui/       # Interface gráfica (TelaGeradorBoleto)
└── pdf/       # Motor de renderização de PDF e Código de Barras
