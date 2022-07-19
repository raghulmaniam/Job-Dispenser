package com.raghul.workorderpriorityqueue.utilities;

import java.util.Date;
import java.util.Queue;
import java.util.concurrent.TimeUnit;

import com.raghul.workorderpriorityqueue.constants.WorkOrderPriorityQueueConstants;
import com.raghul.workorderpriorityqueue.entity.WorkOrder;
import com.raghul.workorderpriorityqueue.entity.WorkOrderType;

public class WorkOrderUtilities {

	/*
	 * Developer: Raghul Subramaniam email: raghulmaniam@gmail.com
	 */

	public static long computeRank(WorkOrderType workOrderType, Date requestDate, Date currdate) throws Exception {

		long seconds = computeTimeDiffSec(currdate.getTime(), requestDate.getTime());

		switch (workOrderType) {
		case NORMAL: {
			return seconds;
		}
		case PRIORITY: {
			return (long) Math.max(WorkOrderPriorityQueueConstants.PRIORITY_COMPARATOR, seconds * Math.log(seconds));
			/* formula : max(3; n log n) */
		}
		case VIP: {
			return (long) Math.max(WorkOrderPriorityQueueConstants.VIP_COMPARATOR, 2 * seconds * Math.log(seconds));
			/* formula : max(4; 2n log n) */
		}
		default: {
			throw new Exception("Check the input format");
		}
		}
	}

	public static long computeTimeDiffSec(long time1, long time2) {

		return TimeUnit.MILLISECONDS.toSeconds(time1 - time2);
	}

	public static WorkOrderType computeWorkOrderType(long l) {

		if (l % 3 == 0) {
			if (l % 5 == 0)
				return WorkOrderType.MANAGEMENT;
			else
				return WorkOrderType.PRIORITY;
		} else if (l % 5 == 0)
			return WorkOrderType.VIP;
		else
			return WorkOrderType.NORMAL;
	}

	public static boolean checkExists(Queue<WorkOrder> queue, long l) {
		return queue.stream().filter(o -> o.getRequestorId() == l).findFirst().isPresent();
	}

}
