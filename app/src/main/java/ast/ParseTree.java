package ast;

public class ParseTree {

    public Node root;

    public ParseTree(Node root) {
        this.root = root;
    }


    @Override
    public String toString() {
        return root.toString();
    }

}
