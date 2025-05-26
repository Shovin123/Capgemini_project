package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Product;
import com.example.demo.repository.ProductRepository;

@Service
public class ProductService {

    @Autowired
    private ProductRepository repo;

    public Product saveProduct(Product p) {
        return repo.save(p);
    }

    public List<Product> getAll() {
        return repo.findAll();
    }

    public Product getProductById(Long id) {
        return repo.findById(id).orElseThrow(() -> new RuntimeException("Product not found with id: " + id));
    }

    public void deleteById(Long id) {
        if (!repo.existsById(id)) {
            throw new RuntimeException("Product not found with id: " + id);
        }
        repo.deleteById(id);
    }

    public Product updateProduct(Long id, Product updated) {
        Product p = getProductById(id);
        p.setName(updated.getName());
        p.setDescription(updated.getDescription());
        p.setPrice(updated.getPrice());
        p.setStock(updated.getStock());
        p.setCategory(updated.getCategory());
        return repo.save(p);
    }
}
