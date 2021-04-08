package vidmot;


import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;


import java.net.URL;
import java.util.ResourceBundle;


public class Controller implements Initializable {

    private final Image[] dice = new Image[7];
    private final Image[] diceGeyma = new Image[7];
    private final String[] MYNDIR = new String[]{"1", "2", "3", "4", "5", "6"};
    private int[] stigatafla = new int[16];
    private static final int OFFSET = 17;


    public void initialize(URL url, ResourceBundle rb) {
        for (int i = 0; i < 6; i++) {
            String pathToImage = "vidmot/myndir/white/" + MYNDIR[i] + ".png";
            String pathToImageBlue = "vidmot/myndir/blue/" + MYNDIR[i] + ".png";
            dice[i + 1] = new Image(pathToImage);
            diceGeyma[i + 1] = new Image(pathToImageBlue);
        }
        dice[0] = new Image("vidmot/myndir/white/0.png");
        for (int i = 0; i < stigatafla.length; i++) {
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
//
//    @FXML
//    private Label fxL1;
//    @FXML
//    private Label fxL2;
//    @FXML
//    private Label fxL3;
//    @FXML
//    private Label fxL4;
//    @FXML
//    private Label fxL5;
//    @FXML
//    private Label fxL6;
//    @FXML
//    private Label fxLefriSumma;
//    @FXML
//    private Label fxLpar;
//    @FXML
//    private Label fxLtvoPor;
//    @FXML
//    private Label fxLthrenna;
//    @FXML
//    private Label fxLferna;
//    @FXML
//    private Label fxLchance;
//    @FXML
//    private Label fxLlitlaRod;
//    @FXML
//    private Label fxLstoraRod;
//    @FXML
//    private Label fxLfulltHus;
//    @FXML
//    private Label fxLyatzi;
//    @FXML
//    private Label fxLstigAlls;


    //aðrar stýringar
    private Controller motherji;
    // Gagnahlutir
    private Leikmadur l;
    private Teningar teningar = null;

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
    private void naestiLeikmadur(ActionEvent actionEvent) {
        fxSkiptaUmLeikmann.setDisable(true);
        Stage s = (Stage) fxLeikmadur.getScene().getWindow();
        s.setScene(motherji.fxLeikmadur.getScene());
//        System.out.println("Næsti leikmaður");
//        System.out.println(fxLeikmadur.getScene());
//        System.out.println(motherji);
        teningar = new Teningar(5);
        toggleKasta(false);
        for (int i = 1; i < 6; i++) {
            showDiceValue(i);
        }
        setTheTable();
//        System.out.println("fjöldiKasta");
//        System.out.println(teningar.getFjoldiKasta());
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
    //public void toggleSkiptaUmLeikmann(boolean b){
    //    fxSkiptaUmLeikmann.setDisable(b);
    //}

    @FXML
    public void assignScoreToField(ActionEvent event) {
        Node n = (Node) event.getTarget();
        int id = Integer.parseInt(n.getId());
        System.out.println(id);
        ObservableList<Node> labels = fxStigatafla.getChildren();


        int score = calculateScore(id);
        //int score = calculateScore(value, numberOfTheseTypeDice);
        Label l = (Label) labels.get(id + OFFSET);

        int yesOrCancel = 0;
        if (score == 0) {
            yesOrCancel = enginStigAlert();
        }
        if (yesOrCancel == -1) {
            //Do nothing, wait for new event.
        } else if (yesOrCancel == 1) {
            l.setText(Integer.toString(score));
            validateBonus();
            fxSkiptaUmLeikmann.setDisable(false);
            toggleKasta(true);
        }
        l.setText(Integer.toString(score));
        fxSkiptaUmLeikmann.setDisable(false);
        toggleKasta(true);
        n.setDisable(true);
    }

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

    private void validateBonus() {
        if (stigatafla[0] == 0) {
            int c = getSummaStiga(6);
            if (c >= 63) {
                setScore(0, 50);
            }
        }
    }

    private int calculateScore(int id) {
        int value = 0;
        if(id > 0 && id < 7){
            value = teningar.calculateScoreFromDiceWithValue_X(id);
        }
        switch (id) {

            case 9:
                value = teningar.validate_n_OfAKind(2);
                break;
            case 10:
                value = teningar.validate_n_OfAKind(4);
                if (value != 0) {
                    return value;
                }
                value = teningar.validatePairPlusTwoOrThree(2);
                break;
            case 11:
                value = teningar.validate_n_OfAKind(3);
//                if (value == 0) {
//                    value = enginStigAlert();
//                }
//                if (value == -1) {
//                    break;
//                } else if (value == 1) {
//                    fxLthrenna.setText(Integer.toString(0));
//                }
                break;
            case 12:
                value = teningar.validate_n_OfAKind(4);
//                if (value == 0) {
//                    value = enginStigAlert();
//                }
//                if (value == -1) {
//                    break;
//                } else if (value == 1) {
//                    fxLferna.setText(Integer.toString(value));
//                }
                break;
            case 13:
                value = teningar.validateRow(1);
//                if (value == 0) {
//                    value = enginStigAlert();
//                }
//                if (value == -1) {
//                    break;
//                } else if (value == 1) {
//                    fxLlitlaRod.setText(Integer.toString(value));
//                }
                break;
            case 14:
                value = teningar.validateRow(2);
//                if (value == 0) {
//                    value = enginStigAlert();
//                }
//                if (value == -1) {
//                    break;
//                } else if (value == 1) {
//                    fxLstoraRod.setText(Integer.toString(value));
//                }
                break;
            case 15:
                value = teningar.validate_n_OfAKind(5);
                if (value != 0) {
                    return value;
                } else {
                    value = teningar.validatePairPlusTwoOrThree(3);
                }
                break;
            case 16:
                value = teningar.summaAllraTeninga();
//                fxLchance.setText(Integer.toString(value));
                break;
            case 17:
                value = teningar.validate_n_OfAKind(5);
//                if (value == 0) {
//                    value = enginStigAlert();
//                }
//                if (value == -1) {
//                    break;
//                } else if (value == 1) {
//                    fxLyatzi.setText(Integer.toString(0));
                break;
                }
            return value;
        }

//        setStigatafla(id, value);
//        fxLefriSumma.setText(Integer.toString(getSummaStiga(6)));
//        fxLstigAlls.setText(Integer.toString(getSummaStiga(15)));
//    }

    /**
     * Reiknar stig fyrir 1-6
     *
     * @param index    int segir til um gerð teninga
     * @param nrOfDice int segir til um fjölda þesskonar teninga.
     * @return stig
     */
//    private int calculateScore(int index, int nrOfDice) {
//            return index * nrOfDice;
//        }
//    }
    private void setScore(int index, int value) {
        stigatafla[index] = value;
//        for (int i = 0; i < stigatafla.length; i++) {
//            System.out.print(stigatafla[i]);
//        }
//        System.out.println();
    }

    private int enginStigAlert() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Þessi valmöguleiki gefur 0 stig. Ertu viss um að þú viljir gera þetta?",
                ButtonType.YES, ButtonType.CANCEL);
        alert.showAndWait();

        if (alert.getResult() == ButtonType.YES) {
            return 1;
        }
        if (alert.getResult() == ButtonType.CANCEL) {
            return -1;
        }
        return -1;
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
     * n = 15 reiknar heildarsummu stiga.
     *
     * @param n int fjöldi liða sem skal leggja saman
     * @return int sum summa stiga
     */
    private int getSummaStiga(int n) {
        int sum = 0;
        for (int i = 1; i <= n; i++) {
            sum += stigatafla[i];
        }
        if (n > 7) {
            sum += stigatafla[0];
        }
        return sum;
    }
}


