package com.example;


import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.client.Client;
import javax.ws.rs.core.MediaType;

@Path("/")
@Produces(MediaType.APPLICATION_JSON)
public class HelloWorldResource {
    Client client;
    private final String nodePort;
    private static final String NODE_URL = "http://localhost:";
    public HelloWorldResource(Client client, String nodePort) {
        this.client = client;
        this.nodePort = nodePort;
    }

    @GET
    public Saying getHelloWorld() throws Exception {
        NodeResponse nodeResponse = client
                .target(NODE_URL + nodePort)
                .path("/")
                .request(MediaType.APPLICATION_JSON_TYPE)
                .get(NodeResponse.class);
        return new Saying(nodeResponse.getText());
    }
}
