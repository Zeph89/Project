package com.excilys.client;

import com.excilys.beans.Computer;
import com.excilys.model.ComputerList;
import com.excilys.service.ComputerServiceRS;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Core {

    public static void main(String[] args) {

        ApplicationContext ctx = new ClassPathXmlApplicationContext("application-context.xml");
        ComputerServiceRS service = ctx.getBean(ComputerServiceRS.class);

        ComputerList computerList = service.list("a", "IBM");
        java.util.List<Computer> list = computerList.getList();

        for (Computer c: list)
            System.out.println(c.getName());
    }
}
