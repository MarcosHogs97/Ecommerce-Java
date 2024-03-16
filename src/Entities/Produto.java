package Entities;

public class Produto{
    private String nome;
    private String codigo;
    private double preco;
    private int quantidadeEstoque;
    private String fornecedor;

    // Construtor para inicializar os atributos do produto
    public Produto(String nome, String codigo, double preco, int quantidadeEstoque, String fornecedor) {
        this.nome = nome;
        this.codigo = codigo;
        this.preco = preco;
        this.quantidadeEstoque = quantidadeEstoque;
        this.fornecedor = fornecedor;
    }

    // Métodos getters e setters para os atributos
    public String getNome() {
        return nome;
    }

    public String getCodigo() {
        return codigo;
    }

    public double getPreco() {
        return preco;
    }

    public int getQuantidadeEstoque() {
        return quantidadeEstoque;
    }

    public String getFornecedor() {
        return fornecedor;
    }

    public void venderProduto(int quantidade) {
        this.quantidadeEstoque -= quantidade;
    }

    @Override
    public String toString() {
        return "Produto: " + getNome() + ", Codigo: " + getCodigo() + ", Preço: R$" + String.format("%.2f",getPreco()) +
                ", Quantidade Em Estoque: " + getQuantidadeEstoque() + ", Fornecedor: " + getFornecedor();
    }
}