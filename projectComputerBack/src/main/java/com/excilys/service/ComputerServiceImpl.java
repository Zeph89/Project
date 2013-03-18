package com.excilys.service;

import com.excilys.dao.ComputerDAO;
import com.excilys.repository.ComputerRepository;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.excilys.beans.Company;
import com.excilys.beans.Computer;
import com.excilys.beans.Log;
import org.springframework.util.Assert;

import javax.annotation.Resource;

@Service
@Transactional(readOnly=true)
public class ComputerServiceImpl implements ComputerService {

    @Resource
    private ComputerRepository computerRepository;
	
	@Autowired
	private LogService lg;

    @Autowired
    private ComputerDAO computerDAO;

	@Transactional(readOnly=false)
	public void create(Computer computer) {
        Assert.notNull(computer);
        Assert.hasText(computer.getName());

        computerRepository.save(computer);

        DateTime now = DateTime.now();

		Log log = new Log();
		log.setDescription("Insert computer");
		log.setComputerId(computer.getId());
		log.setComputerName(computer.getName());
		log.setDate(now);
		
		lg.create(log);
	}

	
	public Computer findById(int id) {
        return computerRepository.findOne(id);
	}

    public Page<Computer> list(Pageable pageable, String searchComputer, String searchCompany) {
        return computerDAO.list(pageable, searchComputer, searchCompany);
    }

	@Transactional(readOnly=false)
	public void delete(int id) {
		Computer c = findById(id);
        computerRepository.delete(c);

        DateTime now = DateTime.now();

		Log log = new Log();
		log.setDescription("Delete computer");
		log.setComputerId(id);
		log.setComputerName(c.getName());
		log.setDate(now);
		
		lg.create(log);
	}

	@Transactional(readOnly=false)
	public void update(Computer oldComputer, String newName,
			String newIntroducedDate, String newDiscontinuedDate,
			Company newCompany) {
        Assert.notNull(oldComputer);
        Assert.hasText(newName);

        DateTimeFormatter dateFormat = DateTimeFormat.forPattern("yyyy-MM-dd");
        oldComputer.setName(newName);

        try {
            if (newIntroducedDate.equals(""))
                oldComputer.setIntroducedDate(null);
            else
                oldComputer.setIntroducedDate(dateFormat.parseDateTime(newIntroducedDate));

            if (newDiscontinuedDate.equals(""))
                oldComputer.setDiscontinuedDate(null);
            else
                oldComputer.setDiscontinuedDate(dateFormat.parseDateTime(newDiscontinuedDate));
        } catch(Exception e) {}

        oldComputer.setCompany(newCompany);

        computerRepository.save(oldComputer);


        DateTime now = DateTime.now();

		Log log = new Log();
		log.setDescription("Update computer");
		log.setComputerId(oldComputer.getId());
		log.setComputerName(newName);
		log.setDate(now);
		
		lg.create(log);
	}
}
