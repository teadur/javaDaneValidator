Simple javaDaneValidator based on:
===============

* "DNSJava provides functionality above and beyond that of the InetAddress class."   http://www.dnsjava.org/ for DNSSEC validation"

This little java program uses DNSJava and standard libraries to fetch TLSA record from domain in question.
It's assumed TLSA service is on ort 443(in real life TLSA record could point not only to https but smtps etc), 
the input for DNS query is expanded to _443._tcp.$input.
 
After fetching the TLSA record SSL Certificate from host in question is fetched and hash is calculated from certificate,
depending on the parameters set in TLSA record, after that the calculated hash and hash provided in TLSA record are compeared.

 
 TODO: Implement DNSSEC validation on TLSA record fetch
 
 
