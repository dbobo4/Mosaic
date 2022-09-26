
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class MyPanel extends JPanel {

    public MyPanel() {
        this.setPreferredSize(new Dimension(Main.MAIN_IMAGE_WIDTH, Main.MAIN_IMAGE_HEIGHT));
    }

    public void paint(Graphics g) {
        Graphics2D g2D = (Graphics2D) g;
        Tile[] comparedTiles = Main.getResultTiles();
        for (int i = 0; i < comparedTiles.length; i++) {
            BufferedImage currentImage = comparedTiles[i].getImage();
            g2D.drawImage(currentImage, comparedTiles[i].getX1(), comparedTiles[i].getY1(), null);
        }
    }


}
