package hackassembler;

import java.util.HashMap;

/**
 * A symbol table that stores and manages the mappings of predefined symbols,
 * labels, and variable names.
 *
 * @author Mark Pichler
 */
public class SymbolTable {

    public final String VALID_CHARS_REGEX;
    public HashMap<String, Integer> symbolTable;

    public SymbolTable() {

        VALID_CHARS_REGEX = "[_.$:a-zA-Z][_.$:\\w]*";
        symbolTable = new HashMap<>();

        String[] predefinedKeys = {
                "SP", "LCL", "ARG", "THIS", "THAT", "R0", "R1", "R2", "R3",
                "R4", "R5", "R6", "R7", "R8", "R9", "R10", "R11", "R12", "R13",
                "R14", "R15", "SCREEN", "KBD"
        };

        int[] predefinedValues = {
                0, 1, 2, 3, 4, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13,
                14, 15, 16384, 24576
        };

        for (int i = 0; i < predefinedKeys.length; i++) {
            symbolTable.put(predefinedKeys[i], predefinedValues[i]);
        }
    }

    /**
     * Adds a new symbol to the symbol table.  Verifies the symbol is valid and
     * is not already in the table.
     *
     * @param symbol the symbol to add
     * @param address corresponding memory address of symbol
     * @return true if add, false otherwise.
     */
    public boolean addEntry(String symbol, int address) {
        if (contains(symbol) || !isValidName(symbol)) {
            return false;
        } else {
            symbolTable.put(symbol, address);
            return true;
        }
    }

    /**
     * Verifies the presence of the symbol in the table.
     *
     * @param symbol symbol to check if present
     * @return true if present, false otherwise
     */
    public boolean contains(String symbol) {
        return symbolTable.containsKey(symbol);
    }

    /**
     * Returns the corresponding memory address of given symbol.
     *
     * @param symbol symbol whose address is desired
     * @return memory address of symbol
     */
    public int getAddress(String symbol) {
        return symbolTable.getOrDefault(symbol, -1);
    }

    /**
     * Verifies if a symbol is valid according to Hack specifications.
     *
     * @param symbol symbol to verify
     * @return true if valid, false otherwise
     */
    private boolean isValidName(String symbol) {

        if (symbol != null && symbol.matches(VALID_CHARS_REGEX)) {
            return true;
        } else {
            return false;
        }
    }
}
