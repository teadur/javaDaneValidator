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
    public String CertHashCompear(String dns, String cert)

    {

        String response;

        if (Objects.equals(cert, dns)) {
            response="SSL and DNS data match";
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
