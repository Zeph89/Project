package com.excilys.dao;


import com.excilys.beans.Computer;
import com.excilys.beans.QComputer;
import com.excilys.repository.ComputerRepository;
import com.mysema.query.BooleanBuilder;
import com.mysema.query.types.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import javax.annotation.Resource;

@Repository("ComputerDAOImpl")
public class ComputerDAOImpl implements ComputerDAO {

    @Resource
    private ComputerRepository computerRepository;

    private static QComputer computer = QComputer.computer;

    @Override
    public Page<Computer> list(int start, int size, String searchComputer, String searchCompany) {
        return computerRepository.findAll(getPredicate(searchComputer, searchCompany),
                constructPageSpecification(start, size, Sort.Direction.ASC, "name"));
    }

    @Override
    public Page<Computer> list(int start, int size, String searchComputer, String searchCompany, int sort) {
        Sort.Direction d;
        if (sort > 0)
            d = Sort.Direction.ASC;
        else
            d = Sort.Direction.DESC;

        String column = "name";
        if ((sort == 2) || (sort == -2))
            column = "introducedDate";
        else if ((sort == 3) || (sort == -3))
            column = "discontinuedDate";
        else if ((sort == 4) || (sort == -4))
            column = "company.name";

        return computerRepository.findAll(getPredicate(searchComputer, searchCompany),
                constructPageSpecification(start, size, d, column));
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

    private Pageable constructPageSpecification(int start, int size, Sort.Direction d, String column) {
        Pageable pageSpecification = new PageRequest(start, size, getSort(d, column));
        return pageSpecification;
    }

    private Sort getSort(Sort.Direction d, String column) {
        return new Sort(d, column);
    }
}