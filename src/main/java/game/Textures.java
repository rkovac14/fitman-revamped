package game;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.util.Objects;

public class Textures {
    public BufferedImage FieldTexture = null;
    public BufferedImage WallTexture = null;
    public BufferedImage KeyTexture = null;
    public BufferedImage Gate = null;
    public BufferedImage PacOpenR = null;
    public BufferedImage PacOpenL = null;
    public BufferedImage PacOpenT = null;
    public BufferedImage PacOpenD = null;
    public BufferedImage PacClosedR = null;
    public BufferedImage PacClosedL = null;
    public BufferedImage PacClosedT = null;
    public BufferedImage PacClosedD = null;
    public BufferedImage Ghost1 = null;
    public BufferedImage Ghost2 = null;
    public BufferedImage Ghost3 = null;
    public BufferedImage Ghost4 = null;

    /**
     * Puts image textures into memory for faster access
     */
    public Textures(){
        try {
            this.FieldTexture = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/textures/GrassPixel.png")));
            this.WallTexture = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/textures/StonePixel.png")));
            this.KeyTexture = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/textures/Key.png")));
            this.Gate = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/textures/Gate.png")));
            this.PacOpenR = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/textures/PacmanR.png")));
            this.PacOpenL = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/textures/PacmanL.png")));
            this.PacOpenT = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/textures/PacmanT.png")));
            this.PacOpenD = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/textures/PacmanD.png")));
            this.PacClosedR = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/textures/PacmanClosedR.png")));
            this.PacClosedL = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/textures/PacmanClosedL.png")));
            this.PacClosedT = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/textures/PacmanClosedT.png")));
            this.PacClosedD = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/textures/PacmanClosedD.png")));
            this.Ghost1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/textures/Ghost1.png")));
            this.Ghost2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/textures/Ghost2.png")));
            this.Ghost3 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/textures/Ghost3.png")));
            this.Ghost4 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/textures/Ghost4.png")));
        }catch(Exception e) {
            System.out.println("Could not read a texture file!");
            e.printStackTrace();
        }
    }
}
