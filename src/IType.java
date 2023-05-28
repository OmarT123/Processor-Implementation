
public class IType extends Instruction{
	private int r1;
	private int r2;
	private int r1val;
	private int r2val;
	private int immediate;
	
	public IType(String word) {
		super(word);
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
	public int getImmediate() {
		return immediate;
	}
	public void setImmediate(int immediate) {
		this.immediate = immediate;
	}

}
