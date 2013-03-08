package com.excilys.form;

import com.excilys.validator.DateIsValid;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;

public class ComputerForm {

    @NotNull
    @NotBlank(message = "Name required.")
    private String name;

    @DateIsValid
    private String introduced;

    @DateIsValid
    private String discontinued;

    private Integer company;

    public ComputerForm() {}

    public ComputerForm(String name, String introduced,
                        String discontinued, Integer company) {
        this.name = name;
        this.introduced = introduced;
        this.discontinued = discontinued;
        this.company = company;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIntroduced() {
        return introduced;
    }

    public void setIntroduced(String introduced) {
        this.introduced = introduced;
    }

    public String getDiscontinued() {
        return discontinued;
    }

    public void setDiscontinued(String discontinued) {
        this.discontinued = discontinued;
    }

    public Integer getCompany() {
        return company;
    }

    public void setCompany(Integer company) {
        this.company = company;
    }

    @Override
    public String toString() {
        return "ComputerForm{" +
                "name='" + name + '\'' +
                ", introduced='" + introduced + '\'' +
                ", discontinued='" + discontinued + '\'' +
                ", company=" + company +
                '}';
    }
}
