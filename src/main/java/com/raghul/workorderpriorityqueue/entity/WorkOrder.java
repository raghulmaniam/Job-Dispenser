package com.raghul.workorderpriorityqueue.entity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.raghul.workorderpriorityqueue.constants.WorkOrderPriorityQueueConstants;

public class WorkOrder {

	/*
	 * Developer: Raghul Subramaniam email: raghulmaniam@gmail.com
	 */

	private long requestorId;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date requestDate;
	private WorkOrderType workOrderType;
	private long rank;

	SimpleDateFormat dateFormat = new SimpleDateFormat(WorkOrderPriorityQueueConstants.DATEFORMAT_YYYTMMDDHHMMSS);

	public WorkOrder() {

	}

	public WorkOrder(long requestorId, Date requestDate) {
		super();

		this.dateFormat.setTimeZone(TimeZone.getTimeZone(WorkOrderPriorityQueueConstants.IRELAND));
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

	public void setRequestDate(String requestDate) throws ParseException {
		// passing the input date as String rather than Date to avoid UTC conversion
		this.requestDate = dateFormat.parse(requestDate);
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
