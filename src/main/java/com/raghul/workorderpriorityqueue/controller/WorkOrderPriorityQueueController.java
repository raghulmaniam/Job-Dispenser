package com.raghul.workorderpriorityqueue.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.raghul.workorderpriorityqueue.constants.WorkOrderPriorityQueueConstants;
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
	SimpleDateFormat dateFormat = new SimpleDateFormat(WorkOrderPriorityQueueConstants.DATEFORMAT_YYYTMMDDHHMMSS);
	Date currDate;

	@Autowired
	public WorkOrderPriorityQueueController(WorkOrderService workOrderService) {
		super();
		dateFormat.setTimeZone(TimeZone.getTimeZone(WorkOrderPriorityQueueConstants.IRELAND));
		this.workOrderService = workOrderService;
	}

	@GetMapping("date")
	public Date getCurrentDate() {
		// dummy endpoint to get the current date
		return new Date();

	}

	@GetMapping("dateFormat")
	public String getCurrentDateFormat() {
		// dummy endpoint to get the Formatted current date
		return dateFormat.format(new Date());

	}

	@PostMapping("add")
	public String addWorkOrder(@RequestBody WorkOrder workOrder) {

		/*
		 * An endpoint for adding a ID to queue (enqueue). This endpoint should accept
		 * two parameters, the ID to enqueue and the time at which the ID was added to
		 * the queue.
		 * 
		 * @POST: http://localhost:8080/api/v1/workOrder/add
		 * 
		 * sample JSON: { "requestorId":3,"requestDate":"2022-07-19 13:15:52" }
		 * 
		 * Date Format: yyyy-MM-dd HH:mm:ss
		 */

		try {

			currDate = getCurrentDateWithTimeZone();

			// sanity check
			if (workOrder.getRequestDate().compareTo(currDate) == 1)
				return "Entered dateTime is after the current dateTime. Enter a valid date ";

			// sanity check
			if (workOrder == null || workOrder.getRequestorId() <= 0 || workOrder.getRequestDate() == null)
				return "Please enter a valid JSON input. RequstID and RequestDate needs to be valid ";

			return workOrderService.addWorkOrder(workOrder);

		} catch (Exception e) {
			e.printStackTrace();
			return "Exception occured." + e;
		}
	}

	@GetMapping("deQueue")
	public String deQueueWorkOrder() {

		/*
		 * An endpoint for getting the top ID from the queue and removing it (de-queue).
		 * This endpoint should return the highest ranked ID and the time it was entered
		 * into the queue.
		 * 
		 * @GET: http://localhost:8080/api/v1/workOrder/deQueue
		 * 
		 */

		try {

			currDate = getCurrentDateWithTimeZone();

			return workOrderService.deQueueWorkOrder(currDate);
		} catch (Exception e) {
			e.printStackTrace();
			return "Exception occured." + e;
		}
	}

	@GetMapping("list")
	public List<Long> getWorkOrderIdList() {

		/*
		 * An endpoint for getting the list of IDs in the queue. This endpoint should
		 * return a list of IDs sorted from highest ranked to lowest.
		 * 
		 * @GET: http://localhost:8080/api/v1/workOrder/list
		 * 
		 */

		try {

			currDate = getCurrentDateWithTimeZone();

			return workOrderService.getWorkOrderIdList(currDate);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@DeleteMapping("remove/{id}")
	public String removeWorkOrderbyId(@PathVariable long id) {

		/*
		 * An endpoint for removing a specific ID from the queue. This endpoint should
		 * accept a single parameter, the ID to remove.
		 * 
		 * @DELETE: http://localhost:8080/api/v1/workOrder/remove/{id}
		 * 
		 * sample: http://localhost:8080/api/v1/workOrder/remove/1
		 * 
		 */

		try {
			return workOrderService.removeWorkOrderbyId(id);
		} catch (Exception e) {
			e.printStackTrace();
			return "Exception occured." + e;
		}
	}

	@GetMapping("pos/{id}")
	public String getPosition(@PathVariable long id) {

		try {

			/*
			 * An endpoint to get the position of a specific ID in the queue. This endpoint
			 * should accept one parameter, the ID to get the position of. It should return
			 * the position of the ID in the queue indexed from 0.
			 * 
			 * GET: http://localhost:8080/api/v1/workOrder/pos/{id}
			 * 
			 * sample: http://localhost:8080/api/v1/workOrder/pos/17
			 */

			currDate = getCurrentDateWithTimeZone();

			return workOrderService.getPosition(id, currDate);
		} catch (Exception e) {
			e.printStackTrace();
			return "Exception occured." + e;
		}

	}

	@GetMapping("avgWait/{dateInput}")
	public String getAverageWaitingTime(@PathVariable String dateInput) {

		/*
		 * An endpoint to get the average wait time. This endpoint should accept a
		 * single parameter, the current time, and should return the average (mean)
		 * number of seconds that each ID has been waiting in the queue.
		 * 
		 * GET: http://localhost:8080/api/v1/workOrder/avgWait/{dateInput}
		 * 
		 * sample: http://localhost:8080/api/v1/workOrder/avgWait/2022-07-19 13:15:52
		 * 
		 * Date Format: yyyy-MM-dd HH:mm:ss
		 * 
		 */

		try {

			try {

				currDate = dateFormat.parse(dateInput);
			} catch (Exception e) {
				e.printStackTrace();
				return "Check the date format(yyyy-MM-dd HH:mm:ss) and try again";
			}

			return workOrderService.getAverageWaitingTime(currDate);
		} catch (Exception e) {
			e.printStackTrace();
			return "Exception occured." + e;
		}
	}

	private Date getCurrentDateWithTimeZone() throws ParseException {
		return dateFormat.parse(dateFormat.format(new Date()));
	}

}
