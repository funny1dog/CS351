package sample;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.Group;
import javafx.scene.shape.Line;
import javafx.scene.shape.Circle;
import java.lang.Math; // Needed to use Math.round()
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.io.File;  // Import the File class
import java.io.FileNotFoundException;  // Import this class to handle errors
import java.util.Scanner; // Import the Scanner class to read text files


public class Main extends Application {
    public Edge [] eList;
    public Point[] pList;
    public Node [] nList;
    public int[]   setStatus;
    public Set[]   set;
    public Node[]  MAZEK;
    public int eastEdgeStart  = 0;
    public int eastEdgeLast   = 0;
    public int southEdgeStart = 0;
    public int southEdgeLast  = 0;


    int solverMethod = 0;


    int generatorMethod =0;

    final static int UNUSED   = 0;
    final static int ON = 1;
    final static int OFF = 0;
    final static int VISITED   = 1;
    final static int PASSED    = 2;
    final static int EDGEUP   = 1;
    final static int EDGEDOWN = 0;
    final static int EASTEDGE   = 1;
    final static int SOUTHEDGE  = 2;

    int rows       = 11;          // number of rows of cells in maze, including a wall around edges
    int columns    = 11;       // number of columns of cells in maze, including a wall around edges
    int windowSize = 800;
    int cellSize   = 20;
    int numPoint = 0;
    int numNode  = 0;
    int numEdge  = 0;
    int drawFlag = 0;
    int mazeFlag = 0;

    private long last_update;
    private final int FPS = 1;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        primaryStage.setTitle("MAZE World");



        try {
            File myObj = new File("C:\\Project4\\config.txt");
            Scanner myReader = new Scanner(myObj);
            String data = myReader.nextLine();
            System.out.println(data);
            windowSize = Integer.parseInt(data);
            data = myReader.nextLine();
            System.out.println(data);
            cellSize = Integer.parseInt(data);
            data = myReader.nextLine();
            if (data.equals("kruskal")) {
                generatorMethod = 2;
            }
            data = myReader.nextLine();
            if (data.equals("mouse")) {
                solverMethod = 1;
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        int width = windowSize  + 20;
        int height = windowSize + 20;
        rows    = Math.round(windowSize/cellSize) ;
        columns = Math.round(windowSize/cellSize) ;

        // Create the timer to animate the Maze data
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) { // Current time in nanoseconds
                // Waits until specified duration has passed
                if (now - last_update >= calcFPS(FPS)) {
                    if(drawFlag == 1) {
                        // This is the part that I am responsible to implement
                        if( (generatorMethod == 2)  && (solverMethod == 1 )) {
                            drawMaze(primaryStage, width, height, rows, columns, mazeFlag);
                            drawFlag = 0;
                        }
                        else {
                            System.out.println("This is ? ");
                        }
                    }
                    // Remember the last time this was updated
                    last_update = now;
                }
            }
        };

        //Start the timer
        timer.start();

        // timer: display maze
        // a thread to play MAZE: random mouse
        initMaze(rows, columns);

        // create a concurrent thread to run Maze
        // This is the part that I am responsible to implement
        if( (generatorMethod == 2)  && (solverMethod == 1 )) {
            Thread taskThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    mazeFlag = 0;
                    generateMaze(rows, columns);
                    setNodeInfo(rows, columns);
                    mazeFlag = 1;
                    solveMaze(rows, columns);
                }
            });
            taskThread.start();
        }
        else {
            System.out.println("This is ?");
        }

    }

    /**
     * Calculates FPS in nano seconds
     */
    private long calcFPS(int fps){
        return 1_000_000_000 / (long) fps;
    }


    void setNodeInfo(int rows, int cols) {
        int i;
        int j;
        int nNodes = rows * cols + 10;
        int nIDA;
        int nIDB;
        int nID =0;

        MAZEK = new Node[nNodes];
        for(i=0; i < rows; i++) {
            for(j=0; j < cols; j++) {
                MAZEK[nID] = new Node (i, j, nID, rows, cols);
                MAZEK[nID].setNodeNeighbor(j, i, nID, rows, cols);
                nID++;
            }
        }

        for(i=0; i < numEdge; i++) {
            if(eList[i].status == EDGEUP) {
                nIDA = eList[i].nIDA;
                nIDB = eList[i].nIDB;
                if(eList[i].edgeType == EASTEDGE){
                    MAZEK[nIDA].disableNodeEastWall();
                    MAZEK[nIDB].disableNodeWestWall();

                }
                else {
                    MAZEK[nIDA].disableNodeSouthWall();
                    MAZEK[nIDB].disableNodeNorthWall();

                }
            }
        }


    }

    void solveMaze(int rows, int cols) {
        int startNID = 0;
        int endNID   = rows*cols -1;
        int flag;

        flag = solver(startNID, endNID);
        if(flag == 1) {
            System.out.println("Reach destination");
        }
    }


    int solver(int fromNID, int targetNID) {
        int i;
        int rc = 0;
        int[] neb;

        if(fromNID < 0 ) {
            System.out.println("fromNID < 0");
        }

        MAZEK[fromNID].setNodeStatus(VISITED);
        drawFlag = 1;
        try {
            Thread.sleep(40);
        } catch (Exception e) {
            // Do nothing with errors
        }

        neb = MAZEK[fromNID].getNodeNeb();
        neb = shuffle(neb);
        for(i=0; i < 4; i++) {

            if (neb[i] != -1) {
                if ((MAZEK[neb[i]].getNodeStatus()) != VISITED && (MAZEK[neb[i]].getNodeStatus()) != PASSED) {
                    MAZEK[neb[i]].setNodeStatus(VISITED);
                    drawFlag = 1;
                    try {
                        Thread.sleep(10);
                    } catch (Exception e) {
                        // Do nothing with errors
                    }

                    if (neb[i] == targetNID) {
                        rc = 1;
                        return (rc);
                    } else {
                        rc = solver(neb[i], targetNID);
                        if (rc == 1)
                            return (rc);
                        else {
                            MAZEK[neb[i]].setNodeStatus(PASSED);
                            drawFlag = 1;
                            try {
                                Thread.sleep(10);
                            } catch (Exception e) {
                                // Do nothing with errors
                            }
                        }
                    }
                }
            }
        }
        return(rc);
    }

    int [] shuffle(int [] neb) {
        int i;
        Random rand = new Random(); //instance of random class
        for (i=0; i<4; i++) {
            int randomPosition = rand.nextInt(4);
            int temp = neb[i];
            neb[i] = neb[randomPosition];
            neb[randomPosition] = temp;
        }
        return(neb);
    }


    void initMaze(int rows, int cols) {
        int i;
        int j;
        int nID;
        nID = 0;
        nList = new Node[(rows+1)*(cols+1)];

        for(i=0; i < rows; i++) {
            for(j=0; j < cols; j++ ){
                nList[nID]= new Node(i,j, nID, rows, cols);
                nList[nID].status = UNUSED;
                nID++;
            }
        }

        // Init point list
        //pList = new Point[rows*cols];
        // cols: x, rows: Y
        nID = 0;
        pList = new Point[(rows+1)*(cols+1)];

        for(i=1; i < rows; i++) {
            for(j=0; j < cols; j++ ){
                pList[nID] = new Point(j, i, nID);
                pList[nID].status = UNUSED;
                nID++;
            }
        }
        numNode = nID;
        numPoint = nID;

        // eList: east edge
        int eID = 0;
        nID=0;
        int x,y, x1, x2, y1,y2;
        int reserveEdge = (rows+1)*(cols+1)*2;
        System.out.println("Create " + reserveEdge + " edges");
        eList = new Edge[reserveEdge];

        for(i=0; i < rows; i++) {
            for(j=0; j < cols; j++) {
                x = j;
                y = i;
                x1 = x+1;
                y1 = y;
                x2 = x+1;
                y2 = y+1;
                if (x1 != cols) {
                    eList[eID] = new Edge(x1,y1,x2,y2,nID,eID,EASTEDGE,rows,cols);
                    eID++;
                }
                nID++;
            }
        }
        eastEdgeStart = 0;
        eastEdgeLast = eID-1;
        southEdgeStart = eID;

        int numEastEdge = eID;

        // eList: south edge
        nID=0;
        for(i=0; i < cols; i++) {
            for(j=0; j < rows; j++) {
                x = j;
                y = i;
                x1 = x;
                y1 = y+1;
                x2 = x+1;
                y2 = y+1;
                if (y1 != rows) {
                    if(eID < reserveEdge) {
                        eList[eID] = new Edge(x1, y1, x2, y2, nID, eID, SOUTHEDGE, rows, cols);
                        eID++;
                    }
                    else {
                        System.out.println("eID = "+eID + " is out of bound. reserveEdge= " + reserveEdge);
                    }
                }
                nID++;
            }
        }

        southEdgeLast = eID-1;
        numEdge = eID;
    }

    public static <T> Set<T> mergeSet(Set<T> a, Set<T> b)
    {
        return new HashSet<T>() {
            {
                addAll(a);
                addAll(b);
            }
        };
    }


    public void generateMaze(int rows, int cols) {
        int numSet = rows * cols;
        int numSetUsed = numSet;
        int i;

        //generate random values from 0-numSet
        int eID;
        Random rand = new Random(); //instance of random class
        setStatus = new int[numSet];
        set  = new HashSet[numSet];
        for(i=0; i < numSet; i ++) {
            set[i]  = new HashSet<Integer>();
            set[i].add(i);
            setStatus[i] = ON;
        }

        int[] rts;
        int uVisitedNode =numNode;
        rts = updateSet(numSetUsed, numSet, uVisitedNode, eastEdgeStart);
        numSetUsed = rts[0];
        uVisitedNode = rts[1];
        drawFlag = 1;
        rts = updateSet(numSetUsed, numSet, uVisitedNode, eastEdgeLast);
        numSetUsed = rts[0];
        uVisitedNode = rts[1];
        drawFlag = 1;
        rts = updateSet(numSetUsed, numSet, uVisitedNode, southEdgeStart);
        numSetUsed = rts[0];
        uVisitedNode = rts[1];
        drawFlag = 1;
        rts = updateSet(numSetUsed, numSet, uVisitedNode, southEdgeLast);
        numSetUsed = rts[0];
        uVisitedNode = rts[1];
        drawFlag = 1;
        try {
            Thread.sleep(40);
        } catch (Exception e) {
            // Do nothing with errors
        }

        int findEdge;
        while ((numSetUsed > 1)) {
            // randomly pick an edge
            try {
                Thread.sleep(40);
            } catch (Exception e) {
                // Do nothing with errors
            }

            findEdge = 0;
            eID = -1;
            int numCheck=0;
            while(findEdge == 0) {
                eID = rand.nextInt(numEdge);
                findEdge = checkEdge(eID);
                numCheck++;
                if(numCheck == numEdge) {
                   break;
                }
            }
            eList[eID].status = EDGEDOWN;

            if(eID != -1) {
                int before =  numSetUsed;
                rts = updateSet(numSetUsed, numSet, uVisitedNode, eID);
                numSetUsed = rts[0];
                uVisitedNode = rts[1];
                if(before > numSetUsed) {
                    drawFlag = 1;
                }
            }
            else {
                System.out.println("findEdge failed. Check all edge - BUG found");
                break;
            }
        }
    }

    public int[] updateSet(int setUsed, int numSet, int uVisitedNode, int eid) {
        int i;
        int nida, nidb;
        int sIDA = -1;
        int sIDB = -1;

        nida = eList[eid].nIDA;
        nidb = eList[eid].nIDB;
        for(i=0; i < numSet; i++) {
            if(setStatus[i] == ON) {
                if (set[i].contains(nida)) {
                    sIDA = i;
                }
                if (set[i].contains(nidb)) {
                    sIDB = i;
                }
            }
        }

        if(sIDA != -1 && sIDB != -1) {
            if(sIDA != sIDB) {
                setStatus[sIDA] = ON;
                setStatus[sIDB] = OFF;
                set[sIDA] = mergeSet(set[sIDA], set[sIDB]);
                set[sIDB].clear();
                eList[eid].status = EDGEDOWN;
                setUsed--;
                uVisitedNode = uVisitedNode -1;

            }
        }
        else {
            System.out.println("UpdateSet failed: cannot find sIDA and sIDB" + "sIDA=" + sIDA + ", sIDB= " + sIDB);
        }
        eList[eid].status = EDGEDOWN;

        return new int[] {setUsed, uVisitedNode};

    }

    public int checkEdge(int eid) {
        if(eList[eid].status == EDGEUP)
            return (1);
        else
            return(0);
    }


    public void drawMaze(Stage stage, int width, int height, int rows, int cols, int mazeFlag) {
        int i;
        double fx,fy;
        double tx,ty;
        double bs = width/rows;

        Group root = new Group();
        Scene scene = new Scene(root, width, height, Color.LIGHTGREEN);
        scene.setFill(null);
        stage.setTitle("MAZE");
        stage.setScene(scene);

        for(i=0; i < numEdge; i++) {
            if(eList[i].status == EDGEUP) {
                fx = eList[i].fx * bs;
                fy = eList[i].fy * bs;
                tx = eList[i].tx * bs;
                ty = eList[i].ty * bs;
                Line line = new Line();
                line.setStartX(fx);
                line.setStartY(fy);
                line.setEndX(tx);
                line.setEndY(ty);
                line.setStrokeWidth(2);
                line.setStroke(Color.BLACK);
                root.getChildren().add(line);
            }
        }

        i =0;
        int j;
        for(j=1; j < cols; j++) {
            fx = j * bs;
            fy = i * bs;
            tx = (j+1) * bs;
            ty =  i * bs;
            Line line = new Line();
            line.setStartX(fx);
            line.setStartY(fy);
            line.setEndX(tx);
            line.setEndY(ty);
            line.setStrokeWidth(2);
            line.setStroke(Color.BLACK);
            root.getChildren().add(line);

        }
        i = rows;
        for(j=0; j < cols; j++) {
            fx = j * bs;
            fy = i * bs;
            tx = (j+1) * bs;
            ty =  i * bs;
            Line line = new Line();
            line.setStartX(fx);
            line.setStartY(fy);
            line.setEndX(tx);
            line.setEndY(ty);
            line.setStrokeWidth(2);
            line.setStroke(Color.BLACK);
            root.getChildren().add(line);
        }

        i = 0;
        for(j=1; j < cols; j++) {
            fx = i * bs;
            fy = j * bs;
            tx = i * bs;
            ty = (j+1) * bs;
            Line line = new Line();
            line.setStartX(fx);
            line.setStartY(fy);
            line.setEndX(tx);
            line.setEndY(ty);
            line.setStrokeWidth(2);
            line.setStroke(Color.BLACK);
            root.getChildren().add(line);
        }
        i = cols;
        for(j=0; j < (cols-1); j++) {
            fx = i * bs;
            fy = j * bs;
            tx = i * bs;
            ty = (j+1) * bs;
            Line line = new Line();
            line.setStartX(fx);
            line.setStartY(fy);
            line.setEndX(tx);
            line.setEndY(ty);
            line.setStrokeWidth(2);
            line.setStroke(Color.BLACK);
            root.getChildren().add(line);
        }


        for(i=0; i < rows; i++) {
            for (j = 0; j < cols; j++) {
                double lx = i * bs;
                double ly = j * bs;
                double rx = (i + 1) * bs;
                double ry = (j + 1) * bs;
                double mx = (lx + rx) / 2.0;
                double my = (ly + ry) / 2.0;
                Circle circle = new Circle();
                //Setting the properties of the circle
                circle.setCenterX(mx);
                circle.setCenterY(my);
                circle.setRadius(bs / 3.3);
                int nid = i + j * rows;
                if(mazeFlag == 0) {
                    circle.setFill(Color.RED);
                }
                else {
                    //Setting other properties
                    try {
                        if (MAZEK[nid].getNodeStatus() == VISITED) {
                            circle.setFill(Color.BLACK);
                        } else if (MAZEK[nid].getNodeStatus() == PASSED) {
                            circle.setFill(Color.LIGHTBLUE);
                        } else {
                            circle.setFill(Color.RED);
                        }
                    } catch (Exception e) {
                        // Do nothing with errors
                    }
                }


                circle.setStrokeWidth(1.0);
                circle.setStroke(Color.DARKSLATEGREY);
                root.getChildren().add(circle);
            }
        }

        stage.setScene(scene);
        stage.show();

    }

}


