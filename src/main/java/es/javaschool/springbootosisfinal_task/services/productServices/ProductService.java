package es.javaschool.springbootosisfinal_task.services.productServices;
import es.javaschool.springbootosisfinal_task.domain.Product;
import es.javaschool.springbootosisfinal_task.dto.ProductDTO;
import es.javaschool.springbootosisfinal_task.repositories.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class ProductService{


@Autowired
private ProductRepository productRepository;

@Autowired
private ProductMapper productMapper;


    public List<ProductDTO> listAll() {
        return  productRepository
                .findAll()
                .stream()
                .map(productMapper::convertEntityToDto)
                .collect(Collectors.toList());
    }


    public void createProduct(ProductDTO productDTO) {
        Product product = productMapper.convertDtoToEntity(productDTO);
        productRepository.save(product);

    }

    public Product getProductById(Long id) {
        return productRepository
                .findById(id).orElseThrow(() -> new EntityNotFoundException("Client not found with id: " + id));
    }

    public void updateProduct(ProductDTO productDTO) {
        Product existing = getProductById(productDTO.getId());
        Product converted = productMapper.convertDtoToEntity(productDTO);

        existing.setTitle(converted.getTitle());
        existing.setPrice(converted.getPrice());
        existing.setCategory(converted.getCategory());
        existing.setParameters(converted.getParameters());
        existing.setWeight(converted.getWeight());
        existing.setVolume(converted.getVolume());
        existing.setQuantityStock(converted.getQuantityStock());


        productRepository.save(existing);

    }

    public void delete(Long id) {
        productRepository.deleteById(id);
    }



}
