package gameoflife.old;

import gameoflife.filessave.CreateFolderException;
import gameoflife.filessave.WriteToFileException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.SecureRandom;

/*
simulate a game of life and save each generation as a pbm file.
simulation will be on a grid of W * H cells.
computation will be done using n threads ( by default n=2 )
the threads will be created once for all the life time of the simulation.
( do not create threads for each iteration ).
each iteration will result in a generation that we will save to a file named : life.001.pbm
with a running number for the frame sequence. The file will be formatted according to the format specified in https://en.wikipedia.org/wiki/Netpbm#PBM_example
the program shall take the following as arguments:

java Life filename iters threads   width height

filename: base file name for thee frames
iter: number of frames to create, default to 20
threads: number of threads to use, default to 2
width: width of the frame, default to 900
height: height of the frame, default to 800
*/

public class Life {
    public static final String TYPE_FILE = ".pbm";
    public static final String DEFAULT_OUTPUT_FOLDER = "output";
    private static final int DEFAULT_ITERATIONS = 20;
    private static final int DEFAULT_THREADS = 2;
    private static final int DEFAULT_WIDTH = 900;
    private static final int DEFAULT_HEIGHT = 800;
    private static final double INITIAL_POPULATION = 0.2;
    private static final int[][] NEIGHBOR_WINDOW = {
            {-1, -1},
            {-1, 0},
            {-1, 1},
            {0, -1},
            {0, 1},
            {1, -1},
            {1, 0},
            {1, 1}};


    private static int m_iterations;
    private static int m_numThreads; // todo
    private static int m_width;
    private static int m_height;
    private static Path m_filePath;
    private static boolean[][] m_currentGeneration;
    private static boolean[][] m_nextGeneration;

    public static void main(final String[] args) {
        parseArguments(args);
        createTheOutputDirectory();
        m_currentGeneration = new boolean[m_width][m_height];
        m_nextGeneration = new boolean[m_width][m_height];
        initializeFirstGeneration();
        simulateIterationsGenerations();
    }

    private static void parseArguments(final String[] args) {
        // java Life 'movie/frame' '100' '4'  '200' '200'
        // java Life '200'
        int index = 0;
        m_filePath = Path.of(args.length > 0 ? args[index++] : DEFAULT_OUTPUT_FOLDER);
        m_iterations = args.length > 1 ? Integer.parseInt(args[index++]) : DEFAULT_ITERATIONS;
        m_numThreads = args.length > 2 ? Integer.parseInt(args[index++]) : DEFAULT_THREADS;
        m_width = args.length > 3 ? Integer.parseInt(args[index++]) : DEFAULT_WIDTH;
        m_height = args.length > 4 ? Integer.parseInt(args[index++]) : DEFAULT_HEIGHT;
        assert args.length == index;
    }

    private static void createTheOutputDirectory() {
        // java Life movie/frame 100 4  200 200
        final Path outputDir = m_filePath;
        if (!Files.exists(outputDir)) {
            try {
                // Files.createDirectory(outputDir.getParent());
                Files.createDirectories(outputDir.getParent()); // ask
            }
            catch (IOException ex) {
                throw new CreateFolderException(ex);
            }
        }
    }

    private static void initializeFirstGeneration() {
        final SecureRandom secureRandom = new SecureRandom();
        for (int row = 0; row < m_width; row++) {
            for (int col = 0; col < m_height; col++) {
                m_currentGeneration[row][col] = secureRandom.nextDouble() < INITIAL_POPULATION;
            }
        }
    }

    private static void simulateIterationsGenerations() {
        for (int generationNumber = 0; generationNumber < m_iterations; generationNumber++) {
            simulateOneGeneration();
            saveCurrentGenerationToFile(generationNumber);
            copyNextGenerationToCurrent();
        }
    }

    /*
    Any live cell with fewer than two live neighbours dies, as if by underpopulation.
    Any live cell with two or three live neighbours lives on to the next generation.
    Any live cell with more than three live neighbours dies, as if by overpopulation.
    */
    private static void simulateOneGeneration() {
        for (int row = 0; row < m_width; row++) {
            for (int col = 0; col < m_height; col++) {
                int numNeighbors = countLiveNeighbors(row, col);

                // Live cell
                if (m_currentGeneration[row][col]) {
                    // Any live cell with two or three live neighbours survives.
                    m_nextGeneration[row][col] = numNeighbors == 2 || numNeighbors == 3;
                }
                // Dead cell
                else {
                    // Any dead cell with three live neighbours becomes a live cell.
                    m_nextGeneration[row][col] = numNeighbors == 3;
                }
            }
        }
    }

    private static int countLiveNeighbors(final int row, final int col) {
        int count = 0;
       /* {-1, -1},
        {-1, 0},
        {-1, 1},
        {0, -1},
        {0, 1},
        {1, -1},
        {1, 0},
        {1, 1}};*/
        for (int[] offset : NEIGHBOR_WINDOW) {
            int neighborRow = row + offset[0];
            int neighborCol = col + offset[1];

            if (neighborRow >= 0 && neighborRow < m_width && // outside 'width' bound of the grid
                    neighborCol >= 0 && neighborCol < m_height && // outside 'height' bound of the grid
                    m_currentGeneration[neighborRow][neighborCol]) // now position is alive?
            {
                ++count;
            }
        }
        return count;
    }

    private static void copyNextGenerationToCurrent() {
        for (int row = 0; row < m_width; row++) {
            System.arraycopy(m_nextGeneration[row], 0, m_currentGeneration[row], 0, m_height);
        }
    }

    // https://stackoverflow.com/questions/30490471/how-do-i-save-a-bufferedimage-to-a-pgm-file
    private static void saveCurrentGenerationToFile(final int generation) {
        final StringBuilder stringBuilder = new StringBuilder();

        // write the PBM header
        stringBuilder.append("P1\n");
        stringBuilder.append(m_height).append(" ").append(m_width).append("\n");

        // write the grid data
        for (int row = 0; row < m_width; row++) {
            for (int col = 0; col < m_height; col++) {
                stringBuilder.append(m_currentGeneration[row][col] ? "1 " : "0 ");
            }
            stringBuilder.append("\n");
        }

        final Path outputFile = Paths.get(m_filePath.getParent().toString(),
                m_filePath.getFileName().toString() + generation + TYPE_FILE);
//        try {
//            Files.createFile(outputFile);
//        }
//        catch (IOException e) {
//            throw new RuntimeException(e);
//        }
        try {
            Files.write(outputFile, stringBuilder.toString().getBytes());
        }
        catch (IOException ex) {
            throw new WriteToFileException(ex);
        }
    }


}
