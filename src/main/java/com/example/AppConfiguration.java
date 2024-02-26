package com.example;


import io.dropwizard.Configuration;

import javax.validation.constraints.NotEmpty;

public class AppConfiguration extends Configuration {
    @NotEmpty
    private String nodePort;

    public String getNodePort() {
        return this.nodePort;
    }

    public void setNodePort(String nodePort) {
        this.nodePort = nodePort;
    }

}
