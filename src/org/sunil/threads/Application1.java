package org.sunil.threads;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

class Task{
	private int taskId;

	public Task(int taskId) {
		super();
		this.taskId = taskId;
	}

	public int getTaskId() {
		return taskId;
	}

	public String doWork(){
		return "Work done. Task completion id is "+ taskId;
	}
}

class WorkerThread implements Runnable{
	private Task task;

	public WorkerThread(Task task) {
		super();
		this.task = task;
	}

	@Override
	public void run(){
		System.out.println(task.doWork() + "\t"+Thread.currentThread().getName());
		try{
			Thread.sleep(10);
		}catch(InterruptedException ex){
			ex.printStackTrace();
		}
	}
}

public class Application1 {
	public static void main(String []args){

		Long startTime = System.currentTimeMillis();
		//i am creating 4 thread in the thread pool
		ExecutorService executor = Executors.newFixedThreadPool(5);

		//would like to perform 10 tasks
		for(int i=1; i<=10; i++)
			executor.submit(new WorkerThread(new Task(i)));

		//Initiates an orderly shutdown in which previously submitted tasks are executed, 
		//but no new tasks will be accepted. Invocation has no additional effect if already shut down. 
		//This method does not wait for previously submitted tasks to complete execution

		executor.shutdown();

		try {
			//Blocks until all tasks have completed execution after a 
			//shutdown request, or the timeout occurs, or the current 
			//thread is interrupted, whichever happens first.

			executor.awaitTermination(1, TimeUnit.DAYS);
			
			Long endTime = System.currentTimeMillis();
			
			System.out.println("Time taken to complete "+ (endTime - startTime));
			
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}
}
