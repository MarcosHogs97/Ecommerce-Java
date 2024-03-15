package Entities;

import java.util.ArrayList;
import java.util.Scanner;

public class Estoque {
    protected static ArrayList<Produto> produtos = null;

    public ArrayList<Produto> getProdutos() {
        return produtos;
    }

    // Definindo a quantidade mínima como constante de classe
    private static final int quantidadeMinimaEmEstoque = 10;

    // Construtor que inicializa a lista de produtos
    public Estoque() {
        produtos = new ArrayList<>();
    }

    // Método para cadastrar um novo produto no estoque
    public void cadastrarProduto(Produto produto) {
        // Verifica se o produto já está cadastrado pelo código
        if (buscarProduto(produto.getCodigo(), produto.getNome()) == null) {
            produtos.add(produto);
            System.out.println("\nProduto foi cadastrado com sucesso.");
        } else {
            System.out.println("\nProduto já cadastrado!");
        }
    }

    // Método para realizar uma venda e atualizar o estoque
    public static void realizarVenda(String codigoOuNome, int quantidade) {

        Produto produto = buscarProduto(codigoOuNome, "");
        if (produto != null) {
            // Verifica se há estoque suficiente para a venda
            if (produto.getQuantidadeEstoque() >= quantidade) {
                produto.venderProduto(quantidade);
                System.out.println("\nVenda realizada com sucesso.");
                // Verifica se o estoque ficou abaixo do mínimo
                if (produto.getQuantidadeEstoque() <= quantidadeMinimaEmEstoque) {
                    System.out.println("Produto com estoque baixo!");
                }
            } else {
                System.out.println("\nEstoque insuficiente para realizar a venda.");
            }
        } else {
            System.out.println("\nProduto não encontrado.");
        }
    }

    // para listar todos os produtos no estoque
    public void listarProdutos() {
        if (getProdutos().isEmpty()) {
            System.out.println("\nNao a produtos cadastrado");
        } else {
            System.out.println("\nLista de Produtos:");
            for (Produto produto : produtos) {
                System.out.println(produto);
            }
            listarProdutosEstoqueBaixo();
        }
    }

    // Método para listar produtos com estoque abaixo de um valor definido
    public void listarProdutosEstoqueBaixo(int quantidadeMinima) {
        // Variável para controlar se pelo menos um produto com estoque baixo foi encontrado
        boolean encontrouProdutoBaixo = false;
        System.out.println("Produtos com estoque abaixo de " + quantidadeMinima + ":");
        for (Produto produto : produtos) {
            if (produto.getQuantidadeEstoque() <= quantidadeMinima) {
                System.out.println(produto);
                encontrouProdutoBaixo = true; // Indica que pelo menos um produto com estoque baixo foi encontrado
            }
        }
        // Se nenhum produto com estoque baixo foi encontrado, exibe uma mensagem indicando isso
        if (!encontrouProdutoBaixo) {
            System.out.println("\nNenhum produto com estoque baixo.");
        }
    }

    // Método para listar produtos com estoque abaixo da quantidade mínima
    public void listarProdutosEstoqueBaixo() {
        // Variável para controlar se pelo menos um produto com estoque baixo foi encontrado
        boolean encontrouProdutoBaixo = false;

        // verificar quais estão com estoque abaixo da quantidade mínima
        for (Produto produto : produtos) {
            if (produto.getQuantidadeEstoque() < quantidadeMinimaEmEstoque) {
                if (!encontrouProdutoBaixo) {
                    System.out.println("\nPRODUTOS COM ESTOQUE A BAIXO DE " + quantidadeMinimaEmEstoque);
                    encontrouProdutoBaixo = true; // Indica que pelo menos um produto com estoque baixo foi encontrado
                }
                System.out.println(produto);
            }
        }
        // Se nenhum produto com estoque baixo foi encontrado, exibe uma mensagem indicando isso
        if (!encontrouProdutoBaixo) {
            System.out.println("\nNenhum produto com estoque baixo.");
        }
    }

    // Método para buscar um produto pelo código ou nome
    public static Produto buscarProduto(String codigo, String nome) {
        for (Produto produto : produtos) {
            if (produto.getCodigo().equals(codigo) || produto.getNome().startsWith(nome)) {
                return produto;
            }
        }
        return null;
    }

    // Método para calcular o valor total do estoque
    public double calcularValorTotalEstoque() {
        double valorTotal = 0;
        for (Produto produto : produtos) {
            valorTotal += produto.getPreco() * produto.getQuantidadeEstoque();
        }
        return valorTotal;
    }

    // Método para cadastrar um novo produto verificando se o código ja foi cadastrado, se a quantidade e o preço é positivo
    public static void cadastrarProduto(Estoque estoque, Scanner scanner) {

        String nome = cadastrarNome(scanner);

        String codigo = cadastrarCodigo(scanner);

        double valor = solicitarPreco(scanner);

        int quantidadeEstoque = solicitarQuantidade(scanner);

        String fornecedor = cadastrarFornecedor(scanner);

        // Cria um novo produto e o guarda no estoque
        Produto produto = new Produto(nome, codigo, valor, quantidadeEstoque, fornecedor);
        estoque.cadastrarProduto(produto);
    }

    // Método para realizar uma venda validando se o valor informado é menor que a quantidade em estoque e maior que 0
    public static void realizarVenda(Scanner scanner) {
        System.out.print("Digite o código ou nome do produto: ");
        String codigoOuNome = scanner.nextLine();

        Produto produto = buscarProduto(codigoOuNome, codigoOuNome);
        if (produto != null) {
            int quantidade = solicitarQuantidade(scanner);

            if (produto.getQuantidadeEstoque() >= quantidade) {
                realizarVenda(produto.getCodigo(), quantidade);
            } else {
                System.out.println("Estoque insuficiente para realizar a venda.");
            }
        } else {
            System.out.println("Produto não encontrado.");
        }
    }

    // Método para listar produtos com estoque abaixo de um valor mínimo
    public static void listarProdutosEstoqueBaixo(Estoque estoque, Scanner scanner) {
        int quantidadeMinima = solicitarQuantidade(scanner);

        estoque.listarProdutosEstoqueBaixo(quantidadeMinima);
    }

    // Método para buscar um produto pelo código ou nome
    public static void buscarProduto(Scanner scanner) {
        System.out.print("Digite o código ou nome do produto: ");
        String codigoOuNome = scanner.nextLine();

        Produto produtoBuscado = buscarProduto(codigoOuNome, codigoOuNome);
        if (produtoBuscado != null) {
            System.out.println("Produto encontrado: \n" + produtoBuscado);
        } else {
            System.out.println("Produto não encontrado.");
        }
    }

    // Método para cadastrar o nome do produto
    private static String cadastrarNome(Scanner scanner) {
        String nome;
        while (true) {
            System.out.print("Digite o nome do produto: ");
            nome = scanner.nextLine();
            if (nome.isEmpty()) {
                System.out.println("**** ERRO!: INFORME O NOME DO PRODUTO! ****");
            } else {
                break;
            }

        }
        return nome;
    }

    // Método para cadastrar o nome do produto
    private static String cadastrarFornecedor(Scanner scanner) {
        String fornecedor;
        while (true) {
            System.out.print("Digite o nome do fornecedor: ");
            fornecedor = scanner.nextLine();
            if (fornecedor.isEmpty()) {
                System.out.println("**** ERRO!: INFORME O NOME DO FORNECEDOR! ****");
            } else {
                break;
            }

        }
        return fornecedor;
    }

    // Método para buscar um produto pelo código ou nome
    private static String cadastrarCodigo(Scanner scanner) {
        String codigo;
        while (true) {
            System.out.print("Digite o código do produto: ");
            codigo = scanner.nextLine();
            if (codigo.isEmpty()) {
                System.out.println("ERRO!: INFORME O CODIGO DO PRODUTO!");
                continue;
            }
            Produto produtoBuscado = buscarProduto(codigo, codigo);
            if (produtoBuscado != null) {
                System.out.println("Codigo ja Cadastrado: " + produtoBuscado);
            } else {
                break;
            }
        }
        return codigo;
    }

    // Método para solicitar o preço do produto, garantindo que seja um valor positivo
    private static double solicitarPreco(Scanner scanner) {
        double valor;
        while (true) {
            System.out.print("Digite o preço do produto: ");
            String entradaValor = scanner.nextLine();

            if (entradaValor.isEmpty()) {
                System.out.println("ERRO!: INFORME O VALOR DO PRODUTO!");
                continue;
            }
            entradaValor = entradaValor.replace( ',', '.');
            valor = Double.parseDouble(entradaValor);
            if (valor > 0) {
                break;
            } else {
                System.out.println("O preço deve ser um valor positivo.");
            }
        }
        return valor;
    }

    // Método para solicitar a quantidade do produto, garantindo que seja um valor positivo inteiro
    private static int solicitarQuantidade(Scanner scanner) {
        int quantidade;
        while (true) {
            System.out.print("Digite a quantidade do produto: ");
            String entradaQuantidade = scanner.nextLine();

            if (entradaQuantidade.isEmpty()) {
                System.out.println("ERRO!: INFORME A QUANTIDADE DO PRODUTO EM ESTOQUE!");
                continue;
            }
            quantidade = Integer.parseInt(entradaQuantidade);
            if (quantidade > 0) {
                break;
            } else {
                System.out.println("O quantidade deve ser um valor positivo.");
            }
        }
        return quantidade;
    }
}

