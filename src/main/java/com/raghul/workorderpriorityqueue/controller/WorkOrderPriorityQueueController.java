package com.raghul.workorderpriorityqueue.controller;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.raghul.workorderpriorityqueue.constants.WorkOrderPriorityQueueConstants;
import com.raghul.workorderpriorityqueue.entity.WorkOrder;
import com.raghul.workorderpriorityqueue.entity.WorkOrderType;
import com.raghul.workorderpriorityqueue.service.WorkOrderService;
import com.raghul.workorderpriorityqueue.utilities.WorkOrderUtilities;

@Controller
@RequestMapping("api/v1/workOrder")
@ResponseBody
public class WorkOrderPriorityQueueController {

	private final WorkOrderService workOrderService;
	long rank = 0;
	

	@Autowired
	public WorkOrderPriorityQueueController(WorkOrderService workOrderService) {
		super();
		this.workOrderService = workOrderService;
	}

	@GetMapping
	public Date getPosition() {
		int id = 0;
		// return workOrderService.getPosition(id);
		return new Date();

	}

	@PostMapping("")
	public String saveWorkOrder(@RequestBody WorkOrder workOrder) {

		// check the id is already there
		// check not null
		

		// rank = computeRank(workOrderType, workOrder.getRequestDate());
		// workOrder.setRank(rank);

		return workOrderService.addWorkOrder(workOrder);

		// handle exception if not saved
		// compute rank here
	}

}
