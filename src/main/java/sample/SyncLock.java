package sample;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class SyncLock implements Runnable {

	String name;
	public SyncLock(String name){
		this.name = name;
		
	}
	
	public void run() {
		this.method();
	}
	
	ReentrantLock lock = new ReentrantLock();
	public void method(){

		
		lock.lock();
		System.out.println(name + " is in method");
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(name + " wark up");
		lock.unlock();
	}
	
	public static void main(String[] args ){
		SyncLock sl = new SyncLock("SL 1");
		SyncLock sl2 = new SyncLock("SL 2");
		SyncLock sl3 = new SyncLock("SL 3");
		
//		new Thread(sl).start();
//		new Thread(sl2).start();
		ExecutorService e = Executors.newFixedThreadPool(2);
		e.execute(sl);
		e.execute(sl2);
		e.execute(sl3);
	}
}
