package com.raghul.workorderpriorityqueue.entity;

import java.util.Date;

public class WorkOrder {

	private int requestorId;
	private Date requestDate;
	private WorkOrderType workOrderType;
	private long rank;

	public WorkOrder() {

	}

	public WorkOrder(int requestorId, Date requestDate) {
		super();

		System.out.println(requestorId);
		System.out.println(requestDate);
		System.out.println("********************");

		this.requestorId = requestorId;
		this.requestDate = requestDate;
		// this.rank = rank;

		// to calculate rank here
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

	public WorkOrderType getWorkOrderType() {
		return workOrderType;
	}

	public void setWorkOrderType(WorkOrderType workOrderType) {
		this.workOrderType = workOrderType;
	}

	public long getRank() {
		return rank;
	}

	public void setRank(long rank) {
		this.rank = rank;
	}

}
