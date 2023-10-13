package es.javaschool.springbootosisfinal_task.services.clientAddresServices;

import es.javaschool.springbootosisfinal_task.domain.Client;
import es.javaschool.springbootosisfinal_task.domain.ClientsAddress;
import es.javaschool.springbootosisfinal_task.dto.ClientDTO;
import es.javaschool.springbootosisfinal_task.dto.ClientsAddresDTO;
import es.javaschool.springbootosisfinal_task.repositories.ClientRepository;
import es.javaschool.springbootosisfinal_task.services.clientServices.ClientMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClientAddressMapper {

    @Autowired
    private final ClientMapper clientMapper;

    @Autowired
    private final ClientRepository clientRepository;

    public ClientsAddresDTO convertEntityToDto(ClientsAddress clientsAddress){
       ClientsAddresDTO clientsAddresDTO = new ClientsAddresDTO();

       clientsAddresDTO.setId(clientsAddress.getId());
       clientsAddresDTO.setCountry(clientsAddress.getCountry());
       clientsAddresDTO.setCity(clientsAddress.getCity());
       clientsAddresDTO.setPostalCode(clientsAddress.getPostalCode());
       clientsAddresDTO.setStreet(clientsAddress.getStreet());
       clientsAddresDTO.setHome(clientsAddress.getHome());
       clientsAddresDTO.setApartment(clientsAddress.getApartment());
       clientsAddresDTO.setClient(clientsAddress.getClient());


       return clientsAddresDTO;


    }

    public ClientsAddress convertDtoToEntity(ClientsAddresDTO clientsAddresDTO) {

        ClientsAddress clientsAddress = new ClientsAddress();

        clientsAddress.setId(clientsAddresDTO.getId());
        clientsAddress.setCountry(clientsAddresDTO.getCountry());
        clientsAddress.setCity(clientsAddresDTO.getCity());
        clientsAddress.setPostalCode(clientsAddresDTO.getPostalCode());
        clientsAddress.setStreet(clientsAddresDTO.getStreet());
        clientsAddress.setHome(clientsAddresDTO.getHome());
        clientsAddress.setApartment(clientsAddresDTO.getApartment());
        clientsAddress.setClient(clientsAddresDTO.getClient());


        return clientsAddress;

    }
}
