package testat1;
import java.io.*;
import Main.Egg;
import Main.FarmDeliveryQuantityException; 

public class InputModul implements Runnable {
	
	private CheckRingbuffer workingEggCheckRing;
	private boolean flagIM = true;
	private Main.EggFileConverter parEFC = new Main.EggFileConverter();
	private int textFileNum = 1;
	private int eggLength = 0;
	private int currentEggPosition =0;
	public Egg[] eggsFromFiles = new Egg[10000];
	
			
	/***
	 * @brief constructor for the InputModul
	 * @param workingEggCheckRing
	 */
	public InputModul(CheckRingbuffer workingEggCheckRing) {
		this.workingEggCheckRing = workingEggCheckRing;
	}
	
	/***
	 * @brief the method to request terminate the loop
	 */
	public void requestTermination() {
		flagIM = false ;
	}
	
	public void parseEggFiles() throws FileNotFoundException, IOException, FarmDeliveryQuantityException {
		try {
		while(parEFC.fileToEgg("eier" + textFileNum + ".txt")!=null) {	// the file has to be filled with something on order to be able to transform it
			
			
		for(int i=0; i<parEFC.fileToEgg("eier" + textFileNum + ".txt").length;i++) {
			
			for(int j=0;j<parEFC.fileToEgg("eier" + textFileNum + ".txt")[i].length;j++) { // he following is the method to transform the 
				eggsFromFiles[i+eggLength] = parEFC.fileToEgg("eier" + textFileNum + ".txt")[i][j];	//two dimensional egg Arrays from the files
				eggLength = eggLength + parEFC.fileToEgg("eier" + textFileNum + ".txt")[i].length;	// two a single one dimensional array which can be used later
			}
			
		}
		textFileNum ++;	// the number of the text (which follows a pattern) is increased 
		}
		}catch(FileNotFoundException e) {
			System.out.println("No more files to parse");
		}
		}
// Two dimensional array has to be converted into a one dimensional array
// The eggs of the first palette have their original indexes as their new indexes 
// for the following palettes the index is (sum of)previouspalette(s).length + index on palette
// for example the index of the first egg on the second palette would be previouspalette.length
// = 25 + index on the current palette = 0 -> 25+0 =25 which is the index of the egg in the created
// two dimensional array
			
		
	

	/***
	 * @brief the overridden method run() with custom code 
	 */
	@Override//Eier müssen da noch irgendwie rein!!!
	public void run() {
		
		try {
			parseEggFiles();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FarmDeliveryQuantityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		while(flagIM) { // the termination condition is the value of flag
			try {
			if(eggsFromFiles[currentEggPosition]!=null) {
			workingEggCheckRing.enqueue(eggsFromFiles[currentEggPosition]);	// the method enqueue is called with the parameter Egg 
			Thread.sleep(100);						// Predetermined sleep timer of thread
			currentEggPosition ++;
			Thread.yield();
			}
			}
			catch(BufferOverflowException bofe) {	// in the IM this exception can occur which needs to be handled
				bofe.toString();
				while(workingEggCheckRing.isOverflown()==true) { // as long as the buffer is over flown the thread waits 
					Thread.yield();
				}
				
			}	
			catch(InterruptedException inte) {		// this Exception is present in all threads and is needed to terminate threads
				inte.printStackTrace();
			}
		}
		
		
	}

}
