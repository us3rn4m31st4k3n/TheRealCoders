package testat1;

import Main.Egg;

public class CheckRingbuffer {
	Egg[] eggCheckRing = new Egg[50];
	int inputPointer=0;
	int checkPointer=0;
	int outputPointer=0;
	int numOfEggsInRing;
	// counter für Anzahl Eier im Buffer und Counter für zu prüfende Eier

	/***
	 * @brief Enqueues the inputEgg into the checkRingBufffer
	 * @param inputEgg
	 * @throws BufferOverflowException
	 */
	public void enqueue(Egg inputEgg) throws BufferOverflowException {
			
			while(eggCheckRing[inputPointer%eggCheckRing.length]!=null && inputPointer>outputPointer) { // while the the inputPointer is  pointed on a taken place 
																										//and it is behind the outputpointer, the inputpointer jumps to the next field
				inputPointer++;
			}
			if(eggCheckRing[inputPointer%eggCheckRing.length]==null) {//if the inputpointer is on a empty field, the inputpointer puts an egg in this field
				eggCheckRing[checkPointer]=inputEgg;
			}else {//if the checkringbuffer is full, a BufferOverflowException is thrown
				throw new BufferOverflowException();
			}
			
	}
	/***
	 * @brief Checks the current egg for defects, destroys egg if defect is found
	 * @throws BufferUnderflowException
	 */
	public void checkEgg() throws BufferUnderflowException {
			while(eggCheckRing[checkPointer%eggCheckRing.length]==null && checkPointer<inputPointer) {	// while the pointer is on an empty field and behind the input
																										// pointer at the same time it jumps to point on the next field
				checkPointer++;
			}
			if(eggCheckRing[checkPointer%eggCheckRing.length]!=null && eggCheckRing[checkPointer%eggCheckRing.length].getDefect()) { 
				// If an empty position is reached 
				// And the egg has a defect (determined by the method getDefect() of the given Object of the class Egg) 
					eggCheckRing[checkPointer%eggCheckRing.length]=null;			// it will be destroyed (i.e. the field is set to null)
				
			}else {
				throw new BufferUnderflowException(0); // if no egg is on the checked position the exception is thrown
			}
	}
/***
 * @brief The checked egg is dequeued from the checkRingBuffer an returned as an Egg
 * @return
 * @throws BufferUnderflowException
 */
	public Egg dequeue() throws BufferUnderflowException {
		while(eggCheckRing[outputPointer%eggCheckRing.length]==null && outputPointer<checkPointer) {	// while the outputPointer is on an empty field and behind the
																										// checkPointer, the outputPointer jumps one spot further
			outputPointer++;
		}
		if(eggCheckRing[outputPointer%eggCheckRing.length]!=null) { // if the field at the outputPointer is not empty....
			
			return eggCheckRing[outputPointer%eggCheckRing.length];	//.... the Egg at that position is returned for later use
		}else {
			throw new BufferUnderflowException(1); 	// if checkRingBuffer is completely empty, this exception is thrown
		}
	}
	
	public boolean isUnderflown() {// helping methods to determine constantly whether the buffer is empty or not
		for (int i=0; i< eggCheckRing.length-1; i++) {
			if(eggCheckRing[i]!=null) {
				numOfEggsInRing++;
			}
			
		}
		if(numOfEggsInRing == 0) {
			return true;
		}
		else {
			return false;
		}
	}
	
	public boolean isOverflown() { // helping methods to determine constantly whether the buffer is full or not
		
		for (int i=0; i< eggCheckRing.length-1; i++) {
			if(eggCheckRing[i]!=null) {
				numOfEggsInRing++;
			}
			
		}
		if(numOfEggsInRing == eggCheckRing.length-1) {
			return true;
		}
		else {
			return false;
		}
	}

//	public Egg[] getEggCheckRing() {
//		return eggCheckRing;
//	}
}
