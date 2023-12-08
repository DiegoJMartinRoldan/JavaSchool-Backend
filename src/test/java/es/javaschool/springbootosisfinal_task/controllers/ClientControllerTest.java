//package es.javaschool.springbootosisfinal_task.controllers;
//
//import es.javaschool.springbootosisfinal_task.config.jwt.RefreshRequest;
//import es.javaschool.springbootosisfinal_task.config.jwt.RefreshToken;
//import es.javaschool.springbootosisfinal_task.config.jwt.RefreshTokenDTO;
//import es.javaschool.springbootosisfinal_task.config.security.ChangePasswordRequest;
//import es.javaschool.springbootosisfinal_task.domain.Client;
//import es.javaschool.springbootosisfinal_task.dto.CartProductDTO;
//import es.javaschool.springbootosisfinal_task.dto.ClientDTO;
//import es.javaschool.springbootosisfinal_task.dto.ProductQuantityDto;
//import es.javaschool.springbootosisfinal_task.services.clientServices.ClientService;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//
//import java.util.List;
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.ArgumentMatchers.anyLong;
//
//@ExtendWith(MockitoExtension.class)
//class ClientControllerTest {
//
//    @InjectMocks
//    private ClientController clientController;
//
//    @Mock
//    private ClientService clientService;
//
//    // Test para el método "listAll"
//    @Test
//    void testListAll() {
//        // Configuración de datos de prueba
//        when(clientService.listAll()).thenReturn(/* Lista de datos de prueba */);
//
//        // Llamada al método del controlador
//        ResponseEntity<List<ClientDTO>> responseEntity = clientController.listAll();
//
//        // Verificación de resultados
//        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
//        // Agrega más aserciones según la lógica específica de tu aplicación
//    }
//
//    // Test para el método "createClient"
//    @Test
//    void testCreateClient() {
//        // Configuración de datos de prueba
//        ClientDTO clientDTO = /* Configura tu objeto ClientDTO de prueba */;
//        when(clientService.clientAlreadyExist(clientDTO.getEmail())).thenReturn(/* Cliente existente o vacío */);
//
//        // Llamada al método del controlador
//        ResponseEntity<String> responseEntity = clientController.createClient(clientDTO);
//
//        // Verificación de resultados
//        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
//        // Agrega más aserciones según la lógica específica de tu aplicación
//    }
//
//    // Test para el método "getClientById"
//    @Test
//    void testGetClientById() {
//        // Configuración de datos de prueba
//        Long clientId = 1L;
//        Client client = /* Configura tu objeto Client de prueba */;
//        when(clientService.getClientById(clientId)).thenReturn(client);
//
//        // Llamada al método del controlador
//        ResponseEntity<Client> responseEntity = clientController.getClientById(clientId);
//
//        // Verificación de resultados
//        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
//        // Agrega más aserciones según la lógica específica de tu aplicación
//    }
//
//    // Test para el método "update"
//    @Test
//    void testUpdate() {
//        // Configuración de datos de prueba
//        Long clientId = 1L;
//        ClientDTO clientDTO = /* Configura tu objeto ClientDTO de prueba */;
//
//        // Simula el comportamiento del servicio
//        doNothing().when(clientService).updateClient(clientDTO);
//
//        // Llamada al método del controlador
//        ResponseEntity<String> responseEntity = clientController.update(clientId, clientDTO);
//
//        // Verificación de resultados
//        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
//        // Agrega más aserciones según la lógica específica de tu aplicación
//    }
//
//    // Test para el método "delete"
//    @Test
//    void testDelete() {
//        // Configuración de datos de prueba
//        Long clientId = 1L;
//
//        // Simula el comportamiento del servicio
//        doNothing().when(clientService).delete(clientId);
//
//        // Llamada al método del controlador
//        ResponseEntity<String> responseEntity = clientController.delete(clientId);
//
//        // Verificación de resultados
//        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
//        // Agrega más aserciones según la lógica específica de tu aplicación
//    }
//
//    @Test
//    void testTokenAuthentication() {
//        // Configuración de datos de prueba
//        ClientDTO clientDTO = /* Configura tu objeto ClientDTO de prueba */;
//        when(clientService.getClientRole(clientDTO.getEmail())).thenReturn(/* Rol de prueba */);
//        when(clientService.getClientIdByEmail(clientDTO.getEmail())).thenReturn(/* ID de cliente de prueba */);
//        // Simula el comportamiento del servicio de autenticación
//        when(authenticationManager.authenticate(any())).thenReturn(/* Simula una autenticación exitosa */);
//
//        // Llamada al método del controlador
//        RefreshTokenDTO refreshTokenDTO = clientController.tokenAuthentication(clientDTO);
//
//        // Verificación de resultados
//        // Asegúrate de verificar que los valores de refreshTokenDTO sean los esperados
//    }
//
//    // Test para el método "refreshToken"
//    @Test
//    void testRefreshToken() {
//        // Configuración de datos de prueba
//        RefreshRequest refreshRequest = /* Configura tu objeto RefreshRequest de prueba */;
//        RefreshToken refreshToken = /* Configura tu objeto RefreshToken de prueba */;
//        when(refreshTokenService.findByToken(refreshRequest.getToken())).thenReturn(Optional.of(refreshToken));
//        when(refreshTokenService.verifyExpiration(refreshToken)).thenReturn(refreshToken);
//
//        // Llamada al método del controlador
//        RefreshTokenDTO refreshTokenDTO = clientController.refreshToken(refreshRequest);
//
//        // Verificación de resultados
//        // Asegúrate de verificar que los valores de refreshTokenDTO sean los esperados
//    }
//
//    // Test para el método "changePassword"
//    @Test
//    void testChangePassword() {
//        // Configuración de datos de prueba
//        ChangePasswordRequest changePasswordRequest = /* Configura tu objeto ChangePasswordRequest de prueba */;
//        Client client = /* Configura tu objeto Client de prueba */;
//        when(clientRepository.findByEmail(changePasswordRequest.getEmail())).thenReturn(Optional.of(client));
//        when(clientService.oldPasswordIsValid(client, changePasswordRequest.getOldPwd())).thenReturn(true);
//
//        // Llamada al método del controlador
//        String result = clientController.changePassword(changePasswordRequest);
//
//        // Verificación de resultados
//        assertEquals("Password changed", result);
//        // Agrega más aserciones según la lógica específica de tu aplicación
//    }
//
//    // Test para el método "addToCart"
//    @Test
//    void testAddToCart() {
//        // Configuración de datos de prueba
//        CartProductDTO cartProductDTO = /* Configura tu objeto CartProductDTO de prueba */;
//        HttpServletResponse response = /* Configura tu objeto HttpServletResponse de prueba */;
//        when(shoppingCartService.addToCart(cartProductDTO, response)).thenReturn(/* Resultado de la operación de prueba */);
//
//        // Llamada al método del controlador
//        ResponseEntity<String> responseEntity = clientController.addToCart(cartProductDTO, response);
//
//        // Verificación de resultados
//        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
//        // Agrega más aserciones según la lógica específica de tu aplicación
//    }
//
//    // Test para el método "getShoppingCart"
//    @Test
//    void testGetShoppingCart() {
//        // Configuración de datos de prueba
//        HttpServletRequest request = /* Configura tu objeto HttpServletRequest de prueba */;
//        Authentication authentication = /* Configura tu objeto Authentication de prueba */;
//        when(shoppingCartService.getProductsWithQuantities(anyLong())).thenReturn(/* Lista de productos con cantidades de prueba */);
//
//        // Llamada al método del controlador
//        ResponseEntity<List<ProductQuantityDto>> responseEntity = clientController.getShoppingCart(request, authentication);
//
//        // Verificación de resultados
//        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
//        // Agrega más aserciones según la lógica específica de tu aplicación
//    }
//}