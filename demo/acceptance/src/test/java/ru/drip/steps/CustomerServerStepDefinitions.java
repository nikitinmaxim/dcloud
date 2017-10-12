package ru.drip.steps;

import cucumber.api.java.en.Then;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.test.context.ContextConfiguration;
import ru.drip.config.AcceptanceSpringConfiguration;
import ru.drip.config.RestWorker;
import ru.drip.config.SessionControlBlock;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@ContextConfiguration(classes = AcceptanceSpringConfiguration.class)
public class CustomerServerStepDefinitions {

    @Autowired
    private RestWorker restTemplate;

    @Autowired
    private SessionControlBlock sessionControlBlock;

    @Value("${CUSTOMER_LIST_URL}")
    private String url;

    @Then("^Send checking requests to Customer Service$")
    public void send_checking_requests_to_Customer_Service() throws Throwable {
        System.out.println("CUSTOMER_LIST_URL=" + url);

        assertNotNull(sessionControlBlock.getAccessToken());
        String customerList = restTemplate.getSecureContents(url, HttpMethod.GET, sessionControlBlock.getAccessToken());
        assertNotNull(customerList);
        JSONObject jObj = new JSONObject(String.format("{\"list\":%s}", customerList.trim()));
        assertEquals(jObj.getJSONArray("list").length(), 2);
    }

}
