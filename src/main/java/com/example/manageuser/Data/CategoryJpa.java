package com.example.manageuser.Data;

import com.example.manageuser.Entity.Category;
import com.example.manageuser.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryJpa extends JpaRepository<Category,Long> {
    List<Category> findAll();

}
