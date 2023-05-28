
public class Register {
	private String value; //32 bits
	private String name;
	
	public Register(String name) {
		this.name = name;
		value = "00000000000000000000000000000000";
	}
	
	public void setValue(String value) {
		if (!this.name.equals("R0"))
			this.value = value;
	}
	public String getValue() {
		return this.value;
	}
	public String getName() {
		return this.name;
	}
}
