package com.raghul.workorderpriorityqueue.entity;

import java.util.Date;

public class WorkOrder {

	/*
	 * Developer: Raghul Subramaniam email: raghulmaniam@gmail.com
	 */

	private long requestorId;
	private Date requestDate;
	private WorkOrderType workOrderType;
	private long rank;

	public WorkOrder() {

	}

	public WorkOrder(long requestorId, Date requestDate) {
		super();

		this.requestorId = requestorId;
		this.requestDate = requestDate;
	}

	public long getRequestorId() {
		return requestorId;
	}

	public void setRequestorId(long requestorId) {
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
