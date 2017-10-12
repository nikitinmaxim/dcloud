package org.dclou.example.demogpb.order;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableList;
import org.dclou.example.demogpb.order.data.jpa.entity.Order;
import org.dclou.example.demogpb.order.data.jpa.entity.OrderLine;
import org.dclou.example.demogpb.order.data.jpa.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.List;

@SpringBootApplication
@EnableDiscoveryClient
@EnableCircuitBreaker
public class OrderTestApp {


    private OrderRepository orderRepository;

    @Autowired
	public OrderTestApp(OrderRepository orderRepository) {
		this.orderRepository = orderRepository;
	}

	@Bean
	RestTemplate restTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        List<HttpMessageConverter<?>> converters = restTemplate.getMessageConverters();
        for (HttpMessageConverter<?> converter : converters) {
            if (converter instanceof MappingJackson2HttpMessageConverter) {
                MappingJackson2HttpMessageConverter jsonConverter = (MappingJackson2HttpMessageConverter) converter;
                jsonConverter.setObjectMapper(new ObjectMapper());
                jsonConverter.setSupportedMediaTypes(ImmutableList.of(new MediaType("application", "json", MappingJackson2HttpMessageConverter.DEFAULT_CHARSET), new MediaType("text", "javascript", MappingJackson2HttpMessageConverter.DEFAULT_CHARSET)));
            }
        }
        return restTemplate;

    }

    @PostConstruct
	public void generateTestData() {
        Order o = new Order();
        o.setCustomerId(2);

        OrderLine line = new OrderLine();
        line.setCount(3);
        line.setItemId(1);

        o.getOrderLine().add(line);

        orderRepository.save(o);
    }

    @PreDestroy
    public void cleanUp() {
    }

	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(OrderTestApp.class);
		app.setAdditionalProfiles("test");
		app.run(args);
	}

}
