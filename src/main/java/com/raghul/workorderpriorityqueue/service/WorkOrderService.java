package com.raghul.workorderpriorityqueue.service;

import java.util.List;

import com.raghul.workorderpriorityqueue.entity.WorkOrder;

public interface WorkOrderService {
	
	/*
	 * Developer: Raghul Subramaniam email: raghulmaniam@gmail.com
	 */

	public String addWorkOrder(WorkOrder workOrder) throws Exception;

	public String deQueueWorkOrder() throws Exception;

	public List<Long> getWorkOrderIdList() throws Exception;

	public String removeWorkOrderbyId(long id) throws Exception;

	public String getPosition(long id) throws Exception;

	public String getAverageWaitingTime();

}
