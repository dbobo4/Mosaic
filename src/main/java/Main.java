import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class Main {

    public static final String PATH_OF_MAIN_PICTURE = "D:\\Masterfield\\Projects\\SideLearning\\MyProjects\\Mosaic\\pictures\\red_apple.jpg";
    public static final String PATH_OF_TILES_FOLDER = "D:\\Masterfield\\Projects\\SideLearning\\MyProjects\\Mosaic\\pictures\\mosaic";
    public static BufferedImage MAIN_IMAGE;
    public static int MAIN_IMAGE_HEIGHT = 0;
    public static int MAIN_IMAGE_WIDTH = 0;
    public static int TILE_WIDTH = 0;
    public static int TILE_HEIGHT = 0;
    public static final int SCALE_FACTOR = 100;

    static {
        try {
            MAIN_IMAGE = ImageIO.read(new File(PATH_OF_MAIN_PICTURE));
            MAIN_IMAGE_HEIGHT = MAIN_IMAGE.getHeight();
            MAIN_IMAGE_WIDTH = MAIN_IMAGE.getWidth();
            TILE_WIDTH = MAIN_IMAGE_WIDTH / SCALE_FACTOR;
            TILE_HEIGHT = MAIN_IMAGE_HEIGHT / SCALE_FACTOR;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        double start = System.currentTimeMillis();
        new MyFrame();
        double end = System.currentTimeMillis();
        double time = end-start;
        System.out.println(time);
    }

    public static Tile[] getResultTiles() {
        BufferedImage[] resizedTiles = getResizedMosaicTiles(PATH_OF_TILES_FOLDER, TILE_WIDTH, TILE_HEIGHT);
        Tile[] mosaicTiles = createMosaicTiles(resizedTiles);
        Tile[] mainTiles = createMainTiles(MAIN_IMAGE, SCALE_FACTOR, TILE_WIDTH, TILE_HEIGHT);
        return compareTiles(mainTiles, mosaicTiles);
    }

    public static Tile[] compareTiles(Tile[] mainTiles, Tile[] mosaicTiles) {
        Tile[] comparedTiles = new Tile[mainTiles.length];
        for (int i = 0; i < mainTiles.length; i++) {
            comparedTiles[i] = mostSimilar(mainTiles[i], mosaicTiles);
        }
        return comparedTiles;
    }

    public static Tile mostSimilar(Tile mainTile, Tile[] mosaicTiles) {
        Tile mostSimilar = null;
        double similarity = Double.MAX_VALUE;
        for (Tile mosaicTile : mosaicTiles) {
            double currentSimilarity = mosaicTile.similarity(mainTile);
            if (currentSimilarity < similarity) {
                mostSimilar = mosaicTile;
                similarity = currentSimilarity;
            }
        }
        assert mostSimilar != null;
        return new Tile(mostSimilar.getImage(), mainTile.getX1(), mainTile.getY1());
    }

    public static BufferedImage[] getResizedMosaicTiles(String pathOfMosaicFiles, int width, int height) {
        int numberOfMosaicImages = Objects.requireNonNull(new File(pathOfMosaicFiles).listFiles()).length;
        BufferedImage[] bufferedImages = new BufferedImage[numberOfMosaicImages];
        File[] listOfImages = new File(pathOfMosaicFiles).listFiles();
        for (int i = 0; i < numberOfMosaicImages; i++) {
            try {
                assert listOfImages != null;
                BufferedImage currentImage = ImageIO.read(listOfImages[i]);
                bufferedImages[i] = resizeImage(currentImage, width, height);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return bufferedImages;
    }

    public static Tile[] createMainTiles(BufferedImage mainImage, int scl, int width, int height) {
        Tile[] tiles = new Tile[scl * scl];
        int index = 0;
        for (int i = 0; i < scl; i++) {
            for (int j = 0; j < scl; j++) {
                int currentX1 = j * width;
                int currentY1 = i * height;
                BufferedImage currentSubimage = mainImage.getSubimage(currentX1, currentY1, width, height);
                tiles[index] = new Tile(currentSubimage, currentX1, currentY1);
                index++;
            }
        }
        return tiles;
    }

    public static Tile[] createMosaicTiles(BufferedImage[] resizedMosaicTiles) {
        Tile[] tiles = new Tile[resizedMosaicTiles.length];
        for (int i = 0; i < resizedMosaicTiles.length; i++) {
            BufferedImage currentSubimage = resizedMosaicTiles[i];
            tiles[i] = new Tile(currentSubimage, 0, 0);
        }
        return tiles;
    }

    public static BufferedImage resizeImage(BufferedImage originalImage, int targetWidth, int targetHeight) {
        BufferedImage resizedImage = new BufferedImage(targetWidth, targetHeight, BufferedImage.SCALE_FAST);
        Graphics2D graphics2D = resizedImage.createGraphics();
        graphics2D.drawImage(originalImage, 0, 0, targetWidth, targetHeight, null);
        graphics2D.dispose();
        return resizedImage;
    }
}
