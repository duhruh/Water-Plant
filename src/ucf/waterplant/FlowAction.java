/*
 * Author: David Rivera
 * Course: CNT 4714 – Fall 2012
 * Assignment title: Program 2 – Multi-threaded Programming in Java
 * Date: September 23, 2012
 */
package ucf.waterplant;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

/**
 * Class reads in the file creates the Station objects and starts the threads.
 * @author David
 */
public class FlowAction{
	
	private final static String CONFIG_FILE = "config.txt";

	private static ArrayList<Station> mStationList;
	private static int mStations;

	
	/**
	 * WorkLoad is the thread that will run until the WorkLoad for each station is done.
	 * @author David
	 *
	 */
	class WorkLoad implements Runnable{
		Station mThisStation;
		Station mPrevStation;
		public WorkLoad(Station mStation){
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

			writeFile("Station "+this.mThisStation.mStationNumber+": In connection set to pipe "+this.mThisStation.mPipe1);
			writeFile("Station "+this.mThisStation.mStationNumber+": Out connection set to pipe "+this.mThisStation.mPipe2);
			writeFile("Station "+this.mThisStation.mStationNumber+": Workload set to "+this.mThisStation.mWorkLoad);
			
			while(mThisStation.mWorkLoad != 0){
				Random random = new Random();
				try {
                    Thread.sleep(random.nextInt(10));
                } catch (InterruptedException e) {}
				mThisStation.doWork(mPrevStation);
			}
			if(mThisStation.mWorkLoad == 0){
				System.out.println("Station "+this.mThisStation.mStationNumber+": Workload successfully completed.");
				writeFile("Station "+this.mThisStation.mStationNumber+": Workload successfully completed.");
			}
			
		}
		
	}
	/**
	 * Reads in the config file to the Station object.
	 * @param CONFIG_FILE
	 */
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
				if(i == 0) prevPipe = mStations - 1;
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
	
	/**
	 * Starts the all of the threads
	 * @param args
	 */
	public static void main(String[] args){

		mStationList = new ArrayList<Station>();
		readFile(CONFIG_FILE);
		
		for(Station mS: mStationList){
			new Thread(new FlowAction().new WorkLoad(mS)).start();
			
		}
	}

}
