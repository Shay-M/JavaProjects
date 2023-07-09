package gameoflife;

import gameoflife.rules.ClassicLifeRules;
import gameoflife.rules.GameRules;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GameOptions {
    private static final Logger m_logger = Logger.getLogger(Life.class.getCanonicalName());
    private static final String DEFAULT_FILE_NAME = "/generation_";
    private static final String DEFAULT_OUTPUT_FOLDER = "output";
    private static final int DEFAULT_ITERATIONS = 20;
    private static final int DEFAULT_THREADS = 2;
    private static final int DEFAULT_WIDTH = 900;
    private static final int DEFAULT_HEIGHT = 800;
    private static final double INITIAL_POPULATION = 0.4;
    public static final HashMap<String, GameRules<Boolean>> allGameRules = new HashMap<>();

    public final Path m_filePath;
    public final int m_iterations;
    public final int m_numThreads;
    public final int m_width;
    public final int m_height;
    public final double m_initialPopulation;
    public final GameRules<Boolean> m_gameRules;


    private GameOptions(final Path filePath, final int iterations, final int numThreads, final int width, final int height, final double initialPopulation, final GameRules<Boolean> gameRules) {
        allGameRules.put("classic", new ClassicLifeRules());
        m_filePath = filePath;
        m_iterations = iterations;
        m_numThreads = numThreads;
        m_width = width;
        m_height = height;
        m_initialPopulation = initialPopulation;
        m_gameRules = gameRules;
    }

    // movie/frame 50 4  300 300 0.6 Classic
    public static final GameOptions parseArgs(final String[] args) {
        var filePath = Path.of(DEFAULT_OUTPUT_FOLDER, DEFAULT_FILE_NAME);
        var iterations = DEFAULT_ITERATIONS;
        var numThreads = DEFAULT_THREADS;
        var width = DEFAULT_WIDTH;
        var height = DEFAULT_HEIGHT;
        var initialPopulation = INITIAL_POPULATION;
        GameRules gameRules = new ClassicLifeRules();

        if (args.length > 0) {
            int index = 0;
            try {
                filePath = Path.of(args[index++]);
                iterations = Integer.parseInt(args[index++]);
                numThreads = Integer.parseInt(args[index++]);
                width = Integer.parseInt(args[index++]);
                height = Integer.parseInt(args[index++]);
                initialPopulation = Double.parseDouble(args[index++]);
                gameRules = allGameRules.get(args[index++].toLowerCase());
                assert index == args.length;
            }
            catch (NumberFormatException | ArrayIndexOutOfBoundsException ex) {
                m_logger.log(Level.INFO, "Use Default.");
            }
        }
        return new GameOptions(
                filePath,
                iterations,
                numThreads,
                width,
                height,
                initialPopulation,
                gameRules);
    }


}



