package JavaDaneValidator; /**
 * Created by georg on 3.10.2016.
 */


import java.io.IOException;
import java.net.URL;
import javax.net.ssl.*;
import java.security.KeyManagementException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.cert.Certificate;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateParsingException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;


public class GetCert {

    String url;
    int algo;
    int selector ;
    int usage;
    String hexDataFromCert;
    boolean domainmatch;



    public String GetDigest(String argument,int matchingtype, int cert, boolean dnsok, int certusage) throws NoSuchAlgorithmException, KeyManagementException, IOException, CertificateEncodingException, CertificateParsingException {

        url = argument;
        algo = matchingtype;
        selector = cert;
        usage = certusage;



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
        /* HostnameVerifier allHostsValid = new HostnameVerifier() {
            public boolean verify(String hostname, SSLSession session) {
                return true;
            }
        }; */
        // Install the all-trusting host verifier
        /* HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid); */



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
        /**TODO: algset urli tuleks võrrelda alternatiivsete nimede vastu  - tegelikult lahendab selle ära libra juba ise, püüda aga oleks vaja kinni antud olukorra exception **/
        System.out.println(x509.getSubjectAlternativeNames());

        if (x509.getSubjectAlternativeNames().toString().contains(url)) {

            System.out.println("DEBUG: Certificate name is present");
            System.out.println(x509.getSubjectAlternativeNames().toString());
            domainmatch = true;

        }
        System.out.println(domainmatch);
        /**TODO:  mingil põhjusel ei jõuta kunagi siia **/
        if (!domainmatch) {
            System.out.println("DEBUG: FAIL name not present");
            return "Certificate doesnot match domain";
        }


        // System.out.println(x509.getPublicKey());
        byte[] hash;
        byte[] finalcert;

        // certusage=0 (CA contrain) tuleb võtta cert 1 - või tegelikkuses kui chain oleks pikem siis n'nda seest
        // certusage=3 (Domain constrain) tuleb võtta cert 0
        // on olemas veel certusage 1 ja 2 aga need pole hetkel minul implementeeritud. https://tools.ietf.org/html/rfc6698#section-7.2

        if (certusage == 0) {
            finalcert = certs[1].getEncoded();
        }
        else if (certusage == 3) {
            if (selector == 0) {
                finalcert = certs[0].getEncoded();
            }

            else  { finalcert = certs[0].getPublicKey().getEncoded(); }

        }
        else finalcert = certs[0].getEncoded();



        /** digest is chosen according to | https://tools.ietf.org/html/rfc6698#section-7.4 **/
        /** Vastavalt algoritmile valime millist hashi kasutame, vastavalt selectorile saame teada kas hash tuleb v
         * võtta kogu sertifikaadist või pubkeyst**/
        System.out.println("Selector: "+ selector);
        if (algo == 1) {

            MessageDigest digest = MessageDigest.getInstance("SHA-256");
                hash = digest.digest(finalcert);
                hexDataFromCert = bytesToHexString(hash);
        }

        else if (algo == 2) {
            MessageDigest digest = MessageDigest.getInstance("SHA-512");
                hash = digest.digest(finalcert);
                hexDataFromCert = bytesToHexString(hash);
        }

        else {
            if (dnsok == true && algo == 0) {
                /** TODO: testimiseks/implementeerimiseks oleks vaja leida üks ilma hashita TLSA kirje, hetkel see asi katki **/

                hexDataFromCert = bytesToHexString(finalcert);
                System.out.println("No hash debug");
                return "siit tuleb hashita vastus";
            }
            else {

                return "DNS didnot resolve, wont bother with SSL";
            }
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
