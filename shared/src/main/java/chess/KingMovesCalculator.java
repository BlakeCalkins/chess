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
                if (i < 1 || j < 1 || i > 8 || j > 8 || (i == currRow && j == currCol)) {
                    continue;
                }
                ChessPosition newPosition = new ChessPosition(i, j);
                if (board.getPiece(newPosition) == null || board.getPiece(newPosition).getTeamColor() == enemyColor) {
                    ChessMove move = new ChessMove(currPosition, newPosition, null);
                    moveCollection.add(move);
                }
            }

        }
        return moveCollection;
    }
}
