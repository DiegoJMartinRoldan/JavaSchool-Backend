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
    private ProductService productService;


    @Autowired
    private HttpServletRequest request;



    // Add to cart for authenticated and no authenticated users
    public void addToCart(CartProductDTO cartProductDTO, HttpServletResponse response) {
            handleUnAuthenticatedUser(cartProductDTO, response);

    }




    //Not authenticated
    private void handleUnAuthenticatedUser(CartProductDTO cartProductDTO, HttpServletResponse httpServletResponse) {
        connectProductToCookie(cartProductDTO, httpServletResponse, request);
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

    public void deleteProductFromCart(ProductQuantityDto productQuantityDto, HttpServletRequest request, HttpServletResponse response) {
        try {
            if (productQuantityDto != null && productQuantityDto.getProductDTO() != null) {
                Map<Long, Integer> cartProductMap = getCartProductMap(request);

                ProductDTO productDTO = productQuantityDto.getProductDTO();
                Long productId = productDTO.getId();

                if (cartProductMap.containsKey(productId)) {
                    cartProductMap.remove(productId);

                    // Actualizar la cookie con el nuevo mapa de productos
                    updateCartCookie(cartProductMap, response);

                    // Log de éxito
                    log.info("Product removed from cart successfully. ProductId: {}", productId);
                } else {
                    // Log si el producto no está en el carrito
                    log.warn("Product with ID {} not found in the cart. No action taken.", productId);
                }
            } else {
                // Log si ProductQuantityDto o ProductDTO son null
                log.warn("ProductQuantityDto or ProductDTO is null. No action taken.");
            }
        } catch (Exception e) {
            // Log de error
            log.error("Error removing product from cart", e);
            throw new RuntimeException("Error removing product from cart", e);
        }
    }




    // Otros métodos del servicio

    private Map<Long, Integer> getCartProductMap(HttpServletRequest request) {
        Map<Long, Integer> cartProductMap = new HashMap<>();

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

        return cartProductMap;
    }

    private void updateCartCookie(Map<Long, Integer> cartProductMap, HttpServletResponse response) {
        Cookie cartCookie = new Cookie("cart", "");
        cartCookie.setMaxAge(24 * 60 * 60);

        try {
            String base64Value = Base64.getEncoder().encodeToString(new ObjectMapper().writeValueAsBytes(cartProductMap));
            cartCookie.setValue(base64Value);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        response.addCookie(cartCookie);
    }











    //  public void removeFromCart(CartProductDTO cartProductDTO, HttpServletResponse response) {
//
  //      Long clientId = cartProductDTO.getClientId();
  //      Long clientAddressId = cartProductDTO.getClientAddressId();
  //      List<ProductDTO> products = cartProductDTO.getProducts();
//
  //      if (clientId != null && clientAddressId != null) {
  //          // Usuario autenticado
  //          // Lógica para eliminar productos del carrito para usuarios autenticados
  //          // Esto podría implicar eliminar registros de la base de datos, actualizar una lista en memoria, etc.
  //          for (ProductDTO product : products) {
  //              productService.removeProductFromCart(clientId, clientAddressId, product.getId());
  //          }
  //      } else {
  //          // Usuario no autenticado
  //          // Lógica para eliminar productos del carrito para usuarios no autenticados
  //          // Esto podría implicar actualizar la cookie, eliminar registros de la base de datos, etc.
  //          connectProductToCookie(cartProductDTO, response, null);
  //          // Actualizar la cookie después de la eliminación
  //          updateCartCookie(response);
  //      }
//
  //  }
}
