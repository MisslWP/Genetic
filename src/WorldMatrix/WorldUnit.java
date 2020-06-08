package WorldMatrix;

import WorldMatrix.Lifeforms.LifeType;
import WorldMatrix.Lifeforms.Lifeform;
import WorldMatrix.Terrain.TerrainCell;

public class WorldUnit
{
    protected Lifeform lifeform;
    protected TerrainCell terrainCell;

    protected WorldUnit(Lifeform lifeform, TerrainCell terrainCell) {
        this.lifeform = lifeform;
        this.terrainCell = terrainCell;
    }

    public TerrainCell getTerrain() {
        return terrainCell;
    }
    public Lifeform getLifeform() {
        return lifeform;
    }

    public void setLifeform(Lifeform lifeform) {
        this.lifeform = lifeform;
    }

    public void markEmpty() {
        this.lifeform.setType(LifeType.EMPTY);
    }
}
