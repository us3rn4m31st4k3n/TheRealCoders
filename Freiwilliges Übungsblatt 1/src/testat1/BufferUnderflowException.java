package testat1;

public class BufferUnderflowException extends Exception {

	String explanation; // to differentiate the causes that lead to the exception a variable explanation is initialized

	/***
	 * @brief Constructor for the BufferUnderFlowException with the introduced parameter "cause"
	 * @param cause
	 */
	public BufferUnderflowException(int cause) {
		if (cause == 0) { // if the exception is given the parameter (0) it was addressed by the CheckModule
			this.explanation = "No egg to check!!!";	// a fitting explanation is given
		}
		if (cause == 1) { // if the exception is given the parameter (1) it was addressed by the OutputModule
			this.explanation = "No egg to put into the eggheaven"; // a fitting explanation is given  here as well
		}
	}

	@Override

	public String toString() {
		return explanation; // the chosen explanation is returned as a String 
	}
}
