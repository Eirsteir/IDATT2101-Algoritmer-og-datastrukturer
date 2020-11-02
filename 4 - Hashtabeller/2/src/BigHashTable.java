import java.util.*;

public class BigHashTable {

    static int A = 1327217885; //A example given in textbook/foils
    static int x;
    int sizeOfTable;
    Integer[]table;
    int col = 0;

    /**
     * Our constructor takes in the closest power of two (2^x) and the size of the table we want to create
     * @param x
     * @param sizeOfTable
     */
    public BigHashTable(int x, int sizeOfTable){
        this.sizeOfTable = sizeOfTable;
        BigHashTable.x = x;
        this.table = new Integer[sizeOfTable];
    }

    public int getSizeOfTable(){
        return sizeOfTable;
    }

    public double lastFactor(int nrOfElements){
        return (double) table.length / (double)nrOfElements; //should return a value close to 1.3
    }

    /**
     * multiplication method from book page 159
     * fixed typo, unsigned -> int, A value changed
     * @param k
     * @return
     */

    public static int hash1(int k, int m){
        return (k * A >>> (31 - x)) % m; //uses unsigned right shift for hashing
    }

    /**
     * hash1 funcition is a simple modulo division
     * @param k
     * @param m
     * @return
     */

    public static int hash3(int k, int m){
        return k % m;
    }

    /**
     * hash2 finction is similar to hash1
     * since m (size of table) is prime, we can use do k % (m-1)
     * we add 1 at the end, since hash2 may never be equal to zero
     * @param k
     * @param m
     * @return
     */

    public static int hash2(int k, int m){
        return (k % (m - 1)) + 1;
    }

    public int getCol() {
        return col;
    }

    public double collisionsPerElement(int nrOfElements){
        return (double) col / (double) nrOfElements;
    }

    public int probe(int h, int i, int m){
        return (h + i) % m;
    }

    /**
     * Probe method that returns a possible free position in table
     * @param h1 first hashvalue
     * @param h2 second hashvalue (may not be 0)
     * @param i iterated value that updates when there is a collision
     * @param m lenght of table
     * @return
     */

    public int probeDouble(int h1, int h2, int i, int m){
        return (h1 + i*h2) % m;
    }

    /**
     * Takes in an integer and adds it to table
     * First it hashes it, then uses the probe method to handle collisions
     * @param k
     * @return
     */

    public int addTo1(Integer k){
        int m = table.length;
        //int h = multhash(k.intValue()); //gives us the hashvalue
        int h1 = hash1(k,m);
        int h2 = hash2(k, m);
        for (int i = 0; i < m; i++){ //iterates
            //int j = probe(h, i, m); //uses the probe method to give us an unique position
            int j = probeDouble(h1, h2, i, m);
            if(table[j] == null){
                table[j] = k; //if the position is free then we place the integer at this position
                return j;
            }
            col++; //if it is occupied, we add a collision to collision counter, and uses a new i value
        }
        return -1;
    }

    public int addTo(Integer k){
        int m = table.length;
        int h1 = hash1(k,m); //gives us the first hashvalue
        if (table[h1] == null) {
            table[h1] = k; //if position is free, places the integer at this position
            return h1;
        } else {
            col++; //add one collision
            int h2 = hash2(k, m); //finds the second hashvalue used for probing
            for (int i = 0; i < m; i++){
                int j = probeDouble(h1, h2, i, m); //finds a new position until a free pos is found
                if (table[j] == null){
                    table[j] = k;
                    return j;
                }
                col++;
            }
        }
        return -1;
    }

    /**
     * method that finds the position of a specific integer value
     * @param k
     * @return
     */

    public int findPos(int k){
        int m = table.length;
        int h1 = hash1(k,m);
        int h2 = hash2(k,m);
        for (int i = 0; i < m; i++){
            int j = probeDouble(h1, h2, i, m);
            if (table[j] == null) return -1;
            if (table[j] == k) return j; //returns j if the value at table[j] is equal to parameter k
        }
        return -1;
    }

    public static void main(String[] args) {
        int x = 24; //closest power of two bigger than size of table
        int sizeOfTable = 12999997; //closest prime number to 13 million
        BigHashTable bht = new BigHashTable(x, sizeOfTable);
        Random random = new Random();
        int streamSize = 10000000;
        int[] arrRandom = random.ints(streamSize, 1,1000000).toArray();
        Date start1 = new Date();
        double tid1;
        Date slutt1;
        for (int value : arrRandom) {
            bht.addTo(value);
        }
        slutt1 = new Date();
        tid1 = slutt1.getTime()-start1.getTime();
        System.out.println("Tid brukt av i ms med " + arrRandom.length + " tall: " + tid1);
        System.out.println("Number of collisions: " + bht.getCol());
        System.out.println("Lastfaktor: " + bht.lastFactor(arrRandom.length));
        System.out.println("Collisions per element in table: " + bht.collisionsPerElement(arrRandom.length));

        HashMap<Integer, Integer> hashMap = new HashMap();
        Date start2 = new Date();
        double tid2;
        Date slutt2;
        for (int value : arrRandom) {
            hashMap.put(value, value);
        }
        slutt2 = new Date();
        tid2 = slutt2.getTime()-start2.getTime();
        System.out.println();
        System.out.println("Tid brukt java.util i ms med " + arrRandom.length + " tall: " + tid2);
    }
}
