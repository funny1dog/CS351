package sample;

public class Node {
        int nodeID;
        double x;
        double y;
        int[] myNB = new int[]{-1,-1,-1,-1};
        int   numNB = 0;
        int status;

        final static int UNVISITED = 0;

        public Node (int i, int j, int id, int rows, int cols) {
            nodeID = id;
            x = i;
            y = j;
            status = UNVISITED;
            setNodeNeighbor(i, j, id, rows, cols);
        }

        public int getNodeStatus(){
            return(this.status);
        }

        public void setNodeStatus(int flag){
            this.status = flag;
        }

        public int[] getNodeNeb(){
            return(this.myNB);
        }

        void disableNodeEastWall(){
            this.myNB[1] = -1;     // East
            this.numNB--;
        }
        void disableNodeWestWall(){
            this.myNB[2] = -1;     // East
            this.numNB--;
        }
        void disableNodeNorthWall(){
            this.myNB[3] = -1;     // East
            numNB--;
        }
        void disableNodeSouthWall(){
            this.myNB[0] = -1;     // East
            this.numNB--;
        }

        void setNodeNeighbor(int x, int y, int nID, int rows, int cols){
            int i;

            if(x == 0) {
                if(y == 0) {
                    myNB[3] = -1; // North
                    myNB[0] = nID + rows; // South
                    myNB[1] = nID + 1;     // East
                    myNB[2] = - 1;     // West
                    numNB   = 2;
                }
                else
                if(y == (rows-1)){
                    myNB[3] = nID - rows; // North
                    myNB[0] = -1; // South
                    myNB[1] = nID + 1;     // East
                    myNB[2] = - 1;     // West
                    numNB   = 3;
                }
                else {
                    myNB[3] = nID - rows; // North
                    myNB[0] = nID + rows; // South
                    myNB[1] = nID + 1;     // East
                    myNB[2] = - 1;     // West
                    numNB   = 2;
                }
            }
            else
            if(x == (cols-1)) {
                if (y == 0){
                    myNB[3] = -1; // North
                    myNB[0] = nID + rows; // South
                    myNB[1] = -1;     // East
                    myNB[2] = nID - 1;     // West
                    numNB = 2;
                }
                else
                if(y == (rows-1)){
                    myNB[3] = nID - rows; // North
                    myNB[0] = -1; // South
                    myNB[1] = -1;     // East
                    myNB[2] = nID - 1;     // West
                    numNB = 2;
                }
                else    {
                    myNB[3] = nID - rows; // North
                    myNB[0] = nID + rows; // South
                    myNB[1] = -1;     // East
                    myNB[2] = nID - 1;     // West
                    numNB = 3;
                }
            }
            else
            if(y == 0) {
                if((x >0) && (x < (cols-1))) {
                    myNB[3] = - 1; // North
                    myNB[0] = nID + rows; // South
                    myNB[1] = nID + 1;     // East
                    myNB[2] = nID - 1;     // West
                    numNB   = 3;
                }
            }
            else
            if(y == (rows-1)) {
                if ((x > 0) && (x < (cols - 1))) {
                    myNB[3] = nID - rows; // North
                    myNB[0] = -1; // South
                    myNB[1] = nID + 1;     // East
                    myNB[2] = nID - 1;     // West
                    numNB = 3;
                }
            }
            else { // node in the middle region, not on four border lines
                myNB[3] = nID - rows; // North
                myNB[0] = nID + rows; // South
                myNB[1] = nID +1;     // East
                myNB[2] = nID -1;     // West
                numNB = 4;
            }
        }

    }
