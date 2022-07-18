# Job-Dispenser

The aim of this application is to submit jobs to the queue and then effectively prioritize the jobs based on their severity and the time they were on the queue. 

## steps to run:
1. build the maven project
2. run the jar using this command : java -jar <jar_name>


## The following functionalities(endpoints) are provided:

###### 1. Adding a job to the queue (enqueue)

@POST: http://localhost:8080/api/v1/workOrder/add
Sample JSON: 
{
    "requestorId":3,
    "requestDate":"2022-07-17T13:51:30.497+00:00"
}

(Note: The default format of java.Util.Date class has been used. Please follow the above sample’s format)

###### 2. Dispense the highest priority job from the queue (dequeue)

@GET: http://localhost:8080/api/v1/workOrder/deQueue 
  
###### 3. Display a list of jobs in the queue from high to low priority.

@GET: http://localhost:8080/api/v1/workOrder/list 

###### 4. Removing a specific job from the queue

@POST: http://localhost:8080/api/v1/workOrder/remove/{id} 
	sample: http://localhost:8080/api/v1/workOrder/remove/1

###### 5. Display the position of a job in the queue.

@POST: http://localhost:8080/api/v1/workOrder/pos/{id} 
	sample: http://localhost:8080/api/v1/workOrder/pos/17 

###### 6. Show the average wait time for jobs in the queue.

@GET: http://localhost:8080/api/v1/workOrder/avgWait 
