package es.javaschool.springbootosisfinal_task.controllers;
import aj.org.objectweb.asm.TypeReference;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import es.javaschool.springbootosisfinal_task.config.security.ChangePasswordRequest;
import es.javaschool.springbootosisfinal_task.config.security.ClientToUserDetails;
import es.javaschool.springbootosisfinal_task.domain.*;
import es.javaschool.springbootosisfinal_task.config.jwt.RefreshToken;
import es.javaschool.springbootosisfinal_task.dto.CartProductDTO;
import es.javaschool.springbootosisfinal_task.dto.ClientDTO;
import es.javaschool.springbootosisfinal_task.config.jwt.RefreshRequest;
import es.javaschool.springbootosisfinal_task.config.jwt.RefreshTokenDTO;
import es.javaschool.springbootosisfinal_task.dto.ProductDTO;
import es.javaschool.springbootosisfinal_task.dto.ProductQuantityDto;
import es.javaschool.springbootosisfinal_task.exception.ResourceNotFoundException;
import es.javaschool.springbootosisfinal_task.config.jwt.RefreshTokenService;
import es.javaschool.springbootosisfinal_task.repositories.ClientRepository;
import es.javaschool.springbootosisfinal_task.repositories.OrderHasProductRepository;
import es.javaschool.springbootosisfinal_task.repositories.OrdersRepository;
import es.javaschool.springbootosisfinal_task.services.ShoppingCartService;
import es.javaschool.springbootosisfinal_task.services.clientServices.ClientService;
import es.javaschool.springbootosisfinal_task.config.jwt.JwtService;
import es.javaschool.springbootosisfinal_task.services.orderHasProductServices.OrderHasProductService;
import es.javaschool.springbootosisfinal_task.services.ordersServices.OrdersService;
import es.javaschool.springbootosisfinal_task.services.productServices.ProductService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.util.logging.Logger;


@RestController
@RequestMapping("${client.Controller.url}")
public class ClientController {
    private static final Logger logger = Logger.getLogger(ClientController.class.getName());
    private static final org.slf4j.Logger log = LoggerFactory.getLogger(ShoppingCartService.class);
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

    @Autowired
    private OrderHasProductService orderHasProductService;

    @Autowired
    private OrderHasProductRepository orderHasProductRepository;

    @Autowired
    private OrdersService ordersService;
    @Autowired
    private OrdersRepository ordersRepository;

    @Autowired
    private ShoppingCartService shoppingCartService;



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

    @PatchMapping("/update/{id}")
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


    @PostMapping("/changePassword")
    @PreAuthorize("hasAuthority('ROLE_USER')")

    public String changePassword(@RequestBody ChangePasswordRequest changePasswordRequest){

        Client client = clientRepository.findByEmail(changePasswordRequest.getEmail()).get();

        if (!clientService.oldPasswordIsValid(client, changePasswordRequest.getOldPwd())){
            //Revisar esto con excepción personalizada
            return "Incorrect old password";
        }
        clientService.changePassword(client, changePasswordRequest.getNewPwd());
        return "Password changed";
    }

                                                    //Add Products - Shopping Cart

    @PostMapping("/addToCart")
    @PreAuthorize("permitAll() or isAuthenticated()")
    public ResponseEntity<String> addToCart(@RequestBody CartProductDTO cartProductDTO, HttpServletResponse response) {
        shoppingCartService.addToCart(cartProductDTO, response);

        Long clientId = cartProductDTO.getClientId();
        Long clientAddressId = cartProductDTO.getClientAddressId();

        if (clientId != null && clientAddressId != null) {
            return new ResponseEntity<>("Product added to cart successfully, authenticated", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Product added to cart successfully, not authenticated", HttpStatus.OK);
        }
    }



    //View Shopping Cart
    @GetMapping("/cart")
    @PreAuthorize("permitAll() or isAuthenticated()")
    public ResponseEntity<List<ProductQuantityDto>> getShoppingCart(HttpServletRequest request, Authentication authentication) {
        Map<Long, Integer> cartProductMap = new HashMap<>();


        // Not Auth
        Cookie[] cookies = request.getCookies();

        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("cart")) {
                    try {
                        byte[] decodedBytes = Base64.getDecoder().decode(cookie.getValue());
                        String decodedValue = new String(decodedBytes);

                        Map<String, Object> tempMap = new ObjectMapper().readValue(decodedValue, Map.class);

                        tempMap.forEach((key, value) -> cartProductMap.put(Long.parseLong(key), Integer.parseInt(value.toString())));
                    } catch (JsonProcessingException | NumberFormatException e) {
                        e.printStackTrace();
                    }
                    break;
                }
            }
        }

        List<ProductQuantityDto> productList = new ArrayList<>();


        for (Map.Entry<Long, Integer> entry : cartProductMap.entrySet()) {
            Long productId = entry.getKey();
            Integer quantity = entry.getValue();

            ProductDTO productDTO = productService.getProductDTOById(productId);


            if (productDTO != null) {
                productList.add(new ProductQuantityDto(productDTO, quantity));
            }
        }

        // Auth Products
        if (authentication != null && authentication.isAuthenticated()) {
            String clientEmail = ((ClientToUserDetails) authentication.getPrincipal()).getUsername();
            Long clientId = clientService.getClientIdByEmail(clientEmail);

            List<ProductQuantityDto> productQuantities = shoppingCartService.getProductsWithQuantities(clientId);


            productList.addAll(productQuantities);
        }


        return new ResponseEntity<>(productList, HttpStatus.OK);
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

