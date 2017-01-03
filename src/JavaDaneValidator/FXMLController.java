package JavaDaneValidator;

/**
 * Created by georg on 3.01.2017.
 */
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
/**
Impordime exceptionite jaoks asjad
 **/
import org.xbill.DNS.TextParseException;
import java.net.UnknownHostException;

public class FXMLController {
    @FXML
    private Text actiontarget;

    @FXML
    private TextField url;

    @FXML protected void handleSubmitButtonAction(ActionEvent event) throws TextParseException, UnknownHostException {
        /** actiontarget.setText("Hello world action"); **/

        actiontarget.setText(url.getText());
        /** new GetDNS("test.ee"); **/
        GetDNS dnskirje = new GetDNS();

        actiontarget.setText(dnskirje.getTLSA(url.getText()));

    }
    @FXML protected void handleSubmitButtonExit(ActionEvent event) {
        Platform.exit();
        System.exit(0);
    }
}