package es.javaschool.springbootosisfinal_task.repositories;
import es.javaschool.springbootosisfinal_task.domain.Product;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query("SELECT p, SUM(ohp.quantity) as total " +
            "FROM Product p " +
            "JOIN OrderHasProduct ohp ON p.id = ohp.product.id " +
            "GROUP BY p " +
            "ORDER BY SUM(ohp.quantity) DESC")
    List<Object[]> findTopProducts(Pageable pageable);




}


