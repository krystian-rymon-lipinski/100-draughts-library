package draughts.library.boardmodel;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class TileTest {
	
	Tile testObj;
	
	@Before
	public void setUp() {
		testObj = new Tile(0, 1);
	}
	
	@Test 
	public void calculateIndex_test() {
		assertEquals(0, new Tile(1, 1).getIndex());
		assertEquals(4, new Tile(1, 8).getIndex());
		assertEquals(9, new Tile(2, 7).getIndex());
		assertEquals(13, new Tile(3, 6).getIndex());
		assertEquals(0, new Tile(5, 3).getIndex());
		assertEquals(25, new Tile(5, 10).getIndex());
		assertEquals(0, new Tile(6, 10).getIndex());
		assertEquals(42, new Tile(9, 4).getIndex());
		assertEquals(0, new Tile(10, 2).getIndex());
		assertEquals(50, new Tile(10, 9).getIndex());
	
	}
	
	@Test
	public void getRow_test() {
		testObj.setIndex(1);
		assertEquals(1, testObj.getRow());
		
		testObj.setIndex(4);
		assertEquals(1, testObj.getRow());
		
		testObj.setIndex(6);
		assertEquals(2, testObj.getRow());

		testObj.setIndex(21);
		assertEquals(5, testObj.getRow());

		testObj.setIndex(26);
		assertEquals(6, testObj.getRow());

		testObj.setIndex(33);
		assertEquals(7, testObj.getRow());

		testObj.setIndex(41);
		assertEquals(9, testObj.getRow());

		testObj.setIndex(44);
		assertEquals(9, testObj.getRow());

		testObj.setIndex(49);
		assertEquals(10, testObj.getRow());

		testObj.setIndex(50);
		assertEquals(10, testObj.getRow());
	}
	
	@Test
	public void isRowEven_test() {
		testObj.setIndex(1);
		assertFalse(testObj.isRowEven());
		
		testObj.setIndex(5);
		assertFalse(testObj.isRowEven());

		testObj.setIndex(6);
		assertTrue(testObj.isRowEven());

		testObj.setIndex(19);
		assertTrue(testObj.isRowEven());

		testObj.setIndex(21);
		assertFalse(testObj.isRowEven());

		testObj.setIndex(34);
		assertFalse(testObj.isRowEven());

		testObj.setIndex(45);
		assertFalse(testObj.isRowEven());

		testObj.setIndex(46);
		assertTrue(testObj.isRowEven());

		testObj.setIndex(48);
		assertTrue(testObj.isRowEven());

		testObj.setIndex(50);
		assertTrue(testObj.isRowEven());
	}
	
	@Test
	public void getColumn_test() {
		testObj.setIndex(1);
		assertEquals(2, testObj.getColumn());
		
		testObj.setIndex(5);
		assertEquals(10, testObj.getColumn());
		
		testObj.setIndex(6);
		assertEquals(1, testObj.getColumn());

		testObj.setIndex(21);
		assertEquals(2, testObj.getColumn());

		testObj.setIndex(26);
		assertEquals(1, testObj.getColumn());

		testObj.setIndex(35);
		assertEquals(10, testObj.getColumn());

		testObj.setIndex(41);
		assertEquals(2, testObj.getColumn());

		testObj.setIndex(44);
		assertEquals(8, testObj.getColumn());

		testObj.setIndex(49);
		assertEquals(7, testObj.getColumn());

		testObj.setIndex(50);
		assertEquals(9, testObj.getColumn());
	}

}
