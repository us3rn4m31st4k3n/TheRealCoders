package testat1;

import Main.Egg;

public class InputModul implements Runnable {
	private CheckRingbuffer workingEggCheckRing;
	private boolean flagIM = true;

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

	/***
	 * @brief the overridden method run() with custom code 
	 */
	@Override//Eier müssen da noch irgendwie rein!!!
	public void run() {
		while(flagIM) { // the termination condition is the value of flag
			try {
			workingEggCheckRing.enqueue(inputEgg);	// the method enqueue is called with the parameter Egg 
			Thread.sleep(100);						// Predetermined sleep timer of thread 
			}
			catch(BufferOverflowException bofe) {	// in the IM this exception can occur which needs to be handled
				bofe.toString();
			}	
			catch(InterruptedException inte) {		// this Exception is present in all threads and is needed to terminate threads
				inte.printStackTrace();
			}
		}
		
		
	}

}
