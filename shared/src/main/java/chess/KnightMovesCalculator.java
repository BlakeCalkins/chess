package chess;

import java.util.Collection;

public class KnightMovesCalculator extends PieceMovesCalculator {
    public KnightMovesCalculator(ChessBoard board, ChessPosition position) {
        super(board, position);
    }

    @Override
    public Collection<ChessMove> pieceMoves() {
        for (int row = currRow - 2; row <= currRow + 2; row++) {
            if (row > 8 || row < 1) {
                continue;
            }
            for (int col = currCol -2; col <= currCol + 2; col++) {
                if (col > 8 || col < 1) {
                    continue;
                }
                if (row == currRow && col == currCol) {
                    continue;
                }
                ChessPosition pos = new ChessPosition(row, col);
                if (row == currRow - 2 || row == currRow + 2) {
                    if (col == currCol + 1 || col == currCol - 1) {
                        if (board.getPiece(pos) == null || board.getPiece(pos).getTeamColor() != color) {
                            ChessMove move = new ChessMove(position, pos, null);
                            movesCollection.add(move);
                        }
                    }
                }
                if (row == currRow - 1 || row == currRow + 1) {
                    if (col == currCol + 2 || col == currCol - 2) {
                        if (board.getPiece(pos) == null || board.getPiece(pos).getTeamColor() != color) {
                            ChessMove move = new ChessMove(position, pos, null);
                            movesCollection.add(move);
                        }
                    }
                }
            }
        }
        return movesCollection;
    }
}
