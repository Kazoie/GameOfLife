import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.Random;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

public class GameOfLife extends JPanel {
    private int taurusSize = 100;
    private boolean[][] taurus = new boolean[taurusSize][taurusSize];
    private int cellSize = 10; // Taille de chaque cellule
    private Timer timer;

    public GameOfLife(int maxGeneration, int patternNumber) {
        initialize(); // Initialise le taurus (grille)
        randomCells(taurus, patternNumber); // Génère 5 motifs aléatoires sur la grille

        timer = new Timer(45, e -> {
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
        String[] patterns = {"Blinker", "Glider", "Block", "Beehive", "Toad"};
        Random r = new Random();

        for (int i = 0; i < numberOfPatterns; i++) {
            String pattern = patterns[r.nextInt(patterns.length)];
            int x = r.nextInt(taurus.length - 5); // Laisser de l'espace pour le motif
            int y = r.nextInt(taurus[0].length - 5);

            switch (pattern) {
                case "Blinker":
                    placeBlinker(taurus, x, y);
                    break;
                case "Glider":
                    placeGlider(taurus, x, y);
                    break;
                case "Block":
                    placeBlock(taurus, x, y);
                    break;
                case "Beehive":
                    placeBeehive(taurus, x, y);
                    break;
                case "Toad":
                    placeToad(taurus, x, y);
                    break;
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
                    g.setColor(Color.BLACK);
                } else {
                    g.setColor(Color.WHITE);
                }
                g.fillRect(x * cellSize, y * cellSize, cellSize, cellSize);
                g.setColor(Color.WHITE); //couleur bordure
                g.drawRect(x * cellSize, y * cellSize, cellSize, cellSize);
            }
        }
    }

    public static void main(String[] args) {
        new GameOfLife(100,35);
    }
}
