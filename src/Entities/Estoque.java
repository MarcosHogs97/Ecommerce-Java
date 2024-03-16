package Entities;

import java.util.ArrayList;
import java.util.Scanner;

public class Estoque {
    protected ArrayList<Produto> produtos;

    protected int quantidadeMinimaEmEstoque;

    // No construtor, inicialize a lista de produtos
    public Estoque() {
        this.produtos = new ArrayList<>();
        this.quantidadeMinimaEmEstoque = 10;
    }

    public ArrayList<Produto> getProdutos() {
        return produtos;
    }

    public void criarProduto(Scanner scanner) {
        String nome = cadastrarNome(scanner);
        String codigo = cadastrarCodigo(scanner);
        double valor = solicitarPreco(scanner);
        int quantidadeEstoque = solicitarQuantidade(scanner);
        String fornecedor = cadastrarFornecedor(scanner);
        cadastrarProduto(new Produto(nome, codigo, valor, quantidadeEstoque, fornecedor));
    }

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
    public void realizarVenda(String codigoOuNome, int quantidade) {
        Produto produto = buscarProduto(codigoOuNome, "");
        if (produto != null) {
            int quantidadeAntes = produto.getQuantidadeEstoque(); // Armazena a quantidade antes da venda
            // Verifica se há estoque suficiente para a venda
            if (produto.getQuantidadeEstoque() >= quantidade) {
                produto.venderProduto(quantidade);
                System.out.println("Venda realizada com sucesso.");
                // Verifica se o estoque ficou abaixo do mínimo
                if (produto.getQuantidadeEstoque() <= quantidadeMinimaEmEstoque) {
                    System.out.println("Produto com estoque baixo!");
                }
                // Mostra o estado do produto antes e depois da venda
                System.out.println("Antes da venda: " + quantidadeAntes + " unidades." + "  Depois da venda: " + produto.getQuantidadeEstoque() + " unidades.");

            } else {
                System.out.println("\nEstoque insuficiente para realizar a venda.");
            }
        } else {
            System.out.println("\nProduto não encontrado.");
        }
    }

    // Método para listar todos os produtos no estoque
    public void listarProdutos() {
        if (getProdutos().isEmpty()) {
            System.out.println("\nNao a produtos cadastrado");
        } else {
            System.out.println("\nLista de Produtos:");
            for (Produto produto : produtos) {
                System.out.println(produto);
            }
        }
        listarProdutosEstoqueBaixo();
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
    public Produto buscarProduto(String codigo, String nome) {
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

    // Método para realizar uma venda validando se o valor informado é menor que a quantidade em estoque e maior que 0
    public void realizarVenda(Scanner scanner) {
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
    public void listarProdutosEstoqueBaixo(Scanner scanner) {
        int quantidadeMinima = solicitarQuantidade(scanner);

        listarProdutosEstoqueBaixo(quantidadeMinima);
    }

    // Método para buscar um produto pelo código ou nome
    public void buscarProduto(Scanner scanner) {
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
    private String cadastrarNome(Scanner scanner) {
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
    private String cadastrarFornecedor(Scanner scanner) {
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
    private String cadastrarCodigo(Scanner scanner) {
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
    private int solicitarQuantidade(Scanner scanner) {
        int quantidade;
        while (true) {
            System.out.print("Digite a quantidade do produto: ");
            String entradaQuantidade = scanner.nextLine();

            // Verifica se a entrada pode ser convertida para um número
            try {
                quantidade = Integer.parseInt(entradaQuantidade);
            } catch (NumberFormatException e) {
                System.out.println("ERRO!: A quantidade informada não é válida. Digite um número válido.");
                continue;
            }

            if (quantidade <= 0) {
                System.out.println("A quantidade deve ser um valor positivo.");
            } else {
                break;
            }
        }
        return quantidade;
    }

    public int escolhaMenu(Scanner scanner) {
        produtos.add(new Produto("Rastreador ZIC32", "FFG5789", 675.90, 86, "XP INC"));
        produtos.add(new Produto("Cabo Man 35L2", "JJS5420", 62.15, 92, "Tech Vox New"));
        produtos.add(new Produto("Testador FGT45 Super", "OYT5587", 755.20, 890, "Shudo Link Vok"));
        produtos.add(new Produto("Fixador de RJ45_KAT6", "SAE2013", 35.66, 72, "Kimoro Tech Six"));
        produtos.add(new Produto("Alicate PTG32K Pro", "GGW5219", 225.89, 22, "Mr Hardware Inc"));
        produtos.add(new Produto("Caixa de som PLX 32", "PXT4510", 375.50, 35, "SHIDU"));
        produtos.add(new Produto("Fone de ouvido", "bPL3510", 95.50, 22, "Bell"));
        produtos.add(new Produto("Caixa amplificadora KZ 32", "PLO2030", 550.90, 70, "Zekee"));
        produtos.add(new Produto("Plug KJG 32L", "TGR4520", 32.99, 70, "Ballu"));
        produtos.add(new Produto("GPS JUK32", "DDF7721", 375.90, 42, "FOXBlue"));
        int escolhaMenu;
        while (true) {
            System.out.println("\nGerenciamento de Estoque\n1: Cadastrar Produto\n2: Realizar Venda\n3: Listar Produtos\n4: Listar Produtos com Estoque Baixo\n5: Buscar Produto\n6: Calcular Valor Total do Estoque\n0: Sair\nInforme o que deseja: ");
            String entradaEscolha = scanner.nextLine();

            if (entradaEscolha.isEmpty()) {
                System.out.println("ERRO!: A escolha informada não é válida. Digite um número entre 0 e 6.");
                continue;
            }

            // Verifica se a entrada é um número inteiro
            if (!entradaEscolha.matches("\\d+")) {
                System.out.println("ERRO!: A escolha informada não é válida. Digite um número entre 0 e 6.");
                continue;
            }
            // Converte a entrada para um número inteiro
            escolhaMenu = Integer.parseInt(entradaEscolha);

            // Verifica se a escolha está dentro do intervalo permitido
            if (escolhaMenu < 0 || escolhaMenu > 6) {
                System.out.println("ERRO!: A escolha informada está fora do intervalo permitido. Digite um número entre 0 e 6.");
                continue;
            }

            break;
        }
        return escolhaMenu;
    }

}