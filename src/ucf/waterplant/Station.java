package ucf.waterplant;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Defines the station object which sets many of the attributes of station.
 * @author David
 *
 */
public class Station{
	
	private final Lock mPipeLock = new ReentrantLock();
	
	public int mStationNumber;
	public int mWorkLoad;
	public int mPipe1;
	public int mPipe2;
	
	/**
	 * Station constructor to define a stations number, the two pipes it connects to and the workload.
	 * @param mStationNumber
	 * @param mPipe1
	 * @param mWorkLoad
	 */
	public Station(int mStationNumber, int mPipe1, int mWorkLoad){
		this.mPipe1 = mPipe1;
		this.mPipe2 = mStationNumber;
		this.mStationNumber = mStationNumber;
		this.mWorkLoad = mWorkLoad;
		
		
	}
	
	/**
	 * This method checks the lock for the current station and the adjecent one to see if we can do work.
	 * @param mAdjecentStation
	 * @return true/false if the locks can be locked
	 */
	public boolean CheckLocks(Station mAdjecentStation){
		boolean mPipeLockX = false;
		boolean mPipeLockY = false;
		try{
			if(mPipeLockX = this.mPipeLock.tryLock()) {
				System.out.println("Station "+mStationNumber+": Granted access to pipe "+this.mPipe1);
				writeFile("Station "+mStationNumber+": Granted access to pipe "+this.mPipe1);
			}
			if(mPipeLockY = mAdjecentStation.mPipeLock.tryLock()){
				System.out.println("Station "+mStationNumber+": Granted access to pipe "+this.mPipe2);
				writeFile("Station "+mStationNumber+": Granted access to pipe "+this.mPipe2);
			}
		}finally{
			if(!(mPipeLockX && mPipeLockY)){
				if(mPipeLockX){
					this.mPipeLock.unlock();
					System.out.println("Station "+mStationNumber+": Released access to pipe "+this.mPipe1);
					writeFile("Station "+mStationNumber+": Released access to pipe "+this.mPipe1);
				}
				if(mPipeLockY){
					mAdjecentStation.mPipeLock.unlock();
					System.out.println("Station "+mStationNumber+": Released access to pipe "+this.mPipe2);
					writeFile("Station "+mStationNumber+": Released access to pipe "+this.mPipe2);
				}
			}
		}
		return mPipeLockX && mPipeLockY;
	}
	
	/**
	 * Check the locks with the CheckLock method defined above and if true 'does work' by sleeping for a random period of time and decrementing the workload.
	 * @param mAdjecentStation
	 */
	public void doWork(Station mAdjecentStation){
		
		if(CheckLocks(mAdjecentStation)){
			try{
				System.out.println("Station "+mStationNumber+": Successfully flows "+mWorkLoad);
				writeFile("Station "+mStationNumber+": Successfully flows "+mWorkLoad);
				Random r = new Random();
				try {
					Thread.sleep(r.nextInt(10));
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				mWorkLoad -= 1;
			}finally{
				this.mPipeLock.unlock();
				mAdjecentStation.mPipeLock.unlock();
				System.out.println("Station "+mStationNumber+": Released access to pipe "+this.mPipe1);
				System.out.println("Station "+mStationNumber+": Released access to pipe "+this.mPipe2);
				writeFile("Station "+mStationNumber+": Released access to pipe "+this.mPipe1);
				writeFile("Station "+mStationNumber+": Released access to pipe "+this.mPipe1);
			}
			
		}else{
			//System.out.println("A station is currently using that pipe!");
		}
	}
	private static void writeFile(String mString){
		String FILE = "output.txt";
		try {
			FileWriter mFileWriter = new FileWriter(FILE,true);
			BufferedWriter mBufferedWriter = new BufferedWriter(mFileWriter);
			mBufferedWriter.write(mString);
			mBufferedWriter.newLine();
			mBufferedWriter.close();
			mFileWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}