class GoLThread extends Thread {
    private static int[][] arrayOfLife;
    private static int startingPoint1;
    private static int endPoint1;
    private static int startingPoint2;
    private static int endPoint2;
    private int[][] temporaryArray;

    /**
     * Method creates thread that recieves starting configuration array.
     * loops from startingPoint 1 to endPoint 1 for columns in 2D array
     * And from startingPoint 2 to endPoint 2 for rows in 2D array
     * Then writes modified values to temporaryArray
     * @param arrayOfLife initial array that has been written from input file
     * @param startingPoint1 int variable starts loop from this index for columns
     * @param endPoint1 int variable ends loop at this index for columns
     * @param startingPoint2 int variable starts loop from this index for rows
     * @param endPoint2 int variable ends loop at this index for rows
     * @param temporaryArray temporary object which contins modified  values of arrayOfLife
     */
    GoLThread(int[][] arrayOfLife, int startingPoint1, int endPoint1, int startingPoint2, int endPoint2, int[][] temporaryArray) {
        this.arrayOfLife = arrayOfLife;
        this.startingPoint1 = startingPoint1;
        this.endPoint1 = endPoint1;
        this.startingPoint2 = startingPoint2;
        this.endPoint2 = endPoint2;
        this.temporaryArray = temporaryArray;
    }

    public void start() {
        for (int i = startingPoint1; i < endPoint1; i++) {
            for (int j = startingPoint2; j < endPoint2; j++) {
                int neighboursNumber = 0;
                neighboursNumber += arrayOfLife[(i - 1 + arrayOfLife.length) % arrayOfLife.length][((j + arrayOfLife.length - 1) % arrayOfLife.length)];
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
    }
}

