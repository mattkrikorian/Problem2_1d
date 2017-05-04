/*
Matt Krikorian
2/21/17
This program uses a rational agent that searches a state space so as
to achieve a goal, or a reward state.

Problem 2.1d Code the state space as a maze, in a two-dimensional
array of values, some of which (rewards) are positive.
Explore the maze by passing through states that are
adjacent to each other in a 2-dimensional grid.
 */

package Problem2_1d;

import java.util.*;

public class Problem2_1d {
    public static void main(String[] args){
        
        //2d maze of values. Let '.' = an empty space, 'W' = a wall, 'g' = a goal, 'v' = an already visited space, 'C' = current location
        char[][] graph = new char[8][8];
        
        //create empty maze
        for(int x=0; x<8; x++){
            for(int y=0; y<8; y++){
                graph[x][y] = '.';
            }
        }
        
        //add walls
        graph[0][2] = 'W'; graph[1][2] = 'W'; graph[2][2] = 'W'; graph[2][4] = 'W';
        graph[3][4] = 'W'; graph[3][5] = 'W'; graph[3][6] = 'W'; graph[3][7] = 'W';
        graph[3][2] = 'W'; graph[4][2] = 'W'; graph[5][2] = 'W'; graph[6][2] = 'W';
        graph[6][3] = 'W'; graph[6][4] = 'W'; graph[6][5] = 'W'; graph[6][6] = 'W';
        graph[6][1] = 'W'; graph[2][6] = 'W'; graph[1][6] = 'W';
        
        //add goal
        graph[0][0] = 'g';
        
        int x=3, y=3; //starting location of agent
        int[] location = {x,y};
        
        Queue<Integer> q = new LinkedList<>(); //represents locations that will be checked next
        q.add(x);
        q.add(y);
        
        Queue<Integer> q2 = new LinkedList<>(); //represnts locations that may be added to the first queue
        boolean found=false; //has the goal been found?
        
        while(found == false){
            
            location[0] = q.remove();
            location[1] = q.remove();
            graph[location[0]][location[1]] = 'v';
            
            //add four surrounding locations to queue if they aren't out of bounds, aren't walls, and have not already been visited
            //move right
            if(location[0] + 1 < 8 && graph[location[0] + 1][location[1]] != 'W' && graph[location[0] + 1][location[1]] != 'v') {
                if(graph[location[0] + 1][location[1]] == 'g') {
                    location[0] +=1; //move to goal
                    found = true;
                } else {
                    q2 = search(location[0] + 1, location[1], graph, q2); //add location to queue
                }
            }
            //move left
            if(location[0] - 1 >= 0 && graph[location[0] - 1][location[1]] != 'W' && graph[location[0] - 1][location[1]] != 'v') {
                if(graph[location[0] - 1][location[1]] == 'g') {
                    location[0] -=1; //move to goal
                    found = true;
                } else {
                    q2 = search(location[0] - 1, location[1], graph, q2);
                }
            }
            //move up
            if(location[1] - 1 >= 0 && graph[location[0]][location[1] - 1] != 'W' && graph[location[0]][location[1] - 1] != 'v') {
                if(graph[location[0]][location[1] - 1] == 'g') {
                    location[1] -=1; //move to goal
                    found = true;
                } else {
                    q2 = search(location[0], location[1] - 1, graph, q2);
                }
            }
            //move down
            if(location[1] + 1 < 8 && graph[location[0]][location[1] + 1] != 'W' && graph[location[0]][location[1] + 1] != 'v') {
                if(graph[location[0]][location[1] + 1] == 'g') {
                    location[1] +=1; //move to goal
                    found = true;
                } else {
                    q2 = search(location[0], location[1] + 1, graph, q2);
                }
            }
            
            //display current graph
            for(int i = 0; i < 8; i++) {
                for(int j = 0; j < 8; j++) {
                    
                    if(j == location[0] && i == location[1])
                        System.out.print("C" + "  "); //C represents current location
                    else
                        System.out.print(graph[j][i] + "  ");
                }
                System.out.println();
            }
            
            System.out.println("______________________\n");
            
            if(q.peek() == null){
                while(q2.peek() != null){
                    q.add(q2.remove());
                }
            }
        }
        
        System.out.println("Reward has been found at location: " + location[0] + ", " + location[1]);
    }
    
    public static Queue<Integer> search(int x, int y, char[][] graph, Queue<Integer> q2){
        /*
        check to see if the coordinates should be added to the queue
        they should not be added to the queue if they are already in it
        This is to ensure that each location is only visited one time
        */
        Queue<Integer> q3 = new LinkedList<>();
        
        boolean addToQueue = true;
        
        while(q2.peek() != null){
            
            int z = q2.remove();
            int z2 = q2.remove();
            
            if(z == x && z2 == y){
                addToQueue = false; //don't add to the queue if the same coordinates are already in it
            }

            q3.add(z);
            q3.add(z2);
        }
        
        if(addToQueue){
            q3.add(x);
            q3.add(y);
        }
        return q3;
    }
}