package com.example.product.service;

import com.example.product.model.Product;
import com.example.product.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    private final ProductRepository repo;

    public ProductService(ProductRepository repo) {
        this.repo = repo;
    }

    public List<Product> getAll() {
        return repo.findAll();
    }

    public Product getById(Long id) {
        return repo.findById(id).orElse(null);
    }

    public Product save(Product product) {
        return repo.save(product);
    }

    public void delete(Long id) {
        repo.deleteById(id);
    }
}
