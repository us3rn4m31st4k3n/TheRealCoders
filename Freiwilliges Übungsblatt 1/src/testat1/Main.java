package testat1;

public class Main {

	public static void main(String[] args) {
		CheckRingbuffer myCRB = new CheckRingbuffer();
		Thread inputModul1 = new Thread(new InputModul(myCRB));
		Thread inputModul2 = new Thread(new InputModul(myCRB));
		Thread checkModul = new Thread(new CheckModul(myCRB));
		Thread outputModul = new Thread(new OutputModul(myCRB));
		
		inputModul1.run();
		inputModul2.run();
		checkModul.run();
		outputModul.run();
		

	}

}
