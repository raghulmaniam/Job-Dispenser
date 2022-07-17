package com.raghul.workorderpriorityqueue.controller;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

	@PostMapping("add")
	public String addWorkOrder(@RequestBody WorkOrder workOrder) {

		// check not null

		return workOrderService.addWorkOrder(workOrder);
		// handle exception if not saved
		// compute rank here
	}

	@GetMapping("deQueue")
	public int deQueueWorkOrder() {

		return workOrderService.deQueueWorkOrder();
		// handle exception if not saved
		// compute rank here
	}

	@GetMapping("list")
	public List<Integer> getWorkOrderIdList() {

		return workOrderService.getWorkOrderIdList();
		// handle exception if not saved
		// compute rank here
	}

	@PostMapping("remove/{id}")
	public String removeWorkOrderbyId(@PathVariable Integer id) {

		// check not null

		return workOrderService.removeWorkOrderbyId(id);
		// handle exception if not saved
		// compute rank here
	}

	@PostMapping("pos/{id}")
	public String getPosition(@PathVariable Integer id) {

		// check not null

		return workOrderService.getPosition(id);

		// handle exception if not saved
		// compute rank here
	}

	@GetMapping("avgWait")
	public String getAverageWaitingTime() {

		return workOrderService.getAverageWaitingTime();
		// handle exception if not saved
		// compute rank here
	}

}
