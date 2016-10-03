/**
 * Created by georg on 3.10.2016.
 */
import java.io.IOException;
import java.net.URL;
import java.security.cert.Certificate;

import javax.net.ssl.HttpsURLConnection;

public class GetCert {
    public static void main(String[] args) throws IOException {



        /* URL httpsURL = new URL("https://www.google.com.br/"); */
         URL httpsURL = new URL("https://registrar.internet.ee/");

        HttpsURLConnection connection = (HttpsURLConnection) httpsURL.openConnection();
        connection.connect();
        Certificate[] certs = connection.getServerCertificates();
        for (Certificate cer : certs) {
            System.out.println(cer.getPublicKey());
            System.out.println(cer.getType());

        }
        System.out.println(certs[0].toString());


    }
}
