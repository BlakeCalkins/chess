package chess;

import java.util.ArrayList;
import java.util.Collection;

public abstract class PieceMovesCalculator {
    ChessBoard board;
    ChessPosition position;
    Collection<ChessMove> movesCollection;
    int currRow;
    int currCol;
    ChessGame.TeamColor color;
    ChessGame.TeamColor enemyColor;


    public PieceMovesCalculator (ChessBoard board, ChessPosition position) {
        this.board = board;
        this.position = position;
        this.movesCollection = new ArrayList<>();
        this.currRow = position.getRow();
        this.currCol = position.getColumn();
        this.color = board.getPiece(position).getTeamColor();
        this.enemyColor = switch (color) {
            case BLACK -> ChessGame.TeamColor.WHITE;
            case WHITE -> ChessGame.TeamColor.BLACK;
        };
    }

    public Collection<ChessMove> straightMoves() {
        int row = currRow + 1;
        while (row <= 8) {
            ChessPosition pos = new ChessPosition(row, currCol);
            if (board.getPiece(pos) == null || board.getPiece(pos).getTeamColor() != color) {
                ChessMove move = new ChessMove(position, pos, null);
                movesCollection.add(move);
            } if (board.getPiece(pos) != null) {
                break;
            }
            row++;
        }
        row = currRow - 1;
        while (row >= 1) {
            ChessPosition pos = new ChessPosition(row, currCol);
            if (board.getPiece(pos) == null || board.getPiece(pos).getTeamColor() != color) {
                ChessMove move = new ChessMove(position, pos, null);
                movesCollection.add(move);
            } if (board.getPiece(pos) != null) {
                break;
            }
            row--;
        }
        int col = currCol + 1;
        while (col <= 8) {
            ChessPosition pos = new ChessPosition(currRow, col);
            if (board.getPiece(pos) == null || board.getPiece(pos).getTeamColor() != color) {
                ChessMove move = new ChessMove(position, pos, null);
                movesCollection.add(move);
            } if ( board.getPiece(pos) != null) {
                break;
            }
            col++;
        }
        col = currCol - 1;
        while (col >= 1) {
            ChessPosition pos = new ChessPosition(currRow, col);
            if (board.getPiece(pos) == null || board.getPiece(pos).getTeamColor() != color) {
                ChessMove move = new ChessMove(position, pos, null);
                movesCollection.add(move);
            } if ( board.getPiece(pos) != null) {
                break;
            }
            col--;
        }
        return movesCollection;
    }

    public Collection<ChessMove> pieceMoves() {
        return new ArrayList<>();
    }
}
