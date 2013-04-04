/**
 * 
 */
package com.cch.util;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

/**
 * @author achikane
 *
 */
public class AsyncEmailProcessor implements Runnable
{
	private String from;
	private String toAddress;
	private String ccAddress;
	private String subject;
	private String body;
	private int noOfRetryAllowed = 5;
	private int delayInRetry = 5; //seconds
	private int noOfRetry = 0;
	private ExecutorService executor = Executors.newFixedThreadPool(2);
	private FutureTask<?> futureTask = new FutureTask<>(this , null);
	
	public void sendEmail(String from , String toAddress , String ccAddress , String subject , String body)
	{
		this.from = from;
		this.toAddress = toAddress;
		this.ccAddress = ccAddress;
		this.subject = subject;
		this.body = body;
		
		try
        {	        		
	        executor.execute(futureTask);
        }
        catch (Exception e)
        {
	        e.printStackTrace();
        }
		
		executor.shutdown();
	}	
	
	private void retry()
	{
		System.out.println("Failed to send email try no : " + noOfRetry );
		executor.execute(futureTask);		
	}
	
	public void run() 
	{		
		try
        {
			System.out.println("In RUN");
	        EmailNotification.sendEmail(from , toAddress , ccAddress , body , subject);			
        }
        catch (Exception e)
        {
        	System.out.println("In CATCH");
        	e.printStackTrace();
        	if(this.noOfRetry < this.noOfRetryAllowed)
        	{        		
        		this.noOfRetry++;
        		this.retry();
        	}
        }		
	}
}
