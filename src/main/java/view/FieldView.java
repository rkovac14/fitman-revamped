package view;

import common.*;
import game.*;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.List;
import javax.swing.JPanel;
import java.awt.image.BufferedImage;

import static runner.ShowRunner.textures;
public class FieldView extends JPanel implements Observable.Observer {
    private final CommonField field;
    private final List<ComponentView> objects;
    private int changedField = 0;
    private BufferedImage Texture = null;

    /**
     * Renders a field
     * @param field what field should be rendered
     */
    public FieldView(CommonField field) {
        this.field = field;
        this.objects = new ArrayList<>();
        this.privateUpdate();
        field.addObserver(this);
    }

    /**
     * Component painting
     * @param g the <code>Graphics</code> object to protect
     */
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (Texture != null){
            g.drawImage(Texture, 0, 0, this.getWidth(),this.getHeight(), this);
        }
        try{
            this.objects.forEach((v) -> v.paintComponent(g));} catch (ConcurrentModificationException ignored) {System.out.println("Concurrent modification while painting components ...");}
        this.revalidate();
        this.repaint();

    }

    /**
     * Update component status
     */
    private void privateUpdate() {

        if (this.field.canMove()) {
            this.Texture = textures.FieldTexture;
            if(((PathField)this.field).hasKey()){
                this.Texture = textures.KeyTexture;
            }
            if(((PathField)this.field).isTarget()){
                this.Texture = textures.Gate;
            }
            if (!this.field.isEmpty()) {
                CommonMazeObject o = this.field.get();
                ComponentView v = o.isPacman() ? new PacmanView(this, this.field.get()) : new GhostView(this, this.field.get());
                this.objects.add(v);
            } else {
                this.objects.clear();
            }
        } else {
            this.Texture = textures.WallTexture;
        }

    }

    public final void update(Observable field) {
        ++this.changedField;
        this.privateUpdate();
    }

}
