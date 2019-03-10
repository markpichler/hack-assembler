package hackassembler;

import java.util.ArrayList;
import java.util.List;

public class Assembler {

    private Parser parser;
    private SymbolTable symbolTable;
    private CInstructionMapper cInstructionMapper;
    private int varAddress;

    public Assembler() {
        varAddress = 16;
        symbolTable = new SymbolTable();
        cInstructionMapper = new CInstructionMapper();
    }

    private String decimalToBinary(int number) {
        return String.format("%16s", Integer.toBinaryString(number)).replace(' ', '0');
    }

    private void firstPass(String assemblyFileName) {
        parser = new Parser(assemblyFileName);
        while (parser.hasMoreCommands()) {
            parser.advance();
            if (parser.getCommandType() == Command.L_COMMAND) {
                symbolTable.addEntry(parser.getSymbol(), parser.getLineNumber() + 1);
            }
        }
    }

    private void secondPass(String assemblyFileName, String binaryFileName) {
        String symbol, comp, dest, jump;
        parser = new Parser(assemblyFileName);
        List<String> output = new ArrayList<>();
        while (parser.hasMoreCommands()) {
            parser.advance();

            switch (parser.getCommandType()) {
                case A_COMMAND:
                    symbol = parser.getSymbol();

                    if (symbolTable.contains(symbol)) {
                        output.add(decimalToBinary(symbolTable.getAddress(symbol)));
                    } else {
                        symbolTable.addEntry(symbol, varAddress);
                        output.add(decimalToBinary(varAddress++));
                    }
                    break;
                case C_COMMAND:
                    comp = cInstructionMapper.comp(parser.getCompMnenomic());
                    dest = cInstructionMapper.dest(parser.getDestMnemonic());
                    jump = cInstructionMapper.jump(parser.getjumpMnemonic());
                    output.add("111" + comp + dest + jump);
            }
        }
        for (String s: output) {
            System.out.println(s);

        }
    }

    public static void main(String[] args) {
        Assembler test = new Assembler();
        test.firstPass("C:\\Users\\Mark\\IdeaProjects\\Hack_Assembler\\src\\hackassembler\\Add1ToN.asm");
        test.secondPass("C:\\Users\\Mark\\IdeaProjects\\Hack_Assembler\\src\\hackassembler\\Add1ToN.asm", "");

    }
}
