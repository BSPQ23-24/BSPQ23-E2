package com.deusto.app.server.data.domain;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

@PersistenceCapable(detachable = "true")
public class Loan {
	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.INCREMENT)
	private int id;
	private String loanDate;
	private String startHour;
	private String endHour;

	@Persistent(defaultFetchGroup = "true")
	private Bicycle bicycle;

	@Persistent(defaultFetchGroup = "true")
	private User user;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getLoanDate() {
		return loanDate;
	}

	public void setLoanDate(String loanDate) {
		this.loanDate = loanDate;
	}

	public String getStartHour() {
		return startHour;
	}

	public void setStartHour(String startHour) {
		this.startHour = startHour;
	}

	public String getEndHour() {
		return endHour;
	}

	public void setEndHour(String endHour) {
		this.endHour = endHour;
	}

	public Bicycle getBicycle() {
		return bicycle;
	}

	public void setBicycle(Bicycle bicycle) {
		this.bicycle = bicycle;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String toString() {

		StringBuilder result = new StringBuilder();

		result.append("Loan ID: ");
		result.append(this.id);
		result.append(" / Loan Date: ");
		result.append(this.loanDate);
		result.append(" / Start Hour: ");
		result.append(this.startHour);
		result.append(" / End Hour: ");
		result.append(this.endHour);
		result.append(" / Bicycle ID: ");
		result.append(this.bicycle.getId());
		result.append(" / User DNI: ");
		result.append(this.user.getDni());

		return result.toString();
	}

	@Override
	public boolean equals(Object obj) {
		if (this.getClass().getName().equals(obj.getClass().getName())) {
			return this.id == ((Loan) obj).id;
		}

		return false;
	}
}