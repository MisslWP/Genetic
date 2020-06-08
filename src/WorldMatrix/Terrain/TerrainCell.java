package WorldMatrix.Terrain;

public class TerrainCell
{
    private TerrainType type;

    public TerrainCell up;
    public TerrainCell down;
    public TerrainCell left;
    public TerrainCell right;
    private static TerrainCell
                               upBorder = new TerrainCell(TerrainType.BORDER),
                               downBorder = new TerrainCell(TerrainType.BORDER),
                               leftBorder = new TerrainCell(TerrainType.BORDER),
                               rightBorder = new TerrainCell(TerrainType.BORDER);

    public TerrainCell(TerrainType type)
    {
        this.type = type;
        this.up = upBorder;
        this.down = downBorder;
        this.left = leftBorder;
        this.right = rightBorder;
    }

    public void setType(TerrainType type) {
        this.type = type;
    }

    public TerrainType getType() {
        return type;
    }
    public boolean isBorder() {
        return type == TerrainType.BORDER;}

}
