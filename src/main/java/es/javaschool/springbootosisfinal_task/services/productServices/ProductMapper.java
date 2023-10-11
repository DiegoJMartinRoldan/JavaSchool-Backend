package es.javaschool.springbootosisfinal_task.services.productServices;
import es.javaschool.springbootosisfinal_task.domain.Product;
import es.javaschool.springbootosisfinal_task.dto.ProductDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;

@Service
@RequiredArgsConstructor
public class ProductMapper {

    public ProductDTO convertEntityToDto(Product product){

        ProductDTO productDTO = new ProductDTO();

        productDTO.setId(product.getId());
        productDTO.setTitle(product.getTitle());
        productDTO.setPrice(product.getPrice());
        productDTO.setCategory(product.getCategory());
        productDTO.setParameters(product.getParameters());
        productDTO.setWeight(product.getWeight());
        productDTO.setVolume(product.getVolume());
        productDTO.setQuantityStock(product.getQuantityStock());
        return productDTO;

    }

    public Product convertDtoToEntity(ProductDTO productDTO){

        Product product = new Product();

        product.setId(productDTO.getId());
        product.setTitle(productDTO.getTitle());
        product.setPrice(productDTO.getPrice());
        product.setCategory(productDTO.getCategory());
        product.setParameters(productDTO.getParameters());
        product.setWeight(productDTO.getWeight());
        product.setVolume(productDTO.getVolume());
        product.setQuantityStock(productDTO.getQuantityStock());

        return product;

    }

}
