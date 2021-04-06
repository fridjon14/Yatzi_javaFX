package vidmot;

import java.util.Random;

/**
 * Forritar teninga og heldur utan um fjölda kasta.
 * Hægt er að geyma teninga þannig að ekki öllum teningum sé
 * kastað.
 *
 * @author Ebba Þóra Hvannberg ebba@hi.is

 */
public class Teningar {

    private static final int FJOLDIKASTA = 3 ;
    // tilviksbreytur
    private final int[] teningar;      // gildi á teningunum
    private final boolean[] geymdur;    // geymdir teningar
    private int fjoldiKasta=FJOLDIKASTA;

    private final  Random rand = new Random();
    static final int MAX = 6;
    static final int MIN = 1;

    public int getFjoldiKasta() {
        return fjoldiKasta;
    }

    /**
     * *
     * Smiður fyrir teninga sem býr til fjoldi teninga Frumstillir teninga
     * þannig að þeir hafa allir gildið 1 og þeir eru ekki geymdir
     *
     * @param fjoldi fjöldi teninga
     */
    public Teningar(int fjoldi) {

        this.teningar = new int[fjoldi];
        for (int i = 0; i < 5; i++) {
            teningar[i] = 0;
        }
        this.geymdur = new boolean[fjoldi];
        ekkiGeymdir();
    }


    /**
     * Segir til um hvort i-ti teningur sé geymdur
     *
     * @param i númer tenings
     * @return satt ef teningur er geymdur annars ósatt
     */
    public boolean getNotGeymdur(int i) {
        return !geymdur[i];
    }

    public void setGeymdur(int i, boolean b) {
        geymdur[i] = b;
    }

    /**
     * Nær í alla teninga
     *
     * @return skilar fylki af teningum
     */
    public int[] getTeningar() {
        return teningar;
    }

    /**
     * Býr til random tölu
     *
     * @return random tölu
     */
    public int naestaRandomTala() {

        return rand.nextInt((MAX - MIN) + 1) + MIN;
    }

    /**
     * Stillir teningana þannig að enginn er geymdur
     * og núllstillir þá.
     */
    public void ekkiGeymdir() {
        for (int i = 0; i < teningar.length; i++) {
            geymdur[i] = false;
            teningar[i] = 0;
        }
    }
    public void resetDice(){
        for(int i = 0; i < 5; i++) {
            teningar[i] = 0;
        }
    }
    /**
     * Kastar tening ef hann er ekki geymdur. Ef i-ti teningur er
     * geymdur þá hefur hann gildið 0
     *
     * @return tala á teningi
     */

    public int [] kasta() {
        fjoldiKasta--;
        for (int i=0; i < teningar.length; i++) {
            if (getNotGeymdur(i)) {
                teningar[i] = naestaRandomTala();
                System.out.println(teningar[i]);
            }
        }
        return teningar;
    }

    public void upphafsstillaFjoldiKasta() {
        this.fjoldiKasta = FJOLDIKASTA;
    }
    public int countDiceWithValue_X(int x){
        int c = 0;
        for(int i = 0; i < 5; i++){
            if(teningar[i] == x){
                c++;
            }
        }
        return c;
    }

    /**
     * Leitar að pari, byrjar á 6 og vinnur sig niður,
     * @return stigafjölda fyrir hæsta par sem eru í teningunum.
     * @return skilar 0 ef ekkert par er í boði.
     */
    public int validatePair(){
        int c;
        for(int i = 6; i > 0; i--){
            c = 0;
            for (int j = 0; j < teningar.length; j++){
                if(i == teningar[j]){
                    c++;
                }
                if (c >= 2){
                    return (2 * i);
                }
            }
        }
        return 0;
    }
    public int validatefFourOfAKind(){
        int c;
        for(int i = 6; i > 0; i--) {
            c = 0;
            for (int j = 0; j < teningar.length; j++) {
                if (i == teningar[j]) {
                    c++;
                }
                if (c >= 4) {
                    return (4 * i);
                }
            }
        }
        return 0;

    }
}
