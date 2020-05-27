package draughts.library.boardmodel;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.mockito.Mockito.*;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class TileTest {
	
	Tile testObj;
	
	@Before
	public void setUp() {
		Tile tile = new Tile(10);
		testObj = spy(tile);
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
	
	@Test 
	public void calculateIndex_test() {
		assertEquals(0, Tile.calculateIndex(1, 1));
		assertEquals(4, Tile.calculateIndex(1, 8));
		assertEquals(9, Tile.calculateIndex(2, 7));
		assertEquals(13, Tile.calculateIndex(3, 6));
		assertEquals(0, Tile.calculateIndex(5, 3));
		assertEquals(25, Tile.calculateIndex(5, 10));
		assertEquals(0, Tile.calculateIndex(6, 10));
		assertEquals(42, Tile.calculateIndex(9, 4));
		assertEquals(0, Tile.calculateIndex(10, 2));
		assertEquals(50, Tile.calculateIndex(10, 9));
	
	}

}
