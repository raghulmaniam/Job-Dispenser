package com.raghul.workorderpriorityqueue.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.raghul.workorderpriorityqueue.service.WorkOrderService;

@Controller
public class WorkOrderPriorityQueueController {

	private final WorkOrderService workOrderService;

	@Autowired
	public WorkOrderPriorityQueueController(WorkOrderService workOrderService) {
		super();
		this.workOrderService = workOrderService;
	}

	@RequestMapping("api/v1/workOrder")
	@ResponseBody

	/*
	 * @GetMapping("/test") public String test() { return "test working";
	 * 
	 * }
	 * 
	 * @GetMapping("save") public String saveWorkOrder(int id, Date orderDate) {
	 * return workOrderService.saveWorkOrder(id, orderDate);
	 * 
	 * }
	 */

	@GetMapping()
	public String getPosition() {
		int id = 0;
		return workOrderService.getPosition(id);
		// return "pos working";

	}

}
