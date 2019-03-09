package hackassembler;

public class Assembler {

    private String decimalToBinary(int number) {
        return Integer.toBinaryString(number);
    }

    public static void main(String[] args) {
        Parser test = new Parser("C:\\Users\\Mark\\IdeaProjects\\Hack_Assembler\\src\\hackassembler\\test.txt");
        while (test.hasMoreCommands()) {
            test.advance();
            System.out.println(test.getCleanLine() + test.getCommandType());

        }
        System.out.println(test.getLineNumber());
    }

}
