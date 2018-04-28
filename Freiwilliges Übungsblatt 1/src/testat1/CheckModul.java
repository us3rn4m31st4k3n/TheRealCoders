package testat1;

public class CheckModul implements Runnable {
	private CheckRingbuffer workingEggCheckRing;
	private boolean flag=true;	//flag as an interrupt condition is initialized
	
	public CheckModul(CheckRingbuffer workingEggCheckRing) { // constructor which ensures that all threads address the same CheckRingBuffer
		this.workingEggCheckRing = workingEggCheckRing;	
	}
	
	public void requestTermination() { // method to stop the thread using the interrupt condition flag = false
		flag=false;
	}
	
	@Override
	public void run() {	// overridden method which runs the method as long as no requestTermination is issued
		while(flag) {
			try {
				workingEggCheckRing.checkEgg(); // implemented method which checks egg
				Thread.sleep(50);	// set sleep timer (given through task)
			}catch(BufferUnderflowException bufe) { // custom exception which can be thrown in this case
				bufe.toString();
				while(workingEggCheckRing.isUnderflown()==true) {
					Thread.yield();
				}
			}catch(InterruptedException inte) {
				inte.printStackTrace();
			}
			
		}
	}
	
}
