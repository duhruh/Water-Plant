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

public class FlowAction{
	
	private final String CONFIG_FILE = "config.txt";
	private ArrayList<Integer> mWorkLoad = new ArrayList<Integer>();
	private ArrayList<Boolean> mLocks = new ArrayList<Boolean>();
	private int mStations;
	private int mPipes;
	
	
	@Override
	/*public void run() {
		readFile(CONFIG_FILE);
		// TODO need to spawn threads
		for(int i = 0; i <  )
		
	}*/
	private class StationThread extends Thread{
		public int mStationNumber;
		public int mLock1;
		public int mLock2;
		
		public void run(){
			
		}
	}
	private void readFile(String CONFIG_FILE){
		try {
			FileReader mFile = new FileReader(CONFIG_FILE);
			BufferedReader mReader = new BufferedReader(mFile);
			String mLine;
			mStations = Integer.valueOf(mReader.readLine());
			mPipes = mStations;
			while((mLine = mReader.readLine()) != null){
				mWorkLoad.add(Integer.valueOf(mLine));
			}
			
		} catch (FileNotFoundException e) {
			System.out.println("Could not open file for reading!");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("Could not read from file"+CONFIG_FILE+"!");
			e.printStackTrace();
		}
	}

}
