package game;

import common.*;
import view.*;
import runner.ShowRunner;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
public class Frame extends JFrame implements KeyListener {
    private final PacmanObject pacman;

    /**
     * Generate a JFrame frame with contents
     * @param maze maze to generate a frame from
     */
    public Frame(CommonMaze maze){
        PacmanMaze maze1 = (PacmanMaze) maze;
        this.pacman = (PacmanObject) maze1.getPacman();
        this.setTitle("FitMan");
        ImageIcon img = new ImageIcon("data/textures/icon.png");
        this.setIconImage(img.getImage());
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setPreferredSize(new Dimension(maze.numCols()*60, maze.numRows()*60));
        this.setLayout(null);
        this.addKeyListener(this);
        this.setVisible(true);
        this.pack();

        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);

        int rows = maze1.numRows();
        int cols = maze1.numCols();
        GridLayout layout = new GridLayout(rows, cols);
        JPanel content = new JPanel(layout);

        for(int i = 0; i < rows; i++){
            for(int j = 0; j < cols; j++){
                FieldView field = new FieldView(maze1.getField(i, j));
                content.add(field);
            }
        }

        this.getContentPane().add(content, "Center");
        content.setBounds(0, 0, this.getWidth()-14, this.getHeight()-35);
        this.setVisible(true);


    }

    @Override
    public void keyTyped(KeyEvent e){
    }

    @Override
    public void keyPressed(KeyEvent e){
        if(e.getKeyChar() == 'w'){
            pacman.changeDirection(CommonField.Direction.U);
        }
        else if (e.getKeyChar() == 'a'){
            pacman.changeDirection(CommonField.Direction.L);
        }
        else if (e.getKeyChar() == 'd'){
            pacman.changeDirection(CommonField.Direction.R);
        }
        else if (e.getKeyChar() == 's'){
            pacman.changeDirection(CommonField.Direction.D);
        }
        else if (e.getKeyChar() == 'j'){
            ShowRunner.time = false;
        }
        else if (e.getKeyChar() == 'k'){
            ShowRunner.pause = !ShowRunner.pause;
        }
        else if (e.getKeyChar() == 'l'){
            ShowRunner.time = true;
        }
        else if (e.getKeyChar() == 'm'){
            ShowRunner.step = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e){

    }
}
