package com.raghul.workorderpriorityqueue.service;

import java.util.List;

import com.raghul.workorderpriorityqueue.entity.WorkOrder;

public interface WorkOrderService {

	public String addWorkOrder(WorkOrder workOrder) throws Exception;

	public String deQueueWorkOrder() throws Exception;

	public List<Integer> getWorkOrderIdList() throws Exception;

	public String removeWorkOrderbyId(int id) throws Exception;

	public String getPosition(int id) throws Exception;

	public String getAverageWaitingTime();

}
