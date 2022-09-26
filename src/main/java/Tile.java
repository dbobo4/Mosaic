import java.awt.image.BufferedImage;

public class Tile {

    private BufferedImage image;
    private int x1;
    private int y1;
    private Color avgColor;

    public Tile(BufferedImage image, int x1, int y1) {
        this.image = image;
        this.x1 = x1;
        this.y1 = y1;
        this.avgColor = setAvgColor();
    }

    private Color setAvgColor() {
        int sumRed = 0;
        int sumGreen = 0;
        int sumBlue = 0;

        for (int x = 0; x < Main.TILE_WIDTH; x++) {
            for (int y = 0; y < Main.TILE_HEIGHT; y++) {
                int pixel = image.getRGB(x, y);
                sumRed += (pixel >> 16) & 0xff;
                sumGreen += (pixel >> 8) & 0xff;
                sumBlue += pixel & 0xff;
            }
        }
        int num = Main.TILE_WIDTH * Main.TILE_HEIGHT;
        return new Color(sumRed / num, sumGreen / num, sumBlue / num);
    }

    public double similarity(Tile comparedTile) {
        return Math.sqrt(Math.pow((this.getAvgColor().getRed() - comparedTile.getAvgColor().getRed()), 2) +
                Math.pow((this.getAvgColor().getGreen() - comparedTile.getAvgColor().getGreen()), 2) +
                Math.pow((this.getAvgColor().getBlue() - comparedTile.getAvgColor().getBlue()), 2));
    }

    public BufferedImage getImage() {
        return image;
    }

    public int getX1() {
        return x1;
    }

    public int getY1() {
        return y1;
    }

    public Color getAvgColor() {
        return avgColor;
    }
}
