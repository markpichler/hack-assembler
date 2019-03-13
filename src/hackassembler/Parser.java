package hackassembler;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * This Parser manages the reading, cleaning, and parsing of individual
 * instruction components in the the assembly code.
 *
 * @author Mark Pichler
 */
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

    public enum Command {
        A_COMMAND,
        C_COMMAND,
        L_COMMAND,
        NO_COMMAND
    }

    public Parser(String fileName) {
        lineNumber = -1;
        try {
            inputFile = new Scanner(new File(fileName));
        } catch (FileNotFoundException e) {
            // TODO Turn to helper method.
            System.err.println("Error opening file " + fileName);
            System.exit(0);
        }
    }

    /**
     * Verifies if there are more commands to be parsed.
     *
     * @return true if more commands, false otherwise
     */
    public boolean hasMoreCommands() {
        if (inputFile.hasNextLine()) {
            return true;
        } else {
            inputFile.close();
            return false;
        }
    }

    /**
     * Advances the parser forward one line and parses the different
     * c-instruction mnemonics.  hasMoreCommands() must return true before
     * called.
     */
    public void advance() {
        if (hasMoreCommands()) {
            rawLine = inputFile.nextLine();
            cleanLine = cleanLine(rawLine);
            parseCommandType();
            parse();
            switch (commandType) {
                case A_COMMAND:
                case C_COMMAND:
                    lineNumber++;
            }
        }
    }

    /**
     * Calls all of the parse helper methods and updates the appropriate
     * instance variables.  advance() must be called first.
     */
    private void parse() {
        if (commandType != Command.NO_COMMAND) {
            parseSymbol();
            parseDest();
            parseComp();
            parseJump();
        }
    }

    /**
     * Removes all white space and comments from a raw assembly line.
     * advance() must be called first.
     *
     * @param rawLine a single raw assembly code line
     * @return a clean assembly code line stripped of comments and whitespace
     */
    private String cleanLine(String rawLine) {
        if (rawLine.length() == 0 || rawLine == null) {
            return null;
        }
        return rawLine.split("//")[0].trim().replaceAll(" ", "");
    }

    /**
     * Parses the command type mnemonic from a clean assembly code line.
     * advance() must be called first.
     */
    private void parseCommandType() {
        if (cleanLine == null || cleanLine.length() == 0) {
            commandType = Command.NO_COMMAND;
        } else {
            char firstChar = cleanLine.charAt(0);
            if (firstChar == '@') {
                commandType = Command.A_COMMAND;
            } else if ("01-!AMD".indexOf(firstChar) != -1) {
                commandType = Command.C_COMMAND;
            } else if (firstChar == '(') {
                commandType = Command.L_COMMAND;
            }
        }
    }

    /**
     * Parses the symbol from a clean assembly code line.  advance() must be
     * called first.
     */
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

    /**
     * Parses the dest mnemonic from a clean assembly code line.  advance()
     * must be called first.
     */
    private void parseDest() {
        if (commandType == Command.C_COMMAND) {
            int index = cleanLine.indexOf("=");
            destMnemonic = index != -1 ? cleanLine.split("=")[0] : null;

        }
    }

    /**
     * Parses the comp code mnemonic from a clean assembly code line.
     * advance() must be called first.
     */
    private void parseComp() {
        if (commandType == Command.C_COMMAND) {
            if (cleanLine.indexOf("=") != -1) {
                compMnemonic = cleanLine.split("(.+=)|;")[1];
            } else {
                compMnemonic = cleanLine.split("(.+=)|;")[0];
            }
        }
    }

    /**
     * Parses the jump code mnemonic from a clean assembly code line.
     * advance() must be called first.
     */
    private void parseJump() {
        if (commandType == Command.C_COMMAND) {
            int index = cleanLine.indexOf(";");
            jumpMnemonic = index != -1 ? cleanLine.substring(++index) : null;
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
}
