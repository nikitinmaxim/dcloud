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
public class CatalogServerStepDefinitions {

    @Autowired
    private RestWorker restTemplate;

    @Autowired
    private SessionControlBlock sessionControlBlock;

    @Value("${CATALOG_LIST_URL}")
    private String url;

    @Then("^Send checking requests to Catalog Service$")
    public void send_checking_requests_to_Catalog_Service() throws Throwable {
        System.out.println("CATALOG_LIST_URL=" + url);

        assertNotNull(sessionControlBlock.getAccessToken());
        String catalogList = restTemplate.getSecureContents(url, HttpMethod.GET, sessionControlBlock.getAccessToken());
        assertNotNull(catalogList);
        JSONObject jObj = new JSONObject(String.format("{\"list\":%s}", catalogList.trim()));
        assertEquals(jObj.getJSONArray("list").length(), 4);
    }

}
