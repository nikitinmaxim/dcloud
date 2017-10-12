package ru.drip.config;

import org.apache.commons.codec.binary.Base64;
import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.Charset;
import java.util.Arrays;

public class RestWorker extends RestTemplate {
    public String getNetContents(String url) {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.TEXT_HTML));
        HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);

        ResponseEntity<String> response = exchange(url, HttpMethod.GET, entity, String.class);
        String responseBody = response.getBody();
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

        }
        return accessToken;
    }

    public String getSecureContents(String url, HttpMethod method, String token) {
        String responseData = null;
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Arrays.asList(MediaType.TEXT_PLAIN,
                    MediaType.TEXT_HTML,
                    MediaType.APPLICATION_JSON));
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

            headers.set("Authorization", "Bearer " + new String(token));
            HttpEntity<String> entity = new HttpEntity<String>("", headers);
            ResponseEntity<String> response = null;
            do {
                response = exchange(url, method, entity, String.class);
                if (response != null && response.getStatusCode() == HttpStatus.FOUND) {
                    url = response.getHeaders().getLocation().toString();
                    response = exchange(url, HttpMethod.GET, entity, String.class);
                }
            } while (response != null && response.getStatusCode() == HttpStatus.FOUND);
            if (response != null && response.getStatusCode() == HttpStatus.OK) {
                responseData = response.getBody().trim();
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return responseData;
    }

}
