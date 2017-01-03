package JavaDaneValidator;

/**
 * Created by georg on 3.01.2017.
 */
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.text.Text;

public class FXMLController {
    @FXML
    private Text actiontarget;

    @FXML protected void handleSubmitButtonAction(ActionEvent event) {
        actiontarget.setText("Hello world action");
    }

}