package JavaDaneValidator;

/**
 * Created by georg on 3.01.2017.
 */
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
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
        String sslhash = sslcert.GetDigest(url.getText(),dnskirje.matchingtype);
        /** String cert = GetCert.main("test"); **/
        /** System.out.println(sslcert); **/
        dns.setText(dnshash);
        cert.setText(sslhash);
        String vordle = validate.CertHashCompear(dnshash,sslhash);
        /** String vordle = validate.CertHashCompear("1","1"); **/

        compear.setText(vordle);
        compear.setFill(validate.varv);
        System.out.println(dnskirje.hash);
        System.out.println(dnskirje.matchingtype);

    }
    @FXML protected void handleSubmitButtonExit(ActionEvent event) {
        Platform.exit();
        System.exit(0);
    }
}