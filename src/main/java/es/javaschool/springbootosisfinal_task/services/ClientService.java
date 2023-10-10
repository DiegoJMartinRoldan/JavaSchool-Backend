package es.javaschool.springbootosisfinal_task.services;

import es.javaschool.springbootosisfinal_task.domain.Client;
import es.javaschool.springbootosisfinal_task.dto.ClientDTO;
import es.javaschool.springbootosisfinal_task.repositories.ClientRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClientService {


    @Autowired
    private ClientRepository clientRepository;



    public List<ClientDTO> listAll() {
        return  clientRepository
                .findAll()
                .stream()
                .map(this::convertEntityToDto)
                .collect(Collectors.toList());
    }


    public void createClient(ClientDTO clientDTO) {
        Client client = convertDtoToEntity(clientDTO);
        clientRepository.save(client);

    }

    public Client getClientById(Long id) {
        return clientRepository
                .findById(id).orElseThrow(() -> new EntityNotFoundException("Client not found with id: " + id));
    }

    public void updateClient(ClientDTO clientDTO) {
        Client existing = getClientById(clientDTO.getId());
        Client converted = convertDtoToEntity(clientDTO);

        existing.setName(converted.getName());
        existing.setSurname(converted.getSurname());
      //  existing.setDateOfBirth(converted.getDateOfBirth());
        existing.setEmail(converted.getEmail());

        clientRepository.save(existing);

    }

    public void delete(Long id) {
        clientRepository.deleteById(id);
    }

    //Conversion Methods
    private ClientDTO convertEntityToDto(Client client){
        ClientDTO clientDTO = new ClientDTO();
        clientDTO.setName(client.getName());
        clientDTO.setSurname(client.getSurname());
        clientDTO.setDateOfBirth(client.getDateOfBirth());
        clientDTO.setEmail(client.getEmail());

        return  clientDTO;

    }

    private Client convertDtoToEntity(ClientDTO clientDTO){
        Client client = new Client();
        client.setName(clientDTO.getName());
        client.setSurname(clientDTO.getSurname());
        client.setDateOfBirth(clientDTO.getDateOfBirth());
        client.setEmail(clientDTO.getEmail());

        return client;
    }



}
