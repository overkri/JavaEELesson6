import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;

import static org.junit.jupiter.api.Assertions.*;

class GameOfLifeTest {
    @Test
    void startTheGame() throws FileNotFoundException, InterruptedException {
        GameOfLife game = new GameOfLife();
        String pathStart = "C:/users/user/desktop/start.txt";
        String pathResult = "C:/users/user/desktop/result.txt";
        int numberOfMoves = 25505;
        long begin1 = System.currentTimeMillis();
        game.startTheGameSingle(pathStart, pathResult, numberOfMoves);
        long end1 = System.currentTimeMillis();
        long result1 = end1 - begin1;
        String pathResult2 = "C:/users/user/desktop/result2.txt";
        long begin2 = System.currentTimeMillis();
        game.startTheGameMulti(pathStart, pathResult2, numberOfMoves);
        long end2 = System.currentTimeMillis();
        long result2 = end2 - begin2;
        assertTrue(result1 > result2);
    }
}