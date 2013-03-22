package com.excilys.service;

import com.excilys.beans.ComputerList;
import org.springframework.beans.factory.annotation.Autowired;

public class ComputerServiceRSImpl implements ComputerServiceRS {

    @Autowired
    private ComputerService computerService;

    public ComputerList list(String searchComputer, String searchCompany) {
        if (searchComputer == null)
            searchComputer = "";

        if (searchCompany == null)
            searchCompany = "";

        return new ComputerList(computerService.list(searchComputer, searchCompany));
    }
}
