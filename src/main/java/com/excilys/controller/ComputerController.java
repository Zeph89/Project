package com.excilys.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.excilys.beans.Computer;
import com.excilys.service.ComputerService;

@Controller
public class ComputerController {
	
	public final int PAGE_SIZE = 10;
	
	@Autowired
	private ComputerService cd;
	
	@RequestMapping(value = "/dashboard")
    public String get(@RequestParam(value="search", required=false) String search,
                    @RequestParam(value="page", required=false) Integer page,
                    @RequestParam(value="sort", required=false) Integer sort,
                    @RequestParam(value="nameMess", required=false) String nameMess,
                    @RequestParam(value="message", required=false) Integer message,
                    Model model) {
		
		if(page == null || page.equals("")) {
			model.addAttribute("page", 0);
			page = 0;
		} else {
			model.addAttribute("page", page);
		}
		
		List<Computer> listc = null;
		
		if (sort != null) {
			model.addAttribute("page", 0);
			page = 1;
			
			if (search == null) {
				listc = cd.list(page*PAGE_SIZE, PAGE_SIZE, sort);
				model.addAttribute("nbComputer", cd.getNumberComputers());
			} else {
				listc = cd.list(page*PAGE_SIZE, PAGE_SIZE, search, sort);
				model.addAttribute("nbComputer", cd.getNumberComputers(search));
				model.addAttribute("search", search);
			}

			model.addAttribute("sort", sort*(-1));
		} else if (search == null) {
			System.out.println(cd.toString());
			listc = cd.list(page*PAGE_SIZE, PAGE_SIZE);
			model.addAttribute("nbComputer", cd.getNumberComputers());
		} else {
			listc = cd.list(page*PAGE_SIZE, PAGE_SIZE, search);
			model.addAttribute("nbComputer", cd.getNumberComputers(search));
			model.addAttribute("search", search);
		}

		model.addAttribute("computers", listc);

		if (message != null)
			model.addAttribute("message", message);
		
		if (nameMess != null)
			model.addAttribute("nameMess", nameMess);
		
		model.addAttribute("PAGE_SIZE", PAGE_SIZE);
		
		return "dashboard";
	}
}
