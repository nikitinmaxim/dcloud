package ru.drip.steps;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ContextConfiguration;
import ru.drip.config.AcceptanceSpringConfiguration;
import ru.drip.config.RestWorker;
import ru.drip.config.SessionControlBlock;

import static org.junit.Assert.assertNotNull;

@ContextConfiguration(classes = AcceptanceSpringConfiguration.class)
public class AuthServerStepDefinitions {

    @Autowired
    private RestWorker restTemplate;

    @Autowired
    private SessionControlBlock sessionControlBlock;

    @Value("${AUTH_TOKEN_URL}")
    private String url;

    @Given("^DClou Stack is up and running$")
    public void dclou_Stack_is_up_and_running() throws Throwable {
    }

    @When("^Auth Service container is ready for the user \"([^\"]*)\" and password \"([^\"]*)\" by credentials \"([^\"]*)\" and \"([^\"]*)\"$")
    public void auth_Service_container_is_ready_with_user_and_password(String username,
                                                                       String password,
                                                                       String credentialName,
                                                                       String credentialPassword) throws Throwable {
        System.out.println("AUTH_TOKEN_URL=" + url);

        if (sessionControlBlock.getAccessToken() == null) {
            String token = restTemplate.getToken(url, username, password, credentialName, credentialPassword);

            System.out.println("ACCESS TOKEN=" + token);

            sessionControlBlock.setAccessToken(token);
            assertNotNull(sessionControlBlock.getAccessToken());
        }
    }
}
