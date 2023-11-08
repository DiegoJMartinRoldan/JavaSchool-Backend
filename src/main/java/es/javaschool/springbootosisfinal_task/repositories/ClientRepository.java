package es.javaschool.springbootosisfinal_task.repositories;
import es.javaschool.springbootosisfinal_task.domain.Client;
import es.javaschool.springbootosisfinal_task.dto.ClientDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<Client,Long> {

    Optional<Client> findByName(String username);


    Optional<Client> findByEmail(String username);


    //Query para obtener el top de clientes que más pedidos hacen

    @Query("SELECT c, SUM(ohp.quantity) as totalQuantity " +
            "FROM Client c " +
            "JOIN c.orders o " +
            "JOIN OrderHasProduct ohp ON o.id = ohp.orders.id " +
            "GROUP BY c " +
            "ORDER BY SUM(ohp.quantity) DESC")
    List<Object[]> findTopClients();

    @Query("SELECT c.id FROM Client c WHERE c.email = :email")
    Long findClientIdByEmail(@Param("email") String email);





}
