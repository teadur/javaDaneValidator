package JavaDaneValidator;

import java.util.Objects;

/**
 * Created by georg on 7.01.2017.
 */
public class Validate {
    public String CertHashCompear(String dns, String cert)

    {

        String response;

        if (Objects.equals(cert, dns)) {
            response="SSL and DNS data match";
            System.out.println("vordsed");
        }
        else
            {
                System.out.println("CERT:"+cert.length());
                System.out.println("DNS:"+dns.length());
                response="KATKI";
            }
        return response;
    }

}
