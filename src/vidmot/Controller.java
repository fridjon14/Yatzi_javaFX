package vidmot;


import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;


public class Controller implements Initializable {

    private final Image[] dice = new Image[7];
    private final Image[] diceGeyma = new Image[7];
    private final String[] MYNDIR = new String[]{"1", "2", "3", "4", "5", "6"};
    private int[] stigatafla = new int[18];
    private static final int OFFSET = 17;
    private int leikLokið = 0;


    public void initialize(URL url, ResourceBundle rb) {
        for (int i = 0; i < 6; i++) {
            String pathToImage = "vidmot/myndir/white/" + MYNDIR[i] + ".png";
            String pathToImageBlue = "vidmot/myndir/blue/" + MYNDIR[i] + ".png";
            dice[i + 1] = new Image(pathToImage);
            diceGeyma[i + 1] = new Image(pathToImageBlue);
        }
        dice[0] = new Image("vidmot/myndir/white/0.png");
        for (int i = 0; i < stigatafla.length; i++) {
            stigatafla[i] = -1;
        }
        stigatafla[7] = 0;
        stigatafla[8] = 0;
        stigatafla[0] = 0;
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
    private GridPane fxStigatafla;
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



    //aðrar stýringar
    private Controller motherji;
    // Gagnahlutir
    private Leikmadur l;
    private Teningar teningar = null;


    /**
     *
     * @param mEvent
     */
    @FXML
    private void geymaTening(MouseEvent mEvent) {
        Node n = (Node) mEvent.getTarget();
        int teningurNumer = Integer.parseInt(n.getId());
        teningar.toggleGeymdur(teningurNumer - 1);
        showDiceValue(teningurNumer);
    }

    @FXML
    private void kasta(ActionEvent actionEvent) {
        if (teningar == null) {
            teningar = new Teningar(5);
        }
        teningar.kasta();
        if (teningar.getFjoldiKasta() == 0)
            toggleKasta(true);
        String kostEftir = Integer.toString(teningar.getFjoldiKasta());
        fxKostEftir.setText(kostEftir);
        for (int i = 1; i < 6; i++) {
            showDiceValue(i);
        }
    }

    @FXML
    private void naestiLeikmadur(ActionEvent actionEvent) throws Exception {
        fxSkiptaUmLeikmann.setDisable(true);
        enableUnusedScoreButtons();
        Stage s = (Stage) fxLeikmadur.getScene().getWindow();
        s.setScene(motherji.fxLeikmadur.getScene());

        teningar = new Teningar(5);
        toggleKasta(false);
        for (int i = 1; i < 6; i++) {
            showDiceValue(i);
        }
        setTheTable();
        //leiksLok();

        if(hefurLokiðLeik() && motherji.hefurLokiðLeik()) {
            leiksLok();
        }
    }

    public void setLeikmadur(String nafn) {
        l = new Leikmadur(nafn);
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


    /**
     * Höndlar event þegar smellt er á takka til að skrá stig.
     * gefur viðvörum er reitur gefur 0 stig
     * skiptir um leikmann
     * @param event buttonclick
     */
    @FXML
    public void handleScoring(ActionEvent event) {
        Node n = (Node) event.getTarget();
        int id = Integer.parseInt(n.getId());
        ObservableList<Node> labels = fxStigatafla.getChildren();

        int score = calculateScore(id);
        Label l = (Label) labels.get(id + OFFSET);
        Label heldarstig = (Label) labels.get(2*OFFSET+1);
        Label bonusSum = (Label) labels.get(7+OFFSET);
        Label bonus = (Label) labels.get(8+OFFSET);
        int yesOrCancel = 1;
        if (score == 0) {
            yesOrCancel = enginStigAlert();
        }
        if (yesOrCancel == -1) {
            //Do nothing, wait for new event.
        }
        else{
            l.setText(Integer.toString(score));
            fxSkiptaUmLeikmann.setDisable(false);
            toggleKasta(true);
            disableAllScoreButtons();
            setScore(id, score);
            validateBonus(bonus);
            heldarstig.setText(Integer.toString(getSummaStiga(17)));
            bonusSum.setText(Integer.toString(getSummaStiga(6)));

        }
    }
    /**
     * Birtir myndir af teningum
     * @param dyeId
     */
    @FXML
    public void showDiceValue(int dyeId) {
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

    /**
     * staðfestir að bónus fáist fyrir efstu 6 reiti
     */
    private void validateBonus(Label n) {
        if (stigatafla[0] == 0) {
            int c = getSummaStiga(6);
            if (c >= 63) {
                n.setText(Integer.toString(50));
                setScore(0, 50);
            }
        }
    }

    /**
     * Reiknar út stigafjölda eftir hvaða sögn er valin.
     * @param id segir til eftir hvaða aðferð stigin reiknast
     * @return
     */
    private int calculateScore(int id) {
        int value = 0;
        switch (id) {
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
                value = teningar.calculateScoreFromDiceWithValue_X(id);
                break;
            case 9:                                                         //Tvenna
                value = teningar.validate_n_OfAKind(2);
                break;
            case 10:                                                        //Tvö pör
                value = teningar.validate_n_OfAKind(4);
                if (value != 0) {
                    return value;
                }
                value = teningar.validatePairPlusTwoOrThree(2);
                break;
            case 11:                                                        //Þrenna
                value = teningar.validate_n_OfAKind(3);
                break;
        case 12:                                                            //Ferna
                value = teningar.validate_n_OfAKind(4);
                break;
            case 13:                                                        //Lág Röð
                value = teningar.validateRow(1);
                break;
            case 14:                                                        //Há röð
                value = teningar.validateRow(2);
                break;
            case 15:                                                        //Fullt hús
                value = teningar.validate_n_OfAKind(5);
                if (value != 0) {
                    return value;
                } else {
                    value = teningar.validatePairPlusTwoOrThree(3);
                }
                break;
            case 16:                                                        //Áhætta
                value = teningar.summaAllraTeninga();
                break;
            case 17:                                                        //Yatzi
                value = teningar.validate_n_OfAKind(5);
                if(value != 0){
                    value = 50;
                }
                break;
                }
            return value;
        }

    /**
     * Skráir stig í fylki sem heldur um stigatöflu.
     * @param index sæti í fylki
     * @param value stig
     */
    private void setScore(int index, int value) {
        stigatafla[index] = value;
    }

    /**
     * birtir Warning Dialog
     * @return 1 fyrir já
     * @return -1 fyrir cancel
     */
    private int enginStigAlert() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Þessi valmöguleiki gefur 0 stig. Ertu viss um að þú viljir gera þetta?",
                  ButtonType.YES,  ButtonType.CANCEL);
        alert.showAndWait();

        if (alert.getResult() == ButtonType.YES) {
            return 1;
        }
        if (alert.getResult() == ButtonType.CANCEL) {
            return -1;
        }
        return -1;
    }
    private int leikLokið() {
        ButtonType ja = new ButtonType("Nýr leikur", ButtonBar.ButtonData.YES);
        ButtonType nei = new ButtonType("Hætta", ButtonBar.ButtonData.NO);

        Alert alert = new Alert(Alert.AlertType.INFORMATION,  "viltu spila aftur?",
                ja, nei);
        int a, b = 0;

        a = motherji.getSummaStiga(17);
        b = getSummaStiga(17);
        String winner = "";
        String lokastig1,lokastig2 = "";
        if(a < b){
            winner = l.getNafn() + ". Þú vannst!";

        }
        else if(a > b){
            winner = motherji.l.getNafn() + ". Þú vannst!";

        }
        else if(a == b){
            winner = "Það var jafntefli";
            lokastig1 = "Báðir með " + a + " stig.";
        }
        lokastig1 = " " + l.getNafn() + " fékk " + b + " stig";
        lokastig2 = " " + motherji.l.getNafn() + " fékk " + a + " stig";

        alert.setTitle("Leik Lokið");
        alert.setHeaderText("Til hamingju, " + winner + "\n" + lokastig1 + " " + lokastig2);
        alert.setContentText("Annan leik?");


        Optional<ButtonType> result = alert.showAndWait();


        if (result.orElse(nei) == ja) {
            return 2;
        }
        if (result.orElse(ja) == nei) {
            return 3;
        }
        return 1;
    }
    /**
     * Stillir upp teningum til að kasta
     * Stillir köst eftir = 3
     */
    private void setTheTable() {
        fxKostEftir.setText("3");
        fx1.setImage(dice[0]);
        fx2.setImage(dice[0]);
        fx3.setImage(dice[0]);
        fx4.setImage(dice[0]);
        fx5.setImage(dice[0]);
    }

    /**
     * Reiknar summu stigatöflu frá byrjun að n-ta staki.
     * n = 17 reiknar heildarsummu stiga.
     *
     * @param n int fjöldi liða sem skal leggja saman
     * @return int sum summa stiga
     */
    private int getSummaStiga(int n) {
        int sum = 0;
        for (int i = 1; i <= n; i++) {
            if(stigatafla[i] != -1)
                sum += stigatafla[i];
        }
        if (n > 7) {
            sum += stigatafla[0];               //Bónus fyrir efstu 6.
        }
        return sum;
    }

    /**
     * Gerir alla valmöguleika óvirka
     */
    private void disableAllScoreButtons(){
        ObservableList<Node> buttons = fxStigatafla.getChildren();
        for(int i = 0; i < OFFSET; i++){
            buttons.get(i).setDisable(true);
        }
    }

    /**
     * Virkjar alla ónotaða svarmöguleika leikmanns
     */
    private void enableUnusedScoreButtons(){
        ObservableList<Node> buttons = fxStigatafla.getChildren();
        for(int i = 1; i < stigatafla.length; i++){
            if(stigatafla[i] == -1)
                buttons.get(i-1).setDisable(false);
        }
    }

    /**
     * Fer í gegnum stigatöflu og athugar hvort
     * einhver svarmöguleiku sé ónotaður.
     * @return true ef allir leikmaður hefur lokið leik.
     */
    private boolean hefurLokiðLeik(){
        for(int i = stigatafla.length - 1; i >= 0; i--){      //Byrjum á yatzi og vinnum okkur niður til að fækka
            if(stigatafla[i] == -1){                          //reikniaðgerðum. yatzi og áhætta klárast yfirleitt síðast
                return false;
            }
        }
        return true;
    }
    private void leiksLok() throws Exception {
                                                             //þegar leikmaður klárar alla stigatöfluna verður
        int opt = leikLokið();                            //leikLokið = 1, næst þegar hann ætti að gera tilkynnast
        if(opt == 2){                                 // úrslit og val um að byrja upp á nýtt eða hætta.
            Main m = new Main();
            m.resetGame();
            //restart game
        }
        else if(opt == 3){
            Platform.exit();
        }

    }
    private void hreinsaTil(){
       // this.fxStigatafla.clear();

    }
    @FXML
    private void closeProgram(){
        Platform.exit();
    }
    @FXML
    private void nyrLeikur() throws Exception {
        Stage s = (Stage) fxLeikmadur.getScene().getWindow();
        s.close();
        Main m = new Main();
        m.startGame(s);
    }
}