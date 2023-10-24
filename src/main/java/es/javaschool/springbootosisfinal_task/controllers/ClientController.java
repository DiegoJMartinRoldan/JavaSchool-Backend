package es.javaschool.springbootosisfinal_task.controllers;
import es.javaschool.springbootosisfinal_task.domain.Client;
import es.javaschool.springbootosisfinal_task.config.jwt.RefreshToken;
import es.javaschool.springbootosisfinal_task.dto.ClientDTO;
import es.javaschool.springbootosisfinal_task.config.jwt.RefreshRequest;
import es.javaschool.springbootosisfinal_task.config.jwt.RefreshTokenDTO;
import es.javaschool.springbootosisfinal_task.exception.ResourceNotFoundException;
import es.javaschool.springbootosisfinal_task.config.jwt.RefreshTokenService;
import es.javaschool.springbootosisfinal_task.services.clientServices.ClientService;
import es.javaschool.springbootosisfinal_task.config.jwt.JwtService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;



@RestController
@RequestMapping("${client.Controller.url}")
public class ClientController {

    @Autowired
    private ClientService clientService;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private RefreshTokenService refreshTokenService;


    @GetMapping("/list")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<List<ClientDTO>> listAll() {
        List<ClientDTO> clientDTOS = clientService.listAll();
        if (clientDTOS.isEmpty()) {
            throw new ResourceNotFoundException("list");
        }
        return new ResponseEntity<>(clientDTOS, HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<String> createClient(@Valid @RequestBody ClientDTO clientDTO) {

        try {
            clientService.createClient(clientDTO);
            return new ResponseEntity<>("Client created successfully", HttpStatus.CREATED);
        }catch (Exception e){
            throw  new ResourceNotFoundException("create");
        }


    }


    @GetMapping("/getby/{id}")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<Client> getClientById(@PathVariable Long id) {
        try{
            Client client = clientService.getClientById(id);

            if (client == null) {
                throw new ResourceNotFoundException("getby", "id", id);
            }
            return new ResponseEntity<>(client, HttpStatus.OK);

        }catch (EntityNotFoundException exception){
            throw  new ResourceNotFoundException("getby", "id", id);
        }

    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> update(@PathVariable Long id, @Valid @RequestBody ClientDTO clientDTO) {

        try{
            clientService.updateClient(clientDTO);
            return new ResponseEntity<>("Client updated successfully", HttpStatus.OK);
        }catch (EntityNotFoundException exception){
            throw  new ResourceNotFoundException("update", "id", id);
        }


    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {

        try {
            Client client = clientService.getClientById(id);

            if (client == null) {
                throw new ResourceNotFoundException("delete", "id", id);
            }

            clientService.delete(id);
            return new ResponseEntity<>("Client deleted successfully", HttpStatus.OK);

        } catch (EntityNotFoundException exception) {
            throw new ResourceNotFoundException("delete", "id", id);
        }

    }



                                    //JWT

    @PostMapping("/login")
    public RefreshTokenDTO tokenAuthentication (@RequestBody ClientDTO clientDTO){

       Authentication authentication =
               authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(clientDTO.getName(), clientDTO.getPassword()));
            if (authentication.isAuthenticated()){
                RefreshToken refreshToken = refreshTokenService.createTokenRefresh(clientDTO.getName());

               return RefreshTokenDTO.builder()
                        .accessToken(jwtService.generateTokenMethod(clientDTO.getName()))
                        .token(refreshToken.getToken())
                        .build();
            }else {
                throw new UsernameNotFoundException("invalid request");
            }


    }

    //JWT Token Refresh
    @PostMapping("/refreshToken")
    public RefreshTokenDTO refreshToken(@RequestBody RefreshRequest refreshRequest){

        return refreshTokenService.findByToken(refreshRequest.getToken())
            .map(refreshTokenService::verifyExpiration)
            .map(RefreshToken::getClient)
            .map(client -> {
                String accessToken = jwtService.generateTokenMethod(client.getName());
                return RefreshTokenDTO.builder()
                        .accessToken(accessToken)
                        .token(refreshRequest.getToken())
                        .build();
            }).orElseThrow(() -> new RuntimeException("Token is not in the database"));

    }


}

