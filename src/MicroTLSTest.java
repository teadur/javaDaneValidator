/* import org.bouncycastle.asn1.DERInteger;
import org.bouncycastle.asn1.DERObject;
import org.bouncycastle.asn1.DERSequence;
import org.bouncycastle.asn1.x509.X509CertificateStructure;
import org.bouncycastle.crypto.DataLengthException;
import org.bouncycastle.crypto.digests.SHA1Digest;
import org.bouncycastle.crypto.params.RSAKeyParameters;
import org.bouncycastle.crypto.tls.CertificateVerifyer;
import org.bouncycastle.crypto.tls.TlsProtocolHandler;
import org.junit.Test;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class MicroTLSTest {

    @Test
    public void testConnect() throws UnknownHostException, IOException {
        Socket s = new Socket(InetAddress.getByName("localhost"), 10000);

        TlsProtocolHandler tlsHandler = new TlsProtocolHandler(s.getInputStream(), s.getOutputStream());
        tlsHandler.connect(new CertificateVerifyer() {

            @Override
            public boolean isValid(X509CertificateStructure[] certs) {
                System.out.println(certs);
                // get to-be-signed certificate (according to spec signature of DER encoded TBS cert is taken)
                byte [] toSign = certs[0].getTBSCertificate().getDEREncoded();
                System.out.println(certs[1].getIssuer().toString());
                try {
                    // taking public key of parent certificate in chain
                    DERObject publicKey = certs[1].getSubjectPublicKeyInfo().getPublicKey();

                    // assuming public key is RSA, for RSA publicKey is a sequence of two big integers
                    // proper key type could be obtained by algorithm OID
                    DERSequence asn1Object = (DERSequence) publicKey.toASN1Object();
                    DERInteger modulusInteger = (DERInteger)asn1Object.getObjectAt(0);
                    DERInteger expInteger = (DERInteger) asn1Object.getObjectAt(1);

                    RSAKeyParameters cp = new RSAKeyParameters(false, modulusInteger.getValue(), expInteger.getValue());

                    byte[] actual = certs[0].getSignature().getBytes();

                    // assuming hash algorithm is SHA-1
                    // proper hash algorithm could be obtained by algorithm OID
                    SHA1Digest digest = new SHA1Digest();

                    // sign and verify
                    RSADigestSigner signer = new RSADigestSigner(digest);
                    signer.init(false, cp);

                    signer.update(toSign, 0, toSign.length);
                    boolean isValid = signer.verifySignature(actual);
                    System.out.println(isValid);

                } catch (IOException e) {
                    e.printStackTrace();
                } catch (DataLengthException e) {
                    e.printStackTrace();
                }
                return true;
            }
        });


        String get = "GET / HTTP/1.0\n\r\n";
        tlsHandler.getTlsOuputStream().write(get.getBytes("ascii"));
        byte [] b = new byte[1024];

        int count = tlsHandler.getTlsInputStream().read(b);
        while (count != -1) {
            System.out.println(new String(b, 0, count, "utf-8"));
            count = tlsHandler.getTlsInputStream().read(b);
        }
        //System.out.println(new String(b, 0, count, "utf-8"));

        get = "GET / HTTP/1.0\n\r\n";
        tlsHandler.getTlsOuputStream().write(get.getBytes("ascii"));
        b = new byte[1024];

        count = tlsHandler.getTlsInputStream().read(b);
        while (count != -1) {
            System.out.println(new String(b, 0, count, "utf-8"));
            count = tlsHandler.getTlsInputStream().read(b);
        }

        tlsHandler.close();
    }

}
*/