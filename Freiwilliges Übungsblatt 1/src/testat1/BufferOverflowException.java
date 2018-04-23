package testat1;

public class BufferOverflowException extends Exception{
	@Override
	public String toString() {
		return("I konn ne mer Checkringbuffer voll");
	}
}
