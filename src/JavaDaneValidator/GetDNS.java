package JavaDaneValidator;

import org.xbill.DNS.*;

import java.net.UnknownHostException;

/**
 * Created by georg on 3.01.2017.
 */
public class GetDNS {

    String url;
    String hash;
    int matchingtype;
    int selector;
    String kogustring;

    public static void main(String[] args)  {


    }


    public String getTLSA(String argument) throws TextParseException, UnknownHostException{

        url = argument;
        Lookup lookup = new Lookup("_443._tcp." + argument, Type.TLSA);

        /**
         * for some reason using system resolver doesnot work, lets hardcode google TODO: seadistatav kastist vaikimisi google
         */

        Resolver resolver = new SimpleResolver("8.8.8.8");
        lookup.setResolver(resolver);
        lookup.setCache(null);
        Record[] records = lookup.run();
        String responseMessage = null;
        if(lookup.getResult() == Lookup.SUCCESSFUL) {


        for (int i = 0; i < records.length; i++)
            {

                if(records[i] instanceof TLSARecord)
                {
                    TLSARecord tlsaRecord = (TLSARecord) records[i];
                    responseMessage += tlsaRecord.getName() + "|";
                    responseMessage += tlsaRecord.toString();
                    /**
                     *  tlsaRecord.getCertificateUsage() = 3 ( esimene väli)
                     *  tlsaRecord.getSelector() = 0 ( teine väli)
                     *  tlsaRecord.getMatchingType() = 1 ( kolmas väli)
                     *  tlsaRecord.getCertificateAssociationData =  B@3d82c5f3 binaar väljund
                     *  Kui tõesti ei saa kuidagi otse õiget väärtust kätte tuleb ilmselt algset stringi lõhkuda
                     */
                    selector = tlsaRecord.getSelector();
                    matchingtype = tlsaRecord.getMatchingType();
                    kogustring =  tlsaRecord.toString();
                    String[] TLSA = tlsaRecord.toString().split(" ");
                    hash = TLSA[3];

                    responseMessage = TLSA[3];
                }
            }

        /* System.err.println("Found!");
        System.err.println("Response Message: " + responseMessage);
        */
    }
        else if(lookup.getResult() == Lookup.HOST_NOT_FOUND)
    {
        System.err.println("Not found.");
        responseMessage = "Not Found.";
        return "Not Found.";
    }
        else
    {
        System.err.println(lookup.getResult());
        System.err.println("Error!");
    }

        System.out.println(kogustring);
        return responseMessage;

    }






}