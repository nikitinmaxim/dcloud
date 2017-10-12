package org.dclou.example.demogpb.order.service;

import org.dclou.example.demogpb.order.domain.CatalogItem;
import org.dclou.example.demogpb.order.domain.Customer;
import org.dclou.example.demogpb.order.domain.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("orderService")
public class OrderServiceImpl implements OrderService {

    @Value("${catalog.url}")
    private String catalogUrl;

    @Value("${customer.url}")
    private String customerUrl;

    @Value("${order.url}")
    private String orderUrl;

    @Autowired
    private OAuth2RestTemplate restTemplate;

    public OrderServiceImpl() {
    }

    public List<CatalogItem> getCatalog() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);

        HttpEntity<?> request = new HttpEntity<>(headers);

        ResponseEntity<List<CatalogItem>> response = restTemplate.exchange(catalogUrl,
                HttpMethod.GET, request, new ParameterizedTypeReference<List<CatalogItem>>() {});

        return response.getBody();
    }

    @Override
    public List<Customer> getCustomers() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);

        HttpEntity<?> request = new HttpEntity<>(headers);

        ResponseEntity<List<Customer>> response = restTemplate.exchange(customerUrl,
                HttpMethod.GET, request, new ParameterizedTypeReference<List<Customer>>() {});

        return response.getBody();
    }

    @Override
    public List<Order> getOrders() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);

        HttpEntity<?> request = new HttpEntity<>(headers);

        ResponseEntity<List<Order>> response = restTemplate.exchange(orderUrl,
                HttpMethod.GET, request, new ParameterizedTypeReference<List<Order>>() {});

        return response.getBody();
    }

    @Override
    public String saveOrder(Order order) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", MediaType.APPLICATION_JSON_VALUE);
        headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);

        HttpEntity<Order> request = new HttpEntity<>(order, headers);

        return restTemplate.postForObject(orderUrl, request, String.class);
    }

    @Override
    public void deleteOrder(long orderId) {
        restTemplate.delete(orderUrl + "/" + String.valueOf(orderId));
    }
}
