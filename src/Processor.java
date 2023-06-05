import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Processor {
	static final HashMap<Integer, Register> hmap = new HashMap<>();
	static final String[] MEMORY = new String[2048]; // 0-1023 for instructions, and 1024-2047 for data
	static Instruction pipelinereg1 = new Instruction(null), pipelinereg2 = new Instruction(null),
			pipelinereg3 = new Instruction(null), pipelinereg4 = new Instruction(null);
	static final HashMap<String, String> parser = new HashMap<>();
	static int instructionIndex = 0;
	static int timeOutMem = 0, timeOutWB = 0;
	static int cycle = 0;
	static int numberOfInstructions = 0;
	static boolean f = true, m = false, w = true, wb = false, mw = false, mm = false;
	static Register lastChange;
	static int indexChanged;

	public static void printMemory() {
		for (int i = 0; i < 2048; i++) {
			System.out.println(i + ": " + MEMORY[i]);
		}
	}

	public static Instruction instructionFetch() {
		Register PC = hmap.get(32);
		String address = PC.getValue();
		int memoryAddress = binaryToInt(address);
		PC.setValue(intToBinary(memoryAddress + 1));
		if (MEMORY[memoryAddress] == "00000000000000000000000000000000")
			return null;
		return new Instruction(MEMORY[memoryAddress]);
	}

	public static int binaryToInt(String s) {
		return Integer.parseInt(s, 2);
	}

	public static String intToBinary(int x) {
		String binaryString = Integer.toBinaryString(x);
		while (binaryString.length() < 32) {
			binaryString = "0" + binaryString;
		}
		return binaryString;
	}

	public static Instruction instructionDecode(Instruction instruction) {
		if (instruction == null)
			return null;
		instruction.setOPcode(binaryToInt(instruction.getWord().substring(0, 4)));
		int operation = instruction.getOPcode();
		switch (operation) {
		case 0:
			// ADD R
			Instruction.setCounter(Instruction.getCounter() - 1);
			RType temp = new RType(instruction.getWord());
			temp.setOPcode(operation);
			temp.setR1(binaryToInt(instruction.getWord().substring(4, 9)));
			temp.setR2(binaryToInt(instruction.getWord().substring(9, 14)));
			temp.setR2val(binaryToInt(hmap.get(temp.getR2()).getValue()));
			temp.setR3(binaryToInt(instruction.getWord().substring(14, 19)));
			temp.setR3val(binaryToInt(hmap.get(temp.getR3()).getValue()));
			temp.setShamt(binaryToInt(instruction.getWord().substring(19)));
			temp.setWriteBack(temp.getR1());
			return temp;
		case 1:
			// SUB R
			Instruction.setCounter(Instruction.getCounter() - 1);
			RType temp1 = new RType(instruction.getWord());
			temp1.setOPcode(operation);
			temp1.setR1(binaryToInt(instruction.getWord().substring(4, 9)));
			temp1.setR2(binaryToInt(instruction.getWord().substring(9, 14)));
			temp1.setR2val(binaryToInt(hmap.get(temp1.getR2()).getValue()));
			temp1.setR3(binaryToInt(instruction.getWord().substring(14, 19)));
			temp1.setR3val(binaryToInt(hmap.get(temp1.getR3()).getValue()));
			temp1.setShamt(binaryToInt(instruction.getWord().substring(19)));
			temp1.setWriteBack(temp1.getR1());
			return temp1;
		case 2:
			// MUL R
			Instruction.setCounter(Instruction.getCounter() - 1);
			RType temp2 = new RType(instruction.getWord());
			temp2.setOPcode(operation);
			temp2.setR1(binaryToInt(instruction.getWord().substring(4, 9)));
			temp2.setR2(binaryToInt(instruction.getWord().substring(9, 14)));
			temp2.setR2val(binaryToInt(hmap.get(temp2.getR2()).getValue()));
			temp2.setR3(binaryToInt(instruction.getWord().substring(14, 19)));
			temp2.setR3val(binaryToInt(hmap.get(temp2.getR3()).getValue()));
			temp2.setShamt(binaryToInt(instruction.getWord().substring(19)));
			temp2.setWriteBack(temp2.getR1());
			return temp2;
		case 3:
			// MOVI I
			Instruction.setCounter(Instruction.getCounter() - 1);
			IType temp3 = new IType(instruction.getWord());
			temp3.setOPcode(operation);
			temp3.setR1(binaryToInt(instruction.getWord().substring(4, 9)));
			temp3.setWriteBack(temp3.getR1());
			temp3.setImmediate(binaryToInt(instruction.getWord().substring(14)));
			return temp3;
		case 4:
			// JEQ I
			Instruction.setCounter(Instruction.getCounter() - 1);
			IType temp4 = new IType(instruction.getWord());
			temp4.setOPcode(operation);
			temp4.setR1(binaryToInt(instruction.getWord().substring(4, 9)));
			temp4.setR2(binaryToInt(instruction.getWord().substring(9, 14)));
			temp4.setR1val(binaryToInt(hmap.get(temp4.getR1()).getValue()));
			temp4.setR2val(binaryToInt(hmap.get(temp4.getR2()).getValue()));
			temp4.setImmediate(binaryToInt(hmap.get(32).getValue()) + binaryToInt(instruction.getWord().substring(14)));
			temp4.setWriteBack(32);
			return temp4;
		case 5:
			// AND R
			Instruction.setCounter(Instruction.getCounter() - 1);
			RType temp5 = new RType(instruction.getWord());
			temp5.setOPcode(operation);
			temp5.setR1(binaryToInt(instruction.getWord().substring(4, 9)));
			temp5.setR2(binaryToInt(instruction.getWord().substring(9, 14)));
			temp5.setR2val(binaryToInt(hmap.get(temp5.getR2()).getValue()));
			temp5.setR3(binaryToInt(instruction.getWord().substring(14, 19)));
			temp5.setR3val(binaryToInt(hmap.get(temp5.getR3()).getValue()));
			temp5.setShamt(binaryToInt(instruction.getWord().substring(19)));
			temp5.setWriteBack(temp5.getR1());
			return temp5;
		case 6:
			// XORI I
			Instruction.setCounter(Instruction.getCounter() - 1);
			IType temp6 = new IType(instruction.getWord());
			temp6.setOPcode(operation);
			temp6.setR1(binaryToInt(instruction.getWord().substring(4, 9)));
			temp6.setR2(binaryToInt(instruction.getWord().substring(9, 14)));
			temp6.setImmediate(binaryToInt(instruction.getWord().substring(14)));
			temp6.setWriteBack(temp6.getR1());
			temp6.setR2val(binaryToInt(hmap.get(temp6.getR2()).getValue()));
			return temp6;
		case 7:
			// JMP J
			Instruction.setCounter(Instruction.getCounter() - 1);
			JType temp7 = new JType(instruction.getWord());
			temp7.setOPcode(operation);
			temp7.setAddress((instruction.getWord().substring(4)));
			temp7.setValue(binaryToInt(hmap.get(32).getValue()));
			temp7.setWriteBack(32);
			temp7.setJump(1);
			return temp7;
		case 8:
			// LSL R
			Instruction.setCounter(Instruction.getCounter() - 1);
			RType temp8 = new RType(instruction.getWord());
			temp8.setOPcode(operation);
			temp8.setR1(binaryToInt(instruction.getWord().substring(4, 9)));
			temp8.setR2(binaryToInt(instruction.getWord().substring(9, 14)));
			temp8.setR2val(binaryToInt(hmap.get(temp8.getR2()).getValue()));
			temp8.setShamt(binaryToInt(instruction.getWord().substring(19)));
			temp8.setWriteBack(temp8.getR1());
			return temp8;
		case 9:
			// LSR R
			Instruction.setCounter(Instruction.getCounter() - 1);
			RType temp9 = new RType(instruction.getWord());
			temp9.setOPcode(operation);
			temp9.setR1(binaryToInt(instruction.getWord().substring(4, 9)));
			temp9.setR2(binaryToInt(instruction.getWord().substring(9, 14)));
			temp9.setR2val(binaryToInt(hmap.get(temp9.getR2()).getValue()));
			temp9.setShamt(binaryToInt(instruction.getWord().substring(19)));
			temp9.setWriteBack(temp9.getR1());
			return temp9;
		case 10:
			// MOVR I
			Instruction.setCounter(Instruction.getCounter() - 1);
			IType temp10 = new IType(instruction.getWord());
			temp10.setOPcode(operation);
			temp10.setMemoryRead(1);
			temp10.setR1(binaryToInt(instruction.getWord().substring(4, 9)));
			temp10.setR2(binaryToInt(instruction.getWord().substring(9, 14)));
			temp10.setR2val(binaryToInt(hmap.get(temp10.getR2()).getValue()));
			temp10.setImmediate(binaryToInt(instruction.getWord().substring(14)));
			temp10.setWriteBack(temp10.getR1());
			return temp10;
		case 11:
			// MOVM I
			Instruction.setCounter(Instruction.getCounter() - 1);
			IType temp11 = new IType(instruction.getWord());
			temp11.setOPcode(operation);
			temp11.setMemoryWrite(1);
			temp11.setR1(binaryToInt(instruction.getWord().substring(4, 9)));
			temp11.setR2(binaryToInt(instruction.getWord().substring(9, 14)));
			temp11.setR1val(binaryToInt(hmap.get(temp11.getR1()).getValue()));
			temp11.setR2val(binaryToInt(hmap.get(temp11.getR2()).getValue()));
			temp11.setImmediate(binaryToInt(instruction.getWord().substring(14)));
			temp11.setValue(temp11.getR1val());
			return temp11;
		}
		return instruction;
	}

	public static Instruction execute(Instruction instruction) {
		if (instruction == null)
			return null;
		int opcode = instruction.getOPcode();
		switch (opcode) {
		case 0:
			// ADD
			RType cur1 = (RType) instruction;
			cur1.setValue(cur1.getR2val() + cur1.getR3val());
			return cur1;
		case 1:
			// SUB
			RType cur2 = (RType) instruction;
			cur2.setValue(cur2.getR2val() - cur2.getR3val());
			return cur2;
		case 2:
			// MUL
			RType cur3 = (RType) instruction;
			cur3.setValue(cur3.getR2val() * cur3.getR3val());
			return cur3;
		case 3:
			// MOVI
			IType cur4 = (IType) instruction;
			cur4.setValue(cur4.getImmediate());
			return cur4;
		case 4:
			// JEQ
			IType cur5 = (IType) instruction;
			if (cur5.getR1val() == cur5.getR2val()) {
				cur5.setJump(1);
				cur5.setValue(cur5.getImmediate());
			}
			return cur5;
		case 5:
			// AND
			RType cur6 = (RType) instruction;
			cur6.setValue(cur6.getR2val() & cur6.getR3val());
			return cur6;
		case 6:
			// XORI
			IType cur7 = (IType) instruction;
			cur7.setValue(cur7.getImmediate() ^ cur7.getR2val());
			return cur7;
		case 7:
			// JMP
			JType cur8 = (JType) instruction;
			cur8.setValue(binaryToInt(intToBinary(cur8.getValue()).substring(0, 4) + "" + cur8.getAddress()));
			return cur8;
		case 8:
			// LSL
			RType cur9 = (RType) instruction;
			cur9.setValue(cur9.getR2val() << cur9.getShamt());
			return cur9;
		case 9:
			// LSR
			RType cur10 = (RType) instruction;
			cur10.setValue(cur10.getR2val() >>> cur10.getShamt());
			return cur10;
		case 10:
			// MOVR
			IType cur11 = (IType) instruction;
			cur11.setValue(cur11.getR2val() + cur11.getImmediate());
			return cur11;
		case 11:
			// MOVM
			IType cur12 = (IType) instruction;
			cur12.setWriteBack(cur12.getR2val() + cur12.getImmediate());
			return cur12;
		}
		return null;
	}

	public static Instruction memory(Instruction inst) {
		if (inst == null)
			return null;
		mw = false;
		if (timeOutMem <= 0) {
			if (inst.getMemoryRead() == 1) {
				inst.setValue(binaryToInt(MEMORY[inst.getValue()]));
			} else if (inst.getMemoryWrite() == 1) {
				MEMORY[inst.getWriteBack()] = intToBinary(inst.getValue());
				mw = true;
				indexChanged = inst.getWriteBack();
			}
		}
		// timeOutMem--;
		return inst;
	}

	public static void writeBack(Instruction instruction) {
		if (instruction == null)
			return;
		if (timeOutWB <= 0) {
			// wb = false;
			if (instruction.getMemoryWrite() == 1)
				return;
			Register cur = hmap.get(instruction.getWriteBack());
			if (instruction.getWriteBack() != 32 || instruction.getJump() == 1)
				cur.setValue(intToBinary(instruction.getValue()));
			if (instruction.getJump() == 1) {
				timeOutMem = 2;
				timeOutWB = 3;
			}
			lastChange = cur;
			// wb = true;
			// System.out.println("WB: " + binaryToInt(instruction.getWord()));
		}
		// timeOutWB--;
		// System.out.println("WB: " + binaryToInt(instruction.getWord()));
	}

	public static void runProgram(String fileName) {
		int numInst = readFile(fileName);
		numberOfInstructions = numInst;
		int clockCycles = 7 + ((numInst - 1) * 2);
		int decodeTimer = 1;
		int executeTimer = 3;
		int memoryTimer = 5;
		int writeBackTimer = 6;
		for (cycle = 1; true; cycle++) {
			mm = false;
			wb = false;
			if (writeBackTimer == 0) {
				if (timeOutWB <= 0) {
					writeBack(pipelinereg4);
					wb = true;
				}
				timeOutWB--;
				writeBackTimer = 1;
			} else
				writeBackTimer--;
			if (memoryTimer == 0 && pipelinereg4 != null) {
				if (timeOutMem <= 0) {
					pipelinereg4 = memory(pipelinereg3);
					mm = true;
				}
				timeOutMem--;
				memoryTimer = 1;
			} else
				memoryTimer--;
			if (executeTimer == 0 && pipelinereg3 != null) {
				pipelinereg3 = execute(pipelinereg2);
				executeTimer = 1;
			} else
				executeTimer--;
			if (decodeTimer == 0 && pipelinereg2 != null) {
				pipelinereg2 = instructionDecode(pipelinereg1);
				decodeTimer = 1;
			} else
				decodeTimer--;
			if (cycle % 2 == 1 && pipelinereg1 != null) {
				pipelinereg1 = instructionFetch();
			}
			if (pipelinereg1 == null && pipelinereg2 == null && pipelinereg3 == null && pipelinereg4 == null) {
				Register pc = hmap.get(32);
				int val = binaryToInt(pc.getValue()) - 1;
				pc.setValue(intToBinary(val));
				break;
			}
			printCycle();
		}
		printHashMap();
		System.out.println();
		printMemory();
	}

	public static void printCycle() {
		System.out.println("Cycle #" + cycle);
		System.out.println("--------------------");
		if (pipelinereg1 != null && pipelinereg1.getWord() != null && f) {
			System.out.println("Fetch Instruction #" + pipelinereg1.getInstructionNumber());
			System.out.println(binaryToInt(pipelinereg1.getWord()));
			System.out.println("-----");
		}
		if (pipelinereg2 != null && pipelinereg2.getWord() != null) {
			System.out.println("Decode Instruction #" + pipelinereg2.getInstructionNumber());
			System.out.println(binaryToInt(pipelinereg2.getWord()));
			System.out.println("-----");
		}
		if (pipelinereg3 != null && pipelinereg3.getWord() != null) {
			System.out.println("Execute Instruction #" + pipelinereg3.getInstructionNumber());
			System.out.println(binaryToInt(pipelinereg3.getWord()));
			System.out.println("-----");
		}
		if (pipelinereg4 != null && pipelinereg4.getWord() != null && m && mm) {
			System.out.println("Memory #" + pipelinereg4.getInstructionNumber());
			System.out.println(binaryToInt(pipelinereg4.getWord()));
			if (mw)
				System.out.println("MEMORY[" + indexChanged + "]" + " = " + MEMORY[indexChanged]);
			System.out.println("-----");
		}
		if (pipelinereg4 != null && pipelinereg4.getWord() != null && w && wb) {
			System.out.println("Write Back #" + pipelinereg4.getInstructionNumber());
			System.out.println(binaryToInt(pipelinereg4.getWord()));
			System.out.println(lastChange.getName() + " = " + lastChange.getValue());
			System.out.println("-----");
		}
		w = !w;
		f = !f;
		m = !m;
		System.out.println();
		System.out.println();
	}

	public static int readFile(String fileName) {
		int numInst = 0;
		try {
			BufferedReader reader = new BufferedReader(new FileReader(fileName));
			String line;

			while ((line = reader.readLine()) != null) {
				// read next instruction
				StringTokenizer st = new StringTokenizer(line);
				StringBuilder sb = new StringBuilder();
				while (st.hasMoreTokens()) {
					String key = st.nextToken();
					if (parser.containsKey(key)) // one of the known instructions/registers
						sb.append(parser.get(key));
					else { // either a number or wrong input
						try {
							int num = Integer.parseInt(key);
							String numString;
							if (sb.toString().length() == 19) {
								// R type (shamt)
								numString = intToBinary(num).substring(19);
							} else if (sb.toString().length() == 14) {
								// I type (immediate)
								numString = intToBinary(num).substring(14);
							} else if (sb.toString().length() == 4) {
								// J type (address)
								numString = intToBinary(num).substring(4);
							} else {
								numString = "00000" + intToBinary(num).substring(14);
							}
							sb.append(numString);
						} catch (Exception e) {
							System.err.println("Error wrong input: " + e.getMessage());
						}
					}
				}
				while (sb.toString().length() < 32)
					sb.append("0");
				MEMORY[instructionIndex++] = sb.toString(); // Where should the instruction be placed
				numInst++;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return numInst;
	}

	public static void printHashMap() {
		for (Map.Entry<Integer, Register> entry : hmap.entrySet()) {
			if (entry.getKey() != 32)
				System.out.println("Name: R" + entry.getKey() + ", Value: " + entry.getValue().getValue() + ", Integer: "
					+ binaryToInt(entry.getValue().getValue()));
			else
				System.out.println("Name: PC" + ", Value: " + entry.getValue().getValue() + ", Integer: "
					+ binaryToInt(entry.getValue().getValue()));
		}
	}

	public static void initialize() {
		parser.put("ADD", "0000");
		parser.put("SUB", "0001");
		parser.put("MUL", "0010");
		parser.put("MOVI", "0011");
		parser.put("JEQ", "0100");
		parser.put("AND", "0101");
		parser.put("XORI", "0110");
		parser.put("JMP", "0111");
		parser.put("LSL", "1000");
		parser.put("LSR", "1001");
		parser.put("MOVR", "1010");
		parser.put("MOVM", "1011");

		parser.put("R0", "00000");
		parser.put("R1", "00001");
		parser.put("R2", "00010");
		parser.put("R3", "00011");
		parser.put("R4", "00100");
		parser.put("R5", "00101");
		parser.put("R6", "00110");
		parser.put("R7", "00111");
		parser.put("R8", "01000");
		parser.put("R9", "01001");
		parser.put("R10", "01010");
		parser.put("R11", "01011");
		parser.put("R12", "01100");
		parser.put("R13", "01101");
		parser.put("R14", "01110");
		parser.put("R15", "01111");
		parser.put("R16", "10000");
		parser.put("R17", "10001");
		parser.put("R18", "10010");
		parser.put("R19", "10011");
		parser.put("R20", "10100");
		parser.put("R21", "10101");
		parser.put("R22", "10110");
		parser.put("R23", "10111");
		parser.put("R24", "11000");
		parser.put("R25", "11001");
		parser.put("R26", "11010");
		parser.put("R27", "11011");
		parser.put("R28", "11100");
		parser.put("R29", "11101");
		parser.put("R30", "11110");
		parser.put("R31", "11111");

		for (int i = 0; i < 2048; i++)
			MEMORY[i] = "00000000000000000000000000000000";

		for (int i = 0; i < 32; i++) {
			Register temp = new Register("R" + i);
			hmap.put(i, temp);
		}
		hmap.put(32, new Register("PC"));
	}

	public static void main(String[] args) {
		initialize();
		runProgram("test.txt");
	}
}
