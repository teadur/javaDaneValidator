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
    private Text dns;
    public Text cert;
    public Text compear;


    @FXML
    private TextField url;

    @FXML protected void handleSubmitButtonAction(ActionEvent event) throws Exception {
        /** actiontarget.setText("Hello world action"); **/

        /** cert.setText(url.getText()); **/
        /** new GetDNS("test.ee"); **/
        GetDNS dnskirje = new GetDNS();
        GetCert sslcert = new GetCert();
        Validate validate = new Validate();

        String dnshash = dnskirje.getTLSA(url.getText());
        String sslhash = sslcert.GetDigest(url.getText());
        /** String cert = GetCert.main("test"); **/
        /** System.out.println(sslcert); **/
        dns.setText(dnshash);
        cert.setText(sslhash);
        String vordle = validate.CertHashCompear(dnshash,sslhash);
        compear.setText(vordle);

    }
    @FXML protected void handleSubmitButtonExit(ActionEvent event) {
        Platform.exit();
        System.exit(0);
    }
}