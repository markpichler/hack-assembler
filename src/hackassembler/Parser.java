package hackassembler;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Parser {

    private String cleanLine;
    private Command commandType;
    private String compMnenomic;
    private String destMnenomic;
    private Scanner inputFile;
    private String jumpMnenomic;
    private int lineNumber;
    private String rawLine;
    private String symbol;

    public Parser(String fileName) {
        try {
            inputFile = new Scanner(new File(fileName));
        } catch (FileNotFoundException e) {
            // TODO Turn to helper method.
            System.err.println("Error opening file " + fileName);
            System.exit(0);
        }
    }

    public boolean hasMoreCommands() {
        if (inputFile.hasNextLine()) {
            return true;
        } else {
            inputFile.close();
            return false;
        }
    }

    public void advance() {
        if (hasMoreCommands()) {
            rawLine = inputFile.nextLine();
            cleanLine = cleanLine(rawLine);
        }
    }

    private String cleanLine(String rawLine) {
        if (rawLine.length() == 0 || rawLine == null) {
            return null;
        }
        return rawLine.split("//")[0].trim().replaceAll(" ", "");
    }

    private Command parseCommandType(String cleanLine) {
        char firstChar = cleanLine.charAt(0);
        if (firstChar == '@') {
            return Command.A_COMMAND;
        } else if ("01-!AMD".indexOf(firstChar) != -1) {
            return Command.C_COMMAND;
        } else if (firstChar == '(') {
            return Command.L_COMMAND;
        } else {
            return Command.NO_COMMAND;
        }
    }

    private void parseSymbol() {
        if (cleanLine != null) {
            char firstChar = cleanLine.charAt(0);
            if (firstChar == '@') {
                symbol =  cleanLine.substring(1);
                commandType = Command.A_COMMAND;
                cleanLine = null;
            } else if (firstChar == '(') {
                symbol = cleanLine.substring(1, cleanLine.length() - 1);
                commandType =  Command.L_COMMAND;
                cleanLine = null;
            }
        }
    }

    public static void main(String[] args) {
        Parser s = new Parser("C:\\Users\\Mark\\IdeaProjects\\Hack_Assembler\\src\\hackassembler\\test.txt");
        s.advance();
        System.out.println(s.cleanLine);
        s.advance();
        System.out.println(s.cleanLine);
        s.advance();
        System.out.println(s.cleanLine);
    }
}
