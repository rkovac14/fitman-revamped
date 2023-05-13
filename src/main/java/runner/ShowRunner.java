package runner;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.*;

import common.*;
import game.*;
import game.Frame;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

public class ShowRunner {
    static int round = 0;
    public static boolean time = true;
    public static boolean pause = false;
    public static boolean step = false;
    public static boolean animationSwitch = false;
    public static final Textures textures = new Textures();
    public static boolean won = false;
    public static int play = 0;
    private static PacmanMaze maze;
    private static int numberOfGhosts;

    /**
     * Files with needed graphics
     */
    private static final ImageIcon defaultIcon = new ImageIcon(Objects.requireNonNull(ShowRunner.class.getResource("/textures/icon.png")));
    private static final ImageIcon replayIcon = new ImageIcon(Objects.requireNonNull(ShowRunner.class.getResource("/textures/replay.png")));
    private static final ImageIcon fitMan = new ImageIcon(Objects.requireNonNull(ShowRunner.class.getResource("/textures/fitman.png")));

    static final int PACSOUND = 1;
    static final int MENUSOUND = 2;
    static final int ENDSOUND = 3;
    static final int DEATHSOUND = 5;

    /**
     * Main method
     */
    public static void main(String[] args){
        ShowRunner.Run();
    }

    public static void Run() {
        String selectedFilePath = menuWindow();

        if (selectedFilePath == null)
            System.exit(0);

        MapReader reader;

        (new File(System.getProperty("user.dir") + "\\logs")).mkdir();

        if(play == 1){
            reader = new MapReader(selectedFilePath);
        }else{
            reader = new MapReader(selectedFilePath);
        }

        MazeConfigure cfg = new MazeConfigure();
        reader.ReadMap(cfg);
        maze = (PacmanMaze) cfg.createMaze();
        new Frame(maze);
        numberOfGhosts = maze.ghosts().size();

        if(play == 1){
            //TODO Maroš pls fix dis
            new Logger(System.getProperty("user.dir") + "\\logs\\log.txt");
            Logger.writeMap(maze);
        }else{
            //TODO Maroš pls fix dis
            new LogReader(selectedFilePath);
            LogReader.readLogFile(numberOfGhosts);
        }

        /*
          Periodically check for new movement from user and update ghosts
         */
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
        if(play == 1){
            executor.scheduleAtFixedRate(movement, 1000, 400, TimeUnit.MILLISECONDS);
        } else {
            executor.scheduleAtFixedRate(replay, 1000, 500, TimeUnit.MILLISECONDS);
        }

    }

    /**
     * Opens a menu window for a user to choose game mode and game file
     * @return Returns chosen file
     */
    private static String menuWindow(){
        Sounds.playSound(MENUSOUND);

        JFrame frame = new JFrame("FitMan");

        frame.setIconImage(defaultIcon.getImage());
        frame.setPreferredSize(new Dimension(500, 500));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel menuPanel = new JPanel();
        menuPanel.setBorder(new EmptyBorder(50, 20, 10, 20));
        menuPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.insets = new Insets(5,5,5,5);

        menuPanel.add(new JLabel(fitMan), gbc);


        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        Border line = new LineBorder(Color.BLACK,2);
        Border margin = new EmptyBorder(5, 15, 5, 15);
        Border compound = new CompoundBorder(line, margin);

        JPanel buttons = new JPanel(new GridBagLayout());
        JButton playButton = new JButton(" New game", defaultIcon);
        playButton.setContentAreaFilled(true);
        playButton.setFocusPainted(false);
        playButton.setBorder(compound);
        playButton.setRolloverEnabled(false);
        buttons.add(playButton, gbc);

        JButton replayButton = new JButton(" Replay previous game", replayIcon);
        replayButton.setContentAreaFilled(true);
        replayButton.setFocusPainted(false);
        replayButton.setBorder(compound);
        buttons.add(replayButton, gbc);

        playButton.addMouseListener(new MouseAdapter() {
            Color color = playButton.getForeground();
            public void mouseEntered(MouseEvent me) {
                color = playButton.getBackground();
                playButton.setBackground(Color.lightGray);
            }
            public void mouseExited(MouseEvent me) {
                playButton.setBackground(color);
            }
            public void mouseClicked(MouseEvent me) {
                play = 1;
            }
        });

        replayButton.addMouseListener(new MouseAdapter() {
            Color color = replayButton.getForeground();
            public void mouseEntered(MouseEvent me) {
                color = replayButton.getBackground();
                replayButton.setBackground(Color.lightGray); // change the color to green when mouse over a button
            }
            public void mouseExited(MouseEvent me) {
                replayButton.setBackground(color);
            }
            public void mouseClicked(MouseEvent me) {
                play = 2;
            }
        });
        gbc.weighty = 50;
        menuPanel.add(buttons, gbc);
        menuPanel.add(new JLabel("See README.txt for controls"));

        frame.add(menuPanel);
        frame.pack();

        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setLocation(dim.width/2-frame.getSize().width/2, dim.height/2-frame.getSize().height/2);

        frame.setVisible(true);

        //Wait for user selection ...
        while(play == 0){
            try {
                //noinspection BusyWait
                Thread.sleep(50);
            } catch(InterruptedException ignored) {
            }
        }

        frame.setVisible(false);
        frame.dispose();

        FileDialog fd = new FileDialog(new JFrame(), "Open map .txt file", FileDialog.LOAD);
        fd.setLocation(dim.width/2-frame.getSize().width/2, dim.height/2-frame.getSize().height/2);
        fd.setFile("*.txt");
        fd.setVisible(true);
        String filename = fd.getFile();
        if (filename == null)
            return null;
        else
            return fd.getDirectory()+filename;
    }
    static final Runnable replay = () -> {
        if(pause && !step)
            return;
        if (time) {
            if(round == LogReader.pacmanMovements.size())   //if the game ended
                return;

            //ghost movement
            int ghostCounter = 0;
            for (CommonMazeObject ghost : maze.ghosts()) {
                String direction = LogReader.ghostMovements.get(round * numberOfGhosts + ghostCounter);
                if (Objects.equals(direction, " ")) {
                    ghostCounter++;
                    continue;
                }
                ((PacmanObject) ghost).changeDirection(LogReader.directionParser(direction));
                ghost.move(null);
                ghostCounter++;
            }
            //pacman movement
            PacmanObject pacman = ((PacmanObject) maze.getPacman());
            String direction = LogReader.pacmanMovements.get(round);
            pacman.changeDirection(LogReader.directionParser(direction));
            animationSwitch = !animationSwitch;
            pacman.move(null);
            round++;
        } else {
            if (round == 0)
                return;
            round--;

            //ghost movement
            int ghostCounter = numberOfGhosts - 1;
            List<CommonMazeObject> ghostsReversed = maze.ghosts();
            Collections.reverse(ghostsReversed);
            for (CommonMazeObject ghost : ghostsReversed) {
                String direction = LogReader.ghostMovements.get(round * numberOfGhosts + ghostCounter);
                if (Objects.equals(direction, " ")) {
                    ghostCounter--;
                    continue;
                }
                ((PacmanObject) ghost).changeDirection((LogReader.reverseDirectionParser(direction)));
                ghost.move(null);
                ghostCounter--;
            }
            //pacman movement
            PacmanObject pacman = ((PacmanObject) maze.getPacman());
            String direction = LogReader.pacmanMovements.get(round);
            pacman.changeDirection(LogReader.reverseDirectionParser(direction));
            pacman.move(null);

            if(!LogReader.keyRounds.isEmpty() && LogReader.keyRounds.get(0) == round){
                PacmanMaze.map[LogReader.keyRow.get(0)][LogReader.keyCol.get(0)].putKey();
            }
        }
        step = false;
    };

    static final Runnable movement = () -> {
        Logger.write("Round Start\n");
        animationSwitch = !animationSwitch;
        //ghost movement
        for(CommonMazeObject ghost : maze.ghosts()){
                try {
                    ((PacmanObject)ghost).calculateDirection((PathField) maze.getPacman().getField());
                    CommonField.Direction dir = ghost.move(null);
                    Logger.write("Ghost moving ");
                    Logger.writeDirection(dir);
                }catch (NullPointerException e) {
                    Sounds.playSound(DEATHSOUND);
                    try {
                        Thread.sleep(2500);
                    } catch (InterruptedException ignored) {
                    }
                    System.exit(0);
                }

        }
        //pacman movement
            CommonField.Direction dir =  maze.getPacman().move(null);
            Logger.write("Pacman moving ");
            Logger.writeDirection(dir);
            Logger.write("Round End\n");


        if (won){
            Sounds.playSound(ENDSOUND);
            try {
                Thread.sleep(5000);
            } catch (InterruptedException ignored) {
            }
            System.exit(0);
        }else
            Sounds.playSound(PACSOUND);

        try{
            Logger.logFile.flush();
        } catch (IOException e){
            e.printStackTrace();
        }
    };
}
