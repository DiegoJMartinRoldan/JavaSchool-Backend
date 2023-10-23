package es.javaschool.springbootosisfinal_task.repositories;
import es.javaschool.springbootosisfinal_task.domain.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<Client,Long> {

    Optional<Client> findByName(String username);


    Optional<Client> findByEmail(String username);
}
