package JavaDaneValidator; /**
 * Created by georg on 3.10.2016.
 */


import java.io.IOException;
import java.net.URL;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.security.KeyManagementException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.cert.Certificate;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateParsingException;
import java.security.cert.X509Certificate;


public class GetCert {

    String url;
    int algo;
    String hexDataFromCert;


    public String GetDigest(String argument,int matchingtype) throws NoSuchAlgorithmException, KeyManagementException, IOException, CertificateEncodingException, CertificateParsingException {

        url = argument;
        algo = matchingtype;

        /**
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
    /**
     * end of the fix
     **/

        URL httpsURL = new URL("https://"+ url);

        HttpsURLConnection connection = (HttpsURLConnection) httpsURL.openConnection();
        try {
            connection.connect();
        }
        catch(java.net.UnknownHostException e){
            return "Unknown Host";
        }

        Certificate[] certs = connection.getServerCertificates();
        for (Certificate cer : certs) {
            System.out.println(cer.getPublicKey());
            System.out.println(cer.getType());

        }
        X509Certificate x509=(X509Certificate)certs[0];
        /**TODO: algset urli tuleks võrrelda alternatiivsete nimede vastu **/
        System.out.println(x509.getSubjectAlternativeNames());


        /* Collection<?> names = x509.getSubjectAlternativeNames();
        for (Collection<?> name : names) {
            System.out.println(name);
        } */

        /*

        if (x509.getSubjectAlternativeNames().contains("data.internet.ee"))   {
            return "Offered certificate doesnot match url";
        }
        */

        /* if (x509.getSubjectAlternativeNames().contains(url)) {
            System.out.println("yipkayee");
        } */


        /* System.out.println(x509.getSignature()); */
        byte[] hash;


        /** digest is chosen according to | https://tools.ietf.org/html/rfc6698#section-7.4 **/

        if (algo == 1) {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            hash = digest.digest(certs[0].getEncoded());
            hexDataFromCert = bytesToHexString(hash);
        }

        else if (algo == 2) {
            MessageDigest digest = MessageDigest.getInstance("SHA-512");
            hash = digest.digest(certs[0].getEncoded());
            hexDataFromCert = bytesToHexString(hash);
        }

        else {
            hash = certs[0].getEncoded();
            hexDataFromCert = bytesToHexString(hash);
        }

        return hexDataFromCert.toUpperCase();
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

    public static void main(String[] args) {

    }
}
