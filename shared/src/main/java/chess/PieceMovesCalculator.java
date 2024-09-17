package chess;

import java.util.ArrayList;
import java.util.Collection;

public abstract class PieceMovesCalculator {
    ChessBoard board;
    ChessPosition position;
    Collection<ChessMove> movesCollection;
    int row;
    int col;
    ChessGame.TeamColor color;
    ChessGame.TeamColor enemyColor;


    public PieceMovesCalculator (ChessBoard board, ChessPosition position) {
        this.board = board;
        this.position = position;
        this.movesCollection = new ArrayList<>();
        this.row = position.getRow();
        this.col = position.getColumn();
        this.color = board.getPiece(position).getTeamColor();
        this.enemyColor = switch (color) {
            case BLACK -> ChessGame.TeamColor.WHITE;
            case WHITE -> ChessGame.TeamColor.BLACK;
        };
    }

    public Collection<ChessMove> straightMoves() {

        return movesCollection;
    }
}
