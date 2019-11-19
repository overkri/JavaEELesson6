import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Objects;
import java.util.Scanner;

public class GameOfLife {
    /**
     * Utility class, starts private methods for singlethread version of Game of Life
     * @param pathStart String object that contains file location with initial config
     * @param pathResult  String object that contains file location in which modified values will be written
     * @param numberOfMoves how many iterations will method make
     * @throws FileNotFoundException if there's no file in given location
     */
    public void startTheGameSingle(String pathStart, String pathResult, int numberOfMoves) throws FileNotFoundException {
        int[][] array;
        array = readStartConfiguration(pathStart);
        array = moveSingle((array), numberOfMoves);
        outputResult((array), pathResult);
    }

    /**
     * Utility class, starts private methods for multithread version of Game of Life
     * @param pathStart String object that contains file location with initial config
     * @param pathResult  String object that contains file location in which modified values will be written
     * @param numberOfMoves how many iterations will method make
     * @throws FileNotFoundException if there's no file in given location
     */
    public void startTheGameMulti(String pathStart, String pathResult, int numberOfMoves) throws FileNotFoundException {
        int[][] array;
        array = readStartConfiguration(pathStart);
        array = moveMulti(Objects.requireNonNull(array), numberOfMoves);
        outputResult(Objects.requireNonNull(array), pathResult);
    }

    /**
     * Method recieves a String path to file with starting configuration, reads it and puts it into array
     *
     * @param pathStart contains string type input file location
     * @return array of int which contains all values of files
     */


    private int[][] readStartConfiguration(String pathStart) {
        try {
            Scanner scanTheFile;
            scanTheFile = new Scanner(new File(pathStart));
            int side = scanTheFile.nextInt();
            int[][] array = new int[side][side];
            for (int row = 0; row < array.length && scanTheFile.hasNextLine(); row++) {
                for (int col = 0; col < array.length && scanTheFile.hasNextInt(); col++) {
                    array[row][col] = scanTheFile.nextInt();
                }
            }
            scanTheFile.close();
            return array;
        } catch (IOException i) {
            System.out.println("Problems..");
            return null;
        }
    }

    /**
     * Method recieves a modified array which has been changed by number of generations and writes it int txt file located in pathResult
     *
     * @param arrayOfLife modified primal array
     * @param pathResult  contains string type output file location
     * @throws FileNotFoundException if there's no file in given location
     */
    private void outputResult(int[][] arrayOfLife, String pathResult) throws FileNotFoundException {
        File file = new File(pathResult);
        PrintWriter out = new PrintWriter(file);
        for (int[] ints : arrayOfLife) {
            out.println();
            for (int j = 0; j < arrayOfLife[0].length; j++) {
                out.print(ints[j] + " ");
            }
        }
        out.close();
    }

    /**
     * Loops in columns and rows, analizes 8 neighbouring "cells" and then writes resulting value, which depends on neigbours' values
     * Initialiazie temporary array to store modified values from arrayOfLife
     *
     * @param arrayOfLife   primal array which has been read from input file
     * @param numberOfMoves how many iterations will recieve method
     * @return modified array - arrayOfLife
     */
    private static int[][] moveSingle(int[][] arrayOfLife, int numberOfMoves) {
        for (int k = 0; k < numberOfMoves; k++) {
            int[][] temporaryArray = new int[arrayOfLife.length][arrayOfLife.length];
            for (int i = 0; i < arrayOfLife.length; i++) {
                for (int j = 0; j < arrayOfLife.length; j++) {
                    int neighboursNumber = 0;
                    neighboursNumber += arrayOfLife[(i - 1 + arrayOfLife.length) % arrayOfLife.length][(j + arrayOfLife.length - 1) % arrayOfLife.length];
                    neighboursNumber += arrayOfLife[(i - 1 + arrayOfLife.length) % arrayOfLife.length][(j + arrayOfLife.length) % arrayOfLife.length];
                    neighboursNumber += arrayOfLife[(i - 1 + arrayOfLife.length) % arrayOfLife.length][(j + arrayOfLife.length + 1) % arrayOfLife.length];
                    neighboursNumber += arrayOfLife[(i + arrayOfLife.length) % arrayOfLife.length][(j + arrayOfLife.length - 1) % arrayOfLife.length];
                    neighboursNumber += arrayOfLife[(i + arrayOfLife.length) % arrayOfLife.length][(j + arrayOfLife.length + 1) % arrayOfLife.length];
                    neighboursNumber += arrayOfLife[(i + 1 + arrayOfLife.length) % arrayOfLife.length][(j + arrayOfLife.length - 1) % arrayOfLife.length];
                    neighboursNumber += arrayOfLife[(i + 1 + arrayOfLife.length) % arrayOfLife.length][(j + arrayOfLife.length) % arrayOfLife.length];
                    neighboursNumber += arrayOfLife[(i + 1 + arrayOfLife.length) % arrayOfLife.length][(j + arrayOfLife.length + 1) % arrayOfLife.length];
                    switch (neighboursNumber) {
                        case 2:
                            temporaryArray[i][j] = arrayOfLife[i][j];
                            break;
                        case 3:
                            temporaryArray[i][j] = 1;
                            break;
                        default:
                            temporaryArray[i][j] = 0;
                            break;
                    }
                }
            }
            arrayOfLife = temporaryArray;
        }
        return arrayOfLife;
    }

    /**
     * Multithreaded version of moveSingle. Creates 4 threads with overrride run method from GoLThread
     * Initialiazie temporary array to store modified values from arrayOfLife
     *
     * @param arrayOfLife   primal array which has been read from input file
     * @param numberOfMoves how many iterations will recieve method
     * @return modified array - temporaryArray
     */
    private int[][] moveMulti(int[][] arrayOfLife, int numberOfMoves) {
        int[][] temporaryArray = new int[arrayOfLife.length][arrayOfLife.length];
        for (int i = 0; i < numberOfMoves; i++) {
            int[][] temporaryArray = new int[arrayOfLife.length][arrayOfLife.length];
            GoLThread Thread1 = new GoLThread(arrayOfLife, 0, arrayOfLife.length / 2, 0, arrayOfLife.length / 2, temporaryArray);
            GoLThread Thread2 = new GoLThread(arrayOfLife, 0, arrayOfLife.length / 2, arrayOfLife.length / 2, arrayOfLife.length, temporaryArray);
            GoLThread Thread3 = new GoLThread(arrayOfLife, arrayOfLife.length / 2, arrayOfLife.length, 0, arrayOfLife.length / 2, temporaryArray);
            GoLThread Thread4 = new GoLThread(arrayOfLife, arrayOfLife.length / 2, arrayOfLife.length, arrayOfLife.length / 2, arrayOfLife.length, temporaryArray);
            Thread1.start();
            Thread2.start();
            Thread3.start();
            Thread4.start();
        }
        return temporaryArray;
    }
}
