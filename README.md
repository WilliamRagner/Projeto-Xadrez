# Projeto Xadrez

Um simples jogo de xadrez em Java com uma interface gráfica Swing.

## Funcionalidades

- Jogar contra outro jogador localmente.
- Jogar contra um oponente de IA (computador).
- Movimentação de peças de xadrez padrão.
- Detecção de xeque e xeque-mate.

## Como Executar

Este é um projeto Maven. Para compilar e executar:

1.  Compile o projeto:
    ```bash
    mvn clean install
    ```
2.  Execute o arquivo JAR gerado:
    ```bash
    java -jar target/xadrez-1.0.0.jar
    ```
    (Assumindo que o `Main-Class` está definido no `pom.xml` - o que não está no momento, mas é o próximo passo para um projeto maven)
