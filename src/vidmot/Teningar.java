package vidmot;

import java.util.Arrays;
import java.util.Random;

/**
 * Forritar teninga og heldur utan um fjölda kasta.
 * Hægt er að geyma teninga þannig að ekki öllum teningum sé
 * kastað.
 *
 * @author Ebba Þóra Hvannberg ebba@hi.is
 * @author Friðjón Sigvaldason frs5@hi.is
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
     * @param i númer tenings
     * @return satt ef teningur er geymdur annars ósatt
     */
    public boolean getNotGeymdur(int i) {
        return !geymdur[i];
    }

    /**
     * Geymir eða fríar i-ta tening
     * @param i
     */
    public void toggleGeymdur(int i) {
        geymdur[i] = !geymdur[i];
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
            }
        }
        return teningar;
    }

    /**
     * Leggur saman stig allra teninga af einhverju gildi.
     * @param x gildi þeirra teninga sem á að telja
     * @return skilar summu allra teninga með gildið x.
     */
    public int calculateScoreFromDiceWithValue_X(int x){
        int c = 0;
        for(int i = 0; i < 5; i++){
            if(teningar[i] == x){
                c++;
            }
        }
        return c * x;
    }

    /**
     * Leggur saman stig allra teninganna
     * @return summu allra teninga.
     */
    public int summaAllraTeninga(){
        int value = 0;
        for (int i = 0; i < teningar.length; i++){
            value += teningar[i];
        }
        return value;
    }


    /**
     * Leitar að 2/3/4/5 eins, byrjar á 6 og vinnur sig niður,
     * @param n int fjöldi samskonar staka sem leitað er að.
     * @return stigafjölda fyrir hæsta par sem eru í teningunum.
     * @return skilar 0 ef ekki eru n eins í boði.
     */

    public int validate_n_OfAKind(int n){
        int c;
        for(int i = 6; i > 0; i--) {
            c = 0;
            for (int j = 0; j < teningar.length; j++) {
                if (i == teningar[j]) {
                    c++;
                }
                if (c >= n) {
                    return (n * i);
                }
            }
        }
        return 0;
    }
    /**
     * Raðar teningunum í hækkandi röð, og athugar hvort hver sé einum hærri þeim á undan.     *
     * @param start int, litla röð - start = 1; stóra röð - start = 2.
     * @return stigafjöldi fyrir litla/stóra röð.
     * @return skilar 0 ef engin röð er í boði.
     */
    public int validateRow(int start){
        Arrays.sort(teningar);
        for(int i = 0; i < 5; i++){
            if(start + i  != teningar[i]){
                return 0;
            }
        }
        if(start == 1){
            return 15;
        }
        if(start == 2){
            return 21;
        }
    return -1;
    }
    /**
     * Leitar að pari og öðru pari eða þrennu, sem mynda tvö por eða fullt hús.
     * @param n int, 2 leitar að tveimur pörum 3 leitar að fullu húsi.
     * @return stigafjöldi fyrir tvö pör eða fullt hús.
     * @return skilar 0 ef engin röð er í boði.
     */
    public int validatePairPlusTwoOrThree(int n){
        int[] duplicates = new int[] {0, 0, 0, 0, 0, 0};
        for(int i = 6; i > 0; i--) {
            for (int j = 0; j < teningar.length; j++) {
                if (i == teningar[j]) {
                    duplicates[i - 1] += 1;
                }
            }
        }
        int value = 0;
        int nrOfPairs = 0;
        if(n == 2) {
            for (int i = 6; i > 0; i--) {
                if (duplicates[i - 1] >= 2) {
                    value += (2 * i);
                    nrOfPairs += 1;
                }
            }
            if(nrOfPairs >= 2){
                return value;
            }
        }

        else if(n == 3){
            boolean threeOfAKind = false;
            boolean twoOfAKind = false;
            for (int i = 6; i > 0; i--) {
                if (duplicates[i - 1] == 3) {
                    threeOfAKind = true;
                }
                if(duplicates[i - 1] == 2) {
                    twoOfAKind = true;
                }
            }
            if(threeOfAKind && twoOfAKind) {
                return summaAllraTeninga();
            }
        }

        return 0;
    }
}



