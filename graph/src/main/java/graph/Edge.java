package graph;

/**
 * Edge is a subclass created to support Graph. Edges store nodes as T objects and its label/weight as E objects
 *
 */

public class Edge<T,E> {

    /**
     * AF: parent = arriving node of edge, child = leaving node of edge, label = name of edge
     * RI: parent != null, child != null, label != null
     */


    //parent node of this
    T parent;
    //child node of this
    T child;
    //label of this
    E label;

    /**
     * Create and return a new edge that is labeled with a starting node and ending node
     *
     * @param parent starting node of edge
     * @param child  ending node of edge
     * @param label  label of the edge
     * @spec.effects returns edge that is labeled and set its starting Node and ending Node
     * @spec.requires parent != null, child != null, label != null, parent and child must already exist in NodeList
     */

    public Edge(T parent, T child, E label) {
        this.parent = parent;
        this.child = child;
        this.label = label;

    }


    /**
     * Method that returns label of the edge
     *
     * @return this.label
     * @spec.effects return the label of this instance
     */


    public E getLabel() {
        return this.label;

    }


    /**
     * Method that returns parent node of our edge
     *
     * @return this.parent
     * @spec.effects return parent node of this instance
     */
    public T getParent() {
        return this.parent;

    }


    /**
     * Method that returns child node of our edge
     *
     * @return this.child
     * @spec.effects return child node of this instance
     */
    public T getChild() {
        return this.child;
    }


    /**
     * Method that compares Edge objects.
     * @param o Object being compared
     * @return true or false if the labels are the same, parents are the same, and children are the same
     */
    public boolean equals(Object o) {
        if (!(o instanceof Edge)) {
            return false;

        }
        Edge e = (Edge) o;
        return this.label.equals(e.getLabel()) && this.parent.equals(e.getParent()) && this.child.equals(e.getChild());

    }

}
