package ucf.waterplant;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;




public class Station{
	public int mStationNumber;
	public int mWorkLoad;
	private final Lock mPipeLock = new ReentrantLock();
	public int mPipe1;
	public int mPipe2;
	
	public Station(int mStationNumber, int mPipe1, int mWorkLoad){
		this.mPipe1 = mPipe1;
		this.mPipe2 = mStationNumber;
		this.mStationNumber = mStationNumber;
		this.mWorkLoad = mWorkLoad;
		
		
	}
	
	public boolean CheckLocks(Station mAdjecentStation){
		boolean mPipeLockX = false;
		boolean mPipeLockY = false;
		try{
			if(mPipeLockX = this.mPipeLock.tryLock()) System.out.println("Station "+mStationNumber+": Granted access to pipe "+this.mPipe1);
			if(mPipeLockY = mAdjecentStation.mPipeLock.tryLock()) System.out.println("Station "+mStationNumber+": Granted access to pipe "+this.mPipe2);
		}finally{
			if(!(mPipeLockX && mPipeLockY)){
				if(mPipeLockX){
					this.mPipeLock.unlock();
					System.out.println("Station "+mStationNumber+": Released access to pipe "+this.mPipe1);
				}
				if(mPipeLockY){
					mAdjecentStation.mPipeLock.unlock();
					System.out.println("Station "+mStationNumber+": Released access to pipe "+this.mPipe2);
				}
			}
		}
		return mPipeLockX && mPipeLockY;
	}
	
	public void doWork(Station mAdjecentStation){
		
		if(CheckLocks(mAdjecentStation)){
			try{
				System.out.println("Station "+mStationNumber+": Successfully flows "+mWorkLoad);
				mWorkLoad -= 1;
			}finally{
				this.mPipeLock.unlock();
				mAdjecentStation.mPipeLock.unlock();
				//loop.notifyAll();
			}
			
		}else{
			//loop.wait();
			//System.out.println("A station is currently using that pipe!");
		}
	}
}