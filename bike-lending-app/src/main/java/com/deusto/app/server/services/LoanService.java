package com.deusto.app.server.services;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Query;
import javax.jdo.Transaction;

import org.apache.logging.log4j.LogManager;

import javax.jdo.JDOHelper;

import com.deusto.app.server.data.domain.Loan;
import com.deusto.app.server.data.domain.User;
import com.deusto.app.server.pojo.LoanAssembler;
import com.deusto.app.server.pojo.LoanData;

public class LoanService {

	private static LoanService instance;
	private PersistenceManagerFactory pmf;

	private LoanService() {
		pmf = JDOHelper.getPersistenceManagerFactory("datanucleus.properties");
	}

	public static LoanService getInstance() {
		if (instance == null) {
			instance = new LoanService();
		}
		return instance;
	}

	public List<LoanData> getAllLoans() {
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx = pm.currentTransaction();
		List<Loan> loans = null;
		try {
			tx.begin();
			Query<Loan> query = pm.newQuery(Loan.class);
			loans = (List<Loan>) query.execute();
			
			tx.commit();
			return LoanAssembler.getInstance().loansToPOJO(loans);
		} catch (Exception e) {
			if (tx.isActive()) {
				tx.rollback();
			}
			return null;
			
		} finally {
			pm.close();
		}
		
	}

	public boolean createLoan(LoanData loanData) {
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx = pm.currentTransaction();
		try {
			tx.begin();
			Loan loan = new Loan();
			loan.setId(loanData.getId());
			loan.setLoanDate(loanData.getLoanDate());
			loan.setStartHour(loanData.getStartHour());
			loan.setEndHour(loanData.getEndHour());
			loan.setUser(loan.getUser());
			loan.setBicycle(loan.getBicycle());
			pm.makePersistent(loan);
			tx.commit();
			return true;
		} catch (Exception e) {
			if (tx.isActive()) {
				tx.rollback();
			}
			e.printStackTrace();
			return false;
		} finally {
			pm.close();
		}
	}

	public boolean deleteLoan(int loanId) {
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx = pm.currentTransaction();
		try {
			tx.begin();
			Loan loan = pm.getObjectById(Loan.class, loanId);
			if (loan != null) {
				pm.deletePersistent(loan);
				tx.commit();
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			if (tx.isActive()) {
				tx.rollback();
			}
			e.printStackTrace();
			return false;
		} finally {
			pm.close();
		}
	}
	
	public LoanData isLoanActive(long token) {
		List<LoanData> loans = getAllLoans();
		User user=UserService.getInstance().getUser(token);
		LoanData lastLoan=null;
		String dni=user.getDni();
		for (LoanData loan : loans) {
			if(dni.equals(loan.getUserDni())) {
				lastLoan=loan;
			}
		}
		if(lastLoan==null) {
			LogManager.getLogger(AdminService.class).info("You do not have loans yet!");
			return lastLoan;
		}else {
			LocalDateTime now= LocalDateTime.now();
			String date=lastLoan.getLoanDate(); //format YYYY-MM-DD
			String startHour=lastLoan.getStartHour();	//format HH:mm
			String endHour=lastLoan.getEndHour();  //format HH:mm
			LocalDateTime loanStart = LocalDateTime.parse(date + "T" + startHour);
	        LocalDateTime loanEnd = LocalDateTime.parse(date + "T" + endHour);
	        System.out.println(loanEnd);
	        System.out.println("Loan Start:" + now.isAfter(loanStart));
	        System.out.println("Loan End:" + now.isBefore(loanEnd));

	        if(now.isAfter(loanStart) && now.isBefore(loanEnd)) {
	        	return lastLoan;
	        }else {
	        	return lastLoan;
	        }
	        
			
		}
		
	}
}
