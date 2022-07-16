package com.raghul.workorderpriorityqueue.service.impl;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
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

		return String.format("The work order having Id: %s has been removed from the system", id);
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
		// TODO Auto-generated method stub
		return null;
	}

	public String addWorkOrder(WorkOrder workOrder) {

		switch (workOrder.getWorkOrderType()) {
		case NORMAL: {
			normalQueue.add(workOrder);
			break;
		}
		case PRIORITY: {
			priorityQueue.add(workOrder);
			break;
		}
		case VIP: {
			vipQueue.add(workOrder);
			break;
		}
		case MANAGEMENT: {
			managementQueue.add(workOrder);
			break;
		}
		default: {
			// exception
		}

		}

		// handle exception

		return String.format("Work Order with ID: %s has been added to the system at %t", workOrder.getRequestorId(),
				new Date());
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
