package hackassembler;

public class Assembler {

    private Parser parser;
    private SymbolTable symbolTable;

    public Assembler() {
        symbolTable = new SymbolTable();
    }

    private String decimalToBinary(int number) {
        return Integer.toBinaryString(number);
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

    public static void main(String[] args) {
        Assembler test = new Assembler();
        test.firstPass("C:\\Users\\Mark\\IdeaProjects\\Hack_Assembler\\src\\hackassembler\\Add1ToN.asm");

    }

}
