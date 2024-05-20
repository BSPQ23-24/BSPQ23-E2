package com.deusto.app.server.services;

import java.util.ArrayList;
import java.util.List;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Query;
import javax.jdo.Transaction;
import javax.jdo.JDOHelper;

import com.deusto.app.server.data.domain.Loan;
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
		List<LoanData> loanDataList = new ArrayList<>();
		try {
			tx.begin();
			Query<Loan> query = pm.newQuery(Loan.class);
			List<Loan> loans = (List<Loan>) query.execute();
			for (Loan loan : loans) {
				LoanData loanData = new LoanData();
				loanData.setId(loan.getId());
				loanData.setLoanDate(loan.getLoanDate());
				loanData.setStartHour(loan.getStartHour());
				loanData.setEndHour(loan.getEndHour());
				loanData.setUserDni(loan.getId());
				loanData.setBicycleId(loan.getBicycle().getId());
				loanDataList.add(loanData);
			}
			tx.commit();
		} catch (Exception e) {
			if (tx.isActive()) {
				tx.rollback();
			}
			e.printStackTrace();
		} finally {
			pm.close();
		}
		return loanDataList;
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
}
