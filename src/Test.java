import java.util.List;

import com.excilys.beans.Computer;
import com.excilys.dao.ComputerDAO;
import com.excilys.dao.ComputerDaoImpl;
import com.excilys.dao.DAOFactory;

public class Test {

	public static void main(String[] args) {
		System.out.println("START");
		DAOFactory daoFactory = DAOFactory.getInstance();
		ComputerDAO cd = new ComputerDaoImpl(daoFactory);
		
		List<Computer> list = cd.list();
		
		for (int i=0; i<list.size(); i++)
			System.out.println(list.get(i).toString());
		
		
		Computer c = cd.findById(1);
		System.out.println(c);
		
		System.out.println("END");
	}

}
