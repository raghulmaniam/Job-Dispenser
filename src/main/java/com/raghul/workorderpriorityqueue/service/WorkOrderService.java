package com.raghul.workorderpriorityqueue.service;

import java.util.Date;
import java.util.List;

import com.raghul.workorderpriorityqueue.entity.WorkOrder;

public interface WorkOrderService {

	public String addWorkOrder(WorkOrder workOrder);

	public int deQueueWorkOrder();

	public List<Integer> getWorkOrderIdList();

	public String removeWorkOrderbyId(int id);

	public String getPosition(int id);

	public String getAverageWaitingTime();

}
