package chess;

import java.util.ArrayList;
import java.util.Collection;

public class PieceMovesCalculator {

    private final ChessBoard board;
    private final ChessPosition position;
    Collection<ChessMove> moveCollection;
    int currRow;
    int currCol;
    ChessGame.TeamColor color;
    ChessGame.TeamColor enemyColor;


    public PieceMovesCalculator(ChessBoard board, ChessPosition position) {
        this.board = board;
        this.position = position;
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
        while (currRow < 8) {
            row++;
            ChessPosition pos = new ChessPosition(row, currCol);
            if (board.getPiece(pos) == null || board.getPiece(pos).getTeamColor() == enemyColor) {

            }
        }
        return moveCollection;
    }
}
