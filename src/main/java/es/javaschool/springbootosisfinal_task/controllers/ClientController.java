package es.javaschool.springbootosisfinal_task.controllers;
import es.javaschool.springbootosisfinal_task.config.security.ChangePasswordRequest;
import es.javaschool.springbootosisfinal_task.domain.Client;
import es.javaschool.springbootosisfinal_task.config.jwt.RefreshToken;
import es.javaschool.springbootosisfinal_task.domain.ClientsAddress;
import es.javaschool.springbootosisfinal_task.domain.Orders;
import es.javaschool.springbootosisfinal_task.domain.Product;
import es.javaschool.springbootosisfinal_task.dto.ClientDTO;
import es.javaschool.springbootosisfinal_task.config.jwt.RefreshRequest;
import es.javaschool.springbootosisfinal_task.config.jwt.RefreshTokenDTO;
import es.javaschool.springbootosisfinal_task.dto.ProductDTO;
import es.javaschool.springbootosisfinal_task.exception.ResourceNotFoundException;
import es.javaschool.springbootosisfinal_task.config.jwt.RefreshTokenService;
import es.javaschool.springbootosisfinal_task.repositories.ClientRepository;
import es.javaschool.springbootosisfinal_task.services.clientServices.ClientService;
import es.javaschool.springbootosisfinal_task.config.jwt.JwtService;
import es.javaschool.springbootosisfinal_task.services.productServices.ProductService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.parameters.P;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;


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

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private ProductService productService;


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
        String email = clientDTO.getEmail();
        Optional<Client> existingClient = clientService.clientAlreadyExist(email);

        if (existingClient.isPresent()) {
            return new ResponseEntity<>("User already exists with the provided credentials", HttpStatus.CONFLICT);
        } else {
            try {
                clientService.createClient(clientDTO);
                return new ResponseEntity<>("Client created successfully", HttpStatus.CREATED);
            } catch (Exception e) {
                throw new ResourceNotFoundException("create");
            }
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
    @PreAuthorize("hasAuthority('ROLE_USER')")
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
                authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(clientDTO.getEmail(), clientDTO.getPassword()));
        if (authentication.isAuthenticated()){
            RefreshToken refreshToken = refreshTokenService.createTokenRefresh(clientDTO.getEmail());
            return RefreshTokenDTO.builder()
                    .accessToken(jwtService.generateTokenMethod(clientDTO.getEmail()))
                    .token(refreshToken.getToken())
                    .role(clientService.getClientRole(clientDTO.getEmail()))
                    .id(clientService.getClientIdByEmail(clientDTO.getEmail()))
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
                    String accessToken = jwtService.generateTokenMethod(client.getEmail());
                    return RefreshTokenDTO.builder()
                            .accessToken(accessToken)
                            .token(refreshRequest.getToken())
                            .role(clientService.getClientRole(client.getEmail()))
                            .id(clientService.getClientIdByEmail(client.getEmail()))
                            .build();
                }).orElseThrow(() -> new RuntimeException("Token is not in the database"));

    }


                                //Change Password

    //Método para cambiar la contraseña en el controlador, llamará al service con el método correspondiente para comprobar si la antigua contraseña coincide con la nueva
    @PostMapping("/changePassword")
    //Obtenemos como parámetros en este método la request que te piden para hacer la solicitud de cambio, que es el email, la contraseña y la nueva contraseña
    public String changePassword(@RequestBody ChangePasswordRequest changePasswordRequest){

        //Instanciamos cliente y decimos que cliente es igual a: llamamos al repositorio que nos busque por email y como tiene que decibir un parametro, recibe el que le hemos enviado en el request
        Client client = clientRepository.findByEmail(changePasswordRequest.getEmail()).get();

        //Si el método de verificar la contraseña que recibe nua instancia de cliente y la contraseña de la request y las compara para ver si son la misma mediante el metodo OldPassword
        //Si no es valido todos lo anterior:
        if (!clientService.oldPasswordIsValid(client, changePasswordRequest.getOldPwd())){
           //Revisar esto con excepción personalizada
            return "Incorrect old password";
        }
        //Si es valida la contraseña ejecuta el método changePassword para cambiar la contraseña
        clientService.changePassword(client, changePasswordRequest.getNewPwd());
        return "Password changed";
    }

                                                 //Shopping Cart


    @PostMapping("/addToCart")
    public ResponseEntity<String> addToCart(@RequestBody ProductDTO productDTO, HttpSession session) {

        // Get Product By id
        Product product = productService.getProductById(productDTO.getId());
        if (product == null) {
            throw new RuntimeException("Product not available");
        }

        // Map Product in a shopping cart or create a new shoping cart
        Map<Long, Product> cartProductMap = (Map<Long, Product>) session.getAttribute("cartProductMap");
        if (cartProductMap == null) {
            cartProductMap = new HashMap<>();
            session.setAttribute("cartProductMap", cartProductMap);
        }

        // Add product to the shopping cart
        cartProductMap.put(product.getId(), product);

        return new ResponseEntity<>("Product added to cart successfully", HttpStatus.OK);
    }

    @GetMapping("/cart")
    public ResponseEntity<Map<Long, Product>> getShoppingCart(HttpSession session){
        Map<Long, Product> content = (Map<Long, Product>) session.getAttribute("cartProductMap");
        if (content == null){
            throw new ResourceNotFoundException("cart");
        }
        return new ResponseEntity<>(content, HttpStatus.OK);
    }


    //Checkout

 // @PostMapping("/checkout")
 // @PreAuthorize("hasAuthority('ROLE_USER')")
 // public ResponseEntity<String> checkout(HttpSession session, @RequestBody ClientsAddress clientAddress) {
 //
 //     Map<Long, Product> cartProductMap = (Map<Long, Product>) session.getAttribute("cartProductMap");
 //
 //     Orders newOrder = new Orders();
 //
 //     ordersService.createOrder(newOrder);
//
 //
 //     session.removeAttribute("cartProductMap");
//
 //     return new ResponseEntity<>("Order placed successfully", HttpStatus.OK);
 // }
//
 // @PostMapping("/checkout")
 // @PreAuthorize("hasAuthority('ROLE_USER')")
 // public ResponseEntity<String> checkout(HttpSession session, @RequestBody ClientAddress clientAddress) {
 //
 //     Map<Long, Product> cartProductMap = (Map<Long, Product>) session.getAttribute("cartProductMap");
 //
 //     Order newOrder = new Order();
 //     newOrder.setClient(clientService.getCurrentClient());
 //     newOrder.setClientAddress(clientAddress);
 //     List<OrderHasProduct> orderItems = new ArrayList<>();
 //     for (Map.Entry<Long, Product> entry : cartProductMap.entrySet()) {
 //         Product product = entry.getValue();
 //     }
 //     newOrder.setOrderItems(orderItems);
 //
 //     session.removeAttribute("cartProductMap");
//
 //     return new ResponseEntity<>("Order placed successfully", HttpStatus.OK);
 // }
//

}

