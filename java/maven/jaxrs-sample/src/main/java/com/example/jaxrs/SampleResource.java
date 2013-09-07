package com.example.jaxrs;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Path("sample")
public class SampleResource {
    
    @GET
    public String sample() {
        return "Sample Resource";
    }
}
