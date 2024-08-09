import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.Random;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

public class GameOfLife extends JPanel {
    private int taurusSize = 300;
    private boolean[][] taurus = new boolean[taurusSize][taurusSize];
    private int cellSize = 4; // Taille de chaque cellule
    private Timer timer;

    public GameOfLife(int patternNumber) {
        initialize(); // Initialise le taurus (grille)
        randomCells(taurus, patternNumber); // Génère 5 motifs aléatoires sur la grille

        timer = new Timer(30, e -> {
            step();
            repaint();
        });
        timer.start();

        JFrame frame = new JFrame("Game of Life");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(taurus.length * cellSize, taurus[0].length * cellSize);
        frame.add(this);
        frame.setVisible(true);
    }

    public void step() {
        boolean[][] nextGeneration = new boolean[taurusSize][taurusSize];
        for (int x = 0; x < taurus.length; x++) {
            for (int y = 0; y < taurus[0].length; y++) {
                if (taurus[x][y]) {
                    nextGeneration[x][y] = applyRulesForAliveCell(x, y);
                } else {
                    nextGeneration[x][y] = applyRulesForDeadCell(x, y);
                }
            }
        }
        taurus = nextGeneration;
    }

    public void initialize() {
        for (int i = 0; i < taurus.length; i++) {
            for (int j = 0; j < taurus[0].length; j++) {
                taurus[i][j] = false; // Toutes les cellules sont mortes au départ
            }
        }
    }


    public void randomCells(boolean[][] taurus, int numberOfPatterns) {
        String[] patterns = {"Blinker", "Glider", "Block", "Beehive", "Toad", "Loaf", "Boat", "LWSS", "Pulsar", "Pentadecathlon", "Glider Gun"};
        Random r = new Random();

        for (int i = 0; i < numberOfPatterns; i++) {
            String pattern = patterns[r.nextInt(patterns.length)];

            int x = 0, y = 0;

            switch (pattern) {
                case "Blinker":
                    x = r.nextInt(taurus.length - 1);
                    y = r.nextInt(taurus[0].length - 2);
                    placeBlinker(taurus, x, y);
                    break;
                case "Glider":
                    x = r.nextInt(taurus.length - 2);
                    y = r.nextInt(taurus[0].length - 2);
                    placeGlider(taurus, x, y);
                    break;
                case "Block":
                    x = r.nextInt(taurus.length - 1);
                    y = r.nextInt(taurus[0].length - 1);
                    placeBlock(taurus, x, y);
                    break;
                case "Beehive":
                    x = r.nextInt(taurus.length - 2);
                    y = r.nextInt(taurus[0].length - 3);
                    placeBeehive(taurus, x, y);
                    break;
                case "Toad":
                    x = r.nextInt(taurus.length - 1);
                    y = r.nextInt(taurus[0].length - 3);
                    placeToad(taurus, x, y);
                    break;
                case "Loaf":
                    x = r.nextInt(taurus.length - 3);
                    y = r.nextInt(taurus[0].length - 3);
                    placeLoaf(taurus, x, y);
                    break;
                case "Boat":
                    x = r.nextInt(taurus.length - 2);
                    y = r.nextInt(taurus[0].length - 2);
                    placeBoat(taurus, x, y);
                    break;
                case "LWSS":
                    x = r.nextInt(taurus.length - 4);
                    y = r.nextInt(taurus[0].length - 5);
                    placeLWSS(taurus, x, y);
                    break;
                case "Pulsar":
                    x = r.nextInt(taurus.length - 12);
                    y = r.nextInt(taurus[0].length - 12);
                    placePulsar(taurus, x, y);
                    break;
                case "Pentadecathlon":
                    x = r.nextInt(taurus.length - 8);
                    y = r.nextInt(taurus[0].length - 4);
                    placePentadecathlon(taurus, x, y);
                    break;
                case "Glider Gun":
                    x = r.nextInt(taurus.length - 9);
                    y = r.nextInt(taurus[0].length - 36);
                    placeGliderGun(taurus, x, y);
                    break;
            }
        }
    }


    public void placeGliderGun(boolean[][] taurus, int x, int y) {
        //9*36 needed
        int[][] gliderGunPattern = {
                {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,1,0,0,0,0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0,0,0,0,0,1,1,0,0,0,0,0,0,1,1,0,0,0,0,0,0,0,0,0,0,0,0,1,1},
                {0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,1,0,0,0,0,1,1,0,0,0,0,0,0,0,0,0,0,0,0,1,1},
                {1,1,0,0,0,0,0,0,0,0,1,0,0,0,0,0,1,0,0,0,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
                {1,1,0,0,0,0,0,0,0,0,1,0,0,0,1,0,1,1,0,0,0,0,1,0,1,0,0,0,0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,1,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0,0,0,0,0,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}
        };


        for (int i = 0; i < gliderGunPattern.length; i++) {
            for (int j = 0; j < gliderGunPattern[0].length; j++) {
                taurus[x + i][y + j] = gliderGunPattern[i][j] == 1;
            }
        }
    }

    public void placeLoaf(boolean[][] taurus, int x, int y) {
        taurus[x][y + 1] = true;
        taurus[x][y + 2] = true;
        taurus[x + 1][y] = true;
        taurus[x + 1][y + 3] = true;
        taurus[x + 2][y + 1] = true;
        taurus[x + 2][y + 3] = true;
        taurus[x + 3][y + 2] = true;
    }

    public void placeBoat(boolean[][] taurus, int x, int y) {
        taurus[x][y] = true;
        taurus[x][y + 1] = true;
        taurus[x + 1][y] = true;
        taurus[x + 1][y + 2] = true;
        taurus[x + 2][y + 1] = true;
    }

    public void placeLWSS(boolean[][] taurus, int x, int y) {
        taurus[x][y + 1] = true;
        taurus[x][y + 4] = true;
        taurus[x + 1][y] = true;
        taurus[x + 1][y + 4] = true;
        taurus[x + 2][y + 4] = true;
        taurus[x + 3][y] = true;
        taurus[x + 3][y + 3] = true;
        taurus[x + 3][y + 4] = true;
        taurus[x + 4][y + 1] = true;
        taurus[x + 4][y + 2] = true;
        taurus[x + 4][y + 3] = true;
    }

    public void placePulsar(boolean[][] taurus, int x, int y) {
        for (int i = 0; i < 3; i++) {
            taurus[x + i + 2][y] = true;
            taurus[x + i + 2][y + 5] = true;
            taurus[x + i + 2][y + 7] = true;
            taurus[x + i + 2][y + 12] = true;

            taurus[x][y + i + 2] = true;
            taurus[x + 5][y + i + 2] = true;
            taurus[x + 7][y + i + 2] = true;
            taurus[x + 12][y + i + 2] = true;

            taurus[x + i + 2][y + 6] = true;
            taurus[x + i + 2][y + 11] = true;
            taurus[x + 6][y + i + 2] = true;
            taurus[x + 11][y + i + 2] = true;
        }
    }

    public void placePentadecathlon(boolean[][] taurus, int x, int y) {
        for (int i = 0; i < 8; i++) {
            taurus[x + i][y + 1] = true;
            taurus[x + i][y + 3] = true;
            if (i % 2 == 0) {
                taurus[x + i][y] = true;
                taurus[x + i][y + 4] = true;
            }
        }
    }

    public void placeBlinker(boolean[][] taurus, int x, int y) {
        taurus[x][y] = true;
        taurus[x][y + 1] = true;
        taurus[x][y + 2] = true;
    }

    public void placeGlider(boolean[][] taurus, int x, int y) {
        taurus[x][y + 1] = true;
        taurus[x + 1][y + 2] = true;
        taurus[x + 2][y] = true;
        taurus[x + 2][y + 1] = true;
        taurus[x + 2][y + 2] = true;
    }

    public void placeBlock(boolean[][] taurus, int x, int y) {
        taurus[x][y] = true;
        taurus[x][y + 1] = true;
        taurus[x + 1][y] = true;
        taurus[x + 1][y + 1] = true;
    }

    public void placeBeehive(boolean[][] taurus, int x, int y) {
        taurus[x][y + 1] = true;
        taurus[x][y + 2] = true;
        taurus[x + 1][y] = true;
        taurus[x + 1][y + 3] = true;
        taurus[x + 2][y + 1] = true;
        taurus[x + 2][y + 2] = true;
    }

    public void placeToad(boolean[][] taurus, int x, int y) {
        taurus[x][y + 1] = true;
        taurus[x][y + 2] = true;
        taurus[x][y + 3] = true;
        taurus[x + 1][y] = true;
        taurus[x + 1][y + 1] = true;
        taurus[x + 1][y + 2] = true;
    }

    public boolean applyRulesForAliveCell(int x, int y) {
        int aliveNeighbors = countAliveNeighbors(x, y);
        return aliveNeighbors == 2 || aliveNeighbors == 3; // Règle : Survit si 2 ou 3 voisins vivants
    }

    public boolean applyRulesForDeadCell(int x, int y) {
        int aliveNeighbors = countAliveNeighbors(x, y);
        return aliveNeighbors == 3; // Règle : Revient à la vie si exactement 3 voisins vivants
    }

    public int countAliveNeighbors(int x, int y) {
        int count = 0;
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (i == 0 && j == 0) {
                    continue; // Ne pas compter la cellule elle-même
                }
                int newX = (x + i + taurus.length) % taurus.length;
                int newY = (y + j + taurus[0].length) % taurus[0].length;
                if (taurus[newX][newY]) {
                    count++;
                }
            }
        }
        return count;
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(taurus.length * cellSize, taurus[0].length * cellSize);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (int x = 0; x < taurus.length; x++) {
            for (int y = 0; y < taurus[0].length; y++) {
                if (taurus[x][y]) {
                    g.setColor(Color.WHITE);
                } else {
                    g.setColor(Color.DARK_GRAY);
                }
                g.fillRect(x * cellSize, y * cellSize, cellSize, cellSize);
                g.setColor(Color.DARK_GRAY); //couleur bordure
                g.drawRect(x * cellSize, y * cellSize, cellSize, cellSize);
            }
        }
    }

    public static void main(String[] args) {
        new GameOfLife(15 );
    }
}
