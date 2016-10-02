package unionfind;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import common.DirectedGraphNode;

/*
Find the number Weak Connected Component in the directed graph. 
Each node in the graph contains a label and a list of its neighbors. 
(a connected set of a directed graph is a subgraph in which 
    any two vertices are connected by direct edge path.)
Example
Given graph:
A----->B  C
 \     |  | 
  \    |  |
   \   |  |
    \  v  v
     ->D  E <- F
Return {A,B,D}, {C,E,F}. Since there are two connected component which are {A,B,D} and {C,E,F}
Note
Sort the element in the set in increasing order
Tags Expand 
Union Find
*/

/*
Thoughts:
When constructing the dataset before running the method, I guess DirectedGraphNode is contructed in a 
way that one node shots to neighbors, but may not have neibors shooting back.
Then, there is a parent-child relationship, where we can use union-find
[ idea is correct. Need to implement with proper union-find methods. 
    (Implementation here: http://www.jiuzhang.com/solutions/find-the-weak-connected-component-in-the-directed-graph/)
    1. for loop to construct: Map<childNode, parentNode>
    2. Create Map<rootNode, List<nodes>>.
    3. Find(node) return root, and add this node to the rootNode's list
]
In NineChapter's definition:
I. UnionFind class takes HashSet, and makes maps of <child, parent> relatioinship. 
    1. However, in UnionFind constructor, first step is just init <self, self>
    2. Find method on a target element's root parent. If itself is root parent, then parent should = map.get(parent)
    3. Union method, if find(x) and find(y) are different, map them as child vs. parent.
II. In main method:
    1.  Create that HashSet for UnionFind.
    2.  Use Find methods to tacke all parent vs neighbors
    3.  Use union to map out the relationship between parent's root and each neighbor's root.
    OKAY, so now the map<child,parent> should be done, saved within UnionFind.
III. Generate results
    For each element in HashSet, find their root, and add to that root list
Note:
Be careful about the in generateRst method: looking for the root
*/

public class FindtheWeakConnectedComponentintheDirectedGraph {
    class UnionFind{
        HashMap<Integer, Integer> map;
        
        UnionFind(Set<Integer> set) {
            map = new HashMap<>();
            for(int num: set) {
                map.put(num, num);
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
    
    
    //main algorithm logic
    public List<List<Integer>> connectedSet2(ArrayList<DirectedGraphNode> nodes) {
        List<List<Integer>> result = new ArrayList<>();
        
        if(nodes == null || nodes.size()==0)
            return result;
        
        //first get the set for the graph, each element in the set is the node in the graph
        Set<Integer> set = new HashSet<>();
        for(DirectedGraphNode node : nodes) {
            set.add(node.label);
            for(DirectedGraphNode neighbour : node.neighbors) {
                set.add(neighbour.label);
            }
        }
        
        //second: initialize UnionFind data structure using the set 
        UnionFind uf = new UnionFind(set);
        
        //third: union each node with all its neighbors 
        for(DirectedGraphNode node : nodes) {
           for(DirectedGraphNode neighbour : node.neighbors) {
               uf.union(node.label, neighbour.label);
           }
        }
        
        return generateResult(result, uf, set);
        
    }
    
    private List<List<Integer>> generateResult(List<List<Integer>> result, UnionFind uf, Set<Integer> set) {
        
        Map<Integer, List<Integer>> listMap = new HashMap<>();
        
        //遍历 each element in the set, find all the elements have the same root parent and put them in one list
        for(int num : set) {
            int rootParent = uf.find(num);
            if (!listMap.containsKey(rootParent)) {
                List<Integer> elements= new ArrayList<>();
                listMap.put(rootParent, elements);
            }
            listMap.get(rootParent).add(num);
            
        }
        
        //for each list in the listMap values, sort the elements in each list and add it to result
        for(List<Integer> list : listMap.values()) {
            Collections.sort(list);
            result.add(list);
        }
        
        return result;
        
    }
    
}
