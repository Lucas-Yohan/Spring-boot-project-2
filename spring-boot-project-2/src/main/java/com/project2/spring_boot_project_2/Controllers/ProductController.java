package com.project2.spring_boot_project_2.Controllers;

import com.project2.spring_boot_project_2.Dtos.ProductRecordDto;
import com.project2.spring_boot_project_2.Models.ProductModel;
import com.project2.spring_boot_project_2.Repositories.ProductRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/products")
public class ProductController {

    private final ProductRepository productRepository;


    @PostMapping
    public ResponseEntity<ProductModel> createProduct(@RequestBody @Valid ProductRecordDto productDto){
        var newProduct = new ProductModel();
        BeanUtils.copyProperties(productDto, newProduct);
        return ResponseEntity.status(HttpStatus.CREATED).body(productRepository.save(newProduct));
    }

    @GetMapping
    public ResponseEntity<List<ProductModel>> findAll(){
        List<ProductModel> products = productRepository.findAll();

        if (!products.isEmpty()){
            for (ProductModel productList : products){
                Long id = productList.getProductId();
                productList.add(linkTo(methodOn(ProductController.class).findById(id)).withSelfRel());
            }
        }

        return ResponseEntity.status(HttpStatus.OK).body(products);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> findById(@PathVariable Long id){
        Optional<ProductModel> product = productRepository.findById(id);

        if(product.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found");
        }
        product.get().add(linkTo(methodOn(ProductController.class).findAll()).withRel("Products List"));

        return ResponseEntity.status(HttpStatus.OK).body(product);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateProduct(@PathVariable(value = "id") Long id, @RequestBody @Valid ProductRecordDto productDto){

        Optional<ProductModel> product = productRepository.findById(id);
        if (product.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found");
        }

        var productModel = product.get();
        BeanUtils.copyProperties(productDto, productModel);
        return ResponseEntity.status(HttpStatus.OK).body(productRepository.save(productModel));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteProduct(@PathVariable Long id){

        Optional<ProductModel> product = productRepository.findById(id);
        if (product.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found");
        }
        productRepository.delete(product.get());
        return ResponseEntity.status(HttpStatus.OK).body("Product deleted successfully");
    }



}
