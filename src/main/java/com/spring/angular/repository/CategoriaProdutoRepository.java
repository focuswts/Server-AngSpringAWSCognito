package com.spring.angular.repository;

import com.spring.angular.entity.Categoria_Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoriaProdutoRepository extends JpaRepository<Categoria_Produto,Long> {
}
