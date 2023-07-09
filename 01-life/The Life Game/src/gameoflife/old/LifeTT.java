//package gameoflife;
//
//import gameoflife.filessave.FrameWriter;
//import gameoflife.model.Grid;
//import gameoflife.rules.GameRules;
//
//import java.security.SecureRandom;
//import java.util.ArrayList;
//import java.util.concurrent.BrokenBarrierException;
//import java.util.concurrent.CyclicBarrier;
//
///*
//simulate a game of life and save each generation as a pbm file.
//simulation will be on a grid of W * H cells.
//computation will be done using n threads ( by default n=2 )
//the threads will be created once for all the life time of the simulation.
//( do not create threads for each iteration ).
//each iteration will result in a generation that we will save to a file named : life.001.pbm
//with a running number for the frame sequence. The file will be formatted according to the format specified in https://en.wikipedia.org/wiki/Netpbm#PBM_example
//the program shall take the following as arguments:
//
//java Life filename iters threads   width height
//
//filename: base file name for thee frames
//iter: number of frames to create, default to 20
//threads: number of threads to use, default to 2
//width: width of the frame, default to 900
//height: height of the frame, default to 800
//*/
//
//public final class LifeTT {
//    private final int m_width;
//    private final int m_height;
//    private final int m_iterations;
//    private final int m_numThreads;
//    private final double m_initialPopulation;
//
//    private Grid<Boolean> m_currentGenerationGrid;
//    private Grid<Boolean> m_nextGenerationGrid;
//    private final FrameWriter m_frameWriter;
//    private final GameRules<Boolean> m_gameRules;
//
//
//    public LifeTT(final GameRules<Boolean> gameRules, final FrameWriter frameWriter, final int width, final int height, final int iterations, final int numThreads, final double initialPopulation) {
//        m_gameRules = gameRules;
//        m_frameWriter = frameWriter;
//        m_width = width;
//        m_height = height;
//        m_iterations = iterations;
//        m_numThreads = numThreads;
//        m_initialPopulation = initialPopulation;
//    }
//
//    public void start() {
//        m_frameWriter.createTheOutputDirectory();
//
//        final SecureRandom secureRandom = new SecureRandom();
//        m_currentGenerationGrid = new Grid<>(m_height, m_width,
//                () -> secureRandom.nextDouble() > m_initialPopulation);
//
//        m_nextGenerationGrid = new Grid<>(m_height, m_width,
//                () -> Boolean.FALSE);
//
//        simulateIterationsGenerations();
//    }
//
//    private void simulateIterationsGenerations() {
////
//        final var threads = new ArrayList<Thread>();
//        final int sizeGridThread = m_width / m_numThreads;
//        final var cyclicBarrier = new CyclicBarrier(m_numThreads);
//        for (int i = 0; i < m_numThreads; ++i) {
//            final int startRow = i * sizeGridThread;
//            final int endRow;
//            if (i == m_numThreads - 1) {
//                // last thread may have more rows to handle.
//                endRow = m_width;
//            }
//            else {
//                endRow = startRow + sizeGridThread;
//            }
//            // new Thread(() -> simulateSegmentOfGrid(startRow, endRow, cyclicBarrier)).start(); // todo not good!!
//            final var thread = new Thread(() -> simulateSegmentOfGrid(startRow, endRow, cyclicBarrier));
//            threads.add(thread);
//            thread.start();
//        }
//
//
//        for (int generationNumber = 0; generationNumber < m_iterations; ++generationNumber) {
//            simulateFrameGeneration();
//
//            // save to file
//            // m_frameWriter.saveCurrentGenerationToFile(generationNumber, m_currentGenerationGrid);
//
//            // copyNextGenerationToCurrent();
//            var temp = m_currentGenerationGrid;
//            m_currentGenerationGrid = m_nextGenerationGrid;
//            m_nextGenerationGrid = temp;
//        }
//    }
//
//    // https://www.javatpoint.com/java-cyclicbarrier
//    private void simulateFrameGeneration() {
//        //  Executor  << not to use
//
//        final int sizeGridThread = m_width / m_numThreads;
//        final var cyclicBarrier = new CyclicBarrier(m_numThreads);
//
//        for (int i = 0; i < m_numThreads; ++i) {
//            final int startRow = i * sizeGridThread;
//            final int endRow;
//            if (i == m_numThreads - 1) {
//                // last thread may have more rows to handle.
//                endRow = m_width;
//            }
//            else {
//                endRow = startRow + sizeGridThread;
//            }
//            // new Thread(() -> simulateSegmentOfGrid(startRow, endRow, cyclicBarrier)).start(); // todo not good!!
//            final var thread = new Thread(() -> simulateSegmentOfGrid(startRow, endRow, cyclicBarrier));
//            threads.add(thread);
//            thread.start();
//        }
//
////       for (Thread thread : threads) {
////            thread.join();
////        }
//    }
//
//    // m_gameRules
//    private void simulateSegmentOfGrid(final int startRow, final int endRow, final CyclicBarrier cyclicBarrier) {
//        for (int row = startRow; row < endRow; row++) {
//            for (int col = 0; col < m_height; col++) {
//                // do it var , it obj?
//                final Boolean isLive = m_gameRules.nextStage(row, col, m_currentGenerationGrid);
//                m_nextGenerationGrid.set(row, col, isLive);
//            }
//        }
//        try {
//            // wait all threads to finish
//            cyclicBarrier.await();
//        }
//        catch (InterruptedException | BrokenBarrierException ex) {
//            throw new CyclicBarrierException(ex);
//        }
//    }
//
////    private void copyNextGenerationToCurrent() {
////        for (int row = 0; row < m_width; ++row) {
////            System.arraycopy(m_nextGeneration.getRow(row), 0, m_currentGeneration.getRow(row), 0, m_height);
////        }
////    }
//
//}
