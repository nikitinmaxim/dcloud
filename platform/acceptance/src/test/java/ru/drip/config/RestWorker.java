package ru.drip.config;

import org.apache.commons.codec.binary.Base64;
import org.json.JSONObject;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.Charset;
import java.util.Arrays;

public class RestWorker extends RestTemplate {
    public String getNetContents(String url) {
        String responseBody = null;
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Arrays.asList(MediaType.TEXT_HTML, MediaType.APPLICATION_JSON));
            HttpEntity<String> entity = new HttpEntity<String>("", headers);

            ResponseEntity<String> response = exchange(url, HttpMethod.GET, entity, String.class);
            responseBody = response.getBody();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return responseBody;
    }

    public String getToken(String url, String user, String passwd, String credentialName, String credentialPassword) {
        String accessToken = null;
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Arrays.asList(MediaType.TEXT_PLAIN,
                    MediaType.TEXT_HTML,
                    MediaType.APPLICATION_JSON));
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

            String auth = credentialName + ":" + credentialPassword;
            byte[] encodedAuth = Base64.encodeBase64(auth.getBytes(Charset.forName("US-ASCII")) );
            headers.set("Authorization", "Basic " + new String(encodedAuth));
            HttpEntity<String> entity = new HttpEntity<String>(
                    String.format("grant_type=password&username=%s&password=%s", user, passwd),
                    headers);

            ResponseEntity<String> response = exchange(url, HttpMethod.POST, entity, String.class);
            if (response != null && response.getStatusCode() == HttpStatus.OK) {
                JSONObject jObj = new JSONObject(response.getBody().trim());
                accessToken = jObj.getString("access_token");
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return accessToken;
    }
}
