package es.javaschool.springbootosisfinal_task.services.clientServices;

import es.javaschool.springbootosisfinal_task.domain.Client;
import es.javaschool.springbootosisfinal_task.dto.ClientDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Date;

@Service
@RequiredArgsConstructor
public class ClientMapper {

    public ClientDTO convertEntityToDto(Client client){
        ClientDTO clientDTO = new ClientDTO();

        clientDTO.setId(client.getId());
        clientDTO.setName(client.getName());
        clientDTO.setSurname(client.getSurname());
        clientDTO.setDateOfBirth((Date) client.getDateOfBirth());
        clientDTO.setEmail(client.getEmail());
        clientDTO.setPassword(client.getPassword());

        return  clientDTO;

    }

    public Client convertDtoToEntity(ClientDTO clientDTO){
        Client client = new Client();

        client.setId(clientDTO.getId());
        client.setName(clientDTO.getName());
        client.setSurname(clientDTO.getSurname());
        client.setDateOfBirth(clientDTO.getDateOfBirth());
        client.setEmail(clientDTO.getEmail());
        client.setPassword(clientDTO.getPassword());

        return client;
    }

}
