package com.excilys.metamodel;

import com.excilys.beans.Company;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(Company.class)
public class Company_ {
    public static volatile SingularAttribute<Company, String> name;
}