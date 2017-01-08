package JavaDaneValidator;

import javafx.scene.paint.Paint;
import javafx.scene.shape.Shape;

import java.util.Objects;

import static javafx.scene.paint.Color.GREEN;
import static javafx.scene.paint.Color.RED;

/**
 * Created by georg on 7.01.2017.
 */
public class Validate {
    Paint varv;
    public String CertHashCompear(String dns, String cert, int certusage, int selector, int matchingtype)

    {

        String response;


        if (Objects.equals(cert, dns)) {
            response="Hashes Match | ";
            if (certusage == 0)
            { response += "CA constrained | "; }
            if (certusage == 3)
            { response += "Domain issued | "; }

            if (selector == 0)
            { response += "from FULL Certificate | "; }
            if (selector == 1)
            { response += "from Certificate Publickey | "; }

            if (matchingtype == 1)
            { response += "SHA-256 algorithm used"; }

            if (matchingtype == 2) {
                response += "SHA-512 algorithm used";
            }


            System.out.println("vordsed");
            varv = GREEN;
        }
        else
            {
                System.out.println("CERT:"+cert.length());
                System.out.println("DNS:"+dns.length());
                response="KATKI";
                varv = RED;
            }
        return response;
    }

}
