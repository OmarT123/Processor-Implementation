
public class Instruction {
	private String word;
	private int OPcode;
	private int value;
	private int writeBack;
	private int jump;
	private int memoryWrite;
	private int memoryRead;
	private int instructionNumber;
	private static int counter = -3;
	
	public Instruction (String word) {
		this.word = word;
		this.jump = 0;
		this.memoryRead = 0;
		this.memoryWrite = 0;
		this.instructionNumber = counter++;
	}
	
	public static int getCounter() {
		return counter;
	}

	public static void setCounter(int counter) {
		Instruction.counter = counter;
	}

	public int getInstructionNumber() {
		return instructionNumber;
	}

	public void setInstructionNumber(int instructionNumber) {
		this.instructionNumber = instructionNumber;
	}

	public String getWord() {
		return word;
	}
	public void setWord(String word) {
		this.word = word;
	}
	public int getOPcode() {
		return OPcode;
	}
	public void setOPcode(int oPcode) {
		OPcode = oPcode;
	}
	public int getValue() {
		return value;
	}
	public void setValue(int value) {
		this.value = value;
	}
	public int getWriteBack() {
		return writeBack;
	}
	public void setWriteBack(int writeBack) {
		this.writeBack = writeBack;
	}
	public int getJump() {
		return jump;
	}
	public void setJump(int jump) {
		this.jump = jump;
	}
	public int getMemoryWrite() {
		return memoryWrite;
	}
	public void setMemoryWrite(int memoryWrite) {
		this.memoryWrite = memoryWrite;
	}
	public int getMemoryRead() {
		return memoryRead;
	}
	public void setMemoryRead(int memoryRead) {
		this.memoryRead = memoryRead;
	}
	
}
