package testat1;
import java.util.*;
import java.io.FileNotFoundException;
import java.io.IOException;

import Main.*;

public class OutputModul implements Runnable {
	private CheckRingbuffer workingEggCheckRing;
	private boolean flagOM=true; // the termination boolean for our thread
	private Egg[][] checkedEggBox = new Egg [4][25];	// The predefined EggBoxes which were given in the text
	private Egg[] checkedEggs = new Egg [100];	// for simplification we say that the output module transfers the output egg onto an array that equals the total size of the new eggFiles 
	private int fileCounter = 1;	// to have a pattern again we say that the first file is numbered with a 1 and so on
	private int posOfEgg = 0;	// the position of the Egg shows the current max amount of eggs on the formed EggBoxStack (maximum is 100 obviously)
	EggFileConverter eggEFC = new EggFileConverter();	// we need the EggFileConverter to create the Text files from the given Egg arrays
	

	/***
	 * @brief constructor for OM
	 * @param workingEggCheckRing
	 */
	public OutputModul(CheckRingbuffer workingEggCheckRing) {
		this.workingEggCheckRing = workingEggCheckRing;
		
	}
	
	/***
	 * @brief method to end the thread with the variable flagOM
	 */
	public void requestTermination() {
		flagOM=false;
	}

	public void newEggFiles() {
//	What we have to do now is to put the dequeued Egg into an Array which has the limitation of [4][25]
//	Once this Array is full it is exported using the EggToFileMethod we created in the EggFileConverter	
//	Our challenge is to put single Eggs into the array while managing to upkeep the needed sizes of the box	
//	An option would be the use of the create EggBox method we used in our previous test, which would require the use of two arrays	
//	Of which one would be one dimensional and the second would be two to three dimensional
		
		
	}
	/***
	 * @brief the overridden method run()
	 */
	@Override
	public void run() {// try/catch vom Thread in try/catch checkring???

		while (flagOM) {
			Timer idleCheck = new Timer();
			
			try {
				
				
				checkedEggs[posOfEgg]=workingEggCheckRing.dequeue();	 // the method to dequeue the egg on the current position
				if(posOfEgg == 99) { // aka the last empty position in the one dimensional array
					int eggCounter =0;	// a temporal variable to transform from one to two dimensional egg Arrays
					
					for(int i =0; i<checkedEggBox.length-1; i++) {
						
						for (int j =0; j<checkedEggBox[i].length; j++) {
							checkedEggBox[i][j] = checkedEggs[eggCounter];	// standard procedure to transform Arrays between dimensions
							checkedEggs[eggCounter] = null;	// for later use we set the Array on this position to null to ensure that our modules pointers act correctly in the given 
															//situation and to recycle the Array instead of creating a new one
							eggCounter++;	// next position of the egg is taken into account
							posOfEgg=0;		// finally the Array is "reset" in a way to allow a fresh start of the forming of the Array
							
						}
						
					}
					eggEFC.eggToFileConverter(checkedEggBox, "eier-qm"+fileCounter+".txt");	// in the end after the two dimensional checkedArray is created
																							// this Array has to be transformed into a file file. As we want
																							// follow a pattern in the nomination we use the fileCounter
					fileCounter ++;	// the next file has to be named differently from the first one and at the same time has to follow the given pattern, which is given here
				}
				if(checkedEggs!=null) {	// if the condition occurs that there are no checked eggs in the output...
				idleCheck.schedule(new TimerTask() {	// ...a timer-triggered task starts after 10 seconds which packs the existing Eggs on the Array into boxes

					@Override
					public void run() {
						int eggCounterIdle = 0;	// eggCounterIdle is helping variable which serves as a counter in the unfilled Array 
						if((posOfEgg+1)%25==0) { // if the number of Eggs in the stack occasionally happens to be divided through 25 we just fill up the needed amount of boxes
						for(int i =0; i<((posOfEgg+1)/25)-1; i++) {
										//^^^^^^^^^^^^^^^^^ makes sense because for example when 75 eggs are on the Array the last Egg has the index 74
										// plus one would be 75 and that through 25 would be 3. As the counting in IT starts with zero the last palette
										// will have the index 3-1 = 2
							for (int j =0; j<checkedEggBox[i].length; j++) {
								checkedEggBox[i][j] = checkedEggs[eggCounterIdle];
								checkedEggs[eggCounterIdle] = null;
								eggCounterIdle++;
								posOfEgg=0;
								
							}
						}
						}
						else {
							for(int i =0; i<((posOfEgg+1)/25)-1; i++) { // if the frequent occasion happens that it is not divided by 25 all except the
																		// last palette can be filled up completely 
								
								for (int j =0; j<checkedEggBox[i].length; j++) {
									checkedEggBox[i][j] = checkedEggs[eggCounterIdle];
									checkedEggs[eggCounterIdle] = null;
									eggCounterIdle++;
									posOfEgg=0;
									// as mentioned above the EggArray is rest to be recycled and reused
								}
							}
							for(int quanLastBox = 0; quanLastBox < ((posOfEgg+1)%25)-1; quanLastBox++) {	// the last palette has the content of the Eggs
																											// that were missing in the previous ones 
								checkedEggBox[checkedEggs.length/25][quanLastBox] =checkedEggs[eggCounterIdle];
								eggCounterIdle++;
							}
						}
					Thread.yield();	// not sure about the effectiveness of yield but its worth a try
					
					}
					
					
					
				}, 10000); // this method executes if 10 seconds have passed
				}
				posOfEgg++;	// after the very long optional conditions the pointer of the Egg Array has to be moved up
				
				
				
				
				
				
				Thread.sleep(100);				// Predetermined sleep timer of thread
			} catch (BufferUnderflowException bufe) {
				// TODO Auto-generated catch block
				bufe.toString();
				
			}
			 catch (InterruptedException inte) {
				// TODO Auto-generated catch block
				inte.printStackTrace();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
