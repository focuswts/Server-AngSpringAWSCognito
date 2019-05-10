package com.spring.angular.entity;

import javax.persistence.*;

@Entity
public class Produto_Atributos {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "atributo", nullable = false)
    private String atributo;

    @Column(name = "valor", nullable = false)
    private String valor;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getAtributo() {
        return atributo;
    }

    public void setAtributo(String atributo) {
        this.atributo = atributo;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    @Override
    public String toString() {
        return "Produto_Atributos{" +
                "id=" + id +
                ", atributo='" + atributo + '\'' +
                ", valor='" + valor + '\'' +
                '}';
    }
}
