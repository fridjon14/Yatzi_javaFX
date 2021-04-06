package vidmot;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;


public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader loader1 = new FXMLLoader(getClass().getResource("sample.fxml"));
        Parent root1 = loader1.load();
        nyrGluggi(primaryStage, root1, "Yahtzee");
        Controller c1 = loader1.getController();

        FXMLLoader loader2 = new FXMLLoader(getClass().getResource("sample.fxml"));
        Parent root2 = loader2.load();
        new Scene(root2,650,700);
        Controller c2 = loader2.getController();

        stillaController(c1, c2, "A");
        stillaController(c2, c1, "B");
    }

     /**
     * Birtir nýja senu sem er í root í glugganum s með titlinum t
     * @param s glugginn
     * @param root senan (viðmótstréð)
     * @param t titill á glugganum
     */

    private void nyrGluggi(Stage s, Parent root, String t) {
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

    public static void main(String[] args) {
        launch(args);
    }
}
