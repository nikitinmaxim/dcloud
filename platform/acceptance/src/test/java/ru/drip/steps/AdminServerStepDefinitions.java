package ru.drip.steps;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ContextConfiguration;
import ru.drip.config.AcceptanceSpringConfiguration;
import ru.drip.config.RestWorker;

import static org.junit.Assert.assertTrue;

@ContextConfiguration(classes = AcceptanceSpringConfiguration.class)
public class AdminServerStepDefinitions {

    @Autowired
    private RestWorker restTemplate;

    @Value("${ADMIN_APPS_URL}")
    private String url;

    @Given("^DClou Stack is up and running$")
    public void dclou_Stack_is_up_and_running() throws Throwable {
    }

    @When("^Admin server container is ready$")
    public void admin_server_container_is_ready() throws Throwable {
    }

    @Then("^Send checking requests to Admin Server$")
    public void send_checking_requests_to_Admin_Server() throws Throwable {
        System.out.println("ADMIN_APPS_URL=" + url);

        String metaData = restTemplate.getNetContents(url);

        if (metaData != null) {
            JSONObject jObj = new JSONObject(String.format("{\"array\": %s}", metaData.trim()));
            assertTrue(jObj.getJSONArray("array").length() > 0);
        }
    }
}
