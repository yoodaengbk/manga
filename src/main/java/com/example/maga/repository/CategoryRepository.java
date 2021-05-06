/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.maga.repository;

import com.example.maga.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author TAM
 */
@Repository
public interface CategoryRepository  extends JpaRepository<Category, Long> {
    
}
