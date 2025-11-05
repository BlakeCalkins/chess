package dataaccess;

import chess.ChessGame;
import datamodel.GameData;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class GameDataAccessTest {

    GameData game = new GameData(1, null, null, "groovyGame", new ChessGame());
    GameDataAccess db = new MySQLGameDAO();

    public GameDataAccessTest() throws DataAccessException {
    }

    @AfterEach
    void clearOut() {
        db.clear();
    }

    @Test
    void createGame() {
        assertEquals(1, db.createGame(game));
    }

    @Test
    void createDuplicateGame() {
        db.createGame(game);
        assertThrows(RuntimeException.class, () -> db.createGame(game));
    }

    @Test
    void getGame() {
        db.createGame(game);
        assertEquals(game, db.getGame(1));
    }

    @Test
    void getBadGame() {
        db.createGame(game);
        assertNull(db.getGame(2));
    }

    @Test
    void listGames() {
        db.createGame(game);
        assertFalse(db.listGames().isEmpty());
    }

    @Test
    void listNoGames() {
        assertTrue(db.listGames().isEmpty());
    }

    @Test
    void joinGameWhite() {
        db.createGame(game);
        db.joinGame(1, "chungus", "WHITE");
        assertEquals("chungus", db.getGame(1).whiteUsername());
    }

    @Test
    void joinGameBlack() {
        db.createGame(game);
        db.joinGame(1, "Mr. Clean", "BLACK");
        assertEquals("Mr. Clean", db.getGame(1).blackUsername());
    }

    @Test
    void clear() {
        db.createGame(game);
        db.clear();
        assertTrue(db.listGames().isEmpty());
    }
}
