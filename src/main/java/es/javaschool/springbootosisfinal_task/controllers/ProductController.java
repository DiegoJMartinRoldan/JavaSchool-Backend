package es.javaschool.springbootosisfinal_task.controllers;
import es.javaschool.springbootosisfinal_task.domain.Product;
import es.javaschool.springbootosisfinal_task.dto.ProductDTO;
import es.javaschool.springbootosisfinal_task.exception.ResourceNotFoundException;
import es.javaschool.springbootosisfinal_task.services.productServices.ProductService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${product.Controller.url}")
public class ProductController {


    @Autowired
    private ProductService productService;

    @GetMapping("/list")
    public ResponseEntity<List<ProductDTO>> listAll() {
        List<ProductDTO> productDTOS = productService.listAll();
        if (productDTOS.isEmpty()) {
            throw new ResourceNotFoundException("list");
        }
        return new ResponseEntity<>(productDTOS, HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<String> createProduct(@Valid @RequestBody ProductDTO productDTO) {
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
    public ResponseEntity<String> updateProduct(@PathVariable Long id, @Valid @RequestBody ProductDTO productDTO) {
        try {
            productService.updateProduct(productDTO);
            return new ResponseEntity<>("Product updated successfully", HttpStatus.OK);
        } catch (EntityNotFoundException exception) {
            throw new ResourceNotFoundException("update", "id", id);
        }
    }

    @DeleteMapping("/delete/{id}")
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


}
