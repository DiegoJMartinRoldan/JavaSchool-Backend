package es.javaschool.springbootosisfinal_task.services;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import es.javaschool.springbootosisfinal_task.domain.Client;
import es.javaschool.springbootosisfinal_task.domain.OrderHasProduct;
import es.javaschool.springbootosisfinal_task.domain.Orders;
import es.javaschool.springbootosisfinal_task.domain.Product;
import es.javaschool.springbootosisfinal_task.dto.CartProductDTO;
import es.javaschool.springbootosisfinal_task.dto.OrdersDTO;
import es.javaschool.springbootosisfinal_task.dto.ProductDTO;
import es.javaschool.springbootosisfinal_task.dto.ProductQuantityDto;
import es.javaschool.springbootosisfinal_task.repositories.OrderHasProductRepository;
import es.javaschool.springbootosisfinal_task.repositories.OrdersRepository;
import es.javaschool.springbootosisfinal_task.services.clientAddresServices.ClientAddressService;
import es.javaschool.springbootosisfinal_task.services.clientServices.ClientService;
import es.javaschool.springbootosisfinal_task.services.orderHasProductServices.OrderHasProductService;
import es.javaschool.springbootosisfinal_task.services.ordersServices.OrdersService;
import es.javaschool.springbootosisfinal_task.services.productServices.ProductMapper;
import es.javaschool.springbootosisfinal_task.services.productServices.ProductService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ShoppingCartService {

    private static final Logger log = LoggerFactory.getLogger(ShoppingCartService.class);

    @Autowired
    private ClientService clientService;

    @Autowired
    private OrdersService ordersService;

    @Autowired
    private ProductService productService;

    @Autowired
    private OrderHasProductRepository orderHasProductRepository;

    @Autowired
    private ClientAddressService clientAddressService;

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private OrdersRepository ordersRepository;

    @Autowired
    private HttpServletRequest request;



    // Add to cart for authenticated and no authenticated users
    public void addToCart(CartProductDTO cartProductDTO, HttpServletResponse response) {
        Long clientId = cartProductDTO.getClientId();
        Long clientAddressId = cartProductDTO.getClientAddressId();

        if (clientId != null && clientAddressId != null) {
            // Authenticated
            log.info("Authenticated user. ClientId: {} ", clientId);
            log.info("Authenticated user. ClientAddressId: {} ", clientAddressId);
            handleAuthenticatedUser(clientId, clientAddressId, cartProductDTO);
        } else {
            // Not Authenticated
            log.info("Unauthenticated user.");
            handleUnAuthenticatedUser(cartProductDTO, response);
        }
    }



    // Authenticated
    private void handleAuthenticatedUser(Long clientId, Long clientAddressId, CartProductDTO cartProductDTO) {
        // Search current orders
        Client client = clientService.getClientById(clientId);
        Optional<Orders> pendingOrder = ordersRepository.findByClientAndOrderStatus(client, "PENDING");

        if (pendingOrder.isPresent()) {
            // Use current order
            connectProductToOrders(pendingOrder.get(), cartProductDTO.getProducts(), cartProductDTO.getQuantities());
        } else {
            // Create new order
            OrdersDTO ordersDTO = new OrdersDTO();
            ordersDTO.setClient(client);
            ordersDTO.setClientsAddress(clientAddressService.getClientAddressById(clientAddressId));
            ordersDTO.setOrderStatus("PENDING");
            ordersDTO.setPaymentStatus("PENDING");


            Date currentDate = Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant());
            ordersDTO.setOrderDate(currentDate);

            Orders newOrder = ordersService.createOrder(ordersDTO);

            connectProductToOrders(newOrder, cartProductDTO.getProducts(), cartProductDTO.getQuantities());
        }
    }




    //Not authenticated
    private void handleUnAuthenticatedUser(CartProductDTO cartProductDTO, HttpServletResponse httpServletResponse) {
        connectProductToCookie(cartProductDTO, httpServletResponse, request);
    }




    // Product - Order (create orderHasProduct)
     private void connectProductToOrders(Orders orders, List<ProductDTO> productDTOList, List<Integer> quantities) {
         for (int i = 0; i < productDTOList.size(); i++) {
             ProductDTO productDTO = productDTOList.get(i);
             Integer quantity = quantities.get(i);

             OrderHasProduct orderHasProduct = new OrderHasProduct();
             orderHasProduct.setOrders(orders);
             orderHasProduct.setProduct(productService.getProductById(productDTO.getId()));
             orderHasProduct.setQuantity(quantity);
             orderHasProductRepository.save(orderHasProduct);
             log.info("Product connected to Order. ProductId: {}, OrderId: {}", productDTO.getId(), orders.getId());
         }
     }



    // Cookies
    private void connectProductToCookie(CartProductDTO cartProductDTO, HttpServletResponse httpServletResponse, HttpServletRequest httpServletRequest) {
        Map<Long, Integer> productQuantityMap = new HashMap<>();

        // Existing cookie
        Cookie[] cookies = httpServletRequest.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("cart")) {
                    try {
                        byte[] decodedBytes = Base64.getDecoder().decode(cookie.getValue());
                        String decodedValue = new String(decodedBytes);

                        Map<String, Object> existingMap = new ObjectMapper().readValue(decodedValue, Map.class);

                        existingMap.forEach((key, value) -> productQuantityMap.put(Long.parseLong(key), Integer.parseInt(value.toString())));
                    } catch (JsonProcessingException | NumberFormatException e) {
                        e.printStackTrace();
                    }
                    break;
                }
            }
        }

        // Add Products
        for (int i = 0; i < cartProductDTO.getProducts().size(); i++) {
            ProductDTO productDTO = cartProductDTO.getProducts().get(i);
            Integer quantity = cartProductDTO.getQuantities().get(i);

            Product product = productService.getProductById(productDTO.getId());
            productQuantityMap.put(product.getId(), quantity);
        }

        // Update cookie adding new products to the shopping cart
        Cookie cartCookie = new Cookie("cart", "");
        cartCookie.setMaxAge(24 * 60 * 60);

        try {
            String base64Value = Base64.getEncoder().encodeToString(new ObjectMapper().writeValueAsBytes(productQuantityMap));
            cartCookie.setValue(base64Value);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        // Set update cookie
        httpServletResponse.addCookie(cartCookie);
        log.info("Cookie added. CookieName: {}, CookieValue: {}", cartCookie.getName(), cartCookie.getValue());
    }




    // Get Products + Quantities
    public List<ProductQuantityDto> getProductsWithQuantities(Long clientId) {
        List<OrderHasProduct> orderHasProducts = orderHasProductRepository.findOrderHasProductsByClientId(clientId);

        return orderHasProducts.stream()
                .map(orderHasProduct -> {
                    ProductDTO productDTO = productMapper.convertEntityToDto(orderHasProduct.getProduct());
                    int quantity = orderHasProduct.getQuantity();
                    return new ProductQuantityDto(productDTO, quantity);
                })
                .collect(Collectors.toList());
    }

}
