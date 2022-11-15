package org.tinger.cache.couchbase;

public class CouchbaseProviderTest {
    public static void main(String[] args) {
        CouchbaseProvider provider = new CouchbaseProvider("127.0.0.1", "Administrator", "123456");
        String str = "Configure For Multi-Cluster, Multi-Region, & Multi-Cloud. Get Started On Couchbase Today. Enterprise-Class, Multi-Cloud to Edge Modern NoSQL Database. Download Couchbase Today。Full-Text Search at Scale。Hybrid-Cloud Architecture。Memory-First Architecture。";
        provider.put("test", str);
        System.out.println(provider.get(str));
    }
}
