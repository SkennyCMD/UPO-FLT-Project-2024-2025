package test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.FileNotFoundException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import exceptions.*;
import parser.*;
import scanner.*;

public class TestParser {
	/** * Test del parser con file di test.
	 * Questo test controlla se sia possibile eseguire un parsing sul file.
	 * @author Matteo Schintu (20051769).
	 */
    @Test
    void testParserCorretto1() throws FileNotFoundException, SyntacticException {
        Parser parser = new Parser(new Scanner("src/test/TestFiles/parser/testParserCorretto1.txt"));

        Assertions.assertDoesNotThrow(() -> parser.parse());
    }

    /**
	 * Test per controllare se il file di input sia valido.
	 * Questo test controlla se sia possibile eseguire un parsing sul file
	 * @author Matteo Schintu (20051769).
	 */
    @Test
    void testParserCorretto2() throws FileNotFoundException, SyntacticException {
        Parser parser = new Parser(new Scanner("src/test/TestFiles/parser/testParserCorretto2.txt"));

        Assertions.assertDoesNotThrow(() -> parser.parse());
    }
    
    /** 
     * Test che verifica se il parser lancia un'eccezione quando incontra un errore sintattico per cui si ottiene ; al
     * posto di =, +=, *=, ecc...
	 * @author Matteo Schintu (20051769).
	 */
    @Test
    void testParserEcc_0() throws FileNotFoundException, SyntacticException {
        Parser parser = new Parser(new Scanner("src/test/TestFiles/parser/testParserEcc_0.txt"));

        Exception e = Assertions.assertThrows(SyntacticException.class, () -> parser.parse());
        assertEquals("ASSIGN or OP_ASSIGN expected, obtained: SEMI", e.getMessage());
    }

    /**
	 * Test che verifica se il parser lancia un'eccezione quando incontra un errore sintattico per cui si ottiene un token TIMES
	 * al posto di un ID, INT o FLOAT.
	 * @author Matteo Schintu (20051769).
	 */
    @Test
    void testParserEcc_1() throws FileNotFoundException, SyntacticException {
        Parser parser = new Parser( new Scanner("src/test/TestFiles/parser/testParserEcc_1.txt"));

        Exception e = Assertions.assertThrows(SyntacticException.class, () -> parser.parse());
        assertEquals("ID, INT or FLOAT expected, obtained: TIMES", e.getMessage());
    }

    /**
     * Test che verifica se il parser lancia un'eccezione quando incontra un errore sintattico per cui si ottiene un token INT
     * al posto di TYINT, TYFLOAT, PRINT, ID o EOF.
     * @author Matteo Schintu (20051769).
     */
    @Test
    void testParserEcc_2() throws FileNotFoundException, SyntacticException {
        Parser parser = new Parser(new Scanner("src/test/TestFiles/parser/testParserEcc_2.txt"));

        Exception e = Assertions.assertThrows(SyntacticException.class, () -> parser.parse());
        assertEquals("TYINT, TYFLOAT, PRINT, ID or EOF expected, obtained: INT", e.getMessage());
    }

    /**
	 * Test che verifica se il parser lancia un'eccezione quando incontra un errore sintattico per cui si ottiene un token PLUS
	 * al posto di ASSIGN o OP_ASSIGN.
	 * @author Matteo Schintu (20051769).
	 */
    @Test
    void testParserEcc_3() throws FileNotFoundException, SyntacticException {
        Parser parser = new Parser(new Scanner("src/test/TestFiles/parser/testParserEcc_3.txt"));

        Exception e = Assertions.assertThrows(SyntacticException.class, () -> parser.parse());
        assertEquals("ASSIGN or OP_ASSIGN expected, obtained: PLUS", e.getMessage());
    }
    
    /**
     * Test che verifica se il parser lancia un'eccezione quando incontra un errore sintattico per cui si ottiene un token INT
	 * al posto di ID.
	 * @author Matteo Schintu (20051769).
	 */
    @Test
    void testParserEcc_4() throws FileNotFoundException, SyntacticException {
        Parser parser = new Parser(new Scanner("src/test/TestFiles/parser/testParserEcc_4.txt"));

        Exception e = Assertions.assertThrows(SyntacticException.class, () -> parser.parse());
        assertEquals("Syntax Error at row 2: Expected: ID, Actual: INT", e.getMessage());
    }

    /**
	 * Test che verifica se il parser lancia un'eccezione quando incontra un errore sintattico per cui si ottiene un token INT
	 * al posto di ID.
	 * @author Matteo Schintu (20051769).
	 */
    @Test
    void testParserEcc_5() throws FileNotFoundException, SyntacticException {
        Parser parser = new Parser(new Scanner("src/test/TestFiles/parser/testParserEcc_5.txt"));

        Exception e = Assertions.assertThrows(SyntacticException.class, () -> parser.parse());
        assertEquals("Syntax Error at row 3: Expected: ID, Actual: INT", e.getMessage());
    }

    /**
     * Test che verifica se il parser lancia un'eccezione quando incontra un errore sintattico per cui si ottiene un token TYFLOAT
     * al posto di ID.
     * @author Matteo Schintu (20051769).
     */
    @Test
    void testParserEcc_6() throws FileNotFoundException, SyntacticException {
        Parser parser = new Parser(new Scanner("src/test/TestFiles/parser/testParserEcc_6.txt"));

        Exception e = Assertions.assertThrows(SyntacticException.class, () -> parser.parse());
        assertEquals("Syntax Error at row 3: Expected: ID, Actual: TYFLOAT", e.getMessage());
    }

    /**
	 * Test che verifica se il parser lancia un'eccezione quando incontra un errore sintattico per cui si ottiene un token ASSIGN
	 * al posto di ID.
	 * @author Matteo Schintu (20051769).
	 */
    @Test
    void testParserEcc_7() throws FileNotFoundException, SyntacticException {
        Parser parser = new Parser(new Scanner("src/test/TestFiles/parser/testParserEcc_7.txt"));

        Exception e = Assertions.assertThrows(SyntacticException.class, () -> parser.parse());
        assertEquals("Syntax Error at row 2: Expected: ID, Actual: ASSIGN", e.getMessage());
    }
    
    /**
	 * Test che verifica se il parser non lancia alcuuna eccezione quando incontra un file di test che contiene
	 * dichiarazioni di variabili.
	 * @author Matteo Schintu (20051769).
	 */
    @Test
    void testSoloDich() throws FileNotFoundException, SyntacticException {
        Parser parser = new Parser(new Scanner("src/test/TestFiles/parser/testSoloDich.txt"));
        
        Assertions.assertDoesNotThrow(() -> parser.parse());
    }
    
    /**
     * Test che verifica se il parser non lancia alcuuna eccezione quando incontra un file di test che contiene
     * dichiarazioni di variabili e print.
     */
    @Test
    void testSoloDichPrint() throws FileNotFoundException, SyntacticException {
        Parser parser = new Parser(new Scanner("src/test/TestFiles/parser/testSoloDichPrint.txt"));
        
        Assertions.assertDoesNotThrow(() -> parser.parse());
    }
}
