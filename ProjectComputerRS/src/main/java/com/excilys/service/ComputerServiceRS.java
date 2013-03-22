package com.excilys.service;

import com.excilys.beans.Computer;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;


@Path("/ComputerServiceRS")
public interface ComputerServiceRS {

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    List<Computer> list(String searchComputer, String searchCompany);
}