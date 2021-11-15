package sample;

public class Point {
    int x;
    int y;
    int status;
    int nID;
    final static int UNUSED   = 0;


    public Point(int i, int j, int pID) {
        x = i;
        y = j;
        nID = pID;
        status = UNUSED;
    }

    public int getPointX(){
        return this.x;
    }
    public int getPointY(){
        return this.y;
    }

    public int setXY(int xv, int yv){
        this.x = xv;
        this.y = yv;
        return(0);
    }

}