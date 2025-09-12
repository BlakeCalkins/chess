package chess;

import java.util.ArrayList;
import java.util.Collection;

public class PieceMovesCalculator {

    public final ChessBoard board;
    public final ChessPosition currPosition;
    Collection<ChessMove> moveCollection;
    int currRow;
    int currCol;
    ChessGame.TeamColor color;
    ChessGame.TeamColor enemyColor;


    public PieceMovesCalculator(ChessBoard board, ChessPosition position) {
        this.board = board;
        this.currPosition = position;
        this.moveCollection = new ArrayList<>();
        this.currRow = position.getRow();
        this.currCol = position.getColumn();
        this.color = board.getPiece(position).getTeamColor();
        this.enemyColor = switch (color) {
            case WHITE -> ChessGame.TeamColor.BLACK;
            case BLACK -> ChessGame.TeamColor.WHITE;
        };
    }

    public Collection<ChessMove> straightMoves() {
        int row = currRow;
        while (row < 8) {
            row++;
            ChessPosition newPosition = new ChessPosition(row, currCol);
            if (board.getPiece(newPosition) == null || board.getPiece(newPosition).getTeamColor() == enemyColor) {
                ChessMove move = new ChessMove(currPosition, newPosition, null);
                moveCollection.add(move);
                if (board.getPiece(newPosition) != null && board.getPiece(newPosition).getTeamColor() == enemyColor) {
                    break;
                }
            } else {
                break;
            }
        }
        row = currRow;
        while (row > 1) {
            row--;
            ChessPosition newPosition = new ChessPosition(row, currCol);
            if (board.getPiece(newPosition) == null || board.getPiece(newPosition).getTeamColor() == enemyColor) {
                ChessMove move = new ChessMove(currPosition, newPosition, null);
                moveCollection.add(move);
                if (board.getPiece(newPosition) != null && board.getPiece(newPosition).getTeamColor() == enemyColor) {
                    break;
                }
            } else {
                break;
            }
        }
        int col = currCol;
        while (col < 8) {
            col++;
            ChessPosition newPosition = new ChessPosition(currRow, col);
            if (board.getPiece(newPosition) == null || board.getPiece(newPosition).getTeamColor() == enemyColor) {
                ChessMove move = new ChessMove(currPosition, newPosition, null);
                moveCollection.add(move);
                if (board.getPiece(newPosition) != null && board.getPiece(newPosition).getTeamColor() == enemyColor) {
                    break;
                }
            } else {
                break;
            }
        }
        col = currCol;
        while (col > 1) {
            col--;
            ChessPosition newPosition = new ChessPosition(currRow, col);
            if (board.getPiece(newPosition) == null || board.getPiece(newPosition).getTeamColor() == enemyColor) {
                ChessMove move = new ChessMove(currPosition, newPosition, null);
                moveCollection.add(move);
                if (board.getPiece(newPosition) != null && board.getPiece(newPosition).getTeamColor() == enemyColor) {
                    break;
                }
            } else {
                break;
            }
        }
        return moveCollection;
    }

    public Collection<ChessMove> diagonalMoves() {
        int row = currRow;
        int col = currCol;
        while (row < 8 && col < 8) {
            row++;
            col++;
            ChessPosition newPosition = new ChessPosition(row, col);
            if (board.getPiece(newPosition) == null || board.getPiece(newPosition).getTeamColor() == enemyColor) {
                ChessMove move = new ChessMove(currPosition, newPosition, null);
                moveCollection.add(move);
                if (board.getPiece(newPosition) != null && board.getPiece(newPosition).getTeamColor() == enemyColor) {
                    break;
                }
            } else  {
                break;
            }
        }
        row = currRow;
        col = currCol;
        while (row > 1 && col > 1) {
            row--;
            col--;
            ChessPosition newPosition = new ChessPosition(row, col);
            if (board.getPiece(newPosition) == null || board.getPiece(newPosition).getTeamColor() == enemyColor) {
                ChessMove move = new ChessMove(currPosition, newPosition, null);
                moveCollection.add(move);
                if (board.getPiece(newPosition) != null && board.getPiece(newPosition).getTeamColor() == enemyColor) {
                    break;
                }
            } else  {
                break;
            }
        }
        row = currRow;
        col = currCol;
        while (row < 8 && col > 1) {
            row++;
            col--;
            ChessPosition newPosition = new ChessPosition(row, col);
            if (board.getPiece(newPosition) == null || board.getPiece(newPosition).getTeamColor() == enemyColor) {
                ChessMove move = new ChessMove(currPosition, newPosition, null);
                moveCollection.add(move);
                if (board.getPiece(newPosition) != null && board.getPiece(newPosition).getTeamColor() == enemyColor) {
                    break;
                }
            } else  {
                break;
            }
        }
        row = currRow;
        col = currCol;
        while (row > 1 && col < 8) {
            row--;
            col++;
            ChessPosition newPosition = new ChessPosition(row, col);
            if (board.getPiece(newPosition) == null || board.getPiece(newPosition).getTeamColor() == enemyColor) {
                ChessMove move = new ChessMove(currPosition, newPosition, null);
                moveCollection.add(move);
                if (board.getPiece(newPosition) != null && board.getPiece(newPosition).getTeamColor() == enemyColor) {
                    break;
                }
            } else  {
                break;
            }
        }
        return moveCollection;
    }

    public Collection<ChessMove> pieceMoves() {
        return new ArrayList<>();
    }
}
