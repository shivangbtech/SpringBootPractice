package com.irdeto.rest.repositories;

import com.irdeto.rest.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Integer> {
}
