package model;

public class PositionSurCarte {
    private int x;

    @Override
    public String toString() {
        return "PositionNavire{" +
                "x=" + (char)(x+'a') +
                ", y=" + y +
                '}';
    }

    public PositionSurCarte(int x, int y) {
        this.x = x;
        this.y = y;
    }
    public PositionSurCarte(PositionSurCarte positionSurCarte) {
        this.x = positionSurCarte.getX();
        this.y = positionSurCarte.getY();
    }

    private int y;

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public PositionSurCarte avancerHorizontalement(){
        y++;
        return this;
    }

    public PositionSurCarte avancerVerticalement(){
        x++;
        return this;
    }
    public PositionSurCarte reculerHorizontalement(){
        y--;
        return this;
    }

    public PositionSurCarte reculerVerticalement(){
        x--;
        return this;
    }
}
