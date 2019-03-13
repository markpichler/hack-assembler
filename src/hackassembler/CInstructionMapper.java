package hackassembler;

import java.util.HashMap;

/**
 * A mapper that converts c-instruction mnemonics to their corresponding binary
 * representations.
 *
 * @author Mark Pichler
 */
public class CInstructionMapper {

    private HashMap<String, String> compCodes;
    private HashMap<String, String> destCodes;
    private HashMap<String, String> jumpCodes;


    public CInstructionMapper() {

        compCodes = new HashMap<>();
        jumpCodes = new HashMap<>();
        destCodes = new HashMap<>();

        String[] compCodesKeys = {
                "0", "1", "-1", "D", "A", "M", "!D", "!A", "!M", "-D", "-A",
                "-M", "D+1", "A+1", "M+1", "D-1", "A-1", "M-1", "D+A", "D+M",
                "D-A", "D-M", "A-D", "M-D", "D&A", "D&M", "D|A", "D|M"
        };

        String[] compCodesValues = {
                "0101010", "0111111", "0111010", "0001100", "0110000",
                "1110000", "0001101", "0110001", "1110001", "0001111",
                "0110011", "1110011", "0011111", "0110111", "1110111",
                "0001110", "0110010", "1110010", "0000010", "1000010",
                "0010011", "1010011", "0000111", "1000111", "0000000",
                "1000000", "0010101", "1010101"
        };

        String[] destCodesKeys = {
                null, "M", "D", "MD", "A", "AM", "AD", "AMD"
        };

        String[] destCodesValues = {
                "000", "001", "010", "011", "100", "101", "110", "111"
        };

        String[] jumpCodesKeys = {
                null, "JGT", "JEQ", "JGE", "JLT", "JNE", "JLE", "JMP"
        };

        String[] jumpCodesValues = {
                "000", "001", "0101", "011", "100", "101", "110", "111"
        };


        // Populate compCodes
        for (int i = 0; i < compCodesKeys.length; i++) {
            compCodes.put(compCodesKeys[i], compCodesValues[i]);
        }

        // Populate destCodes and jumpCodes
        for (int i = 0; i < destCodesKeys.length; i++) {
            destCodes.put(destCodesKeys[i], destCodesValues[i]);
            jumpCodes.put(jumpCodesKeys[i], jumpCodesValues[i]);
        }
    }

    /**
     * Returns the binary representation of a c-instruction comp mnemonic.
     *
     * @param mnemonic comp mnemonic whose binary representation is desired
     * @return binary representation of mnemonic
     */
    public String comp(String mnemonic) {
        return compCodes.get(mnemonic);
    }

    /**
     * Returns the binary representation of a c-instruction dest mnemonic.
     *
     * @param mnemonic dest mnemonic whose binary representation is desired
     * @return binary representation of mnemonic
     */
    public String dest(String mnemonic) {
        return destCodes.get(mnemonic);
    }

    /**
     * Returns the binary representation of a c-instruction jump mnemonic.
     *
     * @param mnemonic jump mnemonic whose binary representation is desired
     * @return binary representation of mnemonic
     */
    public String jump(String mnemonic) {
        return jumpCodes.get(mnemonic);
    }
}
