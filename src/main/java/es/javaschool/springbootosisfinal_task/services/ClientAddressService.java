package es.javaschool.springbootosisfinal_task.services;

import es.javaschool.springbootosisfinal_task.repositories.ClientsAddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClientAddressService {

    @Autowired
    private ClientsAddressRepository clientsAddressRepository;
}
