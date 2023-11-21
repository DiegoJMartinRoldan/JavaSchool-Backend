package es.javaschool.springbootosisfinal_task.repositories;
import es.javaschool.springbootosisfinal_task.domain.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface OrdersRepository extends JpaRepository<Orders,Long> {


    List<Orders> findByClientName(String username);

    @Query("SELECT SUM(p.price * ohp.quantity) as monthlyEarnings " +
            "FROM Orders o " +
            "JOIN OrderHasProduct ohp ON o.id = ohp.orders.id " +
            "JOIN Product p ON ohp.product.id = p.id " +
            "WHERE YEAR(o.orderDate) = :year AND MONTH(o.orderDate) = :month")
    Double getMonthlyEarnings(@Param("year") int year, @Param("month") int month);

    @Query("SELECT SUM(p.price * ohp.quantity) as weeklyEarnings " +
            "FROM Orders o " +
            "JOIN OrderHasProduct ohp ON o.id = ohp.orders.id " +
            "JOIN Product p ON ohp.product.id = p.id " +
            "WHERE YEARWEEK(o.orderDate) = YEARWEEK(CURRENT_DATE())")
    Double calculateWeeklyEarnings();



}
