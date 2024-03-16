import Entities.Estoque;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Estoque estoque = new Estoque();

        while (true) {

            int opcao = estoque.escolhaMenu(scanner);
            scanner.nextLine();
            // Executa a opção escolhida pelo usuário
            switch (opcao) {
                case 1:
                    estoque.criarProduto(scanner);
                    break;
                case 2:
                    estoque.realizarVenda(scanner);
                    break;
                case 3:
                    estoque.listarProdutos();
                    break;
                case 4:
                    estoque.listarProdutosEstoqueBaixo(scanner);
                    break;
                case 5:
                    estoque.buscarProduto(scanner);
                    break;
                case 6:
                    double valorTotalEstoque = estoque.calcularValorTotalEstoque();
                    System.out.println("Valor total do estoque: R$" + String.format("%.2f", valorTotalEstoque));
                    break;
                case 0:
                    System.out.println("Saindo do sistema. Até logo!");
                    scanner.close();
                    System.exit(0);
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        }
    }

}
