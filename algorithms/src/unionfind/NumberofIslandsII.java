package unionfind;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import common.Point;


/*
Given a n,m which means the row and column of the 2D matrix and an array of pair A( size k). 
Originally, the 2D matrix is all 0 which means there is only sea in the matrix. 
The list pair has k operator and each operator has two integer A[i].x, A[i].y means 
that you can change the grid matrix[A[i].x][A[i].y] from sea to island. 
Return how many island are there in the matrix after each operator.
Example
Given n = 3, m = 3, array of pair A = [(0,0),(0,1),(2,2),(2,1)].
return [1,1,2,2].
Note
0 is represented as the sea, 1 is represented as the island. If two 1 is adjacent, we consider them in the same island. We only consider up/down/left/right adjacent.
Tags Expand 
Union Find
*/

/*
Thoughts:
Each pos(x,y) turns that sea spot into a island spot.
Image each isleand spot is a node in the graph, and each island(many island spots) has a root parent.
In for loop, try to add operators into the matrix one after another.
    Every time when adding a new island spot, check its sourandings and see if there are islands existed.
    If souranding island was land:
        To check if the surrouding spot are on common island (use find and union). 
        Since the operator spot was sea, the it's root parent is itself. Then, souranding spot has different island root, 
        they will surely have differet root parent, but they will do after they connect, so we do count--.
On the otherhand, if surrounding was just sea, then count++ is natural
Note:
1. Know how to write up simple union find class
2. Convert 2D array into 1D
*/
public class NumberofIslandsII {
    class UnionFind{
        HashMap<Integer, Integer> map;
        
        UnionFind(int length) {
            map = new HashMap<>();
            for(int i=0; i<length; i++) {
                map.put(i, i);
            }
        }
        
        int find(int x) {
            int parent = map.get(x);
            while (parent != map.get(parent)) {
                parent = map.get(parent);
            }
            return parent;
        }
        
        void union(int x, int y) {
            int parentX = find(x);
            int parentY= find(y);
            if (parentX != parentY) {
                map.put(parentX, parentY);
            }
        }
    }
    
    public List<Integer> numIslands2(int n, int m, Point[] operators) {
        List<Integer> rst = new ArrayList<Integer>();
        if (operators == null || operators.length == 0) {
            return rst;
        }
        
        int count = 0;
        //first: turn a 2-dimention array to 1 dimension elements and use 1 dimention to initialize unionfind
        int[] island = new int[m*n];
        UnionFind uf = new UnionFind(m*n);
        int[] xs = {-1,1,0,0};
        int[] ys = {0,0,-1,1};
        
        
        //second: main logic
        for(Point point : operators) {
            //for each operator find the islands counts
            int x = point.x;
            int y = point.y;
            //this is the corresponding 1 dimension element position
            int pos = x*m + y;
            count ++;
            if(island[pos] != 1) {
                island[pos] = 1;
                //for the changed position, loop all 4 neighbors
                //for each neighbor, check whether they have the same root parent, if not, union them and islands count -1
                for(int j=0; j<4; j++) {
                    int newX = x+ xs[j];
                    int newY = y + ys[j];
                    int newPos = newX*m + newY;
                    if(newX>0 && newX<m && newY>0 && newY<n && island[newPos] ==1) {
                         int findX = uf.find(pos);
                         int findY = uf.find(newPos);
                         if(findX != findY) {
                             count--;
                             uf.union(findX, findY);
                         }
                        
                    }
                    
                }
                
                
            }
            rst.add(count);
        }
        
        
        return rst;
    }

}
