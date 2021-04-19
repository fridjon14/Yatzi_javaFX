package vidmot;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class NofnLeikmannaController {

    @FXML
    private DialogPane fxDialog;      // dialog innihald
    @FXML
    private TextField fxLeikmadur1;   //Nafn leikmanns 1
    @FXML
    private TextField fxLeikmadur2;   //Nafn leikmanns 2

    /**
     * Initializes the controller class.
     */
    public void initialize(URL url, ResourceBundle rb) {

    }
    public String [] hvadHeitaLeikmenn() {
        // Innihald dialogs búið til
        DialogPane p = new DialogPane();
        fxDialog.setVisible(true);

        // Innihald sett sem DialogPane sem fengið er úr Scene builder
        p.setContent(fxDialog);

        // Umgjörðin búin til
        Dialog<ButtonType> d = new Dialog<>();

        // og innihaldið sett í umgjörðina
        d.setDialogPane(p);

        // Haus, titill og mynd ef vill
        //haus(d);

        ButtonType iLagi = hnapparILagiHaettaVid(d);

        // Regla búin til um hvenær Í lagi hnappurinn á að vera óvirkur/virkur
        iLagiOvirkurRegla(p, iLagi);

        return getNofn(d);
    }
    /**
     * Fá nöfn leikmanna úr dialog
     * @param d dialogur
     * @return nöfn leikmanna
     */
    private String[] getNofn(Dialog<ButtonType> d) {
        String [] nofn =null;
        // Dialog birtur og svarið fengið
        Optional<ButtonType> utkoma = d.showAndWait();
        if (utkoma.isPresent() && (utkoma.get()
                .getButtonData() == ButtonBar.ButtonData.OK_DONE)) {
            nofn = new String[]{fxLeikmadur1.getText(), fxLeikmadur2.getText()};
        }
        return nofn;
    }
    private ButtonType hnapparILagiHaettaVid(Dialog<ButtonType> d) {
        ButtonType iLagi = new ButtonType("Í lagi",
                ButtonBar.ButtonData.OK_DONE);
        d.getDialogPane().getButtonTypes().add(iLagi);
        ButtonType haettaVid = new ButtonType("Hætta við",
                ButtonBar.ButtonData.CANCEL_CLOSE);
        d.getDialogPane().getButtonTypes().add(haettaVid);
        return iLagi;
    }
    private void iLagiOvirkurRegla(DialogPane p, ButtonType iLagi) {
        final Node stadfestingHnappur = p.lookupButton(iLagi);
        stadfestingHnappur.disableProperty()
                .bind(fxLeikmadur1.textProperty().isEmpty()
                        .or(fxLeikmadur2.textProperty().isEmpty()));
    }
}
