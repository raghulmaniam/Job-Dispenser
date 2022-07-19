package com.raghul.workorderpriorityqueue.constants;

import java.time.ZoneId;

public class WorkOrderPriorityQueueConstants {

	/*
	 * Developer: Raghul Subramaniam email: raghulmaniam@gmail.com
	 */

	public static final int PRIORITY_COMPARATOR = 3; /* for the formula max(3; n log n) */
	public static final int VIP_COMPARATOR = 4; /* for the formula max(4; 2n log n) */

	public static final ZoneId IRELAND = ZoneId.of("Europe/Dublin");
	public static final String DATEFORMAT_YYYTMMDDHHMMSS = "yyyy-MM-dd HH:mm:ss";

}
