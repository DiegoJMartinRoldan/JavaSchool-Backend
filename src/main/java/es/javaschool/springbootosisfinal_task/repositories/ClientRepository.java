package es.javaschool.springbootosisfinal_task.repositories;
import es.javaschool.springbootosisfinal_task.domain.Client;
import org.springframework.data.domain.Pageable;
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
    Optional<Client> findByEmailIgnoreCase(String email);


    //Query para obtener el top de clientes que más pedidos hacen, tener en cuenta


    @Query("SELECT c, SUM(ohp.quantity) as totalQuantity " +
            "FROM Client c " +
            "JOIN c.orders o " +
            "JOIN OrderHasProduct ohp ON o.id = ohp.orders.id " +
            "GROUP BY c.id " +  // Agrupar por el ID del cliente en lugar de la entidad completa
            "ORDER BY SUM(ohp.quantity) DESC")
    List<Object[]> findTopClients(Pageable pageable);



    @Query("SELECT c.id FROM Client c WHERE c.email = :email")
    Long findClientIdByEmail(@Param("email") String email);

    @Query("SELECT c.id FROM Client c WHERE c.name = :name")
    Long findClientByName(@Param("name") String name);
}
