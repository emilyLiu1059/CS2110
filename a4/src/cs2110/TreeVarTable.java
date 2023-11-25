package cs2110;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * A TreeVarTable implemented through using a Binary Search Tree
 */
public class TreeVarTable implements VarTable{

    private class Node
    {
        public String name;

        public double value;

        public Node left;

        public Node right;

        public Node(String data, double val)
        {
            value=val;
            name = data;
            right=left = null;
        }
    }
    int v=0;
    Set<String> z = Collections.emptySet();
private Node root;

    /**
     * Create an empty TreeVarTable.
     */
    public TreeVarTable(){
        root=null;
    }

    /**
     * Create a TreeVarTable associating `val` with variable `nam`.
     */

    public TreeVarTable(String nam, double val){
        root=new Node(nam,val);
    }

    /**
     * returns the value associated with the variable 'name'
     */

    @Override
    public double get(String name) throws UnboundVariableException {
        Node c = search(root, name);
        if(c==null){
            throw new UnboundVariableException(name);
        }else{
            return c.value;
        }
    }

    /**
     * Creates a node on the BST associating `val` with variable `nam`.
     */

    @Override
    public void set(String nam, double val) {
        root=insert(root, nam, val);
    }

    /**
     * inserts a new node in the correct place in the BST
     */

    private Node insert(Node r, String nam, double val){
        if(r==null){
            r=new Node(nam, val);
            return r;
        }
        if((r.name).compareTo(nam)>0){
            r.left=insert(r.left, nam, val);
        }
        if((r.name).compareTo(nam)<0){
            r.right=insert(r.right, nam, val);

        }
        return r;
    }

    /**
     * Unsets a value associating `val` with variable `nam`.
     */

    @Override
    public void unset(String nam) {
        root=delete(root, nam);
    }

    /**
     * Deletes a node on the BST associating `val` with variable `nam`.
     */

    private Node delete(Node r, String n){
        if(r==null){
            return r;
        }
        if((r.name).compareTo(n)>0){
            r.left=delete(r.left, n);
        }
        else if((r.name).compareTo(n)<0){
            r.right=delete(r.right, n);

        }else{
            if(r.right==null&&r.left==null){
                return null;
            }
            if(r.right==null){
                return r.left;
            }else if(r.left==null){
                return r.right;
            }
            Node order=order(r.right);
            r.name=order.name;
            r.right=delete(r.right,r.name);
        }
        return r;
    }

    /**
     * finds the left-most node on the BST
     */

    private Node order(Node o){
        while(o.left!=null){
            o=o.left;
        }
        return o;
    }

    /**
     * returns whether or not the BST contains a variable associated with the name 'name'
     */

    @Override
    public boolean contains(String name) {
        Node c = search(root, name);
        if(c==null){
            return false;
        }else{
            return true;
        }
    }
    /**
     * searches the BST given root Node r and variable name n if there exists such a variable
     */
    private Node search(Node r, String n) {
        if (r == null || (r.name).compareTo(n) == 0) {
            return r;
        }
        if ((r.name).compareTo(n) < 0) {
            return search(r.right, n);
        }else {
            return search(r.left, n);
        }}

    /**
     * counts how many nodes are in the BST
     */

        private int travN(Node r){
        if(r!=null){
            v++;
            travN(r.left);
            travN(r.right);
        } return v;
        }

    /**
     * creates a set of strings that contain the name associated with each node
     */

        private Set<String> travS(Node r){
                z.add(r.name);
                if(r.right!=null){
                travS(r.left);}
                if(r.left!=null){
                travS(r.right);}
             return z;
        }

    /**
     * returns the size of the BST
     */


        @Override
        public int size () {
        v=0;
            return travN(root);
        }
    /**
     * returns the names of each of the variables associated with a node in the BST
     */
        @Override
        public Set<String> names () {
            z= new HashSet<String>();
            return travS(root);
        }
    }
