package es.javaschool.springbootosisfinal_task.services.clientservice;

import es.javaschool.springbootosisfinal_task.domain.Client;
import org.springframework.stereotype.Service;

import java.util.List;

public interface ClientService {

    List<Client> listclient();
}
