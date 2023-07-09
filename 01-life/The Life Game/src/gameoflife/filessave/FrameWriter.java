package gameoflife.filessave;

import gameoflife.model.Grid;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FrameWriter {
    private static final String DEFAULT_FILE_NAME = "generation_";
    public static final String ONE = "1 ";
    public static final String ZERO = "0 ";
    public static final String NEW_LINE = "\n";
    public static final String P_1 = "P1";

    private final Path m_filePath;
    private final String m_fileName;
    private final String m_typeFile;

    private int generation = 0;

    public FrameWriter(final Path filePath, final String typeFile) {
        m_filePath = filePath;
        m_typeFile = typeFile;
        m_fileName = !m_filePath.getFileName().toString().isEmpty() ? m_filePath.getFileName().toString() : DEFAULT_FILE_NAME;

    }

    public final void createTheOutputDirectory() {
        // java Life movie/frame 100 4  200 200
        if (!Files.exists(m_filePath)) {
            try {
                // Files.createDirectory(outputDir.getParent());
                Files.createDirectories(m_filePath);
            }
            catch (IOException ex) {
                throw new CreateFolderException(ex);
            }
        }
    }

    public final void saveCurrentGenerationToFile(final Grid<Boolean> currentGeneration) {
        var height = currentGeneration.getHeight();
        var width = currentGeneration.getWidth();
        final StringBuilder stringBuilder = new StringBuilder(height * width * 2 + 30);
        // we use this height * width * 2 + 30 tho not create new capacity


        // write the PBM header
        stringBuilder.append(P_1 + NEW_LINE);
        stringBuilder.append(height).append(" ").append(width).append(NEW_LINE);

        // write the grid data
        for (int row = 0; row < width; ++row) {
            for (int col = 0; col < height; ++col) {
                stringBuilder.append(currentGeneration.get(row, col) ? ONE : ZERO);
            }
            stringBuilder.append(NEW_LINE);
        }
        final Path outputFile = Paths.get(m_filePath.toString(),
                m_fileName + (generation++) + m_typeFile);
        try {
            Files.write(outputFile, stringBuilder.toString().getBytes());
        }
        catch (IOException ex) {
            throw new WriteToFileException(ex);
        }
    }

}
