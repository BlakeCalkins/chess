package chess;

import java.util.Collection;

public class KnightMovesCalculator extends PieceMovesCalculator {

    public KnightMovesCalculator(ChessBoard board, ChessPosition position) {
        super(board, position);
    }

    @Override
    public Collection<ChessMove> pieceMoves() {
        for (int i = currRow - 2; i <= currRow + 2; i += 4) {
            for (int j = currCol - 1; j<= currCol + 1; j += 2) {
                if (outOfBounds(i, j) || isPiecePosition(i, j)) {
                    continue;
                }
                ChessPosition newPosition = new ChessPosition(i, j);
                addIfEmptyOrEnemy(newPosition);
            }
        }
        for (int i = currRow - 1; i <= currRow + 1; i += 2) {
            for (int j = currCol - 2; j<= currCol + 2; j += 4) {
                if (outOfBounds(i, j) || isPiecePosition(i, j)) {
                    continue;
                }
                ChessPosition newPosition = new ChessPosition(i, j);
                addIfEmptyOrEnemy(newPosition);
            }
        }
        return moveCollection;
    }
}
