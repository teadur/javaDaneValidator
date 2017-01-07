package JavaDaneValidator; /**
 * Created by georg on 3.10.2016.
 */
import org.bouncycastle.util.encoders.Base64;

import java.net.URL;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.security.MessageDigest;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;

/* randomcert deps */
import java.math.BigInteger;
import java.security.PublicKey;

public class GetCert {
    public static void main(String[] args) throws Exception {
    /*
     *  fix for
     *    Exception in thread "main" javax.net.ssl.SSLHandshakeException:
     *       sun.security.validator.ValidatorException:
     *           PKIX path building failed: sun.security.provider.certpath.SunCertPathBuilderException:
     *               unable to find valid certification path to requested target
     */
        TrustManager[] trustAllCerts = new TrustManager[] {
                new X509TrustManager() {
                    public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                        return null;
                    }

                    public void checkClientTrusted(X509Certificate[] certs, String authType) {  }

                    public void checkServerTrusted(X509Certificate[] certs, String authType) {  }

                }
        };

        SSLContext sc = SSLContext.getInstance("SSL");
        sc.init(null, trustAllCerts, new java.security.SecureRandom());
        HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());

        // Create all-trusting host name verifier
        HostnameVerifier allHostsValid = new HostnameVerifier() {
            public boolean verify(String hostname, SSLSession session) {
                return true;
            }
        };
        // Install the all-trusting host verifier
        HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);
    /*
     * end of the fix
     */

        URL httpsURL = new URL("https://data.internet.ee/");

        HttpsURLConnection connection = (HttpsURLConnection) httpsURL.openConnection();
        connection.connect();
        Certificate[] certs = connection.getServerCertificates();
        for (Certificate cer : certs) {
            System.out.println(cer.getPublicKey());
            System.out.println(cer.getType());

        }
        System.out.println(certs[0].toString());
        System.out.println("Edasi:");
        System.out.println(certs[0].getEncoded());
        X509Certificate x509=(X509Certificate)certs[0];
        System.out.println(x509.getIssuerDN());
        /* algset urli tuleks võrrelda alternatiivsete nimede vastu */
        System.out.println(x509.getSubjectAlternativeNames());
        System.out.println(x509.getSubjectX500Principal());
        System.out.println(x509.getSubjectUniqueID());
        System.out.println(x509.getSignature().length);
        /* signatuuri tüüpi on vaja digesti arvutamiseks  */
        System.out.println(x509.getSigAlgName());
        System.out.print(x509.getTBSCertificate().toString());
        System.out.println(x509.getSignature());
        byte[] hash;
        String hexDataFromCert;


        /** digest valitakse vastavalt dnsi kirjele **/
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        hash = digest.digest(certs[0].getEncoded());
        hexDataFromCert = bytesToHexString(hash);
        System.out.println(hexDataFromCert);

    }

    /**
     * This method converts bytes array to hex String
     *
     * @param bytes
     * @return
     */
    public static String bytesToHexString(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02x", b & 0xff));
        }
        return sb.toString();
    }
}
