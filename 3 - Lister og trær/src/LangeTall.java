import java.util.HashSet;

public class LangeTall {

    public static void main(String[] args) {
        LangeTall ledd1 = new LangeTall(args[0]);
        LangeTall ledd2 = new LangeTall(args[2]);
        String operator =  args[1];

        LangeTall resultat = null;

        if (operator.equals("+"))
            resultat = ledd1.pluss(ledd2);
        else if (operator.equals("-"))
            resultat = ledd1.minus(ledd2);

        System.out.println("\t" + ledd1 + "\n" + operator + "\t" + ledd2 + "\n=\t" + resultat);
    }

    private Tall hode;
    private Tall hale;

    public LangeTall() {
        this.hode = null;
        this.hale = null;
    }

    public LangeTall(String langtTall) {
        this();
        this.settInnAlleTall(langtTall);
    }

    public LangeTall(LangeTall langeTall) {
        this();
        for (Tall tall = langeTall.hode; tall != null; tall = tall.neste)
            this.settInnBakerst(tall.verdi);
    }

    public LangeTall settInnAlleTall(String langtTall) {
        for (int i = 0; i < langtTall.length(); i++)
            settInnBakerst(Integer.parseInt(String.valueOf(langtTall.charAt(i))));

        return this;
    }

    public void settInnBakerst(int verdi) {
        if (verdi < 0)
            throw new IllegalArgumentException("Må være et positivt heltall");

        Tall nyttTall = new Tall(verdi, null, hale);
        if (hale != null)
            hale.neste = nyttTall;
        else
            hode = nyttTall;

        hale = nyttTall;
    }

    private void settInnFremst(int verdi) {
        if (verdi < 0)
            throw new IllegalArgumentException("Må være et positivt heltall");

        hode = new Tall(verdi, hode, null);
        if (hale == null)
            hale = hode;
        else
            hode.neste.forrige = hode;

    }

    /**
     * Returner summen av denne listen og den andre
     * som en ny liste ved å gå gjennom dem baklengs.
     *
     * Muterer ingen av listene.
     */
    public LangeTall pluss(LangeTall andre) {
        int restSiffer = 0;
        int siffer = 0;

        LangeTall a = new LangeTall(this);
        LangeTall b = new LangeTall(andre);
        LangeTall sum = new LangeTall();

        while (a.hale != null || b.hale != null) {
            if (a.hale != null && b.hale != null) {
                // Finn siste siffer i delsummen, skal stå på denne plassen
                siffer = (a.hale.verdi + b.hale.verdi + restSiffer) % 10;
                // Finn første sifferet i delsummen, må legges til neste siffer
                restSiffer = (a.hale.verdi + b.hale.verdi + restSiffer) / 10;

                a.hale = a.hale.forrige;
                b.hale = b.hale.forrige;
            } else if (a.hale == null && b.hale != null) {
                // Inkluder resterende tall i b
                siffer = (b.hale.verdi + restSiffer) % 10;
                restSiffer = (b.hale.verdi + restSiffer) / 10;
                b.hale = b.hale.forrige;
            } else if (a.hale != null && b.hale == null) {
                // Inkluder resterende tall i a
                siffer = (a.hale.verdi + restSiffer) % 10;
                restSiffer = (a.hale.verdi + restSiffer) / 10;
                a.hale = a.hale.forrige;
            }

            sum.settInnFremst(siffer);
        }

        // Inkluder resterende siffer hvis det finnes
        if (restSiffer != 0)
            sum.settInnFremst(restSiffer);

        return sum;
    }

    /**
     * Returner differansen av denne listen og den andre
     * som en ny liste ved å gå gjennom dem baklengs.
     *
     * Muterer ingen av listene.
     */
    private LangeTall minus(LangeTall andre) {
        int laaneSiffer = 0;
        int siffer = 0;

        LangeTall a = new LangeTall(this);
        LangeTall b = new LangeTall(andre);
        LangeTall sum = new LangeTall();

        while (a.hale != null || b.hale != null) {
            if (a.hale != null && b.hale != null) {
                if (a.hale.verdi + laaneSiffer >= b.hale.verdi) {
                    // Må ikke låne av neste siffer, finn differansen
                    siffer = a.hale.verdi + laaneSiffer - b.hale.verdi;
                    laaneSiffer = 0;
                } else {
                    // Må låne av neste siffer, finn siffer på denne plassen
                    siffer = a.hale.verdi + laaneSiffer + 10 - b.hale.verdi;
                    laaneSiffer = -1;
                } HashSet
                a.hale = a.hale.forrige;
                b.hale = b.hale.forrige;
            } else if (a.hale != null && b.hale == null) {
                if (a.hale.verdi >= 1) {
                    // Trenger ikke låne av neste, finn siffer på denne plassen
                    siffer = a.hale.verdi + laaneSiffer;
                    laaneSiffer = 0;
                } else {
                    //  Naavaerende verdi er mindre enn 1
                    if (laaneSiffer != 0) {
                        // Må låne av neste, finn siffer på denne plassen
                        siffer = a.hale.verdi + 10 + laaneSiffer;
                        laaneSiffer = -1;
                    } else {
                        // Naavaerende verdi er 0, legg til på denne plassen
                        siffer = a.hale.verdi;
                    }
                }

                // Ikke legg til foregående nuller
                if (a.hale.forrige == null && siffer == 0)
                    break;

                a.hale = a.hale.forrige;
            }
            sum.settInnFremst(siffer);
        }

        return sum;
    }

    @Override
    public String toString() {
        if (hode == null)
            return "NaN";

        StringBuilder stringBuilder = new StringBuilder();
        for (Tall tall = hode; tall != null; tall = tall.neste)
            stringBuilder.append(tall.verdi);

        return stringBuilder.toString();
    }

    static class Tall {
        int verdi;
        Tall neste;
        Tall forrige;

        public Tall(int verdi, Tall neste, Tall forrige) {
            this.verdi = verdi;
            this.neste = neste;
            this.forrige = forrige;
        }
    }
}
