import Entities.Estoque;


import java.util.Scanner;

import static Entities.Estoque.*;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Estoque estoque = new Estoque();

        while (true) {
            // Exibe o menu de escolhas
            System.out.println("\nGerenciamento de Estoque");
            System.out.println("1: Cadastrar Produto");
            System.out.println("2: Realizar Venda");
            System.out.println("3: Listar Produtos");
            System.out.println("4: Listar Produtos com Estoque Baixo");
            System.out.println("5: Buscar Produto");
            System.out.println("6: Calcular Valor Total do Estoque");
            System.out.println("0: Sair");
            System.out.print("Informe oque deseja:  ");

            int opcao = scanner.nextInt();
            scanner.nextLine();
            // Executa a opção escolhida pelo usuário
            switch (opcao) {
                case 1:
                    Estoque.cadastrarProduto(estoque, scanner);
                    break;
                case 2:
                    Estoque.realizarVenda(scanner);
                    break;
                case 3:
                    estoque.listarProdutos();
                    break;
                case 4:
                    listarProdutosEstoqueBaixo(estoque, scanner);
                    break;
                case 5:
                    buscarProduto(scanner);
                    break;
                case 6:
                    double valorTotalEstoque = estoque.calcularValorTotalEstoque();
                    System.out.println("Valor total do estoque: R$" + String.format("%.2f",valorTotalEstoque));
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