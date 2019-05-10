package com.spring.angular.entity;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "produto", nullable = false)
    private String produto;

    @Column(name = "quantidade", nullable = false)
    private int quantidade;

    @Column(name = "preco", nullable = false)
    private BigDecimal preco;

    @ManyToOne
    @JoinColumn(name = "idAtributos", nullable = true)
    private Produto_Atributos idAtributos;

    @OneToOne
    @JoinColumn(name = "idCategoria", nullable = false)
    private Categoria_Produto idCategoria;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getProduto() {
        return produto;
    }

    public void setProduto(String produto) {
        this.produto = produto;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public BigDecimal getPreco() {
        return preco;
    }

    public void setPreco(BigDecimal preco) {
        this.preco = preco;
    }

    public Produto_Atributos getIdAtributos() {
        return idAtributos;
    }

    public void setIdAtributos(Produto_Atributos idAtributos) {
        this.idAtributos = idAtributos;
    }

    public Categoria_Produto getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(Categoria_Produto idCategoria) {
        this.idCategoria = idCategoria;
    }

    @Override
    public String toString() {
        return "Produto{" +
                "id=" + id +
                ", produto='" + produto + '\'' +
                ", quantidade=" + quantidade +
                ", preco=" + preco +
                ", idAtributos=" + idAtributos +
                ", idCategoria=" + idCategoria +
                '}';
    }
}
