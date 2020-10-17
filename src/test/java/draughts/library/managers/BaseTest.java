package draughts.library.managers;

import draughts.library.boardmodel.Piece;
import draughts.library.boardmodel.Tile;
import draughts.library.exceptions.NoPieceFoundInRequestedTileException;
import draughts.library.movemodel.Capture;
import draughts.library.movemodel.Hop;
import draughts.library.movemodel.Move;

import java.util.ArrayList;

public class BaseTest {

    protected BoardManager boardManager;

    public Piece getPiece(int index) {
        try {
            return boardManager.findPieceByIndex(index);
        } catch (NoPieceFoundInRequestedTileException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public Tile getTile(int index) {
        return boardManager.findTileByIndex(index);
    }

    public Move<Hop> generateMove(int source, int destination) {
        Piece movingPiece = getPiece(source);
        Tile sourceTile = getTile(source);
        Tile destinationTile = getTile(destination);

        Move<Hop> move = new Move<>(movingPiece, new Hop(sourceTile, destinationTile));
        move.classify();

        return move;
    }

    public Move<Capture> generateMoveWithCaptures(int source, ArrayList<Integer> jumpDestinations,
                                     ArrayList<Integer> takenPawns) {

        Piece movingPiece = getPiece(source);
        Move<Capture> move = new Move<>(movingPiece);

        Capture capture;
        for (int i = 0; i < jumpDestinations.size(); i++) {
            Tile sourceTile;
            if (i == 0) sourceTile = getTile(source);
            else sourceTile = getTile(jumpDestinations.get(i - 1));

            Tile destinationTile = getTile(jumpDestinations.get(i));
            Piece takenPiece = getPiece(takenPawns.get(i));
            capture = new Capture(sourceTile, destinationTile, takenPiece);
            move.addHop(capture);
        }

        move.classify();
        return move;
    }

}
