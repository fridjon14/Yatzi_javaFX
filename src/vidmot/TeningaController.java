package vidmot;

import javafx.fxml.Initializable;
import javafx.scene.image.Image;

import java.net.URL;
import java.util.ResourceBundle;

public class TeningaController implements Initializable {

    private final Image[] dice = new Image[7];
    private final String[] MYNDIR = new String[]{"1", "2", "3", "4", "5", "6", "0"};


    public void initialize(URL url, ResourceBundle rb) {
        for (int i = 0; i < 7; i++){
            dice[i] = new Image(getClass().getResourceAsStream
                    ("vidmot/myndir/white/" + MYNDIR[i] + ".png"));
        }
    }
}
