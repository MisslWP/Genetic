import WorldMatrix.*;
import WorldMatrix.Lifeforms.LifeType;
import WorldMatrix.Lifeforms.Lifeform;
import WorldMatrix.Terrain.TerrainCell;
import WorldMatrix.Terrain.TerrainType;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.TreeSet;

public class MyMap extends JPanel implements ActionListener {
    private int widthInCell, heightInCell, cellSize;
    private WorldTime time = new WorldTime(0.1);
    private JFrame frame;

    private static WorldMatrix matrix;

    MyMap(int width, int height, int cellSize, long seed, JFrame frame) {

        this.frame = frame;
        setLayout(null);
        setPreferredSize(new Dimension(width, height));

        widthInCell = width / cellSize;
        heightInCell = height / cellSize;
        this.cellSize = cellSize;

        Timer timer = new Timer(100, this);
        frame.addKeyListener(new PauseListener(timer));
        frame.addMouseListener(new MouseEvents(cellSize));
        matrix = WorldMatrix.getInstance(widthInCell, heightInCell, seed);


        printInfo();

        timer.start();
    }

    private void printInfo()
    {

        System.out.println("Длины шагов для разных типов ландшафта:");
        for (int i = 0; i < matrix.getSteps().length; i++) {
            System.out.println(matrix.getSteps()[i]);
        }

        int deep=0, water=0, sand=0, grass=0, earth=0, mount=0, peak=0;
        for (TerrainCell[] cells : matrix.getTerrainMap()) {
            for (TerrainCell cell : cells) {
                if (cell.getType() == TerrainType.DEEP_WATER) deep++;
                else if (cell.getType() == TerrainType.WATER) water++;
                else if (cell.getType() == TerrainType.SAND) sand++;
                else if (cell.getType() == TerrainType.GRASS) grass++;
                else if (cell.getType() == TerrainType.EARTH) earth++;
                else if (cell.getType() == TerrainType.MOUNTAIN) mount++;
                else if (cell.getType() == TerrainType.PEAK) peak++;
            }
        }
        System.out.println("\nГлубина: " + ( ((double) deep) /10000) + "%");
        System.out.println("Вода: " + ( ((double) water) /10000) + "%");
        System.out.println("Песок: " + ( ((double) sand) /10000) + "%");
        System.out.println("Трава: " + ( ((double) grass) /10000) + "%");
        System.out.println("Земля: " + ( ((double) earth) /10000) + "%");
        System.out.println("Горы: " + ( ((double) mount) /10000) + "%");
        System.out.println("Вершины: " + ( ((double) peak) /10000) + "%");
    }



    public void paint(Graphics g)
    {
        paintTerrain(g);
        //paintNoize(g);
        //paintGrid(g);
        paintAnimals(g);
        //paintWorldTime(g);
    }
    private void paintGrid(Graphics g)
    {
        for (int i = cellSize*32; i < cellSize*widthInCell; i+= cellSize*32) {
            g.drawLine(i, 0, i, cellSize*heightInCell);
        }
        for (int i = cellSize*32; i < cellSize*heightInCell; i+= cellSize*32){
            g.drawLine(0, i, cellSize*widthInCell, i);
        }
    }

    private void paintNoize(Graphics g)
    {
        Graphics2D g2d = (Graphics2D)g;

        for(int x = 0; x < widthInCell; ++x) {
            for(int y = 0; y < heightInCell; ++y) {
                Rectangle2D cell = new Rectangle2D.Float(x * cellSize, y * cellSize,
                        cellSize, cellSize);
                g2d.setColor(new Color(matrix.getNoiseMap()[x][y],matrix.getNoiseMap()[x][y],matrix.getNoiseMap()[x][y]));

                g2d.fill(cell);
            }
        }
    }

    private void paintTerrain(Graphics g)
    {
        Graphics2D g2d = (Graphics2D)g;

        for(int x = 0; x < widthInCell; ++x) {
            for(int y = 0; y < heightInCell; ++y) {
                Rectangle2D cell = new Rectangle2D.Float(x * cellSize, y * cellSize,
                        cellSize, cellSize);
                /** from 0 150 200 to 10 125 164 with 92 только для красивой графики! Ландшафт определяется грубо
                 if (map[x][y] > 128) {
                 g2d.setColor(new Color((map[x][y]-129)*10/46, 150-((map[x][y]-129)*100/184), 200-((map[x][y]-129)*100/127)));
                 от 20 100 128 до 10 125 164
                 }*/
                if (matrix.getNoiseMap()[x][y] > 160) {
                    g2d.setColor(new Color(20, 100, 128));
                } else if (matrix.getNoiseMap()[x][y] > 128) {
                    g2d.setColor(new Color((matrix.getNoiseMap()[x][y]-129)*10/matrix.getSteps()[1],
                            150-((matrix.getNoiseMap()[x][y]-129)*25/matrix.getSteps()[1]),
                            200-((matrix.getNoiseMap()[x][y]-129)*36/matrix.getSteps()[1])));
                } else if (matrix.getNoiseMap()[x][y] > 122) {
                    g2d.setColor(new Color(250, 240, 40));
                } else if (matrix.getNoiseMap()[x][y] > 80) {
                    g2d.setColor(new Color(70 , 180, 80));
                } else if (matrix.getNoiseMap()[x][y] > 50) {
                    g2d.setColor(new Color(200 - ((matrix.getNoiseMap()[x][y]-51)*65/matrix.getSteps()[4]),
                            150 + ((matrix.getNoiseMap()[x][y]-51)*15/matrix.getSteps()[4]),
                            100 - ((matrix.getNoiseMap()[x][y]-51)*10/matrix.getSteps()[4])));
                } else if (matrix.getNoiseMap()[x][y] > 30) {
                    g2d.setColor(new Color(130 + ((matrix.getNoiseMap()[x][y]-31)*35/matrix.getSteps()[5]),
                            90 + ((matrix.getNoiseMap()[x][y]-31)*30/matrix.getSteps()[5]),
                            40 + ((matrix.getNoiseMap()[x][y]-31)*30/matrix.getSteps()[5])));
                } else {
                    g2d.setColor(new Color(255,255,255));
                }

                g2d.fill(cell);
            }
        }
    }

    private void paintAnimals(Graphics g)
    {
        Graphics2D g2d = (Graphics2D)g;

        for(int x = 0; x < widthInCell; ++x) {
            for(int y = 0; y < heightInCell; ++y) {
                Rectangle2D cell = new Rectangle2D.Float(x * cellSize, y * cellSize,
                        cellSize, cellSize);
                if(matrix.getWorldUnit(x,y).getLifeform().getType() == LifeType.GRASS)
                {
                    g2d.setColor(new Color(0,100,0));
                }
                else g2d.setColor(new Color(0,0,0,0));
                g2d.fill(cell);
            }
        }
    }
    private void paintWorldTime(Graphics g)
    {
        Graphics2D g2d = (Graphics2D)g;
        for(int x = 0; x < widthInCell; ++x) {
            for(int y = 0; y < heightInCell; ++y) {
                Rectangle2D cell = new Rectangle2D.Float(x * cellSize, y * cellSize,
                        cellSize, cellSize);

                if (time.getTimeMod()<0) {
                    g2d.setColor(new Color(0,0,0,  32 + (int) (128 * -time.getTimeMod())));
                }
                else g2d.setColor(new Color(0,0,0,0));
                g2d.fill(cell);
            }
        }
    }

    private void updateLife()
    {
        int size = Lifeform.currentLifeforms.size();
        for (int i = 0; i < size; i++ ) {
            Lifeform.currentLifeforms.get(i).onLiving();
        }
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        //System.out.println("updating...");
        updateLife();
        repaint();
        time.nextTime();

    }

}