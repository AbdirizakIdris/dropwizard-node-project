package com.example;


import com.github.tomakehurst.wiremock.WireMockServer;
import io.dropwizard.testing.ResourceHelpers;
import io.dropwizard.testing.junit5.DropwizardAppExtension;
import io.dropwizard.testing.junit5.DropwizardExtensionsSupport;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.configureFor;
import static com.github.tomakehurst.wiremock.client.WireMock.equalToJson;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.post;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

@ExtendWith(DropwizardExtensionsSupport.class)
public class HelloWorldResourceTest {
    private static final DropwizardAppExtension<AppConfiguration> extension = new DropwizardAppExtension<>(
            App.class,
            ResourceHelpers.resourceFilePath("config/test-it.yml")
    );

    private static WireMockServer wireMockServer;

    @BeforeAll
    public static void setUp() {
        wireMockServer = new WireMockServer(Integer.parseInt(extension.getConfiguration().getNodePort()));
        wireMockServer.start();
        configureFor("localhost", Integer.parseInt(extension.getConfiguration().getNodePort()));
    }

    @AfterAll
    public static void tearDown() {
        wireMockServer.stop();
    }

    @Test
    void shouldReturnHelloWorldGreeting() {

        String getResponseJson = """
                {
                    "text": "Hello, World!"
                }
                """;

        stubFor(get((urlEqualTo("/greeting")))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody(getResponseJson)));

        given()
                .port(extension.getLocalPort())
                .when()
                .get("/greeting")
                .then()
                .statusCode(200)
                .contentType("application/json")
                .body("text", is("Hello, World!"));

    }

    @Test
    void shouldProcessAndReturnCapitalisedName() {

        String postRequestJson = """
                {
                    "name": "Hello from Ali!"
                }
                """;

        String postResponseJson = """
                {
                    "name": "HELLO FROM ALI!"
                }
                """;


        Greeting greetingRequest = new Greeting("Hello from Ali!");

        stubFor(post(urlEqualTo("/process-greeting-node"))
                .withRequestBody(equalToJson(postRequestJson))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody(postResponseJson)));

        given()
                .port(extension.getLocalPort())
                .contentType("application/json")
                .body(greetingRequest)
                .when()
                .post("/process-greeting")
                .then()
                .statusCode(200)
                .contentType("application/json")
                .body("name", is("HELLO FROM ALI!"));
    }
}
