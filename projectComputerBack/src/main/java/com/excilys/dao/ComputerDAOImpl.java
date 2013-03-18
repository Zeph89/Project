package com.excilys.dao;


import com.excilys.beans.Computer;
import com.excilys.beans.QCompany;
import com.excilys.beans.QComputer;
import com.excilys.repository.ComputerRepository;
import com.mysema.query.BooleanBuilder;
import com.mysema.query.jpa.impl.JPAQuery;
import com.mysema.query.types.OrderSpecifier;
import com.mysema.query.types.Predicate;
import com.mysema.query.types.Visitor;
import com.mysema.query.types.path.PathBuilder;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Repository;

import javax.annotation.Nullable;
import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository("ComputerDAOImpl")
public class ComputerDAOImpl implements ComputerDAO {

    @Resource
    private ComputerRepository computerRepository;

    @PersistenceContext
    private EntityManager entityManager;

    private static QComputer computer = QComputer.computer;
    private static QCompany company = QCompany.company;

    @Override
    public Page<Computer> list(Pageable pageable, String searchComputer, String searchCompany) {

        JPAQuery query = new JPAQuery(entityManager)
                .from(computer)
                .where(getPredicate(searchComputer, searchCompany));

        long total = query.count();
        query.leftJoin(computer.company, company)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize());

        for (Sort.Order o : pageable.getSort()) {
            PathBuilder orderByExpression = new PathBuilder(Computer.class, "computer");
            query.orderBy(new OrderSpecifier(o.isAscending() ? com.mysema.query.types.Order.ASC
                    : com.mysema.query.types.Order.DESC, orderByExpression.get(o.getProperty())));
        }

        List<Computer> computers = query.list(QComputer.computer);

        return new PageImpl<Computer>(computers, pageable, total);
    }

    private Predicate getPredicate(String searchComputer, String searchCompany) {
        BooleanBuilder bb = new BooleanBuilder();

        if (!searchComputer.equals(""))
            bb.and(computer.name.containsIgnoreCase(searchComputer));

        if (!searchCompany.equals(""))
            bb.and(computer.company.name.containsIgnoreCase(searchCompany));
        else
            bb.and(computer.isNotNull());

        return bb;
    }
}