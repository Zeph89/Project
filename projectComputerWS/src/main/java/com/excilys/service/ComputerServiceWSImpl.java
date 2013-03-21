package com.excilys.service;

import com.excilys.beans.Computer;
import org.springframework.beans.factory.annotation.Autowired;

import javax.jws.WebService;
import java.util.ArrayList;
import java.util.List;

@WebService(
        endpointInterface = "com.excilys.service.ComputerServiceWS",
        serviceName = "ComputerServiceWS")
public class ComputerServiceWSImpl implements ComputerServiceWS {

    @Autowired
    private ComputerService computerService;

    @Override
    public List<Computer> list(String searchComputer, String searchCompany) {
        return computerService.list(searchComputer, searchCompany);
    }
}