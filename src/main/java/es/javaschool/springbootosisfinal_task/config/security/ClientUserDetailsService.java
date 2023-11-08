package es.javaschool.springbootosisfinal_task.config.security;
import es.javaschool.springbootosisfinal_task.config.security.ClientToUserDetails;
import es.javaschool.springbootosisfinal_task.domain.Client;
import es.javaschool.springbootosisfinal_task.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ClientUserDetailsService implements UserDetailsService {

    //Load the client from the database by name and implement the ClientToUserDetails for the conversion

    @Autowired
    private ClientRepository clientRepository;



    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Client> client;
        if (username.contains("@")){
            client = clientRepository.findByEmail(username);
        }else{
            client = clientRepository.findByName(username);
        }

        return client.map(ClientToUserDetails::new)
                .orElseThrow(()-> new UsernameNotFoundException("User not found"));


    }
}
