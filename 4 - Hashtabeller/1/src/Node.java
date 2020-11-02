public class Node {

    String nokkel;
    int hash;
    Node neste;

    public Node(String nokkel, int hash, Node neste) {
        this.nokkel = nokkel;
        this.hash = hash;
        this.neste = neste;
    }

    @Override
    public String toString() {
        if (neste == null)
            return nokkel;

        return nokkel + " => " + neste;
    }
}
