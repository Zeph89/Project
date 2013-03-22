package com.excilys.service;

import com.excilys.model.ComputerList;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

@Path("/computer/")
public interface ComputerServiceRS {

    @GET
    @Produces("application/xml")
    @Path("{searchComputer}/{searchCompany}")
    ComputerList list(@PathParam("searchComputer") String searchComputer,
                      @PathParam("searchCompany") String searchCompany);
}