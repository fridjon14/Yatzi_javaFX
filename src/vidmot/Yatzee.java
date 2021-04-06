
package vidmot;

/**
 * Interface fyrir stigablað fyrir einfaldan yatzee leik fyrir einn leikmann
 * 
 * @author Ebba Þóra Hvannberg ebba@hi.is
 * @date
 * Háskóli Íslands
 */
public interface Yatzee {

    // get og set fyrir nafn leikmanns
    String getNafnLeikmanns();

    void nyrLeikmadur(String l);


    /**
     * Nær í summuna af stigunum á blaðinu
     *
     * @return summan af stigunum
     */
    int getSumma();

    /**
     * Reiknar út útkomuna úr reit i (ásar, tvistar, ...
     * sexur) á Yatzeeblaði miðað
     * við stöðu teninganna í breytu teningar
     *
     * @param teningar teningarnir á borðinu
     * @param i        lína á yatzee blaðinu
     * @return skilar stigunum fyrir einfalda
     */
    int reiknaEinfalda(int i, Teningar teningar);

    /***
     * Reiknar út stigin fyrir taka sénsinn út frá teningunum
     * @param teningar teningarnir
     * @return stigin
     */
    int reiknaSensinn(Teningar teningar);

    /**
     * Reiknar summu stigana á Yatzee blaði
     *
     * @return summa stigana
     */
    int reiknaSumma();

}

class YatzeeImp implements Yatzee {

    public YatzeeImp Yimp = new YatzeeImp();


    @Override
    public String getNafnLeikmanns() {
        return null;
    }

    @Override
    public void nyrLeikmadur(String l) {


    }



    @Override
    public int getSumma() {
        return 0;
    }

    @Override
    public int reiknaEinfalda(int i, Teningar teningar) {
        return 0;
    }

    @Override
    public int reiknaSensinn(Teningar teningar) {
        return 0;
    }

    @Override
    public int reiknaSumma() {
        return 0;
    }
}



