package chess;

import java.util.Collection;

public class KingMovesCalculator extends PieceMovesCalculator {

    public KingMovesCalculator(ChessBoard board, ChessPosition position) {
        super(board, position);
    }

    @Override
    public Collection<ChessMove> pieceMoves() {
        for (int i = currRow - 1; i <= currRow + 1; i++) {
            for (int j = currCol - 1; j <= currCol + 1; j++) {
                if (outOfBounds(i, j) || isPiecePosition(i, j)) {
                    continue;
                }
                addIfEmptyOrEnemy(i, j);
            }

        }
        return moveCollection;
    }
}
