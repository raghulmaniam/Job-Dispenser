package com.raghul.workorderpriorityqueue.entity;

import java.util.Date;

public class WorkOrder {
	private int requestorId;
	private Date requestDate;

	public WorkOrder(int requestorId, Date requestDate, int rank) {
		super();
		this.requestorId = requestorId;
		this.requestDate = requestDate;
		this.rank = rank;
	}

	public int getRequestorId() {
		return requestorId;
	}

	public void setRequestorId(int requestorId) {
		this.requestorId = requestorId;
	}

	public Date getRequestDate() {
		return requestDate;
	}

	public void setRequestDate(Date requestDate) {
		this.requestDate = requestDate;
	}

	public int getRank() {
		return rank;
	}

	public void setRank(int rank) {
		this.rank = rank;
	}

	private int rank;
}
