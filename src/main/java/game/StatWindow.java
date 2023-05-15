package game;

import runner.ShowRunner;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.Objects;

public class StatWindow {
    JFrame stats = new JFrame("Stats");
    JPanel statPanel = new JPanel();
    private static final ImageIcon defaultIcon = new ImageIcon(Objects.requireNonNull(ShowRunner.class.getResource("/textures/icon.png")));
    String stat1;
    String stat2;
    public StatWindow (){
        //Configure stats window
        this.stats.setIconImage(defaultIcon.getImage());
        this.stats.setPreferredSize(new Dimension(300, 100));
        this.stats.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.statPanel.setBorder(new EmptyBorder(50, 20, 10, 20));
        this.statPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.insets = new Insets(5,5,5,5);
        this.statPanel.add(new JLabel(stat1), gbc);
        this.statPanel.add(new JLabel(stat2), gbc);
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        this.stats.add(statPanel);
        this.stats.pack();
        this.stats.setVisible(true);
    }

    public void updateWindow(String st1, String st2){
        this.stat1 = st1;
        this.stat2 = st2;
        System.out.println(this.stat1);
        System.out.println(this.stat2);
        this.statPanel.setVisible(false);
        this.statPanel.invalidate();
        this.statPanel.validate();
        this.statPanel.repaint();
        this.statPanel.setVisible(true);
    }
}
