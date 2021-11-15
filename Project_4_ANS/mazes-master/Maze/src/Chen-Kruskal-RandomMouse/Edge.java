package sample;

public class Edge {
    int id;
    int numEdge;
    int fx;
    int fy;
    int tx;
    int ty;
    int nIDA;
    int nIDB;
    int edgeType;
    int status;

    final static int EDGEUP   = 1;

    public Edge(int sx, int sy, int ex, int ey, int nid, int eid, int eType, int rows, int cols) {
        fx = sx;
        fy = sy;
        tx = ex;
        ty = ey;
        id = eid;

        status = EDGEUP;
        edgeType = eType;
        if(edgeType == 1) { // east and west node shared an edge
            nIDA = nid ;
            nIDB = nid+1;
        }
        else { // south and north node share an edge
            nIDA =  nid;
            nIDB =  nid+cols;
        }
        numEdge++;
    }
}
