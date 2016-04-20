package org.elsys.valiolucho.drawer;

public abstract class Drawer {

    Drawer() {

    }

    public abstract void draw();
    public abstract void setColor(int color);
    public abstract void setColors(int[] colors);
    public abstract void setDescription(String description);
    public abstract void animateX(int duration);
    public abstract void animateY(int duration);
    public abstract void animateXY(int xDuration, int yDuration);
}
