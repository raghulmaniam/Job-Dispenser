package com.raghul.workorderpriorityqueue.utilities;

import java.util.Date;
import java.util.Queue;
import java.util.concurrent.TimeUnit;

import com.raghul.workorderpriorityqueue.constants.WorkOrderPriorityQueueConstants;
import com.raghul.workorderpriorityqueue.entity.WorkOrder;
import com.raghul.workorderpriorityqueue.entity.WorkOrderType;

public class WorkOrderUtilities {

	public static long computeRank(WorkOrderType workOrderType, Date requestDate) {

		long seconds = computeTimeDiffSec(new Date().getTime(), requestDate.getTime());

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
			// exception
			return Long.MIN_VALUE;
		}
		}
	}

	public static long computeTimeDiffSec(long time1, long time2) {

		return TimeUnit.MILLISECONDS.toSeconds(time1 - time2);
	}

	public static WorkOrderType computeWorkOrderType(int requestorId) {

		if (requestorId % 3 == 0) {
			if (requestorId % 5 == 0)
				return WorkOrderType.MANAGEMENT;
			else
				return WorkOrderType.PRIORITY;
		} else if (requestorId % 5 == 0)
			return WorkOrderType.VIP;
		else
			return WorkOrderType.NORMAL;
	}

	public static boolean checkExists(Queue<WorkOrder> queue, int requestorId) {
		return queue.stream().filter(o -> o.getRequestorId() == requestorId).findFirst().isPresent();
	}

}
