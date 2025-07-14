package test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import org.junit.jupiter.api.Test;
import ast.*;
import exceptions.*;
import token.*;
import visitor.*;
import scanner.*;
import parser.*;


public class TestTypeCheckinVisitor {
	
	/**
	 * Test che verifica che il visitor lanci un errore quando incontra un programma
	 * in cui si cerca di dichiarare una variabile con lo stesso nome di una già
	 * dichiarata.
	 * @author Matteo Schintu (20051769)
	 */
	@Test
	void testDicRipetute() throws FileNotFoundException, SyntacticException {
		NodeProgram program = new Parser(new Scanner("src/test/TestFiles/typeCheckingVisitor/1_dicRipetute.txt")).parse();
		TypeCheckinVisitor visitor = new TypeCheckinVisitor();
		program.accept(visitor);
		
		assertEquals(visitor.getMsg(), "(Semantic Error): var a is already declared");
	}
	
	/**
	 * Test che verifica che il visitor lanci un errore quando incontra un programma
	 * in cui si cerca di usare una variabile non dichiarata.
	 * @author Matteo Schintu (20051769)
	 */
	@Test
	void testIdNonDec() throws FileNotFoundException, SyntacticException {
		NodeProgram program = new Parser(new Scanner("src/test/TestFiles/typeCheckingVisitor/2_idNonDec.txt")).parse();
		TypeCheckinVisitor visitor = new TypeCheckinVisitor();
		program.accept(visitor);
		
		assertEquals(visitor.getMsg(), "(Semantic Error): b is not declared!");
	}
	
	/**
	 * Test che verifica che il visitor lanci un errore quando incontra un programma
	 * in cui si cerca di usare una variabile non dichiarata.
	 * @author Matteo Schintu (20051769)
	 */
	@Test
	void testIdNonDec2() throws FileNotFoundException, SyntacticException {
		NodeProgram program = new Parser(new Scanner("src/test/TestFiles/typeCheckingVisitor/3_idNonDec.txt")).parse();
		TypeCheckinVisitor visitor = new TypeCheckinVisitor();
		program.accept(visitor);
		
		assertEquals(visitor.getMsg(), "(Semantic Error): c is not declared!");
	}
	
	/**
	 * Test che verifica che il visitor lanci un errore quando incontra un programma
	 * in cui si cerca di assegnare un tipo non compatibile ad una variabile.
	 * @author Matteo Schintu (20051769)
	 */
	@Test
	void testTipoNonCompatibile() throws FileNotFoundException, SyntacticException {
		NodeProgram program = new Parser(new Scanner("src/test/TestFiles/typeCheckingVisitor/4_tipoNonCompatibile.txt")).parse();
		TypeCheckinVisitor visitor = new TypeCheckinVisitor();
		program.accept(visitor);
		
		assertEquals(visitor.getMsg(), "(Semantic Error): can't assign a FLOAT expression to b(INT)");
	}
	
	/**
	 * Test che verifica che il visitor non lanci errori quando incontra un programma
	 * corretto, ma con variabili dichiarate in ordine diverso rispetto alla loro
	 * dichiarazione.
	 * @author Matteo Schintu (20051769)
	 */
	@Test
	void testCorretto1() throws FileNotFoundException, SyntacticException {
		NodeProgram program = new Parser(new Scanner("src/test/TestFiles/typeCheckingVisitor/5_corretto.txt")).parse();
		TypeCheckinVisitor visitor = new TypeCheckinVisitor();
		program.accept(visitor);
		
		assertEquals(visitor.getMsg(), "");
	}
	
	/**
	 * Test che verifica che il visitor non lanci errori quando incontra un programma
	 * corretto, ma con variabili dichiarate in ordine diverso rispetto alla loro
	 * dichiarazione.
	 * @author Matteo Schintu (20051769)
	 */
	@Test
	void testCorretto2() throws FileNotFoundException, SyntacticException {
		NodeProgram program = new Parser(new Scanner("src/test/TestFiles/typeCheckingVisitor/6_corretto.txt")).parse();
		TypeCheckinVisitor visitor = new TypeCheckinVisitor();
		program.accept(visitor);
		
		assertEquals(visitor.getMsg(), "");
	}
	
	/**
	 * Test che verifica che il visitor non lanci errori quando incontra un programma
	 * corretto, ma con variabili dichiarate in ordine diverso rispetto alla loro
	 * dichiarazione.
	 * @author Matteo Schintu (20051769)
	 */
	@Test
	void testCorretto3() throws FileNotFoundException, SyntacticException {
		NodeProgram program = new Parser(new Scanner("src/test/TestFiles/typeCheckingVisitor/7_corretto.txt")).parse();
		TypeCheckinVisitor visitor = new TypeCheckinVisitor();
		program.accept(visitor);
		
		assertEquals(visitor.getMsg(), "");
	}
}
