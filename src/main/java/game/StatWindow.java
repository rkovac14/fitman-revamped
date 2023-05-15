package game;

import runner.ShowRunner;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.Objects;

public class StatWindow {
    JFrame stats = new JFrame("Stats");
    JLabel stat1label = new JLabel();
    JLabel stat2label = new JLabel();
    private static final ImageIcon defaultIcon = new ImageIcon(Objects.requireNonNull(ShowRunner.class.getResource("/textures/icon.png")));
    public StatWindow (){
        //Configure stats window
        this.stats.setIconImage(defaultIcon.getImage());
        this.stats.setPreferredSize(new Dimension(300, 150));
        this.stats.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel statPanel = new JPanel();
        statPanel.setBorder(new EmptyBorder(20, 20, 10, 20));
        statPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.insets = new Insets(5,5,5,5);
        statPanel.add(stat1label, gbc);
        statPanel.add(stat2label, gbc);
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        this.stats.add(statPanel);
        this.stats.pack();
        this.stats.setVisible(true);
    }

    public void updateWindow(String st1, String st2){
        stat1label.setText(st1);
        stat2label.setText(st2);
        //this.stats.setVisible(false);
        this.stats.invalidate();
        this.stats.validate();
        this.stats.repaint();
        //this.stats.setVisible(true);
    }
}
