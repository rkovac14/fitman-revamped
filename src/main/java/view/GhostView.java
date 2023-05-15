/**
 * Ghost component file
 * @author Richard Kovac (xkovac55)
 * @author Maros Sztolarik (xsztol00)
 */
package view;

import common.*;
import java.awt.*;
import java.awt.image.BufferedImage;

import static runner.ShowRunner.textures;

/**
 * Ghost rendering
 */
public class GhostView implements ComponentView {
    private final FieldView parent;
    private BufferedImage Texture = null;

    /**
     * Ghost rendering with a random color
     * @param parent fieldView parent
     * @param m MazeObject
     */
    public GhostView(FieldView parent, CommonMazeObject m) {
        this.parent = parent;
        switch (m.getColorID()) {
            case 1 -> this.Texture = textures.Ghost1;
            case 2 -> this.Texture = textures.Ghost2;
            case 3 -> this.Texture = textures.Ghost3;
            case 4 -> this.Texture = textures.Ghost4;
        }
    }

    /**
     * Paint the component
     * @param g graphics handle
     */
    public void paintComponent(Graphics g) {
        g.drawImage(Texture, 0, 0, this.parent.getWidth(),this.parent.getHeight(), this.parent);
    }
}
