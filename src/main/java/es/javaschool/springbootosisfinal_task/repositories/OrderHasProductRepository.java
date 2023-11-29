package es.javaschool.springbootosisfinal_task.repositories;
import es.javaschool.springbootosisfinal_task.domain.OrderHasProduct;
import es.javaschool.springbootosisfinal_task.domain.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderHasProductRepository extends JpaRepository<OrderHasProduct, Long> {


    @Query("SELECT ohp FROM OrderHasProduct ohp JOIN FETCH ohp.product p JOIN ohp.orders o WHERE o.client.id = :clientId")
    List<OrderHasProduct> findOrderHasProductsByClientId(@Param("clientId") Long clientId);



}