package com.example;


import com.github.tomakehurst.wiremock.WireMockServer;
import io.dropwizard.testing.ResourceHelpers;
import io.dropwizard.testing.junit5.DropwizardAppExtension;
import io.dropwizard.testing.junit5.DropwizardExtensionsSupport;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

@ExtendWith(DropwizardExtensionsSupport.class)
public class HelloWorldResourceTest {
    private static final DropwizardAppExtension<AppConfiguration> extension = new DropwizardAppExtension<>(
            App.class,
            ResourceHelpers.resourceFilePath("config/test-it.yml")
    );

//    private static WireMockServer wireMockServer;

//    @BeforeClass
//    public static void setUp() {
//        // Start WireMock server on port 8080
//        wireMockServer = new WireMockServer(8080);
//        wireMockServer.start();
//    }

    @Test
    void ShouldReturnHelloWorldText() {
        WireMockServer wireMockServer = new WireMockServer(Integer.parseInt(extension.getConfiguration().getNodePort()));
        wireMockServer.start();
        // Stub the API response using WireMock
        configureFor("localhost", Integer.parseInt(extension.getConfiguration().getNodePort()));
        stubFor(get((urlEqualTo("/")))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody("{\"text\": \"Hello, world!\"}")));

        // Send a request to the API using REST Assured
            given()
                .port(extension.getLocalPort())
                .when()
                .get("/")
                .then()
                .statusCode(200)
                .contentType("application/json")
                .body("text", is("Hello, world!"));
        wireMockServer.stop();
    }

//    @AfterClass
//    public static void setDown() {
//        wireMockServer.stop();
//    }
}
