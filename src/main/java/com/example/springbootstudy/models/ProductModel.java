package com.example.springbootstudy.models;

import jakarta.persistence.*;
import org.springframework.hateoas.RepresentationModel;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.UUID;

@Entity // Fala para o banco de dados que ela é uma entidade do banco de dados
@Table(name = "TB PRODUCTS") // Define que ela é uma tabela e também define um nome para  a mesma
// Essa implementação Serializable mostra para a JVM que essa é uma classe habilitada a passar por serializações
//
public class ProductModel extends RepresentationModel<ProductModel> implements Serializable {
    private static final long serialVersionUID = 1L; // Define o número de versão da classe ProductModel para fins de serialização.

    // Iniciante os atributos do Model, que será cada uma das colunas da tabela.
    @Id // Identificador da classe que será o identificador da tabela
    @GeneratedValue(strategy = GenerationType.AUTO)
    // Essa anotação demonstra e define como serão gerados os identificadores, nesse caso os valores do ID serão gerados e salvos automaticamente.
    private UUID idProduct; // UUID é um identificador distribuído assim evita conflitos de ID, pois são universais.
    private String name;
    private BigDecimal valor; // BigDecimal é utilizado quando precisamos de uma precisão arbitrária de valores, assim não há problemas de arredondamento.

    public UUID getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(UUID idProduct) {
        this.idProduct = idProduct;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }
}
