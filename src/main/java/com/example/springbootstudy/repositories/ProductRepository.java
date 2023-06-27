package com.example.springbootstudy.repositories;

import com.example.springbootstudy.models.ProductModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
// Será dentro dessa interface que iremos salvar, obter, alterar e manusear determinados recursos com o apoio do JpaRepository.
// Primeiro entre <> é passado qual a entidade que o repository irá contemplar (ProductModel) e depois qual o tipo de identificador (UUID).
@Repository
public interface ProductRepository extends JpaRepository<ProductModel, UUID> {


}
