package JavaDaneValidator;

/**
 * Created by georg on 7.01.2017.
 */
public class Validate {
    public String CertHashCompear(String cert, String dns)

    {
        String reponse;

        String response;
        if (cert == dns) {
            response="SSL and DNS data match";
        }
        else
            {
                System.out.println(cert);
                System.out.println(dns);
                response="KATKI";
            }
        return response;
    }

}
