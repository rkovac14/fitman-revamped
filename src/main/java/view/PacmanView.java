package view;

import common.*;
import game.*;

import java.awt.*;
import java.awt.image.BufferedImage;

import static runner.ShowRunner.animationSwitch;
import static runner.ShowRunner.textures;

/**
 * PacMan rendering
 */
public class PacmanView implements ComponentView {
    private final FieldView parent;
    private BufferedImage Texture = null;

    /**
     * PacMan rendering with texture switch
     * @param parent fieldView parent
     * @param m MazeObject
     */
    public PacmanView(FieldView parent, CommonMazeObject m) {
        this.parent = parent;

        if (m.getDirection() != null){
            if(animationSwitch){
                switch (m.getDirection()) {
                    case D -> this.Texture = textures.PacOpenD;
                    case L -> this.Texture = textures.PacOpenL;
                    case R -> this.Texture = textures.PacOpenR;
                    case U -> this.Texture = textures.PacOpenT;
                }
            }else{
                switch (m.getDirection()) {
                    case D -> this.Texture = textures.PacClosedD;
                    case L -> this.Texture = textures.PacClosedL;
                    case R -> this.Texture = textures.PacClosedR;
                    case U -> this.Texture = textures.PacClosedT;
                }
            }
        }else
            this.Texture = textures.PacOpenR;
    }

    /**
     * Paint the component
     * @param g graphics handle
     */
    public void paintComponent(Graphics g) {
        g.drawImage(Texture, 0, 0, this.parent.getWidth(),this.parent.getHeight(), this.parent);
    }
}
