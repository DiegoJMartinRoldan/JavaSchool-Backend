package es.javaschool.springbootosisfinal_task.controllers;
import ch.qos.logback.core.model.Model;
import es.javaschool.springbootosisfinal_task.domain.Product;
import es.javaschool.springbootosisfinal_task.dto.ProductDTO;
import es.javaschool.springbootosisfinal_task.exception.ResourceNotFoundException;
import es.javaschool.springbootosisfinal_task.services.productServices.ProductService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Path;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;


@RestController
@RequestMapping("${product.Controller.url}")
public class ProductController {


    @Autowired
    private ProductService productService;

    @GetMapping("/list")
    @PreAuthorize("permitAll() or isAuthenticated()")
    public ResponseEntity<List<ProductDTO>> listAll() {
        List<ProductDTO> productDTOS = productService.listAll();
        if (productDTOS.isEmpty()) {
            throw new ResourceNotFoundException("list");
        }
        return new ResponseEntity<>(productDTOS, HttpStatus.OK);
    }

    @PostMapping("/create")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<String> createProduct(@RequestBody ProductDTO productDTO) {
        try {
            productService.createProduct(productDTO);

            return new ResponseEntity<>("Product created successfully", HttpStatus.CREATED);
        } catch (Exception e) {
            throw new ResourceNotFoundException("create");
        }
    }


    @GetMapping("/getby/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        try {
            Product product = productService.getProductById(id);
            if (product == null) {
                throw new ResourceNotFoundException("getby", "id", id);
            }
            return new ResponseEntity<>(product, HttpStatus.OK);
        } catch (EntityNotFoundException exception) {
            throw new ResourceNotFoundException("getby", "id", id);
        }
    }

    @PutMapping("/update/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<String> updateProduct(@PathVariable Long id, @Valid @RequestBody ProductDTO productDTO) {
        try {
            productService.updateProduct(productDTO);
            return new ResponseEntity<>("Product updated successfully", HttpStatus.OK);
        } catch (EntityNotFoundException exception) {
            throw new ResourceNotFoundException("update", "id", id);
        }
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        try {
            Product product = productService.getProductById(id);
            if (product == null) {
                throw new ResourceNotFoundException("delete", "id", id);
            }
            productService.delete(id);
            return new ResponseEntity<>("Product deleted successfully", HttpStatus.OK);
        } catch (EntityNotFoundException exception) {
            throw new ResourceNotFoundException("delete", "id", id);
        }
    }

                                    //Catalog

    @PostMapping("/catalogFilter")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<List<ProductDTO>> catalogFiltering (@RequestBody ProductDTO productDTO){

        List<ProductDTO> result = productService.catalogFiltering(productDTO);
        return ResponseEntity.ok(result);

    }

    @PostMapping("/newCatalogCategory")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<String> newCatalogCategory (@RequestBody ProductDTO productDTO){

         productService.createNewCatalogCategoruy(productDTO);

        return new ResponseEntity<>("New category created suscessfully", HttpStatus.OK);

    }






}
