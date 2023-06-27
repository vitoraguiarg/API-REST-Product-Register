package com.example.springbootstudy.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

// DTO (Data Transfer Object) é uma classe simples usada para transportar dados entre diferentes camadas de uma aplicação.
// @NotBlank é uma anotação que impede que o nome de um produto não poderá ser null, branco, vazio e etc.
// @NotNull é uma anotação que impede que o valor seja nulo.
public record ProductRecordDto(@NotBlank String name, @NotNull BigDecimal valor) {


}
