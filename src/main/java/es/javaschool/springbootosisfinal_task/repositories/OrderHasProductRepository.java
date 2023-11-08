package es.javaschool.springbootosisfinal_task.repositories;
import es.javaschool.springbootosisfinal_task.domain.OrderHasProduct;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderHasProductRepository extends JpaRepository<OrderHasProduct, Long> {
}