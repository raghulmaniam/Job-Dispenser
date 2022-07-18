package com.raghul.workorderpriorityqueue.controller;

import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.raghul.workorderpriorityqueue.entity.WorkOrder;
import com.raghul.workorderpriorityqueue.service.WorkOrderService;

@Controller
@RequestMapping("api/v1/workOrder")
@ResponseBody
public class WorkOrderPriorityQueueController {

	/*
	 * Developer: Raghul Subramaniam email: raghulmaniam@gmail.com
	 */

	private final WorkOrderService workOrderService;
	long rank = 0;

	@Autowired
	public WorkOrderPriorityQueueController(WorkOrderService workOrderService) {
		super();
		this.workOrderService = workOrderService;
	}

	@GetMapping("date")
	public Date getCurrentDate() {
		// dummy endpoint to get the current date
		return new Date();

	}

	@PostMapping("add")
	public String addWorkOrder(@RequestBody WorkOrder workOrder) {

		try {
			if (workOrder.getRequestDate().compareTo(new Date()) == 1)
				return "Entered dateTime is after the current dateTime. Enter a valid date ";

			return workOrderService.addWorkOrder(workOrder);
		} catch (Exception e) {
			e.printStackTrace();
			return "Exception occured." + e;
		}
	}

	@GetMapping("deQueue")
	public String deQueueWorkOrder() {

		try {
			return workOrderService.deQueueWorkOrder();
		} catch (Exception e) {
			e.printStackTrace();
			return "Exception occured." + e;
		}
	}

	@GetMapping("list")
	public List<Integer> getWorkOrderIdList() {

		try {
			return workOrderService.getWorkOrderIdList();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@PostMapping("remove/{id}")
	public String removeWorkOrderbyId(@PathVariable Integer id) {

		try {
			return workOrderService.removeWorkOrderbyId(id);
		} catch (Exception e) {
			e.printStackTrace();
			return "Exception occured." + e;
		}
	}

	@PostMapping("pos/{id}")
	public String getPosition(@PathVariable Integer id) {

		// check not null
		try {
			return workOrderService.getPosition(id);
		} catch (Exception e) {
			e.printStackTrace();
			return "Exception occured." + e;
		}

	}

	@GetMapping("avgWait")
	public String getAverageWaitingTime() {

		try {
			return workOrderService.getAverageWaitingTime();
		} catch (Exception e) {
			e.printStackTrace();
			return "Exception occured." + e;
		}
	}

}
