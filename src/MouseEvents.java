import WorldMatrix.Lifeforms.LifeType;
import WorldMatrix.Lifeforms.Lifeform;
import WorldMatrix.WorldMatrix;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MouseEvents implements MouseListener {
    private int cellSize;

    public MouseEvents(int cellSize)
    {
        this.cellSize = cellSize;
    }
    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        System.out.println(e.getX()/cellSize + " " + e.getY()/cellSize + " " + WorldMatrix.getInstance().getWorldUnit(e.getX()/cellSize, e.getY()/cellSize).getTerrain().getType() + " " + WorldMatrix.getInstance().getWorldUnit(e.getX()/cellSize, e.getY()/cellSize).getLifeform().getType());
        WorldMatrix.getInstance().getWorldUnit(e.getX()/cellSize,e.getY()/cellSize).setLifeform(new Lifeform(LifeType.GRASS, e.getX()/cellSize,e.getY()/cellSize));
    }

    @Override
    public void mouseReleased(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseEntered(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseExited(MouseEvent mouseEvent) {

    }
}
