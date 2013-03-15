package com.excilys.controller;

import com.excilys.form.ComputerIdForm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;

import com.excilys.form.ComputerForm;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.excilys.beans.Computer;
import com.excilys.service.CompanyService;
import com.excilys.service.ComputerService;

import javax.validation.Valid;

@Controller
public class ComputerController {

    static final Logger LOG = LoggerFactory.getLogger(ComputerController.class);
	public final int PAGE_SIZE = 10;
	
	@Autowired
	private ComputerService cd;
	
	@Autowired
	private CompanyService cy;

    @RequestMapping(value = "/login")
    public String initComputer() {
        return "/WEB-INF/jsp/login";
    }

	@RequestMapping(value = "/dashboard")
    public String initComputer(@RequestParam(value="searchComputer", required=false) String searchComputer,
                    @RequestParam(value="searchCompany", required=false) String searchCompany,
                    @RequestParam(value="page", required=false) Integer page,
                    @RequestParam(value="sort", required=false) Integer sort,
                    @RequestParam(value="nameMess", required=false) String nameMess,
                    @RequestParam(value="message", required=false) Integer message,
                    Model model) {

        LOG.info("Hello World!");
        LOG.info("How are you today?");

		if(page == null || page.equals("")) {
			model.addAttribute("page", 0);
			page = 0;
		} else {
			model.addAttribute("page", page);
		}

        List<Computer> listc = null;
		
		if (sort != null) {
			if ((searchComputer == null) && (searchCompany == null)) {
				Page<Computer> p = cd.list(page, PAGE_SIZE, sort);
				listc = p.getContent();
				model.addAttribute("nbComputers", p.getTotalElements());
			} else {
				Page<Computer> p = cd.list(page, PAGE_SIZE, searchComputer, searchCompany,  sort);
                listc = p.getContent();
				model.addAttribute("nbComputers", p.getTotalElements());
				model.addAttribute("searchComputer", searchComputer);
                model.addAttribute("searchCompany", searchCompany);
			}

			model.addAttribute("sort", sort);
		} else if ((searchComputer == null) && (searchCompany == null)) {
			Page<Computer> p = cd.list(page, PAGE_SIZE);
			listc = p.getContent();
			model.addAttribute("nbComputers", p.getTotalElements());
			
		} else {
			Page<Computer> p = cd.list(page, PAGE_SIZE, searchComputer, searchCompany);
			listc = p.getContent();
			model.addAttribute("nbComputers", p.getTotalElements());
			model.addAttribute("searchComputer", searchComputer);
            model.addAttribute("searchCompany", searchCompany);
		}

		model.addAttribute("computers", listc);

		if (message != null)
			model.addAttribute("message", message);
		
		if (nameMess != null)
			model.addAttribute("nameMess", nameMess);
		
		model.addAttribute("PAGE_SIZE", PAGE_SIZE);

		return "/WEB-INF/jsp/dashboard";
	}

	@RequestMapping(value = "/infoComputer")
    public String infoComputer(@RequestParam(value="id", required=true) Integer id,
                    Model model) {
		model.addAttribute(new ComputerForm());
        DateTimeFormatter dateFormat = DateTimeFormat.forPattern("yyyy-MM-dd");
		
		Computer c = cd.findById(id);
		model.addAttribute("id", c.getId());
		model.addAttribute("name", c.getName());

		if (c.getIntroducedDate() != null)
			model.addAttribute("introduced", c.getIntroducedDate().toString(dateFormat));
		else
			model.addAttribute("introduced", "");
		
		if (c.getDiscontinuedDate() != null)
			model.addAttribute("discontinued", c.getDiscontinuedDate().toString(dateFormat));
		else
			model.addAttribute("discontinued", "");
		
		if (c.getCompany() == null)
			model.addAttribute("companyId", -1);
		else
			model.addAttribute("companyId", c.getCompany().getId());
		model.addAttribute("companies", cy.list());

		return "/WEB-INF/jsp/updateComputer";
	}

	@RequestMapping(value = "/deleteComputer")
    public String deleteComputer(@RequestParam(value="id", required=true) Integer id,
                    Model model) {
		
		model.addAttribute("message", 3);
		model.addAttribute("nameMess", cd.findById(id).getName());
		
		cd.delete(id);

		return "redirect:dashboard.html";
	}

    @RequestMapping(value = "/updateComputer")
    public String updateComputer(@RequestParam(value="id", required=true) Integer id,
                                 @Valid @ModelAttribute ComputerForm computerForm,
                                 BindingResult bindingResult,
                                 Model model) {
        if (bindingResult.hasErrors()) {
            if (bindingResult.hasFieldErrors("name"))
                model.addAttribute("nameE", 1);

            if (bindingResult.hasFieldErrors("introduced"))
                model.addAttribute("introducedE", 1);

            if (bindingResult.hasFieldErrors("discontinued"))
                model.addAttribute("discontinuedE", 1);

            model.addAttribute("id", id);
            model.addAttribute("name", computerForm.getName());
            model.addAttribute("introducedDate", computerForm.getIntroduced());
            model.addAttribute("discontinuedDate", computerForm.getDiscontinued());
            model.addAttribute("companyId", computerForm.getCompany());

            model.addAttribute("companies", cy.list());

            return "/WEB-INF/jsp/updateComputer";
        } else {
            Computer c = cd.findById(id);
            if (computerForm.getCompany() == null)
                cd.update(c, computerForm.getName(), computerForm.getIntroduced(), computerForm.getDiscontinued(), null);
            else
                cd.update(c, computerForm.getName(), computerForm.getIntroduced(), computerForm.getDiscontinued(),
                        cy.findById(computerForm.getCompany()));

            model.addAttribute("message", 2);
            model.addAttribute("nameMess", c.getName());

            return "redirect:dashboard.html";
        }
    }

    @RequestMapping(value = "/insertComputer")
    public String insertComputer(@Valid @ModelAttribute ComputerForm computerForm,
                                 BindingResult bindingResult,
                                 Model model) {

        if (computerForm.getName() != null) {
            if (bindingResult.hasErrors()) {
                if (bindingResult.hasFieldErrors("name"))
                    model.addAttribute("nameE", 1);

                if (bindingResult.hasFieldErrors("introduced"))
                    model.addAttribute("introducedE", 1);

                if (bindingResult.hasFieldErrors("discontinued"))
                    model.addAttribute("discontinuedE", 1);

                model.addAttribute("name", computerForm.getName());
                model.addAttribute("introducedDate", computerForm.getIntroduced());
                model.addAttribute("discontinuedDate", computerForm.getDiscontinued());
                model.addAttribute("companyId", computerForm.getCompany());

                model.addAttribute("companies", cy.list());
                return "/WEB-INF/jsp/insertComputer";
            } else if (computerForm.getName() != null) {
                DateTimeFormatter dateFormat = DateTimeFormat.forPattern("yyyy-MM-dd");
                Computer c = new Computer();

                model.addAttribute("discontinuedE", 0);
                c.setName(computerForm.getName());

                DateTime introduced = null;
                try {
                    introduced = dateFormat.parseDateTime(computerForm.getIntroduced());
                    model.addAttribute("introducedE", 0);
                } catch (Exception e) {}
                c.setIntroducedDate(introduced);

                DateTime discontinued = null;
                try {
                    discontinued = dateFormat.parseDateTime(computerForm.getDiscontinued());
                    model.addAttribute("discontinuedE", 0);
                } catch (Exception e) {}
                c.setDiscontinuedDate(discontinued);

                if (computerForm.getCompany() != null)
                    c.setCompany(cy.findById(computerForm.getCompany()));
                else
                    c.setCompany(null);

                cd.create(c);

                model.addAttribute("message", 1);
                model.addAttribute("nameMess", c.getName());

                return "redirect:dashboard.html";
            }
        }

        model.addAttribute("nameE", 0);
        model.addAttribute("introducedE", 0);
        model.addAttribute("discontinuedE", 0);

        model.addAttribute("companies", cy.list());
        return "/WEB-INF/jsp/insertComputer";
	}

}
