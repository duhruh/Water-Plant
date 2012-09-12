/*
 * Author: David Rivera
 * Course: CNT 4714 – Fall 2012
 * Assignment title: Program 2 – Multi-threaded Programming in Java
 * Date: September 23, 2012
 */
package ucf.waterplant;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class FlowAction{
	
	private final static String CONFIG_FILE = "config.txt";

	private static ArrayList<Station> mStationList;
	private static int mStations;

	
	class Loop implements Runnable{
		Station mThisStation;
		Station mPrevStation;
		public Loop(Station mStation){
			this.mThisStation = mStation;
			int pos = mStationList.indexOf(mStation);
			if(pos != 0) mPrevStation = mStationList.get(pos - 1);
			else mPrevStation = mStationList.get(mStationList.size()-1);
		}
		@Override
		public void run() {
			System.out.println("Station "+this.mThisStation.mStationNumber+": In connection set to pipe "+this.mThisStation.mPipe1);
			System.out.println("Station "+this.mThisStation.mStationNumber+": Out connection set to pipe "+this.mThisStation.mPipe2);
			System.out.println("Station "+this.mThisStation.mStationNumber+": Workload set to "+this.mThisStation.mWorkLoad);
			while(mThisStation.mWorkLoad != 0){
				Random random = new Random();
				try {
                    Thread.sleep(random.nextInt(10));
                } catch (InterruptedException e) {}
				mThisStation.doWork(mPrevStation);
			}
			
		}
		
	}
	private static void readFile(String CONFIG_FILE){
		FileReader mFile = null;
		BufferedReader mReader = null;
		try {
			mFile = new FileReader(CONFIG_FILE);
			mReader = new BufferedReader(mFile);
			String mLine;
			mStations = Integer.valueOf(mReader.readLine());
			//mPipes = mStations;
			int i = 0;
			int prevPipe;
			while((mLine = mReader.readLine()) != null){
				if(i == 0) prevPipe = mStations;
				else prevPipe = i - 1;
				Station mStation = new Station(i,prevPipe,Integer.valueOf(mLine));
				mStationList.add(mStation);
				++i;
			}
			
		} catch (FileNotFoundException e) {
			System.out.println("Could not open file for reading!");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("Could not read from file"+CONFIG_FILE+"!");
			e.printStackTrace();
		}finally{
			try {
				mFile.close();
				mReader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void main(String[] args){
		mStationList = new ArrayList<Station>();
		readFile(CONFIG_FILE);
		
		for(Station mS: mStationList){
			new Thread(new FlowAction().new Loop(mS)).start();
			
		}
	}

}
