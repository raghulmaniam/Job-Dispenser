package com.raghul.workorderpriorityqueue.service.impl;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

import org.springframework.stereotype.Service;

import com.raghul.workorderpriorityqueue.entity.WorkOrder;
import com.raghul.workorderpriorityqueue.entity.WorkOrderType;
import com.raghul.workorderpriorityqueue.service.WorkOrderService;
import com.raghul.workorderpriorityqueue.utilities.WorkOrderUtilities;

@Service
public class WorkOrderServiceImpl implements WorkOrderService {

	PriorityQueue<WorkOrder> sortedQueue = new PriorityQueue<WorkOrder>(new WorkOrderComparator());

	Queue<WorkOrder> normalQueue = new LinkedList<WorkOrder>();
	Queue<WorkOrder> priorityQueue = new LinkedList<WorkOrder>();
	Queue<WorkOrder> vipQueue = new LinkedList<WorkOrder>();
	Queue<WorkOrder> managementQueue = new LinkedList<WorkOrder>();

	List<Integer> workOrderIdList = new ArrayList<Integer>();
	WorkOrderType workOrderType;
	Date currDate;
	int totalWaitingTime, totalWorkOrders, averageWaitingTime;

	public int deQueueWorkOrder() {
		WorkOrder topWorkOrder;

		if (!managementQueue.isEmpty()) {
			topWorkOrder = managementQueue.poll();
			return topWorkOrder.getRequestorId();
		} else {
			WorkOrder normalQueueWorkOrder = normalQueue.peek();
			WorkOrder prioriyQueueWorkOrder = priorityQueue.peek();
			WorkOrder vipQueueWorkOrder = vipQueue.peek();

			long topNormalQueueRank = WorkOrderUtilities.computeRank(normalQueueWorkOrder.getWorkOrderType(),
					normalQueueWorkOrder.getRequestDate());

			long topPriorityQueueRank = WorkOrderUtilities.computeRank(prioriyQueueWorkOrder.getWorkOrderType(),
					prioriyQueueWorkOrder.getRequestDate());

			long topvipQueueRank = WorkOrderUtilities.computeRank(vipQueueWorkOrder.getWorkOrderType(),
					vipQueueWorkOrder.getRequestDate());

			// check for null case
			if (topvipQueueRank > topNormalQueueRank && topvipQueueRank > topPriorityQueueRank) {
				return vipQueue.poll().getRequestorId();
			} else if (topPriorityQueueRank > topNormalQueueRank) {
				return priorityQueue.poll().getRequestorId();
			} else
				return normalQueue.poll().getRequestorId();

		}

	}

	public List<Integer> getWorkOrderIdList() {

		if (!workOrderIdList.isEmpty())
			workOrderIdList.clear();

		for (WorkOrder order : managementQueue) {
			workOrderIdList.add(order.getRequestorId());
		}

		rebuildPriortyQueue();

		for (WorkOrder order : sortedQueue) {
			workOrderIdList.add(order.getRequestorId());
		}

		return workOrderIdList;
	}

	private void rebuildPriortyQueue() {

		if (!sortedQueue.isEmpty())
			sortedQueue.clear();

		long rank;
		for (WorkOrder order : vipQueue) {
			rank = WorkOrderUtilities.computeRank(order.getWorkOrderType(), order.getRequestDate());
			order.setRank(rank);
			sortedQueue.add(order);
		}

		for (WorkOrder order : priorityQueue) {
			rank = WorkOrderUtilities.computeRank(order.getWorkOrderType(), order.getRequestDate());
			order.setRank(rank);
			sortedQueue.add(order);
		}

		for (WorkOrder order : normalQueue) {
			rank = WorkOrderUtilities.computeRank(order.getWorkOrderType(), order.getRequestDate());
			order.setRank(rank);
			sortedQueue.add(order);
		}

	}

	public String removeWorkOrderbyId(int id) {
		WorkOrderType type = WorkOrderUtilities.computeWorkOrderType(id);

		switch (type) {
		case NORMAL: {
			removeItemFromQueue(normalQueue, id);
			break;
		}
		case PRIORITY: {
			removeItemFromQueue(priorityQueue, id);
			break;
		}
		case VIP: {
			removeItemFromQueue(vipQueue, id);
			break;
		}
		case MANAGEMENT: {
			removeItemFromQueue(managementQueue, id);
			break;
		}
		default: {
//exception
		}
		}

		return String.format("Success. The work order with Id: %s has been removed from the system", id);
	}

	private void removeItemFromQueue(Queue<WorkOrder> queue, int id) {
		// check if the item gets removed from the master list

		for (WorkOrder order : queue) {
			if (order.getRequestorId() == id) {
				queue.remove(order);
				continue;
			}
		}

	}

	public String getPosition(int id) {
		workOrderIdList = getWorkOrderIdList();

		// handle exception
		return String.valueOf(workOrderIdList.indexOf(id));
	}

	public String getAverageWaitingTime() {

		// re-setting values
		currDate = new Date();
		totalWaitingTime = 0;
		totalWorkOrders = 0;
		averageWaitingTime = 0;

		for (WorkOrder order : managementQueue) {
			totalWaitingTime += WorkOrderUtilities.computeTimeDiffSec(currDate.getTime(),
					order.getRequestDate().getTime());
		}
		for (WorkOrder order : vipQueue) {
			totalWaitingTime += WorkOrderUtilities.computeTimeDiffSec(currDate.getTime(),
					order.getRequestDate().getTime());
		}
		for (WorkOrder order : priorityQueue) {
			totalWaitingTime += WorkOrderUtilities.computeTimeDiffSec(currDate.getTime(),
					order.getRequestDate().getTime());
		}
		for (WorkOrder order : normalQueue) {
			totalWaitingTime += WorkOrderUtilities.computeTimeDiffSec(currDate.getTime(),
					order.getRequestDate().getTime());
		}

		if (totalWorkOrders != 0)
			averageWaitingTime = totalWaitingTime / totalWorkOrders;

		return String.format("The Average waiting time of the orders is %s", averageWaitingTime);
	}

	public String addWorkOrder(WorkOrder workOrder) {

		workOrderType = WorkOrderUtilities.computeWorkOrderType(workOrder.getRequestorId());
		workOrder.setWorkOrderType(workOrderType);

		switch (workOrder.getWorkOrderType()) {
		case NORMAL: {

			if (checkExists(normalQueue, workOrder.getRequestorId()))
				return String.format("Order rejected. Order ID: %s already exists in the system",
						workOrder.getRequestorId());

			normalQueue.add(workOrder);
			break;
		}
		case PRIORITY: {

			if (checkExists(priorityQueue, workOrder.getRequestorId()))
				return String.format("Order rejected. Order ID: %s already exists in the system",
						workOrder.getRequestorId());

			priorityQueue.add(workOrder);
			break;
		}
		case VIP: {

			if (checkExists(vipQueue, workOrder.getRequestorId()))
				return String.format("Order rejected. Order ID: %s already exists in the system",
						workOrder.getRequestorId());

			vipQueue.add(workOrder);
			break;
		}
		case MANAGEMENT: {

			if (checkExists(managementQueue, workOrder.getRequestorId()))
				return String.format("Order rejected. Order ID: %s already exists in the system",
						workOrder.getRequestorId());

			managementQueue.add(workOrder);
			break;
		}
		default: {
			// exception
		}

		}

		// handle exception

		return String.format("Success. Work Order with ID: %s has been added to the system at %t",
				workOrder.getRequestorId(), new Date());
	}

	private boolean checkExists(Queue<WorkOrder> queue, int requestorId) {
		return queue.stream().filter(o -> o.getRequestorId() == requestorId).findFirst().isPresent();
	}

}

class WorkOrderComparator implements Comparator<WorkOrder> {

	public int compare(WorkOrder order1, WorkOrder order2) {

		if (order1.getRank() < order2.getRank())
			return 1;
		else if (order1.getRank() < order2.getRank())
			return -1;
		else
			return 0;

	}

}
