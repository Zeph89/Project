package com.excilys.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.excilys.form.ComputerForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.excilys.beans.Computer;
import com.excilys.service.CompanyService;
import com.excilys.service.ComputerService;

import javax.validation.Valid;

@Controller
public class ComputerController {
	
	public final int PAGE_SIZE = 10;
	
	@Autowired
	private ComputerService cd;
	
	@Autowired
	private CompanyService cy;
	
	@RequestMapping(value = "/dashboard")
    public String initComputer(@RequestParam(value="search", required=false) String search,
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
				Page<Computer> p = cd.list(page*PAGE_SIZE, PAGE_SIZE, sort);
				listc = p.getContent();
				model.addAttribute("nbComputer", p.getTotalElements());
			} else {
				Page<Computer> p = cd.list(page*PAGE_SIZE, PAGE_SIZE, search, sort);
				model.addAttribute("nbComputer", p.getTotalElements());
				model.addAttribute("search", search);
			}

			model.addAttribute("sort", sort*(-1));
		} else if (search == null) {
			Page<Computer> p = cd.list(page*PAGE_SIZE, PAGE_SIZE);
			listc = p.getContent();
			model.addAttribute("nbComputer", p.getTotalElements());
			
		} else {
			Page<Computer> p = cd.list(page*PAGE_SIZE, PAGE_SIZE, search);
			listc = p.getContent();
			model.addAttribute("nbComputer", p.getTotalElements());
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
	
	@RequestMapping(value = "/infoComputer")
    public String infoComputer(@RequestParam(value="id", required=true) Integer id,
                    Model model) {
		model.addAttribute(new ComputerForm());
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		
		Computer c = cd.findById(id);
		model.addAttribute("id", c.getId());
		model.addAttribute("name", c.getName());
		
		if (c.getIntroducedDate() != null)
			model.addAttribute("introducedDate", dateFormat.format(c.getIntroducedDate()));
		else
			model.addAttribute("introducedDate", "");
		
		if (c.getDiscontinuedDate() != null)
			model.addAttribute("discontinuedDate", dateFormat.format(c.getDiscontinuedDate()));
		else
			model.addAttribute("discontinuedDate", "");
		
		if (c.getCompany() == null)
			model.addAttribute("companyId", -1);
		else
			model.addAttribute("companyId", c.getCompany().getId());
		model.addAttribute("companies", cy.list());

        System.out.println("sdsdsd");
		return "updateComputer";
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

            return "updateComputer";
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
	/*@RequestMapping(value = "/updateComputer")
    public String updateComputer(@RequestParam(value="id", required=true) Integer id,
                    @RequestParam(value="name", required=false) String name,
                    @RequestParam(value="introduced", required=false) String introducedDate,
                    @RequestParam(value="discontinued", required=false) String discontinuedDate,
                    @RequestParam(value="company", required=false) Integer companyId,
                    Model model) {
		
		Computer c = cd.findById(id);
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		boolean error = false;
		
		if (introducedDate.equals("") == false) {
			try {
				dateFormat.parse(introducedDate);
				model.addAttribute("introducedE", 0);
			} catch (ParseException e) {
				model.addAttribute("introducedE", 1);
				error = true;
			}
		}
		
		if (discontinuedDate.equals("") == false) {
			try {
				dateFormat.parse(discontinuedDate);	
				model.addAttribute("discontinuedE", 0);
			} catch (ParseException e) {
				model.addAttribute("discontinuedE", 1);
				error = true;
			}
		}
		
		if (!error) {
			if (companyId == null)
				cd.update(c, name, introducedDate, discontinuedDate, null);
			else
				cd.update(c, name, introducedDate, discontinuedDate, cy.findById(companyId));
	
			model.addAttribute("message", 2);
			model.addAttribute("nameMess", c.getName());
	
			return "redirect:dashboard.html";
		} else {
			model.addAttribute("id", c.getId());
			model.addAttribute("name", name);
			model.addAttribute("introducedDate", introducedDate);
			model.addAttribute("discontinuedDate", discontinuedDate);
			
			if (c.getCompany() == null)
				model.addAttribute("companyId", -1);
			else
				model.addAttribute("companyId", c.getCompany().getId());
			model.addAttribute("companies", cy.list());
			return "updateComputer";
		}
	}*/

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
                return "insertComputer";
            } else if (computerForm.getName() != null) {
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                Computer c = new Computer();

                model.addAttribute("discontinuedE", 0);
                c.setName(computerForm.getName());

                Date introduced = null;
                try {
                    introduced = dateFormat.parse(computerForm.getIntroduced());
                    model.addAttribute("introducedE", 0);
                } catch (ParseException e) {}
                c.setIntroducedDate(introduced);

                Date discontinued = null;
                try {
                    discontinued = dateFormat.parse(computerForm.getIntroduced());
                    model.addAttribute("discontinuedE", 0);
                } catch (ParseException e) {}
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
        return "insertComputer";
	}

}
