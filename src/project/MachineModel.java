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
	}

	public class CPU{
		//the getters and setters for these should be in machineModel
		private int accumulator;
		private int instructionPointer;
		private int memoryBase;

		//something in the instructions about this incrementing by one if you don't have int val
		public void incrementIP(int val) {
			instructionPointer += val;
		}


		//The instructions will be stored in code-memory which is also an array stored in Memory. 
		//The plan is to be able to run more than one program in "parallel"
		//which is why the CPU has a memoryBase field, 
		//which is the starting point for the data memory of the program that is running.
	}

}
