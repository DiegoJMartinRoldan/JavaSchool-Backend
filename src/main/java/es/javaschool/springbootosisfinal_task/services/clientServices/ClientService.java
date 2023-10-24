package es.javaschool.springbootosisfinal_task.services.clientServices;
import es.javaschool.springbootosisfinal_task.domain.Client;
import es.javaschool.springbootosisfinal_task.dto.ClientDTO;
import es.javaschool.springbootosisfinal_task.repositories.ClientRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class ClientService{


    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private final ClientMapper clientMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;



    public List<ClientDTO> listAll() {
        return  clientRepository
                .findAll()
                .stream()
                .map(clientMapper::convertEntityToDto)
                .collect(Collectors.toList());
    }


    public void createClient(ClientDTO clientDTO) {
        clientDTO.setPassword(passwordEncoder.encode(clientDTO.getPassword()));

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
        existing.setPassword(converted.getPassword());

        clientRepository.save(existing);

    }

    public void delete(Long id) {
        clientRepository.deleteById(id);
    }







                             //Password Change

    //We verify if the old password by comparing it with the new one that we receive from the method, are the same
    public boolean oldPasswordIsValid(Client client, String oldPassword){
        return passwordEncoder.matches(oldPassword, client.getPassword());

    }


    //Set the password of the entity with the new one
    public void changePassword (@RequestBody Client client, String newPwd){
        client.setPassword(passwordEncoder.encode(newPwd));
        clientRepository.save(client);
    }

}
