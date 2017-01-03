package JavaDaneValidator;

import org.xbill.DNS.*;
import java.util.Iterator;

/**
 * Created by georg on 3.01.2017.
 */
public class GetDNS {


    public static void main(String validateurl) throws Exception {

/**
 Default inet resolver example
 InetAddress addr = Address.getByName("data.internet.ee");
 System.out.print(addr);

 **/

        /**
         * GET MX record example
         Record [] records = new Lookup("gmail.com", Type.MX).run();
         for (int i = 0; i < records.length; i++) {
         MXRecord mx = (MXRecord) records[i];
         System.out.println("Host " + mx.getTarget() + " has preference " + mx.getPriority());

         }
         **/

        /**
         GET TLSA Record example
         Record [] records = new Lookup("_443._tcp.data.internet.ee", Type.TLSA).run();
         for (int i = 0; i < records.length; i++) {

         TLSARecord tlsaRecord = (TLSARecord) records[i];
         System.out.println("Host ");

         } **/

        String ipAddress = "2.0.0.127";
        String dnsblDomain = "sbl-xbl.spamhaus.org";
        /** String validateurl = "data.internet.ee"; **/

        /** Lookup lookup = new Lookup(ipAddress + "." + dnsblDomain, Type.ANY); **/

        /**
         *  Because we handle only https url lets hardcode/append _443._tcp." to validateurl
         */
        Lookup lookup = new Lookup("_443._tcp." + validateurl, Type.ANY);

        /** Lookup lookup = new Lookup("internet.ee", Type.ANY); **/

/**
 * for some reason using system resolver doesnot work, lets hardcode google
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
                /** we dont actualy care about TEXTRecords remove me later **/
                if(records[i] instanceof TXTRecord)
                {
                    TXTRecord txt = (TXTRecord) records[i];
                    for(Iterator j = txt.getStrings().iterator(); j.hasNext();)
                    {
                        responseMessage += (String)j.next();
                    }
                }

                /** lets handle TLSA records **/

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
                    /** for(Iterator j = tlsaRecord.getName(); j.hasNext();)
                     {
                     responseMessage += (String)j.next();
                     } **/

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


}