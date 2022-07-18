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

	/*
	 * Developer: Raghul Subramaniam email: raghulmaniam@gmail.com
	 */

	PriorityQueue<WorkOrder> sortedQueue = new PriorityQueue<WorkOrder>(new WorkOrderComparator());

	Queue<WorkOrder> normalQueue = new LinkedList<WorkOrder>();
	Queue<WorkOrder> priorityQueue = new LinkedList<WorkOrder>();
	Queue<WorkOrder> vipQueue = new LinkedList<WorkOrder>();
	Queue<WorkOrder> managementQueue = new LinkedList<WorkOrder>();

	List<Long> workOrderIdList = new ArrayList<Long>();
	WorkOrderType workOrderType;
	Date currDate;
	int totalWaitingTime, totalWorkOrders, averageWaitingTime;
	long topNormalQueueRank, topPriorityQueueRank, topVipQueueRank = 0;
	WorkOrder managementWorkOrder, normalQueueWorkOrder, prioriyQueueWorkOrder, vipQueueWorkOrder;

	public String deQueueWorkOrder() throws Exception {

		if (normalQueue.isEmpty() && priorityQueue.isEmpty() && managementQueue.isEmpty() && vipQueue.isEmpty()) {
			return "No Work in the system. Empty queue";
		}

		// checking the management queue first
		if (!managementQueue.isEmpty()) {

			managementWorkOrder = managementQueue.poll();
			return String.format("Order ID: %s | Date: %s", managementWorkOrder.getRequestorId(),
					managementWorkOrder.getRequestDate());

		} else {

			// reset
			topPriorityQueueRank = 0;
			topNormalQueueRank = 0;
			topVipQueueRank = 0;

			normalQueueWorkOrder = normalQueue.peek();
			if (normalQueueWorkOrder != null) {
				topNormalQueueRank = WorkOrderUtilities.computeRank(normalQueueWorkOrder.getWorkOrderType(),
						normalQueueWorkOrder.getRequestDate());
			}

			prioriyQueueWorkOrder = priorityQueue.peek();
			if (prioriyQueueWorkOrder != null) {
				topPriorityQueueRank = WorkOrderUtilities.computeRank(prioriyQueueWorkOrder.getWorkOrderType(),
						prioriyQueueWorkOrder.getRequestDate());
			}

			vipQueueWorkOrder = vipQueue.peek();
			if (vipQueueWorkOrder != null) {
				topVipQueueRank = WorkOrderUtilities.computeRank(vipQueueWorkOrder.getWorkOrderType(),
						vipQueueWorkOrder.getRequestDate());

			}

			// optimal approach - comparing only the top items of all the queues rather than
			// recomputing the rank for all the items.

			if (topVipQueueRank > topNormalQueueRank && topVipQueueRank > topPriorityQueueRank) {
				vipQueueWorkOrder = vipQueue.poll();
				return String.format("Order ID: %s | Date: %s ", vipQueueWorkOrder.getRequestorId(),
						vipQueueWorkOrder.getRequestDate());
			} else if (topPriorityQueueRank > topNormalQueueRank) {
				prioriyQueueWorkOrder = priorityQueue.poll();
				return String.format("Order ID: %s | Date: %s", prioriyQueueWorkOrder.getRequestorId(),
						prioriyQueueWorkOrder.getRequestDate());
			} else {
				normalQueueWorkOrder = normalQueue.poll();
				return String.format("Order ID: %s | Date: %s", normalQueueWorkOrder.getRequestorId(),
						normalQueueWorkOrder.getRequestDate());
			}
		}
	}

	public List<Long> getWorkOrderIdList() throws Exception {

		// reset
		if (!workOrderIdList.isEmpty())
			workOrderIdList.clear();

		// building the management queue first
		for (WorkOrder order : managementQueue) {
			workOrderIdList.add(order.getRequestorId());
			System.out.println(
					"-- console log to test -> id: " + order.getRequestorId() + " | rank : " + order.getRank());
		}

		// priority queue for the remaining non-management work orders
		rebuildPriortyQueue();

		for (WorkOrder order : sortedQueue) {
			workOrderIdList.add(order.getRequestorId());
			System.out.println(
					"-- console log to test -> id: " + order.getRequestorId() + " | rank : " + order.getRank());
		}

		return workOrderIdList;
	}

	private void rebuildPriortyQueue() throws Exception {

		// reset
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

	public String removeWorkOrderbyId(long id) throws Exception {
		WorkOrderType type = WorkOrderUtilities.computeWorkOrderType(id);

		switch (type) {
		case NORMAL: {

			return removeItemFromQueue(normalQueue, id);
		}
		case PRIORITY: {

			return removeItemFromQueue(priorityQueue, id);
		}
		case VIP: {

			return removeItemFromQueue(vipQueue, id);
		}
		case MANAGEMENT: {

			return removeItemFromQueue(managementQueue, id);
		}
		default: {
			throw new Exception("check input format");
		}
		}
	}

	private String removeItemFromQueue(Queue<WorkOrder> queue, long id) {

		Boolean hasRemoved = false;

		for (WorkOrder order : queue) {
			if (order.getRequestorId() == id) {
				queue.remove(order);
				hasRemoved = true;
				continue;
			}
		}

		if (hasRemoved)
			return String.format("Work order Id: %s removed successfully", id);
		else
			return String.format("Work order Id: %s does not exist", id);

	}

	public String getPosition(long id) throws Exception {

		workOrderIdList = getWorkOrderIdList();
		if (workOrderIdList.contains(id))
			return "Position in the queue: " + String.valueOf(workOrderIdList.indexOf(id));
		else
			return "Work ID: " + id + " doesn't exists";
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

		totalWorkOrders = managementQueue.size() + vipQueue.size() + priorityQueue.size() + normalQueue.size();

		// sanity check to avoid arithmetic exception
		if (totalWorkOrders != 0)
			averageWaitingTime = totalWaitingTime / totalWorkOrders;

		return String.format("Average waiting time: %s seconds", averageWaitingTime);
	}

	public String addWorkOrder(WorkOrder workOrder) throws Exception {

		workOrderType = WorkOrderUtilities.computeWorkOrderType(workOrder.getRequestorId());
		workOrder.setWorkOrderType(workOrderType);

		switch (workOrder.getWorkOrderType()) {
		case NORMAL: {

			if (WorkOrderUtilities.checkExists(normalQueue, workOrder.getRequestorId()))
				return String.format("Order ID: %s already exists in the system. Order rejected.",
						workOrder.getRequestorId());

			normalQueue.add(workOrder);
			break;
		}
		case PRIORITY: {

			if (WorkOrderUtilities.checkExists(priorityQueue, workOrder.getRequestorId()))
				return String.format("Order ID: %s already exists in the system. Order rejected.",
						workOrder.getRequestorId());

			priorityQueue.add(workOrder);
			break;
		}
		case VIP: {

			if (WorkOrderUtilities.checkExists(vipQueue, workOrder.getRequestorId()))
				return String.format("Order ID: %s already exists in the system. Order rejected.",
						workOrder.getRequestorId());

			vipQueue.add(workOrder);
			break;
		}
		case MANAGEMENT: {

			if (WorkOrderUtilities.checkExists(managementQueue, workOrder.getRequestorId()))
				return String.format("Order ID: %s already exists in the system. Order rejected.",
						workOrder.getRequestorId());

			managementQueue.add(workOrder);
			break;
		}
		default: {
			throw new Exception("check input format");
		}
		}

		return String.format("Work order Id: %s added successfully", workOrder.getRequestorId());
	}

}

class WorkOrderComparator implements Comparator<WorkOrder> {

	// comparator class for the priority queue
	public int compare(WorkOrder order1, WorkOrder order2) {

		if (order1.getRank() < order2.getRank())
			return 1;
		else if (order1.getRank() > order2.getRank())
			return -1;
		else
			return 0;
	}

}
