package hackassembler;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * Driver program for the Hack Assembler
 *
 * @author Mark Pichler
 */
public class Assembler {

    private Parser parser;
    private SymbolTable symbolTable;
    private CInstructionMapper cInstructionMapper;
    private int varAddress;

    /**
     * Initializes symbolTable, cInstructionMapper, and sets varAddress to 16.
     */
    public Assembler() {
        varAddress = 16;
        symbolTable = new SymbolTable();
        cInstructionMapper = new CInstructionMapper();
    }

    /**
     * Converts a decimal number to its 16-bit binary representation.
     *
     * @param number decimal number to convert
     * @return 16-bit binary representation
     */
    private String decimalToBinary(int number) {
        return String.format("%16s", Integer.toBinaryString(number)).replace(' ', '0');
    }

    /**
     * Initial pass over of the assembly code.  During this pass, all labels
     * added to the symbolTable with their corresponding line numbers.
     *
     * @param assemblyFileName assembly file to parse
     */
    private void firstPass(String assemblyFileName) {
        parser = new Parser(assemblyFileName);
        while (parser.hasMoreCommands()) {
            parser.advance();
            if (parser.getCommandType() == Parser.Command.L_COMMAND) {
                symbolTable.addEntry(parser.getSymbol(), parser.getLineNumber() + 1);
            }
        }
    }

    /**
     * To be called after firstPass().  During this pass, all assembly commands
     * are converted to their 16-bit machine code representation and saved to
     * a .hack file.
     *
     * @param assemblyFileName assembly file to parse
     * @param binaryFileName output file name
     */
    private void secondPass(String assemblyFileName, String binaryFileName) {
        String symbol, comp, dest, jump;
        parser = new Parser(assemblyFileName);
        List<String> output = new ArrayList<>();
        while (parser.hasMoreCommands()) {
            parser.advance();

            switch (parser.getCommandType()) {
                case A_COMMAND:
                    symbol = parser.getSymbol();

                    if (symbol.matches("[0-9]*")) {
                        output.add(decimalToBinary(Integer.parseInt(symbol)));
                    } else if (symbolTable.contains(symbol)) {
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

        PrintWriter writer = null;
        try {
            writer = new PrintWriter(binaryFileName + ".hack");
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (String instruction : output) {
            writer.println(instruction);
        }
        writer.close();
    }

    /**
     * Driver method
     * @param args args[0] file path for .asm file, args[1] name of .hack file
     */
    public static void main(String[] args) {
        Assembler assembler = new Assembler();
        assembler.firstPass(args[0]);
        assembler.secondPass(args[0], args[1]);
    }
}