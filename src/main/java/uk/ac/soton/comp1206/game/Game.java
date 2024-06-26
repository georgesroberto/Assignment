package uk.ac.soton.comp1206.game;

import java.util.Random;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import uk.ac.soton.comp1206.component.GameBlock;
import uk.ac.soton.comp1206.scene.Multimedia;

/**
 * The Game class handles the main logic, state and properties of the TetrECS game. Methods to manipulate the game state
 * and to handle actions made by the player should take place inside this class.
 */
public class Game {

    private static final Logger logger = LogManager.getLogger(Game.class);

     // Add a Multimedia field
    private final Multimedia multimedia = new Multimedia();
    /**
     * Number of rows
     */
    protected final int rows;

    /**
     * Number of columns
     */
    protected final int cols;

    /**
     * The grid model linked to the game
     */
    protected final Grid grid;

    /**
     * Create a new game with the specified rows and columns. Creates a corresponding grid model.
     * @param cols number of columns
     * @param rows number of rows
     */
    public Game(int cols, int rows) {
        this.cols = cols;
        this.rows = rows;

        //Create a new grid model to represent the game state
        this.grid = new Grid(cols, rows);
    }
    


    /**
     * TODO: HANDLE UI
     */

    // Add bindable properties
    private final IntegerProperty score = new SimpleIntegerProperty(0);
    private final IntegerProperty level = new SimpleIntegerProperty(1);
    private final IntegerProperty lives = new SimpleIntegerProperty(3);
    private final IntegerProperty multiplier = new SimpleIntegerProperty(1);

    // Add appropriate accessor methods
    public IntegerProperty scoreProperty() { return score; }
    public int getScore() { return score.get(); }
    public void setScore(int score) { this.score.set(score); }

    public IntegerProperty levelProperty() { return level; }
    public int getLevel() { return level.get(); }
    public void setLevel(int level) { this.level.set(level); }

    public IntegerProperty livesProperty() { return lives; }
    public int getLives() { return lives.get(); }
    public void setLives(int lives) { this.lives.set(lives); }

    public IntegerProperty multiplierProperty() { return multiplier; }
    public int getMultiplier() { return multiplier.get(); }
    public void setMultiplier(int multiplier) { this.multiplier.set(multiplier); }
  
    
    /**
     * Start the game
     */
    public void start() {
        logger.info("Starting game");
        initialiseGame();
    }

    /**
     * Initialise a new game and set up anything that needs to be done at the start
     */
    public void initialiseGame() {
        logger.info("Initialising game");
    }

    /**
     * Handle what should happen when a particular block is clicked
     * @param gameBlock the block that was clicked
     */
    public void blockClicked(GameBlock gameBlock) {
        //Get the position of this block
        int x = gameBlock.getX();
        int y = gameBlock.getY();

        //Get the new value for this block
        int previousValue = grid.get(x,y);
        int newValue = previousValue + 1;
        if (newValue  > GamePiece.PIECES) {
            newValue = 0;
        }

        //Update the grid with the new value
        grid.set(x,y,newValue);
    }

    /**
     * Get the grid model inside this game representing the game state of the board
     * @return game grid model
     */
    public Grid getGrid() {
        return grid;
    }

    /**
     * Get the number of columns in this game
     * @return number of columns
     */
    public int getCols() {
        return cols;
    }

    /**
     * Get the number of rows in this game
     * @return number of rows
     */
    public int getRows() {
        return rows;
    }



    /**
     * Building game Logic
     */

    private GamePiece currentPiece;

    public void spawnPiece() {
        // Create a new random GamePiece and set it as the current piece
        // This is a simple implementation and may need to be updated based on your game rules
        currentPiece = GamePiece.createPiece(new Random().nextInt(GamePiece.PIECES));
        System.out.println(currentPiece);
    }

    public void nextPiece() {
        // Replace the current piece with a new piece
        spawnPiece();
    }

    public void afterPiecePlayed() {
        // Assume piece is the GamePiece that was just played
        GamePiece piece = this.currentPiece;

        int lines = 0;
        int blocks = 0;

        // Count the number of blocks in the piece
        for (int[] row : piece.getBlocks()) {
            for (int block : row) {
                if (block != 0) {
                    blocks++;
                }
            }
        }

        // Check each row and column in the grid
        for (int i = 0; i < grid.getRows(); i++) {
            boolean rowFull = true;
            for (int j = 0; j < grid.getCols(); j++) {
                if (grid.get(j, i) == 0) {
                    rowFull = false;
                    break;
                }
            }
            if (rowFull) {
                lines++;
            }
        }
        for (int i = 0; i < grid.getCols(); i++) {
            boolean colFull = true;
            for (int j = 0; j < grid.getRows(); j++) {
                if (grid.get(i, j) == 0) {
                    colFull = false;
                    break;
                }
            }
            if (colFull) {
                lines++;
            }
        }

        // Call the score method
        score(lines, blocks);

         // Play a sound effect when a piece is played
        multimedia.playAudio("path/to/sound/effect.mp3");
    }

    /*
     * 
     * TODO: Implement Score 
     */


    public void score(int lines, int blocks) {
        // Calculate the score based on the number of lines, number of blocks, and the multiplier
        int scoreIncrease = lines * blocks * 10 * getMultiplier();

        // Increase the score
        setScore(getScore() + scoreIncrease);

        // Increase the level if the score has reached a multiple of 1000
        if (getScore() >= getLevel() * 1000) {
            setLevel(getLevel() + 1);
        }

        // Reset the multiplier if no lines were cleared, otherwise increase it
        if (lines == 0) {
            setMultiplier(1);
        } else {
            setMultiplier(getMultiplier() + 1);
        }
    }


}
