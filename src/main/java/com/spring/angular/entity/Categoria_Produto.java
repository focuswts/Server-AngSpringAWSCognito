package com.spring.angular.entity;


import javax.persistence.*;

@Entity
public class Categoria_Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name="categoria",nullable = false)
    private String categoria;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    @Override
    public String toString() {
        return "Categoria_Produto{" +
                "id=" + id +
                ", categoria='" + categoria + '\'' +
                '}';
    }
}
