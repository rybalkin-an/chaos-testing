package com.github.rybalkin_an.app.toxiproxy;

import com.github.rybalkin_an.app.user.model.User;
import eu.rekawek.toxiproxy.Proxy;
import eu.rekawek.toxiproxy.ToxiproxyClient;
import eu.rekawek.toxiproxy.model.ToxicDirection;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class ToxiProxyTest {

    private ToxiproxyClient toxiproxyClient;
    private Proxy proxy;
    private User user;
    private String path = "/users";
    private RequestSpecification requestSpecification = new RequestSpecBuilder()
            .setContentType(ContentType.JSON)
            .setBaseUri("http://localhost")
            .setPort(8888)
            .setBasePath("api")
            .build();

    private ResponseSpecification responseSpecification = new ResponseSpecBuilder()
            .expectHeader("Location", containsString("/users/"))
            .expectBody("id", notNullValue())
            .expectBody("firstName", equalTo("John"))
            .expectBody("lastName", equalTo("Doe"))
            .expectBody("email", equalTo("john.doe@example.com"))
            .build();

    @BeforeEach
    public void setup() {
        user = new User();
        user.setEmail("john.doe@example.com");
        user.setLastName("Doe");
        user.setFirstName("John");

        toxiproxyClient = new ToxiproxyClient("localhost", 8474); // Default Toxiproxy server port

        try {
            // Get the proxy, or create it if it doesn't exist
            proxy = toxiproxyClient.getProxyOrNull("spring-boot-api-proxy");
            if (proxy == null) {
                proxy = toxiproxyClient.createProxy("spring-boot-api-proxy", "0.0.0.0:8888", "localhost:8080");
                System.out.println("Proxy created successfully.");
            } else {
                System.out.println("Proxy already exists.");
            }
        } catch (Exception e) {
            // Handle potential exceptions (e.g., connection issues or proxy creation issues)
            e.printStackTrace();
            throw new RuntimeException("Failed to set up Toxiproxy proxy", e);
        }
    }


    @AfterEach
    public void cleanup() {
        try {
            proxy.delete();
        }  catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testCreateUserWithoutNetworkIssues() {
        given(requestSpecification).body(user)
                .when().post(path)
                .then().statusCode(201).spec(responseSpecification);
    }

    @Test
    public void testCreateUserWithLatency() throws Exception {
        // Add 2 seconds of latency to the proxy
        proxy.toxics().latency("latency", ToxicDirection.DOWNSTREAM, 2000).setJitter(100);

        long startTime = System.currentTimeMillis();
        given(requestSpecification).body(user).log().all()
                .when().post(path)
                .then().statusCode(201).spec(responseSpecification);

        long endTime = System.currentTimeMillis();
        assertTrue((endTime - startTime) >= 2000, "Latency was not applied!");
    }

    @Test
    public void testCreateUserWithTimeout() throws Exception {
        // Add a 1 second timeout toxic
        proxy.toxics().limitData("disconnect", ToxicDirection.DOWNSTREAM, 1);

        given(requestSpecification).body(user)
                .when().post(path)
                .then().statusCode(500);
    }
}
