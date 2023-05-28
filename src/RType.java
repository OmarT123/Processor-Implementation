
public class RType extends Instruction {
	private int r1;
	private int r2;
	private int r3;
	private int r1val;
	private int r2val;
	private int r3val;
	private int shamt;

	public RType(String word) {
		super(word);
	}

	public int getR3val() {
		return r3val;
	}

	public void setR3val(int r3val) {
		this.r3val = r3val;
	}

	public int getR1() {
		return r1;
	}

	public void setR1(int r1) {
		this.r1 = r1;
	}

	public int getR2() {
		return r2;
	}

	public void setR2(int r2) {
		this.r2 = r2;
	}

	public int getR3() {
		return r3;
	}

	public void setR3(int r3) {
		this.r3 = r3;
	}

	public int getR1val() {
		return r1val;
	}

	public void setR1val(int r1val) {
		this.r1val = r1val;
	}

	public int getR2val() {
		return r2val;
	}

	public void setR2val(int r2val) {
		this.r2val = r2val;
	}

	public int getShamt() {
		return shamt;
	}

	public void setShamt(int shamt) {
		this.shamt = shamt;
	}

}
