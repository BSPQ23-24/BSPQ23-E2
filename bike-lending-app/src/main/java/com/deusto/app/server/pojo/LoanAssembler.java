package com.deusto.app.server.pojo;

import java.util.ArrayList;
import java.util.List;

import com.deusto.app.server.data.domain.Loan;

public class LoanAssembler {
    private static LoanAssembler instance;

    private LoanAssembler() { }

    public static LoanAssembler getInstance() {
        if (instance == null) {
            instance = new LoanAssembler();
        }
        return instance;
    }

    public LoanData loanToPOJO(Loan loan) {
        LoanData loanData = new LoanData();
        
        loanData.setId(loan.getId());
        loanData.setLoanDate(loan.getLoanDate());
        loanData.setStartHour(loan.getStartHour());
        loanData.setEndHour(loan.getEndHour());
        loanData.setBicycleId(loan.getBicycle().getId());
        loanData.setUserDni(loan.getId());
        
        return loanData;
    }

    public List<LoanData> loansToPOJO(List<Loan> loans) {
        List<LoanData> loanDataList = new ArrayList<>();
        
        for (Loan loan : loans) {
            loanDataList.add(this.loanToPOJO(loan));
        }
        
        return loanDataList;
    }
}
