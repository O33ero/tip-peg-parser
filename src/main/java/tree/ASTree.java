package tree;

public class ASTree {
    private String treeRepresentation;

    ASTree(String treeRepresentation) {
        this.treeRepresentation = treeRepresentation;
    }

    @Override
    public String toString() {
        return treeRepresentation;
    }
}
