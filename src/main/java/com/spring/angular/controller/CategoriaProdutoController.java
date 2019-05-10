package com.spring.angular.controller;

import com.spring.angular.entity.Categoria_Produto;
import com.spring.angular.repository.CategoriaProdutoRepository;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/categoriaProduto")
public class CategoriaProdutoController {

    @Autowired
    private CategoriaProdutoRepository repository;

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_USER')")
    public List<Categoria_Produto> getAll() {
        return repository.findAll();
    }

    @RequestMapping(value = "/all/{id}", method = RequestMethod.GET)
    public ResponseEntity<Categoria_Produto> getCategoriaById(@PathVariable(value = "id") Long categoriaProdutoId) throws ResourceNotFoundException {

        Categoria_Produto categoriaProduto = repository.findById(categoriaProdutoId).orElseThrow(
                () -> new ResourceNotFoundException("Categoria Não Encontrada para esse id: " + categoriaProdutoId));

        return ResponseEntity.ok().body(categoriaProduto);

    }

    @PostMapping(value = "/create")
    @PreAuthorize("hasRole('ROLE_USER')")
    public Categoria_Produto createCategoriaProduto(@Valid @RequestBody Categoria_Produto categoriaProduto) {
        return repository.save(categoriaProduto);
    }

    @RequestMapping(value = "/update/{id}")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<Categoria_Produto> updateCategoriaProduto(@PathVariable(value = "id") Long categoriaProdutoId, @Valid @RequestBody Categoria_Produto categoriaProdutoDetails) {

        Categoria_Produto categoriaProduto = repository.findById(categoriaProdutoId).orElseThrow(
                () -> new ResourceNotFoundException("Categoria Não Encontrada para esse id: " + categoriaProdutoId));

        categoriaProduto.setCategoria(categoriaProdutoDetails.getCategoria());

        final Categoria_Produto updatedCategoria = repository.save(categoriaProduto);
        return ResponseEntity.ok(updatedCategoria);
    }

    @DeleteMapping(value = "/delete/{id}")
    @PreAuthorize("hasRole('ROLE_USER')")
    public Map<String, Boolean> deleteCategoriaProduto(@PathVariable(value = "id") Long categoriaProdutoId) throws ResourceNotFoundException {

        Categoria_Produto categoriaProduto = repository.findById(categoriaProdutoId).orElseThrow(
                () -> new ResourceNotFoundException("Categoria Não Encontrada para esse id: " + categoriaProdutoId));

        repository.delete(categoriaProduto);

        Map<String, Boolean> response = new HashMap<>();

        response.put("deleted", Boolean.TRUE);

        return response;
    }


}
