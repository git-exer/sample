package sample;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.ReentrantLock;

import javax.swing.text.html.HTMLDocument.Iterator;

public class SyncLock implements Runnable {

	String name;

	public SyncLock(String name) {
		this.name = name;

	}

	public void run() {
		this.method();
	}

	ReentrantLock lock = new ReentrantLock();

	public void method() {

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

	BlockingQueue queue = new ArrayBlockingQueue(10);

	public static void main(String[] args) {
		SyncLock sl = new SyncLock("SL 1");
		SyncLock sl2 = new SyncLock("SL 2");
		SyncLock sl3 = new SyncLock("SL 3");

		sl.queue.add("1");
		sl.queue.add("2");
		// ...
		sl.queue.add("11");

		sl.queue.poll();

		// new Thread(sl).start();
		// new Thread(sl2).start();
		ExecutorService e = Executors.newCachedThreadPool();
		e.execute(sl);
		e.execute(sl2);
		e.execute(sl3);
	}

	public static void question4() {
		try {
			ServerSocket server = new ServerSocket(1234);
			while (true) {
				// 服务器端代码
				Socket conn = server.accept();
				PrintStream ps = new PrintStream(conn.getOutputStream());
				BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
				ps.println("echo: " + reader.readLine());

				conn.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void client() {
		try {
			//客户端代码
			Socket socket = new Socket("localhost", 1234);
			PrintStream ps = new PrintStream(socket.getOutputStream());
			BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			ps.println("hello server");
			System.out.println(reader.readLine());
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	

}

