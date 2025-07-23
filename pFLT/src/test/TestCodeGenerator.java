package test;

import static org.junit.jupiter.api.Assertions.*;

import static org.junit.Assert.assertEquals;

import java.io.FileNotFoundException;

import org.junit.jupiter.api.Test;

import ast.*;
import exceptions.*;
import parser.*;
import scanner.*;
import visitor.*;

public class TestCodeGenerator {
	

	@Test
	void testAssign1() throws FileNotFoundException, SyntacticException {
		NodeProgram nP = new Parser(new Scanner("src/test/TestFiles/codeGenerator/1_assign.txt")).parse();
		var tcVisit = new TypeCheckinVisitor();
		nP.accept(tcVisit);
		var cgVisit = new CodeGeneratorVisitor();
		nP.accept(cgVisit);
		
		assertEquals(cgVisit.getMsg(), "");
		assertEquals(cgVisit.getGenerated(), "1 6 / sa la p P");
	}
	
	@Test
	void testDivisioni() throws FileNotFoundException, SyntacticException {
		NodeProgram nP = new Parser(new Scanner("src/test/TestFiles/codeGenerator/2_divsioni.txt")).parse();
		var tcVisit = new TypeCheckinVisitor();
		nP.accept(tcVisit);
		var cgVisit = new CodeGeneratorVisitor();
		nP.accept(cgVisit);
		
		
		assertEquals(cgVisit.getMsg(), "");
		assertEquals(cgVisit.getGenerated(), "0 sa la 1 + sa 6 sb 1.0 6 5 k / 0 k la lb / + sc la p P lb p P lc p P");
	}
	
	@Test
	void testGenerale() throws FileNotFoundException, SyntacticException {
		NodeProgram nP = new Parser(new Scanner("src/test/TestFiles/codeGenerator/3_generale.txt")).parse();
		var tcVisit = new TypeCheckinVisitor();
		nP.accept(tcVisit);
		var cgVisit = new CodeGeneratorVisitor();
		nP.accept(cgVisit);
		
		assertEquals(cgVisit.getMsg(), "");
		assertEquals(cgVisit.getGenerated(), "5 3 + sa la 0.5 5 k / 0 k sb la p P lb 4 5 k / 0 k sb lb p P lb 1 - sc lc lb 5 k / 0 k sc lc p P");
	}
	
	@Test
	void testRegistriFiniti() throws FileNotFoundException, SyntacticException {
		NodeProgram nP = new Parser(new Scanner("src/test/TestFiles/codeGenerator/4_registriFiniti.txt")).parse();
		var tcVisit = new TypeCheckinVisitor();
		nP.accept(tcVisit);
		var cgVisit = new CodeGeneratorVisitor();
		nP.accept(cgVisit);
		
		/* il log se contiene un errore e non viene generato il codice */
		assertEquals(cgVisit.getMsg(), "There are no more free registers");
		assertEquals(cgVisit.getGenerated(), "");
	}
	
}
