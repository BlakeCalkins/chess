package chess;

import java.util.Collection;

public class PawnMovesCalculator extends PieceMovesCalculator {
    public PawnMovesCalculator(ChessBoard board, ChessPosition position) {
        super(board, position);
    }

    @Override
    public Collection<ChessMove> pieceMoves() {
        if (color == ChessGame.TeamColor.WHITE) {
            int row = currRow + 1;
            for (int col = currCol - 1; col <= currCol + 1; col++) {
                if (col > 8 || col < 1 || row > 8) {
                    continue;
                }
                ChessPosition pos = new ChessPosition(row, col);
                if (row == 8) {
                    if (((board.getPiece(pos) == null) && col == currCol) ||
                            ((board.getPiece(pos) != null) && board.getPiece(pos).getTeamColor() != color && col != currCol)) {
                        movesCollection.add(new ChessMove(position, pos, ChessPiece.PieceType.QUEEN));
                        movesCollection.add(new ChessMove(position, pos, ChessPiece.PieceType.KNIGHT));
                        movesCollection.add(new ChessMove(position, pos, ChessPiece.PieceType.ROOK));
                        movesCollection.add(new ChessMove(position, pos, ChessPiece.PieceType.BISHOP));
                        continue;
                    }
                }
                if (((board.getPiece(pos) == null) && col == currCol) ||
                        ((board.getPiece(pos) != null) && board.getPiece(pos).getTeamColor() != color && col != currCol)) {
                    movesCollection.add(new ChessMove(position, pos, null));
                }
                if (currRow == 2 && col == currCol) {
                    ChessPosition newPos = new ChessPosition(currRow + 2, col);
                    if (board.getPiece(newPos) == null && board.getPiece(pos) == null) {
                        movesCollection.add(new ChessMove(position, newPos, null));
                    }
                }
            }
        }
        if (color == ChessGame.TeamColor.BLACK) {
            int row = currRow - 1;
            for (int col = currCol - 1; col <= currCol + 1; col++) {
                if (col > 8 || col < 1 || row < 1) {
                    continue;
                }
                ChessPosition pos = new ChessPosition(row, col);
                if (row == 1) {
                    if (((board.getPiece(pos) == null) && col == currCol) ||
                            ((board.getPiece(pos) != null) && board.getPiece(pos).getTeamColor() != color && col != currCol)) {
                        movesCollection.add(new ChessMove(position, pos, ChessPiece.PieceType.QUEEN));
                        movesCollection.add(new ChessMove(position, pos, ChessPiece.PieceType.KNIGHT));
                        movesCollection.add(new ChessMove(position, pos, ChessPiece.PieceType.ROOK));
                        movesCollection.add(new ChessMove(position, pos, ChessPiece.PieceType.BISHOP));
                        continue;
                    }
                }
                if (((board.getPiece(pos) == null) && col == currCol) ||
                        ((board.getPiece(pos) != null) && board.getPiece(pos).getTeamColor() != color && col != currCol)) {
                    movesCollection.add(new ChessMove(position, pos, null));
                }
                if (currRow == 7 && col == currCol) {
                    ChessPosition newPos = new ChessPosition(currRow - 2, col);
                    if (board.getPiece(newPos) == null && board.getPiece(pos) == null) {
                        movesCollection.add(new ChessMove(position, newPos, null));
                    }
                }
            }
        }
        return movesCollection;
    }
}
