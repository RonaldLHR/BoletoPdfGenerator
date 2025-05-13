VIDEO: https://youtu.be/RCL9kKi-cWk
🧾 Gerador de Boleto Bancário em PDF - Java (Builder Pattern)
Este projeto demonstra a aplicação do padrão de projeto Builder para gerar boletos bancários personalizados. Ao final da execução, o sistema cria um arquivo PDF do boleto com as informações definidas.

🔧 Padrões e Conceitos Usados
✅ Builder Pattern: separa a construção de um objeto complexo da sua representação.

📦 Organização modular por pacotes (builder, boleto, pdf, director, main).

🖨️ Geração de PDF com bibliotecas Java (ex: iText ou similar — especifique se estiver usando alguma).

📂 Estrutura do Projeto
bash
Copiar
Editar
src/
├── br/com/ronald/builder/
│   ├── main/               # Classe de execução (TestaGeradorDeBoleto)
│   ├── builder/            # Builders para diferentes tipos de boletos (ex: BBBoletoBuilder)
│   ├── director/           # GeradorDeBoleto: controla o processo de criação
│   ├── boleto/             # Classe Boleto e atributos relacionados
│   └── pdf/                # Geração de PDF (BoletoPdfGenerator)

VIDEO: https://youtu.be/RCL9kKi-cWk



🚀 Como Executar
Clone o repositório:

bash
Copiar
Editar
git clone https://github.com/RonaldLHR/BoletoPdfGenerator/tree/master
Abra o projeto em sua IDE (IntelliJ, Eclipse etc.).

Execute a classe:

Copiar
Editar
br.com.ronald.builder.main.TestaGeradorDeBoleto
O console exibirá os dados do boleto e informará o caminho de salvamento do PDF:

bash
Copiar
Editar
Gerando PDF em: /home/seu_usuario/Downloads/boleto_bb.pdf
📌 Exemplo de Saída
yaml
Copiar
Editar
Dados do Boleto:
Sacado: João da Silva
Cedente: Empresa XYZ LTDA
Valor: R$ 150.00
Gerando PDF em: /home/ronald/Downloads/boleto_bb.pdf
O arquivo PDF gerado será salvo na pasta Downloads do usuário.

🛠️ Requisitos
Java 8 ou superior
Biblioteca externa para geração de PDF, como iText.

✍️ Autor
Ronald Xavier Queiroz
GitHub
VIDEO: https://youtu.be/RCL9kKi-cWk
