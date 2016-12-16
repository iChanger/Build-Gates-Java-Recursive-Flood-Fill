
import java.io.*;
import java.util.*;
class gates {
    public static boolean[][] graph; //We do this so we can have a filled matrix once we run our BFS (Flood Fill Recursion)
    //NECESSARY Constraints: All the regions are CONNECTED and a gate can only connect TWO distinct fence regions
    public static boolean ifNotBordered; //If we touch the border of the graph, that will NOT be a distinct box (obviously because it touched the end of the graph, so there is no enclosed border). We thus don't increment our counting of distinct regions if it touches the border.
    public static void main (String [] args) throws IOException {
        //Use a Flood-Fill Approach to fill in the area and to count distinct regions.
        //WARNING: When working with arrays as "graphs, note that it is always array[y][x]. It is NOT array[x][y]
        BufferedReader f = new BufferedReader(new FileReader("gates.in"));
        PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("gates.out")));       
        int N = Integer.parseInt(f.readLine());
        String st = f.readLine();
        boolean[][] graph = new boolean[2*N][2*N]; //We let each direction count as two units in our boolean array. Furthermore, a true means it has been visited and a false means it hasn't been visited yet. In other words, a true means there is a fence there
        int xpos = N;
        int ypos = N;
        int numDistinctRegion = 0;
        graph[xpos][ypos] = true; //We initialize our starting position as visited
        for(int i = 0; i < N; i++){ //Easy way to fill out the boolean array.
            char dir = st.charAt(i);
            if(dir == 'N'){
                ypos--;
                graph[ypos][xpos] = true;
                ypos--;
                graph[ypos][xpos] = true;
            }
            if(dir == 'S'){
                ypos++;
                graph[ypos][xpos] = true;
                ypos++;
                graph[ypos][xpos] = true;
            }
            if(dir == 'E'){
                xpos++;
                graph[ypos][xpos] = true;
                xpos++;
                graph[ypos][xpos] = true;
            }
            if(dir == 'W'){
                xpos--;
                graph[ypos][xpos] = true;
                xpos--;
                graph[ypos][xpos] = true;
            }
        }
        
        for(int i = 0; i < 2*N; i++){ //We look through our boolean array for any empty spots, or "false" spots
            for(int j = 0; j < 2*N; j++){
                if(!graph[i][j]) { //Great! We found an empty spot!
                    fill(j, i, graph); //We run our recursive flood-fill, which will fill the entire region
                    if(!ifNotBordered) //Once again, a way to detect distinct region
                        numDistinctRegion++;
                    else
                        ifNotBordered = false;
                }
            }
        }
        int numGates = numDistinctRegion - 1; //The answer of the number of the gates is the number of distinct regions - 1
        /*for(boolean[] bo : graph)
            System.out.println(Arrays.toString(bo));*/
        out.println(numGates + 1);
        out.close();                                  // close the output file
    }
    public static void fill(int x, int y, boolean[][] graph){
        if(!checkInBound(x, y, graph.length)) { //There is a chance our x and y coordinates will be out of the graph boundaries. If this is true, then we do NOT use continue as we will get an array out of bounds error
            ifNotBordered = true; //Just some way to detect if it is an actual box by detecting if it is on the outer layer that we fill
            return;
        }
        if(graph[y][x]) return; //If true which means the graph has been visited, return true and we do NOT bother with this spot. We only care about the empty spots, or the "False" spots
        graph[y][x] = true;
        int[][] d = {{-1, 1, 0, 0}, {0, 0, -1, 1}}; //d[0] = [-1, 1, 0, 0] => d[1] = [0, 0, -1, 1]
        for(int i = 0; i < 4; i++) //In simple terms, this loop just means to repeat the fill method (recursion) by going in all 4 directions
            fill(x + d[0][i], y + d[1][i], graph); //Flood-Fill in respectively East, West, North, South. I'm not too sure 
        return;
    }
    public static boolean checkInBound(int x, int y, int N){ 
        if((x >= 0 && y >= 0) && (x < N && y < N)) return true;
        else return false;
    }
}
