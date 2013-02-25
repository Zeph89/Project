import java.util.List;

import com.excilys.beans.Company;
import com.excilys.beans.Computer;
import com.excilys.dao.CompanyDAO;
import com.excilys.dao.CompanyDaoImpl;
import com.excilys.dao.ComputerDAO;
import com.excilys.dao.ComputerDaoImpl;
import com.excilys.dao.DAOFactory;

public class Test {

	public static void main(String[] args) {
		System.out.println("START");
		DAOFactory daoFactory = DAOFactory.getInstance();
		CompanyDAO cy = new CompanyDaoImpl(daoFactory);
		ComputerDAO cd = new ComputerDaoImpl(daoFactory);
		
		System.out.println("List company :");
		List<Company> listy = cy.list();
		for (int i=0; i<listy.size(); i++)
			System.out.println(listy.get(i).toString());
		System.out.println("");
		
		System.out.println("One company id=4 :");
		Company cc = cy.findById(4);
		System.out.println(cc);
		System.out.println("");
		
		System.out.println("List computer :");
		List<Computer> listc = cd.list();
		for (int i=0; i<listc.size(); i++)
			System.out.println(listc.get(i).toString());
		System.out.println("");
		
		System.out.println("One computer id=1 :");
		Computer c = cd.findById(1);
		System.out.println(c);
		System.out.println("");
		
		System.out.println("END");
	}

}
