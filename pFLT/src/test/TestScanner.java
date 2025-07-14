package test;

import static org.junit.jupiter.api.Assertions.*;
import java.io.IOException;
import org.junit.jupiter.api.Test;
import exceptions.LexicalException;
import scanner.Scanner;
import token.Token;
import token.TokenType;

class TestScanner {

	@Test
	void testCaratteriNonCaratteri() throws IOException, LexicalException {
		Scanner scanner = new Scanner("src/test/TestFiles/scanner/caratteriNonCaratteri.txt");
		LexicalException e;

		e = assertThrows(LexicalException.class, scanner::nextToken);
		assertEquals("Lexical Error at 1 row: Invalid sequence '^' ", e.getMessage());
		e = assertThrows(LexicalException.class, scanner::nextToken);
		assertEquals("Lexical Error at 1 row: Invalid sequence '&' ", e.getMessage());
		assertEquals(TokenType.SEMI, scanner.nextToken().getType());
		e = assertThrows(LexicalException.class, scanner::nextToken);
		assertEquals("Lexical Error at 2 row: Invalid sequence '|' ", e.getMessage());
		assertEquals(TokenType.PLUS, scanner.nextToken().getType());
		assertEquals(TokenType.EOF, scanner.nextToken().getType()); 

	}

	@Test
	void testSkipChars() throws IOException {
		Scanner scanner = new Scanner("src/test/TestFiles/scanner/caratteriSkip");
		var token = assertDoesNotThrow(scanner::nextToken);
		assertEquals(TokenType.EOF, token.getType());
		assertEquals(5, token.getRow());
	}

	@Test
	void testErroriNumbers() throws LexicalException, IOException {
		LexicalException e;
		Scanner scanner = new Scanner("src/test/TestFiles/scanner/erroriNumbers.txt");

		assertEquals("0", scanner.nextToken().getVal());
		assertEquals("33", scanner.nextToken().getVal());
		e = assertThrows(LexicalException.class, scanner::nextToken);
		assertEquals("Lexical Error at 3 row: Invalid sequence '123.121212' ", e.getMessage());
		var t = assertDoesNotThrow(scanner::nextToken);
		assertEquals(t, new Token(TokenType.FLOAT, 5, "123.123"));
		assertThrows(LexicalException.class, scanner::nextToken);
		assertEquals(TokenType.EOF, scanner.nextToken().getType());

	}

	@Test
	void testEOF() throws LexicalException, IOException {
		Scanner scanner = new Scanner("src/test/TestFiles/scanner/testEOF.txt");
		assertEquals(TokenType.EOF, scanner.nextToken().getType());

	}

	@Test
	void testFLOAT() throws LexicalException, IOException {
		Scanner scanner = new Scanner("src/test/TestFiles/scanner/testFloat.txt");

		assertEquals("098.8095", scanner.nextToken().getVal());
		assertEquals("0.", scanner.nextToken().getVal());
		assertEquals("98.", scanner.nextToken().getVal());
		assertEquals("89.99999", scanner.nextToken().getVal());
		assertEquals(TokenType.EOF, scanner.nextToken().getType());
	}

	@Test
	void testFLOATToken() throws LexicalException, IOException {
		Scanner scanner = new Scanner("src/test/TestFiles/scanner/testFloat.txt");
		Token t1 = new Token(TokenType.FLOAT, 1, "098.8095");
		Token t2 = new Token(TokenType.FLOAT, 2, "0.");
		Token t3 = new Token(TokenType.FLOAT, 3, "98.");
		Token t4 = new Token(TokenType.FLOAT, 5, "89.99999");
		Token t5 = new Token(TokenType.EOF, 5);
		
		Token t1_aus = scanner.nextToken();
		Token t2_aus = scanner.nextToken();
		Token t3_aus = scanner.nextToken();
		Token t4_aus = scanner.nextToken();
		Token t5_aus = scanner.nextToken();

		assertTrue(t1.equals(t1_aus));
		assertTrue(t2.equals(t2_aus));
		assertTrue(t3.equals(t3_aus));
		assertTrue(t4.equals(t4_aus));
		assertTrue(t5.equals(t5_aus));
	}

	@Test
	void testGenerale() throws IOException, LexicalException {
		Scanner scanner = new Scanner("src/test/TestFiles/scanner/testGenerale.txt");

		assertEquals("<TYINT,r:1>", scanner.nextToken().toString());
		assertEquals("<ID,r:1,temp>", scanner.nextToken().toString());
		assertEquals("<SEMI,r:1>", scanner.nextToken().toString());

		assertEquals("<ID,r:2,temp>", scanner.nextToken().toString());
		assertEquals("<OP_ASSIGN,r:2,+=>", scanner.nextToken().toString());
		assertEquals("<FLOAT,r:2,5.>", scanner.nextToken().toString());
		assertEquals("<SEMI,r:2>", scanner.nextToken().toString());

		assertEquals("<TYFLOAT,r:4>", scanner.nextToken().toString());
		assertEquals("<ID,r:4,b>", scanner.nextToken().toString());
		assertEquals("<SEMI,r:4>", scanner.nextToken().toString());

		assertEquals("<ID,r:5,b>", scanner.nextToken().toString());
		assertEquals("<ASSIGN,r:5>", scanner.nextToken().toString());
		assertEquals("<ID,r:5,temp>", scanner.nextToken().toString());
		assertEquals("<PLUS,r:5>", scanner.nextToken().toString());
		assertEquals("<FLOAT,r:5,3.2>", scanner.nextToken().toString());
		assertEquals("<SEMI,r:5>", scanner.nextToken().toString());

		assertEquals("<PRINT,r:6>", scanner.nextToken().toString());
		assertEquals("<ID,r:6,b>", scanner.nextToken().toString());
		assertEquals("<SEMI,r:6>", scanner.nextToken().toString());

		assertEquals("<EOF,r:7>", scanner.nextToken().toString());
	}

	@Test
	void testId() throws IOException {
		Scanner scanner = new Scanner("src/test/TestFiles/scanner/testId.txt");

		var token = assertDoesNotThrow(scanner::nextToken);
		assertEquals("jskjdsfhkjdshkf", token.getVal());
		assertEquals(1, token.getRow());
		assertEquals(TokenType.ID, token.getType());

		token = assertDoesNotThrow(scanner::nextToken);
		assertEquals("printl", token.getVal());
		assertEquals(2, token.getRow());
		assertEquals(TokenType.ID, token.getType());

		token = assertDoesNotThrow(scanner::nextToken);
		assertEquals("ffloat", token.getVal());
		assertEquals(4, token.getRow());
		assertEquals(TokenType.ID, token.getType());

		token = assertDoesNotThrow(scanner::nextToken);
		assertEquals("hhhjj", token.getVal());
		assertEquals(6, token.getRow());
		assertEquals(TokenType.ID, token.getType());

		token = assertDoesNotThrow(scanner::nextToken);
		assertEquals(7, token.getRow());
		assertEquals(TokenType.EOF, token.getType());
	}

	@Test
	void testIdKeywords() throws IOException {
		Scanner scanner = new Scanner("src/test/TestFiles/scanner/testIdKeyWords.txt");

		var token = assertDoesNotThrow(scanner::nextToken);
		assertEquals(1, token.getRow());
		assertEquals(TokenType.TYINT, token.getType());

		token = assertDoesNotThrow(scanner::nextToken);
		assertEquals("inta", token.getVal());
		assertEquals(1, token.getRow());
		assertEquals(TokenType.ID, token.getType());

		token = assertDoesNotThrow(scanner::nextToken);
		assertEquals(2, token.getRow());
		assertEquals(TokenType.TYFLOAT, token.getType());

		token = assertDoesNotThrow(scanner::nextToken);
		assertEquals(3, token.getRow());
		assertEquals(TokenType.PRINT, token.getType());

		token = assertDoesNotThrow(scanner::nextToken);
		assertEquals("nome", token.getVal());
		assertEquals(4, token.getRow());
		assertEquals(TokenType.ID, token.getType());

		token = assertDoesNotThrow(scanner::nextToken);
		assertEquals("intnome", token.getVal());
		assertEquals(5, token.getRow());
		assertEquals(TokenType.ID, token.getType());

		token = assertDoesNotThrow(scanner::nextToken);
		assertEquals(6, token.getRow());
		assertEquals(TokenType.TYINT, token.getType());

		token = assertDoesNotThrow(scanner::nextToken);
		assertEquals("nome", token.getVal());
		assertEquals(6, token.getRow());
		assertEquals(TokenType.ID, token.getType());

		token = assertDoesNotThrow(scanner::nextToken);
		assertEquals(6, token.getRow());
		assertEquals(TokenType.EOF, token.getType());
	}

	@Test
	void testINT() throws LexicalException, IOException {
		Scanner scanner = new Scanner("src/test/TestFiles/scanner/testint.txt");

		assertEquals("0050", scanner.nextToken().getVal());
		assertEquals("698", scanner.nextToken().getVal());
		assertEquals("560099", scanner.nextToken().getVal());
		assertEquals("1234", scanner.nextToken().getVal());
		assertEquals(TokenType.EOF, scanner.nextToken().getType());
	}

	@Test
	void testINTToken() throws LexicalException, IOException {
		Scanner scanner = new Scanner("src/test/TestFiles/scanner/testint.txt");
		Token t = new Token(TokenType.INT, 1, "0050");
		Token t2 = new Token(TokenType.INT, 2, "698");
		Token t3 = new Token(TokenType.INT, 4, "560099");
		Token t4 = new Token(TokenType.INT, 5, "1234");
		Token t5 = new Token(TokenType.EOF, 5);

		assertEquals(t, scanner.nextToken());
		assertEquals(t2, scanner.nextToken());
		assertEquals(t3, scanner.nextToken());
		assertEquals(t4, scanner.nextToken());
		assertEquals(t5, scanner.nextToken());

	}

	@Test
	void testKeyWords() throws LexicalException, IOException {
		Scanner scanner = new Scanner("src/test/TestFiles/scanner/testKeyWords.txt");
		Token t = new Token(TokenType.PRINT, 2);
		Token t2 = new Token(TokenType.TYFLOAT, 2);
		Token t3 = new Token(TokenType.TYINT, 5);
		Token t4 = new Token(TokenType.EOF, 5);

		assertEquals(t, scanner.nextToken());
		assertEquals(t2, scanner.nextToken());
		assertEquals(t3, scanner.nextToken());
		assertEquals(t4, scanner.nextToken());

	}

	@Test
	void testOpsDels() throws LexicalException, IOException {
		Scanner scanner = new Scanner("src/test/TestFiles/scanner/testOpsDels.txt");

		assertEquals("<PLUS,r:1>", scanner.nextToken().toString());
		assertEquals("<OP_ASSIGN,r:1,/=>", scanner.nextToken().toString());

		assertEquals("<MINUS,r:2>", scanner.nextToken().toString());
		assertEquals("<TIMES,r:2>", scanner.nextToken().toString());

		assertEquals("<DIVIDE,r:3>", scanner.nextToken().toString());

		assertEquals("<OP_ASSIGN,r:5,+=>", scanner.nextToken().toString());

		assertEquals("<ASSIGN,r:6>", scanner.nextToken().toString());
		assertEquals("<OP_ASSIGN,r:6,-=>", scanner.nextToken().toString());

		assertEquals("<MINUS,r:8>", scanner.nextToken().toString());
		assertEquals("<ASSIGN,r:8>", scanner.nextToken().toString());
		assertEquals("<OP_ASSIGN,r:8,*=>", scanner.nextToken().toString());

		assertEquals("<SEMI,r:10>", scanner.nextToken().toString());
		assertEquals("<EOF,r:10>", scanner.nextToken().toString());

	}
	
	@Test
	void testPeekToken() throws IOException, LexicalException {
		Scanner s = new Scanner("src/test/TestFiles/scanner/testGenerale.txt");
		assertEquals(s.peekToken().getType(), TokenType.TYINT );
		assertEquals(s.nextToken().getType(), TokenType.TYINT );
		assertEquals(s.peekToken().getType(), TokenType.ID );
		assertEquals(s.peekToken().getType(), TokenType.ID );
		Token t = s.nextToken();
		assertEquals(t.getType(), TokenType.ID);
		assertEquals(t.getRow(), 1);
		assertEquals(t.getVal(), "temp");
		
		assertEquals(s.peekToken().getType(), TokenType.SEMI );
		Token t1 = s.nextToken();
		assertEquals(t1.getType(), TokenType.SEMI);
		assertEquals(t1.getRow(), 1);
		
		assertEquals(s.nextToken().getType(), TokenType.ID );
		assertEquals(s.nextToken().getType(), TokenType.OP_ASSIGN);
		
		Token t2 = s.nextToken();
		assertEquals(t2.getType(), TokenType.FLOAT);
		assertEquals(t2.getRow(), 2);
	}


}
