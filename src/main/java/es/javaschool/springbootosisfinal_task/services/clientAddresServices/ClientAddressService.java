package es.javaschool.springbootosisfinal_task.services.clientAddresServices;

import es.javaschool.springbootosisfinal_task.domain.Client;
import es.javaschool.springbootosisfinal_task.domain.ClientsAddress;
import es.javaschool.springbootosisfinal_task.dto.ClientsAddresDTO;
import es.javaschool.springbootosisfinal_task.repositories.ClientRepository;
import es.javaschool.springbootosisfinal_task.repositories.ClientsAddressRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class ClientAddressService {

    @Autowired
    private ClientsAddressRepository clientsAddressRepository;

    @Autowired
    private ClientAddressMapper clientAddressMapper;


    public List<ClientsAddresDTO> listAll() {
        return  clientsAddressRepository
                .findAll()
                .stream()
                .map(clientAddressMapper::convertEntityToDto)
                .collect(Collectors.toList());
    }

    public void createClientAddress(ClientsAddresDTO clientsAddresDTO) {

        ClientsAddress clientsAddress = clientAddressMapper.convertDtoToEntity(clientsAddresDTO);
        clientsAddressRepository.save(clientsAddress);

    }



    public ClientsAddress getClientAddressById(Long id) {
        return clientsAddressRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Client not found with id: " + id));
    }


   public void updateClientAddress(ClientsAddresDTO clientsAddresDTO) {
       ClientsAddress existing = getClientAddressById(clientsAddresDTO.getId());
       ClientsAddress converted = clientAddressMapper.convertDtoToEntity(clientsAddresDTO);

       existing.setCountry(converted.getCountry());
       existing.setCity(converted.getCity());
       existing.setPostalCode(converted.getPostalCode());
       existing.setStreet(converted.getStreet());
       existing.setHome(converted.getHome());
       existing.setApartment(converted.getApartment());

       clientsAddressRepository.save(existing);

   }

    public void delete(Long id) {
        clientsAddressRepository.deleteById(id);
    }


}
