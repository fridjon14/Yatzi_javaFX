package vidmot;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class Main extends Application {

    private Stage ps;

    @Override
    public void start(Stage primaryStage) throws Exception{
        ps = primaryStage;
        String[] leikmennNofn = getLeikmennNofn();
        FXMLLoader loader1 = new FXMLLoader(getClass().getResource("sample.fxml"));
        Parent root1 = loader1.load();
        nyrGluggi(primaryStage, root1, "Yahtzee");
        Controller c1 = loader1.getController();

        FXMLLoader loader2 = new FXMLLoader(getClass().getResource("sample.fxml"));
        Parent root2 = loader2.load();
        new Scene(root2,650,700);
        Controller c2 = loader2.getController();

        stillaController(c1, c2, leikmennNofn[0]);
        stillaController(c2, c1, leikmennNofn[1]);
        //primaryStage.close();
    }
    public void startGame(Stage primaryStage) throws Exception{
        Stage s = new Stage();
        start(s);
    }

     /**
     * Birtir nýja senu sem er í root í glugganum s með titlinum t
     * @param s glugginn
     * @param root senan (viðmótstréð)
     * @param t titill á glugganum
     */

    private void nyrGluggi(Stage s, Parent root, String t) {
        if(s == null) {
            s = new Stage();
        }

        s.setTitle(t);
        Scene s1 = new Scene(root, 650, 700);
        s.setScene(s1);
        s.show();
    }


    private void stillaController(Controller c1, Controller c2, String s) {
        c1.setLeikmadur(s);
        c1.birtaNafnLeikmanns();
        c1.setMotherjinn(c2);
    }
    private String[] getLeikmennNofn() throws java.io.IOException {
        FXMLLoader dLoader = new FXMLLoader (getClass().getResource("nofnLeikmanna.fxml"));
        dLoader.load();
        NofnLeikmannaController d1 = dLoader.getController();
        return d1.hvadHeitaLeikmenn();
    }
    public static void main(String[] args) {
        launch(args);
    }

    public void resetGame() throws Exception {
      //  start(ps);
    //    cleanup();
    //    startGame(ps);
    }
    public void cleanup() throws Exception {
        ps.close();

       // ps = new Stage();

    }
}
