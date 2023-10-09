package es.javaschool.springbootosisfinal_task.services.clientservice;

import es.javaschool.springbootosisfinal_task.domain.Client;
import es.javaschool.springbootosisfinal_task.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ClientServiceImpl implements ClientService{

    @Autowired
    private ClientRepository clientRepository;


    @Override
    public List<Client> listclient() {
        return clientRepository.findAll();
    }
}
