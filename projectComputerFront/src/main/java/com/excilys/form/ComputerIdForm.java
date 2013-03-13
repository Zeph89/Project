package com.excilys.form;

import com.excilys.validator.DateIsValid;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;

public class ComputerIdForm {

    @NotNull
    private Integer id;

    @NotNull
    @NotBlank(message = "Name required.")
    private String name;

    @DateIsValid
    private String introduced;

    @DateIsValid
    private String discontinued;

    private Integer company;

    public ComputerIdForm() {}

    public ComputerIdForm(Integer id, String name, String introduced,
                          String discontinued, Integer company) {
        this.id = id;
        this.name = name;
        this.introduced = introduced;
        this.discontinued = discontinued;
        this.company = company;
    }

    public Integer getId() {
        return id;
    }

    public void setId() {
        this.id = id;
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
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", introduced='" + introduced + '\'' +
                ", discontinued='" + discontinued + '\'' +
                ", company=" + company +
                '}';
    }
}
