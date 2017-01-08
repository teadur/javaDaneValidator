package JavaDaneValidator;

/**
 * Created by georg on 3.01.2017.
 */
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
/**
Impordime exceptionite jaoks asjad
 **/

public class FXMLController {
    @FXML
    private Text dns;
    public Text cert;
    public Text compear;
    public Text domain;


    @FXML
    private TextField url;
    /** TODO: minna üle comboboxile **/
    private ComboBox url2;


    @FXML protected void handleSubmitButtonAction(ActionEvent event) throws Exception {
        /** actiontarget.setText("Hello world action"); **/

        /** cert.setText(url.getText()); **/
        /** new GetDNS("test.ee"); **/
        GetDNS dnskirje = new GetDNS();
        GetCert sslcert = new GetCert();
        Validate validate = new Validate();

        String dnshash = dnskirje.getTLSA(url.getText());
        String sslhash = sslcert.GetDigest(url.getText(),dnskirje.matchingtype,dnskirje.selector,dnskirje.dnsok,dnskirje.certusage);
        /** String cert = GetCert.main("test"); **/
        /** System.out.println(sslcert); **/
        domain.setText(url.getText());
        dns.setText(dnshash);
        cert.setText(sslhash);
        String vordle = validate.CertHashCompear(dnshash,sslhash,dnskirje.certusage,dnskirje.selector,dnskirje.matchingtype);
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