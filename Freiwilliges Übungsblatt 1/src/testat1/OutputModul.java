package testat1;

import Main.Egg;

public class OutputModul implements Runnable {
	private CheckRingbuffer workingEggCheckRing;
	private boolean flagOM=true;
	private Egg[][] checkedEggsBox = new Egg [4][25];

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

	/***
	 * @brief the overridden method run()
	 */
	@Override
	public void run() {// try/catch vom Thread in try/catch checkring???

		while (flagOM) {
			try {
				workingEggCheckRing.dequeue();	// the current Egg on the position of the OM pointer is dequeued
				Thread.sleep(100);				// Predetermined sleep timer of thread
			} catch (BufferUnderflowException bufe) {
				// TODO Auto-generated catch block
				bufe.toString();
				
			}
			 catch (InterruptedException inte) {
				// TODO Auto-generated catch block
				inte.printStackTrace();
			}
		}
	}

}
