package WorldMatrix.Lifeforms;

import WorldMatrix.Terrain.TerrainCell;
import WorldMatrix.Terrain.TerrainType;
import WorldMatrix.WorldMatrix;

import java.util.ArrayList;

public class Lifeform
{
    public static WorldMatrix matrix;
    public static ArrayList<Lifeform> currentLifeforms = new ArrayList<>();
    private LifeType type;
    public int x,y;

    private Lifeform(LifeType type)
    {
        this.type = type;
        currentLifeforms.add(this);
    }

    public Lifeform(LifeType type, int x, int y)
    {
        this(type);
        this.x = x;
        this.y = y;
    }

    private void Die()
    {
        this.type = LifeType.EMPTY;
        currentLifeforms.remove(this);
    }
    private Lifeform(Lifeform lifeform, int x, int y) {
        this(lifeform.getType(), x, y);
    }

    public void goUp()
    {

    }
    public void goLeft()
    {

    }
    public void goRight()
    {

    }
    public void goDown()
    {

    }

    public LifeType getType() {
        return type;
    }

    public void setType(LifeType type) {
        this.type = type;
    }

    public void onLiving() {
        if (type == LifeType.GRASS) {
            if (isGoodForGrass(this.x-1, this.y)) {
                matrix.getWorldUnit(this.x-1, this.y).setLifeform(new Lifeform(this, this.x-1, this.y));
            }
            if (isGoodForGrass(this.x+1, this.y)) {
                matrix.getWorldUnit(this.x+1, this.y).setLifeform(new Lifeform(this, this.x+1, this.y));
            }
            if (isGoodForGrass(this.x, this.y-1)) {
                matrix.getWorldUnit(this.x, this.y-1).setLifeform(new Lifeform(this, this.x, this.y-1));
            }
            if (isGoodForGrass(this.x, this.y+1)) {
                matrix.getWorldUnit(this.x, this.y+1).setLifeform(new Lifeform(this, this.x, this.y+1));
            }
        }
    }

    private boolean isGoodForGrass(int x, int y)
    {
        return !matrix.getWorldUnit(x, y).getTerrain().isBorder() && matrix.getWorldUnit(x, y).getLifeform().getType().equals(LifeType.EMPTY) && (matrix.getWorldUnit(x, y).getTerrain().getType() == TerrainType.GRASS );
    }
}
