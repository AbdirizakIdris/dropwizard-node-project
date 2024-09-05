package com.example;


import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
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
    @Path("/greeting")
    public Saying getGreeting() throws Exception {
        NodeResponse nodeResponse = client
                .target(NODE_URL + nodePort)
                .path("/greeting")
                .request(MediaType.APPLICATION_JSON_TYPE)
                .get(NodeResponse.class);
        return new Saying(nodeResponse.getText());
    }


    @POST
    @Path("/process-greeting")
    public Greeting processGreeting(Greeting nodeRequest) throws Exception {
        return client
                .target(NODE_URL + nodePort)
                .path("/process-greeting-node")
                .request(MediaType.APPLICATION_JSON_TYPE)
                .post(Entity.entity(nodeRequest, MediaType.APPLICATION_JSON), Greeting.class);
    }
}
