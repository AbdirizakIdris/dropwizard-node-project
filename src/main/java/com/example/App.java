package com.example;


import io.dropwizard.Application;
import io.dropwizard.client.JerseyClientBuilder;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

import javax.ws.rs.client.Client;


public class App extends Application<AppConfiguration> {

    public static void main(String[] args) throws Exception {
        new App().run(args);
    }

    @Override
    public void initialize(Bootstrap<AppConfiguration> bootstrap) {
        // nothing to do yet
    }
    @Override
    public void run(AppConfiguration configuration, Environment environment) throws Exception {
        final Client client = new JerseyClientBuilder(environment)
                .build(getName());
        environment.jersey().register(new HelloWorldResource(client, configuration.getNodePort()));
    }
}
