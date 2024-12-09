package com.project2.spring_boot_project_2.Repositories;

import com.project2.spring_boot_project_2.Models.ProductModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ProductRepository extends JpaRepository<ProductModel, Long> {

}
