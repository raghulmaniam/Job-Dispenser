# Job-Dispenser

The aim of this application is to submit jobs to the queue and then effectively prioritize the jobs based on their severity and the time they were on the queue. 

## Prerequisites: 
1. Java 8+
2. REST client to use the endpoints: Boomerang/ Postman

## steps to build:
Prerequisite: Maven 3.8.x+ needs to be installed to build the project
1. Open cmd
2. change the directory to the project root folder (*/Job-Dispenser). (You should be able to see the pom.xml in your current path)
3. run the command : mv clean install

<alternatively you can find the latest jar here -- https://drive.google.com/drive/folders/1DEUbaL4RbSrNXE8-ZDhAsHAqT-we1G-q>

## steps to run:
3. navigate to the target folder or the downloaded path of the jar
4. run the command : java -jar <jar-name.jar>  
 	typically its : java -jar work-order-priority-queue-0.0.1-SNAPSHOT.jar


## Functionalities(endpoints):

###### 1. Adding a job to the queue (enqueue)

@POST: http://localhost:8080/api/v1/workOrder/add
Sample JSON: 
{
    "requestorId":3,
    "requestDate":"2022-07-19 13:15:52"
}

Date Format: yyyy-MM-dd HH:mm:ss

###### 2. Dispense the highest priority job from the queue (dequeue)

@GET: http://localhost:8080/api/v1/workOrder/deQueue 
  
###### 3. Display a list of jobs in the queue from high to low priority.

@GET: http://localhost:8080/api/v1/workOrder/list 

###### 4. Removing a specific job from the queue

@DELETE: http://localhost:8080/api/v1/workOrder/remove/{id} 
	sample: http://localhost:8080/api/v1/workOrder/remove/1

###### 5. Display the position of a job in the queue.

@GET: http://localhost:8080/api/v1/workOrder/pos/{id} 
	sample: http://localhost:8080/api/v1/workOrder/pos/17 

###### 6. Show the average wait time for jobs in the queue.

@GET: http://localhost:8080/api/v1/workOrder/avgWait/{dateInput}
	sample: http://localhost:8080/api/v1/workOrder/avgWait/2022-07-19 13:15:52
	
Date Format: yyyy-MM-dd HH:mm:ss

Known Issues: None

Release Notes can be found here -- here -- https://drive.google.com/drive/folders/1DEUbaL4RbSrNXE8-ZDhAsHAqT-we1G-q
