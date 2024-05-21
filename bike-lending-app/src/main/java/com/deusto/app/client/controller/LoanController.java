package com.deusto.app.client.controller;

import com.deusto.app.client.remote.ServiceLocator;
import com.deusto.app.server.pojo.LoanData;

import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.client.Invocation;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.GenericType;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.apache.logging.log4j.LogManager;

import java.util.List;

public class LoanController {
    private static LoanController instance;

    private LoanController() {
    }

    public static synchronized LoanController getInstance() {
        if (instance == null) {
            instance = new LoanController();
        }
        return instance;
    }

    /**
     * Retrieves all loans from the system.
     *
     * @return a list of LoanData representing all loans, or null if retrieval fails
     */
    public List<LoanData> getAllLoans() {
        LogManager.getLogger(LoanController.class).info("Get All Loans Start");
        WebTarget getAllLoansWebTarget = ServiceLocator.getInstance().getWebTarget().path("bikeapp/loan/all")
                .queryParam("token", UserController.getToken());
        Invocation.Builder invocationBuilder = getAllLoansWebTarget.request(MediaType.APPLICATION_JSON);

        Response response = invocationBuilder.get();
        if (response.getStatus() == Response.Status.OK.getStatusCode()) {
            LogManager.getLogger(LoanController.class).info("Get All Loans Success");
            return response.readEntity(new GenericType<List<LoanData>>() {});
        } else {
            LogManager.getLogger(LoanController.class).error("Get All Loans Failed | Code: {} | Reason: {}",
                    response.getStatus(), response.readEntity(String.class));
            return null;
        }
    }

    /**
     * Creates a new loan in the system.
     *
     * @param loanData the loan data to be created
     * @return true if the loan creation is successful, false otherwise
     */
    public boolean createLoan(LoanData loanData) {
        LogManager.getLogger(LoanController.class).info("Create Loan Start");
        WebTarget createLoanWebTarget = ServiceLocator.getInstance().getWebTarget().path("bikeapp/loan/create")
                .queryParam("token", UserController.getToken());
        Invocation.Builder invocationBuilder = createLoanWebTarget.request(MediaType.APPLICATION_JSON);

        Response response = invocationBuilder.post(Entity.entity(loanData, MediaType.APPLICATION_JSON));
        if (response.getStatus() == Response.Status.OK.getStatusCode()) {
            LogManager.getLogger(LoanController.class).info("Create Loan Success");
            return true;
        } else {
            LogManager.getLogger(LoanController.class).error("Create Loan Failed | Code: {} | Reason: {}",
                    response.getStatus(), response.readEntity(String.class));
            return false;
        }
    }

    /**
     * Deletes a loan from the system.
     *
     * @param loanId the ID of the loan to be deleted
     * @return true if the loan is successfully deleted, false otherwise
     */
    public boolean deleteLoan(int loanId) {
        LogManager.getLogger(LoanController.class).info("Delete Loan Start");
        WebTarget deleteLoanWebTarget = ServiceLocator.getInstance().getWebTarget().path("bikeapp/loan/delete/" + loanId)
                .queryParam("token", UserController.getToken());
        Invocation.Builder invocationBuilder = deleteLoanWebTarget.request(MediaType.APPLICATION_JSON);

        Response response = invocationBuilder.delete();
        if (response.getStatus() == Response.Status.OK.getStatusCode()) {
            LogManager.getLogger(LoanController.class).info("Delete Loan Success");
            return true;
        } else {
            LogManager.getLogger(LoanController.class).error("Delete Loan Failed | Code: {} | Reason: {}",
                    response.getStatus(), response.readEntity(String.class));
            return false;
        }
    }
    
    /**
	 * Check if the user has an active loan.
	 *
	 * @param token the user's authentication token
	 * @return Response containing a boolean if the user has an active loan
	 */
    
    public LoanData isLoanActive(@QueryParam("token") long token) {
        LogManager.getLogger(LoanController.class).info("Do you have any loan active?");
        WebTarget loanActive = ServiceLocator.getInstance().getWebTarget().path("bikeapp/loan/active")
                																	.queryParam("token", token);
        Invocation.Builder invocationBuilder = loanActive.request(MediaType.APPLICATION_JSON);

        Response response = invocationBuilder.get();
        if (response.getStatus() == Response.Status.OK.getStatusCode()) {
            return response.readEntity(LoanData.class);
        } else {
            LogManager.getLogger(LoanController.class).error("Checking Loans Failed | Code: {} | Reason: {}",
                    response.getStatus(), response.readEntity(String.class));
            return null;
        }
    }

}
