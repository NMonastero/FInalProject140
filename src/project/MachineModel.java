package project;

import java.util.TreeMap;

public class MachineModel {
	public TreeMap<Integer, Instruction> INSTRUCTIONS = new TreeMap<>();
	private CPU cpu;
	private Memory memory = new Memory();
	private HaltCallback callback;
	private boolean withGUI;

	public MachineModel() {
		this(false, null);
	}

	public MachineModel(boolean b, HaltCallback h) {
		withGUI = b;
		callback = h;

		//INSTRUCTION_MAP entry "No operation"
		INSTRUCTIONS.put(0x0, arg -> {
			cpu.incrementIP(1);
		});

		//INSTRUCTION_MAP entry for "LODI"
		INSTRUCTIONS.put(0x1, arg -> {
			cpu.accumulator = arg;
			cpu.incrementIP(1);
		});

		//INSTRUCTION_MAP entry for "LOD"
		INSTRUCTIONS.put(0x2, arg -> {
			int arg1 = memory.getData(cpu.memoryBase+arg);
			cpu.accumulator = arg1;
			cpu.incrementIP(1);
		});

		//INSTRUCTION_MAP entry for "LODN"
		INSTRUCTIONS.put(0x3, arg -> {
			int arg1 = memory.getData(cpu.memoryBase+arg);
			int arg2 = memory.getData(cpu.memoryBase+arg1);
			cpu.accumulator = arg2;
			cpu.incrementIP(1);
		});

		//INSTRUCTION_MAP entry for "STO"
		INSTRUCTIONS.put(0x4, arg -> {
			memory.setData(cpu.memoryBase+arg, cpu.accumulator);
			cpu.incrementIP(1);
		});

		//INSTRUCTION_MAP entry for "STON"
		INSTRUCTIONS.put(0x5, arg -> {
			int arg1 = memory.getData(cpu.memoryBase+arg);
			memory.setData(cpu.memoryBase+arg1, cpu.accumulator);
			cpu.incrementIP(1);
		});

		//INSTRUCTOIN_MAP entry for "JUMPR"
		INSTRUCTIONS.put(0x6,  arg -> {
			cpu.incrementIP(arg);
		});

		//INSTRUCTOIN_MAP entry for "JUMP"
		INSTRUCTIONS.put(0x7,  arg -> {
			cpu.incrementIP(cpu.memoryBase+arg);
		});

		//INSTRUCTOIN_MAP entry for "JUMPI"
		INSTRUCTIONS.put(0x8,  arg -> {
			cpu.incrementIP(cpu.instructionPointer*-1);
			cpu.incrementIP(arg);
		});

		//INSTRUCTOIN_MAP entry for "JMPZR"
		INSTRUCTIONS.put(0x9,  arg -> {
			if(cpu.accumulator == 0) {
				cpu.incrementIP(arg);
			}
			else {
				cpu.incrementIP(1);
			}
		});

		//INSTRUCTOIN_MAP entry for "JMPZ"
		INSTRUCTIONS.put(0xA,  arg -> {
			if(cpu.accumulator == 0) {
				cpu.incrementIP(cpu.memoryBase+arg);
			}
			else {
				cpu.incrementIP(1);
			}
		});

		//INSTRUCTOIN_MAP entry for "JUMPZI"
		INSTRUCTIONS.put(0xB,  arg -> {
			if(cpu.accumulator == 0) {
				cpu.incrementIP(cpu.instructionPointer*-1);
				cpu.incrementIP(arg);
			}
			else {
				cpu.incrementIP(1);
			}
		});

		//INSTRUCTION_MAP entry for "ADDI"
		INSTRUCTIONS.put(0xC, arg -> {
			cpu.accumulator += arg;
			cpu.incrementIP(1);
		});

		//INSTRUCTION_MAP entry for "ADD"
		INSTRUCTIONS.put(0xD, arg -> {
			int arg1 = memory.getData(cpu.memoryBase+arg);
			cpu.accumulator += arg1;
			cpu.incrementIP(1);
		});

		//INSTRUCTION_MAP entry for "ADDN"
		INSTRUCTIONS.put(0xE, arg -> {
			int arg1 = memory.getData(cpu.memoryBase+arg);
			int arg2 = memory.getData(cpu.memoryBase+arg1);
			cpu.accumulator += arg2;
			cpu.incrementIP(1);
		});

		//INSTRUCTION_MAP entry for "SUBI"
		INSTRUCTIONS.put(0xF, arg -> {
			cpu.accumulator -= arg;
			cpu.incrementIP(1);
		});

		//INSTRUCTION_MAP entry for "SUB"
		INSTRUCTIONS.put(0x10, arg -> {
			int arg1 = memory.getData(cpu.memoryBase+arg);
			cpu.accumulator -= arg1;
			cpu.incrementIP(1);
		});

		//INSTRUCTION_MAP entry for "SUBN"
		INSTRUCTIONS.put(0x11, arg -> {
			int arg1 = memory.getData(cpu.memoryBase+arg);
			int arg2 = memory.getData(cpu.memoryBase+arg1);
			cpu.accumulator -= arg2;
			cpu.incrementIP(1);
		});

		//INSTRUCTION_MAP entry for "MULI"
		INSTRUCTIONS.put(0x12, arg -> {
			cpu.accumulator *= arg;
			cpu.incrementIP(1);
		});

		//INSTRUCTION_MAP entry for "MUL"
		INSTRUCTIONS.put(0x13, arg -> {
			int arg1 = memory.getData(cpu.memoryBase+arg);
			cpu.accumulator *= arg1;
			cpu.incrementIP(1);
		});

		//INSTRUCTION_MAP entry for "MULN"
		INSTRUCTIONS.put(0x14, arg -> {
			int arg1 = memory.getData(cpu.memoryBase+arg);
			int arg2 = memory.getData(cpu.memoryBase+arg1);
			cpu.accumulator *= arg2;
			cpu.incrementIP(1);
		});

		//INSTRUCTION_MAP entry for "DIVI"
		INSTRUCTIONS.put(0x15, arg -> {
			if(arg == 0) {
				throw new DivideByZeroException("Can't divide by zero");
			}
			cpu.accumulator /= arg;
			cpu.incrementIP(1);
		});

		//INSTRUCTION_MAP entry for "DIV"
		INSTRUCTIONS.put(0x16, arg -> {
			if(arg == 0) {
				throw new DivideByZeroException("Can't divide by zero");
			}
			int arg1 = memory.getData(cpu.memoryBase+arg);
			cpu.accumulator /= arg1;
			cpu.incrementIP(1);
		});

		//INSTRUCTION_MAP entry for "DIVN"
		INSTRUCTIONS.put(0x17, arg -> {
			if(arg == 0) {
				throw new DivideByZeroException("Can't divide by zero");
			}
			int arg1 = memory.getData(cpu.memoryBase+arg);
			int arg2 = memory.getData(cpu.memoryBase+arg1);
			cpu.accumulator /= arg2;
			cpu.incrementIP(1);
		});

		//INSTRUCTION_MAP entry for "ANDI"
		INSTRUCTIONS.put(0x18, arg -> {
			if(cpu.accumulator != 0 && arg != 0) {
				cpu.accumulator = 1;
			}
			else {
				cpu.accumulator = 0;
			}
			cpu.incrementIP(1);
		});

		//INSTRUCTION_MAP entry for "AND"
		INSTRUCTIONS.put(0x19, arg -> {
			if(cpu.accumulator != 0 && cpu.memoryBase+arg != 0) {
				cpu.accumulator = 1;
			}
			else {
				cpu.accumulator = 0;
			}
			cpu.incrementIP(1);
		});

		//INSTRUCTION_MAP entry for "NOT"
		INSTRUCTIONS.put(0x1A, arg -> {
			if(cpu.accumulator != 0) {
				cpu.accumulator = 0;
			}
			else {
				cpu.accumulator = 1;
			}
			cpu.incrementIP(1);
		});

		//INSTRUCTION_MAP entry for "CMPL"
		INSTRUCTIONS.put(0x1B, arg -> {
			if(cpu.memoryBase < 0) {
				cpu.accumulator = 1;
			}
			else {
				cpu.accumulator = 0;
			}
			cpu.incrementIP(1);
		});

		//INSTRUCTION_MAP entry for "CMPZ"
		INSTRUCTIONS.put(0x1C, arg -> {
			if(cpu.memoryBase == 0) {
				cpu.accumulator = 1;
			}
			else {
				cpu.accumulator = 0;
			}
			cpu.incrementIP(1);
		});

		//INSTRUCTION_MAP entry for "HALT"
		INSTRUCTIONS.put(0x1F, arg -> {
			callback.halt();
		});
	}
	
	int[] getData() {
		return memory.getData();
	}
	
	public int getData(int index) {
		return memory.getData(index);
	}
	
	public void setData(int index, int value) {
		memory.setData(index, value);
	}
	
	public int getAccumulator() {
		return cpu.accumulator;
	}

	public void setAccumulator(int accumulator) {
		cpu.accumulator = accumulator;
	}

	public int getInstructionPointer() {
		return cpu.instructionPointer;
	}

	public void setInstructionPointer(int instructionPointer) {
		cpu.instructionPointer = instructionPointer;
	}

	public int getMemoryBase() {
		return cpu.memoryBase;
	}

	public void setMemoryBase(int memoryBase) {
		cpu.memoryBase = memoryBase;
	}
	
	public class CPU{
		//the getters and setters for these should be in machineModel
		private int accumulator;
		private int instructionPointer;
		private int memoryBase;

		public void incrementIP(int val) {
			instructionPointer += val;
		}
		

		//The instructions will be stored in code-memory which is also an array stored in Memory. 
		//The plan is to be able to run more than one program in "parallel"
		//which is why the CPU has a memoryBase field, 
		//which is the starting point for the data memory of the program that is running.
	}

}
