package com.excilys.metamodel;

import com.excilys.beans.Computer;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(Computer.class)
public class Computer_ {
    public static volatile SingularAttribute<Computer, String> name;
}