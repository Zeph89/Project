package com.excilys.service;

import com.excilys.beans.Computer;

import java.util.List;
import javax.jws.WebParam;
import javax.jws.WebService;

@WebService
public interface ComputerServiceWS {

	List<Computer> list(
            @WebParam(name="searchComputer") String searchComputer,
            @WebParam(name="searchCompany") String searchCompany);
}