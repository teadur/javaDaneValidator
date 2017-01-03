package JavaDaneValidator;

import org.xbill.DNS.*;

import java.net.UnknownHostException;

/**
 * Created by georg on 3.01.2017.
 */
public class GetDNS {


    public static void main(String[] args) throws Exception {

        String validateurl = "data.internet.ee";
        Lookup lookup = new Lookup("_443._tcp." + validateurl, Type.ANY);

/**
 * for some reason using system resolver doesnot work, lets hardcode google TODO: seadistatav kastist vaikimisi google
 */
        Resolver resolver = new SimpleResolver("8.8.8.8");
        lookup.setResolver(resolver);
        lookup.setCache(null);
        Record[] records = lookup.run();
        if(lookup.getResult() == Lookup.SUCCESSFUL)
        {
            System.err.println("Victory");
            String responseMessage = null;
            String listingType = null;
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
                    String[] kogustring = tlsaRecord.toString().split(" ");

                    listingType = kogustring[3];
                }

                else if(records[i] instanceof ARecord)
                {
                    listingType = ((ARecord)records[i]).getAddress().getHostAddress();
                }
            }

            System.err.println("Found!");
            System.err.println("Response Message: " + responseMessage);
            System.err.println("Listing Type: " + listingType);
        }
        else if(lookup.getResult() == Lookup.HOST_NOT_FOUND)
        {
            System.err.println("Not found.");
        }
        else
        {
            System.err.println(lookup.getResult());
            System.err.println("Error!");
        }

    }

    public String muutuja(String argument) {

        return argument;
    }

    public String getTLSA(String argument) throws TextParseException, UnknownHostException{
        Lookup lookup = new Lookup("_443._tcp." + argument, Type.TLSA);
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
                    String[] kogustring = tlsaRecord.toString().split(" ");

                    responseMessage = kogustring[3];
                }
            }

        System.err.println("Found!");
        System.err.println("Response Message: " + responseMessage);
    }
        else if(lookup.getResult() == Lookup.HOST_NOT_FOUND)
    {
        System.err.println("Not found.");
        return "Not Found.";
    }
        else
    {
        System.err.println(lookup.getResult());
        System.err.println("Error!");
    }


        return responseMessage;
    }



}