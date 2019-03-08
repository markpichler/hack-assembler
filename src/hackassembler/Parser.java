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
            // Turn to helper method.
            System.err.println("Error opening file " + fileName);
            System.exit(0);
        }
    }

    public void advance() {
        rawLine = inputFile.nextLine();
        cleanLine = cleanLine(rawLine);
    }

    private String cleanLine(String rawLine) {
        if (rawLine.length() == 0 || rawLine == null) {
            return null;
        }
        return rawLine.split("//")[0].trim().replaceAll(" ", "");
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
