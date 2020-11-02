import java.util.stream.Stream;


class Test {
    public static void main(String[] args) {
        HashTabell tabell = new HashTabell(
                "C:\\Users\\Eirik\\onedrive - ntnu\\skole\\idatt2101\\øvinger\\4 - Hashtabeller\\1\\src\\navn.txt");
        System.out.println();
        System.out.println(tabell);
        System.out.println("Lastfaktor: " + tabell.lastfaktor());
        System.out.println("Gj. snittlig antall kollisjoner per person: " +
                                   ((double) tabell.antallKollisjoner / tabell.antallElementer));

        String soketerm = "Eirik,Steira";
        Node node = tabell.finnNode(soketerm);
        int pos = tabell.finnPos(soketerm);

        System.out.println("Søker etter '" + soketerm + '\'');
        System.out.println("Fant '" + node.nokkel + "' på posisjon " + pos);
    }
}


public class HashTabell {

    Node[] tabell;
    int antallKollisjoner;
    int antallElementer;

    public HashTabell(String filnavn) {
        this.antallKollisjoner = 0;
        this.antallElementer = 0;
        leggInnAlle(filnavn);
    }

    private void leggInnAlle(String filnavn) {
        String[] navn = FilBearbeider.bearbeid(filnavn);
        tabell = new Node[nestePrimtall((int) (navn.length * 1.3))];

        for (String s : navn)
            leggInn(s);
    }

    private int nestePrimtall(int n) {
        n++;
        for (int i = 2; i < n; i++) {
            if(n%i == 0) {
                n++;
                i=2;
            }
        }

        return n;
    }

    private void leggInn(String s) {
        int h = hentHash(s);
        Node ny = new Node(s, h, null);

        if (tabell[h] == null) {
            tabell[h] = ny;
        } else {
            System.out.println("Kollisjon mellom " + ny + " og " + tabell[h]);
            leggInnForrerst(ny, h);
            antallKollisjoner++;
        }

        antallElementer++;
    }

    private void leggInnForrerst(Node ny, int hash) {
        Node temp = tabell[hash];
        tabell[hash] = ny;
        ny.neste = temp;
    }

    private int hentHash(String s) {
        int asciiVerdi = 0;

        for (int i = 0; i < s.length(); i++)
            asciiVerdi += (int) s.charAt(i) * (i + 1);

        return asciiVerdi  % tabell.length;
    }

    public int finnPos(String nokkel) {
        Node funnetNode = finnNode(nokkel);

        if (funnetNode == null)
            return -1;

        return funnetNode.hash;
    }

    public Node finnNode(String nokkel) {
        int hash = hentHash(nokkel);
        Node nodeFunnet = tabell[hash];

        if (nodeFunnet.neste == null && nodeFunnet.nokkel.equals(nokkel))
            return nodeFunnet;

        return finnNodeIListe(nodeFunnet, nokkel);
    }

    private Node finnNodeIListe(Node denne, String nokkel) {
        for (Node node = denne; node != null; node = node.neste)
            if (node.nokkel.equals(nokkel))
                return node;

        return null;
    }

    public double lastfaktor() {
        return (double) antallElementer / tabell.length;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        Stream.of(tabell).forEach(n -> sb.append(n).append(",\n"));

        return "Tabell: \n" + sb.toString()
                + "\nAntall Kollisjoner: " + antallKollisjoner;
    }
}



