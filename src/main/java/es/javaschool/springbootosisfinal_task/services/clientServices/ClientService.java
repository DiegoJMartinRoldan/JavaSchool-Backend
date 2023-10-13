package es.javaschool.springbootosisfinal_task.services.clientServices;
import es.javaschool.springbootosisfinal_task.domain.Client;
import es.javaschool.springbootosisfinal_task.dto.ClientDTO;
import es.javaschool.springbootosisfinal_task.repositories.ClientRepository;
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
public class ClientService {


    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private final ClientMapper clientMapper;



    public List<ClientDTO> listAll() {
        return  clientRepository
                .findAll()
                .stream()
                .map(clientMapper::convertEntityToDto)
                .collect(Collectors.toList());
    }


    public void createClient(ClientDTO clientDTO) {
        Client client = clientMapper.convertDtoToEntity(clientDTO);
        clientRepository.save(client);

    }

    public Client getClientById(Long id) {
        return clientRepository
                .findById(id).orElseThrow(() -> new EntityNotFoundException("Client not found with id: " + id));
    }

    public void updateClient(ClientDTO clientDTO) {
        Client existing = getClientById(clientDTO.getId());
        Client converted = clientMapper.convertDtoToEntity(clientDTO);

        existing.setName(converted.getName());
        existing.setSurname(converted.getSurname());
        existing.setDateOfBirth(converted.getDateOfBirth());
        existing.setEmail(converted.getEmail());

        clientRepository.save(existing);

    }

    public void delete(Long id) {
        clientRepository.deleteById(id);
    }




}
