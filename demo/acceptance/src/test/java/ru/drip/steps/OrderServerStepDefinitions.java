package ru.drip.steps;

import cucumber.api.java.en.Then;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.test.context.ContextConfiguration;
import ru.drip.config.AcceptanceSpringConfiguration;
import ru.drip.config.RestWorker;
import ru.drip.config.SessionControlBlock;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

@ContextConfiguration(classes = AcceptanceSpringConfiguration.class)
public class OrderServerStepDefinitions {

    @Autowired
    private RestWorker restTemplate;

    @Autowired
    private SessionControlBlock sessionControlBlock;

    @Value("${ORDER_LIST_URL}")
    private String url;

    @Then("^Send checking requests to Order Service$")
    public void send_checking_requests_to_Order_Service() throws Throwable {
        System.out.println("ORDER_LIST_URL=" + url);

        assertNotNull(sessionControlBlock.getAccessToken());
        String orderList = restTemplate.getSecureContents(url, HttpMethod.GET, sessionControlBlock.getAccessToken());
        assertThat(orderList, not(containsString("Новости Яндекса")));
    }
}
