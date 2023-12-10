package es.javaschool.springbootosisfinal_task.services.productServices;
import es.javaschool.springbootosisfinal_task.domain.Product;
import es.javaschool.springbootosisfinal_task.dto.ProductDTO;
import es.javaschool.springbootosisfinal_task.repositories.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;


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


    @Transactional
    public void createProduct(ProductDTO productDTO) {
        Product product = productMapper.convertDtoToEntity(productDTO);

        // Guardar el archivo
        saveFile(productDTO.getImage(), product.getId());
        System.out.println(productDTO.getImage());
        productRepository.save(product);

    }

    public void saveFile(byte[] image, Long productId) {
        try {
            // Obtener el nombre del archivo
            String fileName = productId + "_" + System.currentTimeMillis() + ".jpg";

            // Obtener la ruta del archivo
            String filePath = "uploads/" + fileName;

            // Guardar el archivo en la carpeta "uploads"
            Files.write(Paths.get(filePath), image);
        } catch (IOException e) {
            throw new RuntimeException("Error al guardar el archivo");
        }
    }


    public Product getProductById(Long id) {
        return productRepository
                .findById(id).orElseThrow(() -> new EntityNotFoundException("Product not found with id: " + id));
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
        existing.setImage(converted.getImage());

        productRepository.save(existing);
    }


    public void delete(Long id) {
        productRepository.deleteById(id);
    }


                                            //Catalog filtering
    public List<ProductDTO> catalogFiltering (ProductDTO productDTO){

        String category = productDTO.getCategory();
        BigDecimal price = productDTO.getPrice();
        String title = productDTO.getTitle();

        List<Product> products = productRepository.findAll();
        Stream<Product> productStream = products.stream();

        if (category != null){
            productStream = productStream.filter(product -> category.equals(product.getCategory()));
        }

        if (price != null) {
            productStream = productStream.filter(product -> price.compareTo(product.getPrice()) <= 0);
        }

        if (title != null) {
            productStream = productStream.filter(product -> title.equals(product.getTitle()));
        }

        List<ProductDTO> productDTOS = productStream.map(productMapper::convertEntityToDto).collect(Collectors.toList());
        return productDTOS;
    }

    public void createNewCatalogCategoruy(ProductDTO productDTO) {
        Product category = productMapper.convertDtoToEntity(productDTO);

        category.setCategory(productDTO.getCategory());
        productRepository.save(category);

    }


    //Top 10 products
    public List<Product> getTop10ProductsSold() {
        Pageable top10Products = PageRequest.of(0, 10);
        List<Object[]> topProducts = productRepository.findTopProducts(top10Products);

        List<Product> result = new ArrayList<>();
        for (Object[] obj : topProducts) {
            if (obj[0] instanceof Product) {
                Product product = (Product) obj[0];
                result.add(product);
            }
        }
        return result;
    }





    public ProductDTO getProductDTOById(Long id) {
        Product product = productRepository
                .findById(id).orElseThrow(() -> new EntityNotFoundException("Product not found with id: " + id));

        return productMapper.convertEntityToDto(product);
    }


}
