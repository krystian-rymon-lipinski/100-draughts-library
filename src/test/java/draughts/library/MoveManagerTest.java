package draughts.library;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class MoveManagerTest {
	
	MoveManager testObj;
	BoardManager boardManager;
	
	@Before
	public void setUp() {
		testObj = new MoveManager();
		boardManager = testObj.getBoardManager();
		boardManager.createEmptyBoard();
	}
	
	@Test
	public void addHops_test() {
		Move<Hop> move = new Move<Hop>(new Hop(31, 26));
		move.addHop(new Hop(26, 21));
		
		assertEquals(2, move.getNumberOfHops());
		
		Move<Capture> move2 = new Move<Capture>(new Capture(35, 24, 30));
		move2.addHop(new Capture(24, 15, 20));
		move2.addHop(new Capture(15, 4, 10));
		
		assertEquals(3, move2.getNumberOfHops());
	}
	
	@Test
	public void findAllCorrectMoves_whenCapturesAvailable_test() {
		boardManager.createEmptyBoard();
		
		boardManager.addWhitePawn(46);
		boardManager.addWhitePawn(49);
		boardManager.addWhitePawn(43);
		boardManager.addWhiteQueen(35);
		boardManager.addBlackPawn(13);
		boardManager.addBlackPawn(16);
		boardManager.addBlackPawn(22);
		boardManager.addBlackPawn(26);
		
		testObj.findAllCorrectMoves(true);
		
		assertEquals(2, testObj.getPossibleMoves().size());	
	}
	
	@Test
	public void findAllCorrectMoves_whenNoCaptureAvailable_test() {
		boardManager.createEmptyBoard();
		
		boardManager.addWhitePawn(46);
		boardManager.addWhitePawn(49);
		boardManager.addWhitePawn(43);
		boardManager.addWhiteQueen(35);
		boardManager.addBlackPawn(18);
		boardManager.addBlackPawn(16);
		boardManager.addBlackPawn(22);
		boardManager.addBlackPawn(26);
		
		testObj.findAllCorrectMoves(true);
		
		assertEquals(12, testObj.getPossibleMoves().size());
	}
	
	
	

}
