package com.raghul.workorderpriorityqueue.utilities;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import com.raghul.workorderpriorityqueue.constants.WorkOrderPriorityQueueConstants;
import com.raghul.workorderpriorityqueue.entity.WorkOrderType;

public class WorkOrderUtilities {

	public static long computeRank(WorkOrderType workOrderType, Date requestDate) {

		long diff = new Date().getTime() - requestDate.getTime();

		long seconds = TimeUnit.MILLISECONDS.toSeconds(diff);

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

}
