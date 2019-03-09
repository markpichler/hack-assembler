package hackassembler;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Parser {

    private String cleanLine;
    private Command commandType;
    private String compMnemonic;
    private String destMnemonic;
    private Scanner inputFile;
    private String jumpMnemonic;
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

    public Command getCommandType() {
        return commandType;
    }

    public String getSymbol() {
        return symbol;
    }

    public String getDestMnemonic() {
        return destMnemonic;
    }

    public String getCompMnenomic() {
        return compMnemonic;
    }

    public String getjumpMnemonic() {
        return jumpMnemonic;
    }

    public String getRawLine() {
        return rawLine;
    }

    public String getCleanLine() {
        return cleanLine;
    }

    public int getLineNumber() {
        return lineNumber;
    }

    private void parse() {
        if (cleanLine != null) {
            parseCommandType();
            parseSymbol();
            parseDest();
            parseComp();
            parseJump();
            cleanLine = null;
        }
    }

    private String cleanLine(String rawLine) {
        if (rawLine.length() == 0 || rawLine == null) {
            return null;
        }
        return rawLine.split("//")[0].trim().replaceAll(" ", "");
    }

    private void parseCommandType() {
        char firstChar = cleanLine.charAt(0);
        if (firstChar == '@') {
            commandType = Command.A_COMMAND;
        } else if ("01-!AMD".indexOf(firstChar) != -1) {
            commandType = Command.C_COMMAND;
        } else if (firstChar == '(') {
            commandType = Command.L_COMMAND;
        } else {
            commandType = Command.NO_COMMAND;
        }
    }

    private void parseSymbol() {

        switch (commandType) {
            case A_COMMAND:
                symbol =  cleanLine.substring(1);
                break;
            case L_COMMAND:
                symbol = cleanLine.substring(1, cleanLine.length() - 1);
                break;
        }

    }

    private void parseDest() {
        if (commandType == Command.C_COMMAND) {
            int index = cleanLine.indexOf("=");
            destMnemonic = index != -1 ? cleanLine.split("=")[0] : null;

        }
    }

    private void parseComp() {
        if (commandType == Command.C_COMMAND) {
            if (cleanLine.indexOf("=") != -1) {
                compMnemonic = cleanLine.split("(.+=)|;")[1];
            } else {
                compMnemonic = cleanLine.split("(.+=)|;")[0];
            }
        }
    }

    private void parseJump() {
        if (commandType == Command.C_COMMAND) {
            int index = cleanLine.indexOf(";");
            jumpMnemonic = index != -1 ? cleanLine.substring(index) : null;
        }
    }

    public static void main(String[] args) {

    }
}
