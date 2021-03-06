package com.excilys.service;

import com.excilys.repository.LogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.excilys.beans.Log;

@Service
@Transactional
public class LogServiceImpl implements LogService {

    @Autowired
    private LogRepository logRepository;

    @Secured({"ROLE_USER"})
	public void create(Log log) {
        logRepository.save(log);
	}
}
