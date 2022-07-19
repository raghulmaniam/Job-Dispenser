package com.raghul.workorderpriorityqueue.service;

import java.util.Date;
import java.util.List;

import com.raghul.workorderpriorityqueue.entity.WorkOrder;

public interface WorkOrderService {

	/*
	 * Developer: Raghul Subramaniam email: raghulmaniam@gmail.com
	 */

	public String addWorkOrder(WorkOrder workOrder) throws Exception;

	public String deQueueWorkOrder(Date currDate) throws Exception;

	public List<Long> getWorkOrderIdList(Date currDate) throws Exception;

	public String removeWorkOrderbyId(long id) throws Exception;

	public String getPosition(long id, Date currDate) throws Exception;

	public String getAverageWaitingTime(Date currDate);

}
