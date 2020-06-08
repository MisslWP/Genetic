package WorldMatrix;

import WorldMatrix.Lifeforms.LifeType;
import WorldMatrix.Lifeforms.Lifeform;
import WorldMatrix.Terrain.TerrainCell;
import WorldMatrix.Terrain.TerrainType;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.TreeMap;
import java.util.TreeSet;

public class WorldMatrix
{
    public static TreeMap<Coordinates, WorldUnit> wMatrix;
    private static int[][] noiseMap;
    private static TerrainCell[][] terrainMap;
    private static Lifeform[][] lifeformMap;
    private int[] steps;
    private static WorldMatrix singleMatrix;

    private WorldMatrix(int widthInCell, int heightInCell, long seed)
    {
        noiseMap = new int[widthInCell][heightInCell];

        Perlin2D perlin = new Perlin2D(seed);
        for(int x = 0; x < widthInCell; x++) {
            for(int y = 0; y < heightInCell; y++) {
                float value = perlin.getNoise(x/100f,y/100f,5,0.4f) + 0.5f;
                noiseMap[x][y] = (int)(value * 255) & 255;
            }
        }

        terrainMap = new TerrainCell[widthInCell][heightInCell];

        steps = new int[TerrainType.values().length-1];
        ArrayList<TreeSet<Integer>> heightSets = new ArrayList<>();
        for (int i = 0; i < TerrainType.values().length-1; i++)
        {
            heightSets.add(i, new TreeSet<>());
        }

        for (int x = 0; x < widthInCell; ++x) {
            for (int y = 0; y < heightInCell; ++y) {
                if (x==0 || x==widthInCell-1 || y==0 || y==heightInCell-1)
                {
                    terrainMap[x][y] = new TerrainCell(TerrainType.BORDER);
                }
                else {
                    if (noiseMap[x][y] > 160) {
                        heightSets.get(0).add(noiseMap[x][y]);
                        terrainMap[x][y] = new TerrainCell(TerrainType.DEEP_WATER);
                    } else if (noiseMap[x][y] > 128) {
                        heightSets.get(1).add(noiseMap[x][y]);
                        terrainMap[x][y] = new TerrainCell(TerrainType.WATER);
                    } else if (noiseMap[x][y] > 122) {
                        heightSets.get(2).add(noiseMap[x][y]);
                        terrainMap[x][y] = new TerrainCell(TerrainType.SAND);
                    } else if (noiseMap[x][y] > 90) {
                        heightSets.get(3).add(noiseMap[x][y]);
                        terrainMap[x][y] = new TerrainCell(TerrainType.GRASS);
                    } else if (noiseMap[x][y] > 60) {
                        heightSets.get(4).add(noiseMap[x][y]);
                        terrainMap[x][y] = new TerrainCell(TerrainType.EARTH);
                    } else if (noiseMap[x][y] > 40) {
                        heightSets.get(5).add(noiseMap[x][y]);
                        terrainMap[x][y] = new TerrainCell(TerrainType.MOUNTAIN);
                    } else {
                        heightSets.get(6).add(noiseMap[x][y]);
                        terrainMap[x][y] = new TerrainCell(TerrainType.PEAK);
                    }
                }
            }
        }
        for (int i = 0; i < heightSets.size(); i++)
        {
            steps[i] = heightSets.get(i).last() - heightSets.get(i).first() + 1;
        }

        for (int x = 0; x < terrainMap.length; x++)
        {
            for (int y = 0; y < terrainMap[x].length; y++)
            {
                if (x!=0) terrainMap[x][y].left = terrainMap[x-1][y];
                if (x!=terrainMap.length-1) terrainMap[x][y].right = terrainMap[x+1][y];
                if (y!=0) terrainMap[x][y].up = terrainMap[x][y-1];
                if (y!=terrainMap[x].length-1) terrainMap[x][y].down = terrainMap[x][y+1];
            }
        }

        lifeformMap = new Lifeform[widthInCell][heightInCell];
        for (int x = 0; x < widthInCell; x++) {
            for (int y = 0; y < heightInCell; y++) {
                lifeformMap[x][y] = new Lifeform(LifeType.EMPTY,x,y);
            }
        }
        Lifeform.matrix = this;

            wMatrix =  new TreeMap<Coordinates, WorldUnit>(new Comparator<>() {
                @Override
                public int compare(Coordinates c1, Coordinates c2) {
                    if (Integer.compare(c1.getX(), c2.getX()) != 0) {
                        return Integer.compare(c1.getX(), c2.getX());
                    } else return Integer.compare(c1.getY(), c2.getY());
                }
            });
            for (int i = 0; i < widthInCell; i++) {
                for (int j = 0; j < heightInCell; j++) {
                    wMatrix.put(new Coordinates(i, j), new WorldUnit(lifeformMap[i][j], terrainMap[i][j]));
                }
            }

    }


    public static WorldMatrix getInstance(int x, int y, long seed) {
        if (wMatrix == null) {
            singleMatrix = new WorldMatrix(x, y, seed);
        }
        for (Coordinates c : wMatrix.keySet())
        {
            System.out.println(c.getX() + " " + c.getY());
        }
        return singleMatrix;

    }
    public static WorldMatrix getInstance() {
        if (wMatrix == null) {
            System.out.println("Матрица ещё не была создана");
        }
        return singleMatrix;

    }

    public WorldUnit getWorldUnit(int x, int y)
    {
        return wMatrix.get(new Coordinates(x, y));
    }

    public int[] getSteps() {
        return steps;
    }

    public TerrainCell[][] getTerrainMap() {
        return terrainMap;
    }

    public int[][] getNoiseMap() {
        return noiseMap;
    }

    public Lifeform[][] getLifeformMap() {
        return lifeformMap;
    }
}

class Coordinates {
    protected int x;
    protected int y;

    protected Coordinates (int x, int y)
    {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }


}


