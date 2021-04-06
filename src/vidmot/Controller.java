package vidmot;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;


public class Controller implements Initializable {

    private final Image[] dice = new Image[7];
    private final Image[] diceGeyma = new Image[7];
    private final String[] MYNDIR = new String[]{"1", "2", "3", "4", "5", "6"};
    private int[] stigatafla = new int[14];

    public void initialize(URL url, ResourceBundle rb) {
        for (int i = 0; i < 6; i++){
            String pathToImage = "vidmot/myndir/white/" + MYNDIR[i] + ".png";
            String pathToImageBlue = "vidmot/myndir/blue/" + MYNDIR[i] + ".png";
            dice[i + 1] = new Image(pathToImage);
            diceGeyma[i + 1] = new Image(pathToImageBlue);
        }
        dice[0] = new Image("vidmot/myndir/white/0.png");
        for (int i = 0; i < stigatafla.length; i++){
            stigatafla[i] = 0;
        }
    }


    @FXML
    public Label fxLeikmadur;
    @FXML
    public Button fxKasta;
    @FXML
    public Label fxKostEftir;
    @FXML
    public Button fxSkiptaUmLeikmann;
    @FXML
    private ImageView fx1;
    @FXML
    private ImageView fx2;
    @FXML
    private ImageView fx3;
    @FXML
    private ImageView fx4;
    @FXML
    private ImageView fx5;

    @FXML
    private Label fxL1;
    @FXML
    private Label fxL2;
    @FXML
    private Label fxL3;
    @FXML
    private Label fxL4;
    @FXML
    private Label fxL5;
    @FXML
    private Label fxL6;
    @FXML
    private Label fxLpar;
    @FXML
    private Label fxLefriSumma;
    @FXML
    private Label fxLferna;
    @FXML
    private Label fxLchance;
    @FXML
    private Label fxLstigAlls;
    @FXML
    private Button B1;
    @FXML
    private Button B2;
    @FXML
    private Button B3;
    @FXML
    private Button B4;
    @FXML
    private Button B5;
    @FXML
    private Button B6;
    @FXML
    private Button B7;


    //aðrar stýringar
    private Controller motherji;
    // Gagnahlutir
    private Leikmadur l;
    private Teningar teningar = null;

    @FXML
    private void geymaTening(MouseEvent mEvent){
        Node n = (Node) mEvent.getTarget();
        int teningurNumer = Integer.parseInt(n.getId());
        System.out.println("Geyma tening");
        System.out.println(teningurNumer);
        teningar.setGeymdur(teningurNumer - 1, true);
        showDiceValue(teningurNumer);
    }

    @FXML
    private void kasta(ActionEvent actionEvent){
        if(teningar == null) {
            teningar = new Teningar(5);
        }

        teningar.kasta();
        if(teningar.getFjoldiKasta() == 0)
            toggleKasta(true);
        String kostEftir = Integer.toString(teningar.getFjoldiKasta());
        fxKostEftir.setText(kostEftir);
        for(int i = 1; i < 6; i++){
            showDiceValue(i);
        }
    }

    @FXML
    private void naestiLeikmadur(ActionEvent actionEvent){
        Stage s = (Stage)fxLeikmadur.getScene().getWindow();
        s.setScene(motherji.fxLeikmadur.getScene());
        System.out.println("Næsti leikmaður");
        System.out.println(fxLeikmadur.getScene());
        System.out.println(motherji);
        teningar = new Teningar(5);
        toggleKasta(false);
        for(int i = 1; i < 6; i++){
            showDiceValue(i);
        }
        setTheTable();
        System.out.println("fjöldiKasta");
        System.out.println(teningar.getFjoldiKasta());
    }

    public void setLeikmadur (String nafn) {
        l = new Leikmadur (nafn);
    }

    public void birtaNafnLeikmanns() {
        fxLeikmadur.setText(l.getNafn());
        fxKasta.requestFocus();
    }

    public void setMotherjinn(Controller motherjinn) {
        this.motherji = motherjinn;
    }

    public void toggleKasta(boolean b) {
            fxKasta.setDisable(b);
    }
    public void toggleSkiptaUmLeikmann(boolean b){
        fxSkiptaUmLeikmann.setDisable(b);
    }

    @FXML
    public void assignScoreToField(ActionEvent event){
        Node n = (Node) event.getTarget();
        int value = Integer.parseInt(n.getId());

        int numberOfTheseTypeDice = teningar.countDiceWithValue_X(value);
        int score = calculateScore(value, numberOfTheseTypeDice);
        setStigatafla(value, score);
        setScore(value, score);
        toggleSkiptaUmLeikmann(false);
        toggleKasta(true);
    }

    @FXML
    public void showDiceValue(int dyeId) {
        System.out.println("BlueDice");
        System.out.println(dyeId);
        if (dyeId == 1) {
            if (teningar.getNotGeymdur(dyeId - 1))
                fx1.setImage(dice[teningar.getTeningar()[dyeId - 1]]);
            else {
                fx1.setImage(diceGeyma[teningar.getTeningar()[dyeId - 1]]);
            }
        }
        if (dyeId == 2) {
            if (teningar.getNotGeymdur(dyeId - 1))
                fx2.setImage(dice[teningar.getTeningar()[dyeId - 1]]);
            else {
                fx2.setImage(diceGeyma[teningar.getTeningar()[dyeId - 1]]);
            }
        }
        if (dyeId == 3) {
            if (teningar.getNotGeymdur(dyeId - 1))
                fx3.setImage(dice[teningar.getTeningar()[dyeId - 1]]);
            else {
                fx3.setImage(diceGeyma[teningar.getTeningar()[dyeId - 1]]);
            }
        }
        if (dyeId == 4) {
            if (teningar.getNotGeymdur(dyeId - 1))
                fx4.setImage(dice[teningar.getTeningar()[dyeId - 1]]);
            else {
                fx4.setImage(diceGeyma[teningar.getTeningar()[dyeId - 1]]);
            }
        }
        if (dyeId == 5) {
            if (teningar.getNotGeymdur(dyeId - 1))
                fx5.setImage(dice[teningar.getTeningar()[dyeId - 1]]);
            else {
                fx5.setImage(diceGeyma[teningar.getTeningar()[dyeId - 1]]);
            }
        }
    }

    private void setScore(int id, int value){
        switch(id){
            case 1:
                fxL1.setText(Integer.toString(value));
        }
        switch (id){
            case 2:
                fxL2.setText(Integer.toString(value));
                break;
        }
        switch (id){
            case 3:
                fxL3.setText(Integer.toString(value));
                break;
        }
        switch (id){
            case 4:
                fxL4.setText(Integer.toString(value));
                break;
        }
        switch (id){
            case 5:
                fxL5.setText(Integer.toString(value));
                break;
        }
        switch (id){
            case 6:
                fxL6.setText(Integer.toString(value));
                break;
        }
        fxLefriSumma.setText(Integer.toString(getSummaStiga(6)));
        switch (id) {
            case 7:
                value = teningar.validatePair();
                fxLpar.setText(Integer.toString(value));
        }
        switch (id) {
            case 10:
                value = teningar.validatefFourOfAKind();
                fxLferna.setText(Integer.toString(value));
        }
        switch (id){
            case 14:
                fxLchance.setText(Integer.toString(value));
                break;
        }
        fxLstigAlls.setText(Integer.toString(getSummaStiga(-1)));
    }
    /**
     * Reiknar stig fyrir 1-6
     * @param index int segir til um gerð teninga
     * @param  nrOfDice int segir til um fjölda þesskonar teninga.
     * @return stig
     */
    private int calculateScore(int index, int nrOfDice){
        return index * nrOfDice;
    }
    private void setStigatafla(int index, int value){
        stigatafla[index] = value;
    }

    /**
     * Stillir upp teningum til að kasta
     * Stillir köst eftir = 3
     */
    private void setTheTable(){
        fxKostEftir.setText("3");
        fx1.setImage(dice[0]);
        fx2.setImage(dice[0]);
        fx3.setImage(dice[0]);
        fx4.setImage(dice[0]);
        fx5.setImage(dice[0]);
    }

    /**
     * Reiknar summu stigatöflu frá byrjun að n-ta staki.
     * n = -1 reiknar heildarsummu stiga.
     * @param n int fjöldi staka sem skal leggja saman
     * @return int sum summa stiga
     */
    private int getSummaStiga(int n){
        int sum = 0;
        if(n == -1) {
            for (int i = 0; i < stigatafla.length; i++){
                sum += stigatafla[i];
            }
        }
        for (int i = 0; i < n; i++){
            sum += stigatafla[i];
        }
        return sum;
    }
}


