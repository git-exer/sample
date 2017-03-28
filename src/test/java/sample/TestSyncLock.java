package sample;

import org.junit.Test;

public class TestSyncLock {

	@Test
	public void syncTest(){
		SyncLock sl = new SyncLock("SL 1");
		SyncLock sl2 = new SyncLock("SL 2");
		
		new Thread(sl).start();
		new Thread(sl2).start();
		
	}
}
