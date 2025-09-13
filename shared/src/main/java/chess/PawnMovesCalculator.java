package chess;

import java.util.Collection;

public class PawnMovesCalculator extends PieceMovesCalculator {

    public PawnMovesCalculator(ChessBoard board, ChessPosition position) {
        super(board, position);
    }

    @Override
    public Collection<ChessMove> pieceMoves() {
        if (color == ChessGame.TeamColor.WHITE) {
            if (currRow + 1 < 8) {
                ChessPosition positionA = new ChessPosition(currRow + 1, currCol - 1);
                ChessPosition positionB = new ChessPosition(currRow + 1, currCol + 1);
                ChessPosition positionC = new ChessPosition(currRow + 1, currCol);
                if (currCol - 1 != 0 && board.getPiece(positionA) != null && board.getPiece(positionA).getTeamColor() == enemyColor) {
                    moveCollection.add(new ChessMove(currPosition, positionA, null));
                }
                if (currCol + 1 != 9 && board.getPiece(positionB) != null && board.getPiece(positionB).getTeamColor() == enemyColor) {
                    moveCollection.add(new ChessMove(currPosition, positionB, null));
                }
                if (board.getPiece(positionC) == null) {
                    moveCollection.add(new ChessMove(currPosition, positionC, null));
                }
            } else if (currRow + 1 == 8) {
                ChessPosition positionA = new ChessPosition(currRow + 1, currCol - 1);
                ChessPosition positionB = new ChessPosition(currRow + 1, currCol + 1);
                ChessPosition positionC = new ChessPosition(currRow + 1, currCol);
                if (currCol - 1 != 0 && board.getPiece(positionA) != null && board.getPiece(positionA).getTeamColor() == enemyColor) {
                    moveCollection.add(new ChessMove(currPosition, positionA, ChessPiece.PieceType.QUEEN));
                    moveCollection.add(new ChessMove(currPosition, positionA, ChessPiece.PieceType.BISHOP));
                    moveCollection.add(new ChessMove(currPosition, positionA, ChessPiece.PieceType.KNIGHT));
                    moveCollection.add(new ChessMove(currPosition, positionA, ChessPiece.PieceType.ROOK));
                }
                if (currCol + 1 != 9 && board.getPiece(positionB) != null && board.getPiece(positionB).getTeamColor() == enemyColor) {
                    moveCollection.add(new ChessMove(currPosition, positionB, ChessPiece.PieceType.QUEEN));
                    moveCollection.add(new ChessMove(currPosition, positionB, ChessPiece.PieceType.BISHOP));
                    moveCollection.add(new ChessMove(currPosition, positionB, ChessPiece.PieceType.KNIGHT));
                    moveCollection.add(new ChessMove(currPosition, positionB, ChessPiece.PieceType.ROOK));
                }
                if (board.getPiece(positionC) == null) {
                    moveCollection.add(new ChessMove(currPosition, positionC, ChessPiece.PieceType.QUEEN));
                    moveCollection.add(new ChessMove(currPosition, positionC, ChessPiece.PieceType.BISHOP));
                    moveCollection.add(new ChessMove(currPosition, positionC, ChessPiece.PieceType.KNIGHT));
                    moveCollection.add(new ChessMove(currPosition, positionC, ChessPiece.PieceType.ROOK));
                }
            } if (currRow + 2 == 4){
                ChessPosition positionA = new ChessPosition(currRow + 1, currCol);
                ChessPosition positionB = new ChessPosition(currRow + 2, currCol);
                if (board.getPiece(positionA) == null && board.getPiece(positionB) == null) {
                    moveCollection.add(new ChessMove(currPosition, positionB, null));
                }
            }
        } else {
            if (currRow - 1 > 1) {
                ChessPosition positionA = new ChessPosition(currRow - 1, currCol - 1);
                ChessPosition positionB = new ChessPosition(currRow - 1, currCol + 1);
                ChessPosition positionC = new ChessPosition(currRow - 1, currCol);
                if (currCol - 1 != 0 && board.getPiece(positionA) != null && board.getPiece(positionA).getTeamColor() == enemyColor) {
                    moveCollection.add(new ChessMove(currPosition, positionA, null));
                }
                if (currCol + 1 != 9 && board.getPiece(positionB) != null && board.getPiece(positionB).getTeamColor() == enemyColor) {
                    moveCollection.add(new ChessMove(currPosition, positionB, null));
                }
                if (board.getPiece(positionC) == null) {
                    moveCollection.add(new ChessMove(currPosition, positionC, null));
                }
            } else if (currRow - 1 == 1) {
                ChessPosition positionA = new ChessPosition(currRow - 1, currCol - 1);
                ChessPosition positionB = new ChessPosition(currRow - 1, currCol + 1);
                ChessPosition positionC = new ChessPosition(currRow - 1, currCol);
                if (currCol - 1 != 0 && board.getPiece(positionA) != null && board.getPiece(positionA).getTeamColor() == enemyColor) {
                    moveCollection.add(new ChessMove(currPosition, positionA, ChessPiece.PieceType.QUEEN));
                    moveCollection.add(new ChessMove(currPosition, positionA, ChessPiece.PieceType.BISHOP));
                    moveCollection.add(new ChessMove(currPosition, positionA, ChessPiece.PieceType.KNIGHT));
                    moveCollection.add(new ChessMove(currPosition, positionA, ChessPiece.PieceType.ROOK));
                }
                if (currCol + 1 != 9 && board.getPiece(positionB) != null && board.getPiece(positionB).getTeamColor() == enemyColor) {
                    moveCollection.add(new ChessMove(currPosition, positionB, ChessPiece.PieceType.QUEEN));
                    moveCollection.add(new ChessMove(currPosition, positionB, ChessPiece.PieceType.BISHOP));
                    moveCollection.add(new ChessMove(currPosition, positionB, ChessPiece.PieceType.KNIGHT));
                    moveCollection.add(new ChessMove(currPosition, positionB, ChessPiece.PieceType.ROOK));
                }
                if (board.getPiece(positionC) == null) {
                    moveCollection.add(new ChessMove(currPosition, positionC, ChessPiece.PieceType.QUEEN));
                    moveCollection.add(new ChessMove(currPosition, positionC, ChessPiece.PieceType.BISHOP));
                    moveCollection.add(new ChessMove(currPosition, positionC, ChessPiece.PieceType.KNIGHT));
                    moveCollection.add(new ChessMove(currPosition, positionC, ChessPiece.PieceType.ROOK));
                }
            }
            if (currRow - 2 == 5){
                ChessPosition positionA = new ChessPosition(currRow - 1, currCol);
                ChessPosition positionB = new ChessPosition(currRow - 2, currCol);
                if (board.getPiece(positionA) == null && board.getPiece(positionB) == null) {
                    moveCollection.add(new ChessMove(currPosition, positionB, null));
                }
            }
        }
        return moveCollection;
    }
}
