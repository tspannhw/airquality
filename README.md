## airquality

Example code for Spring IO 2022 Barcelona, Spain

Timothy Spann

### Setup

* Visual Code with Spring & Java 11
* Set an environment variable with your api key code from airnow
* Point to your Apache Pulsar cluster, if you are using StreamNative cloud I have SSL and configuration in the config class

### src/main/resources/application.resources

````
airnowapi.url=${AIRPORTNOWAPIURL}
topic.name=persistent://public/default/airquality
producer.name=airquality
send.timeout=60
security.mode=on
#off
#pulsar.service.url=pulsar://pulsar1:6650
pulsar.service.url=pulsar+ssl://demo.demo.snio.cloud:6651
pulsar.oauth2.audience=urn:sn:pulsar:demo:demo-cluster
pulsar.oauth2.credentials-url=file:///Users/tspann/Downloads/sndemo-tspann.json
pulsar.oauth2.issuer-url=https://auth.streamnative.cloud/
````

### Spark Run

````
val dfPulsar = spark.readStream.format("pulsar").option("service.url", "pulsar://pulsar1:6650").option("admin.url", "http://pulsar1:8080").option("topic", "persistent://public/default/airquality").load()

dfPulsar.printSchema()

scala> dfPulsar.printSchema()
root
 |-- additionalProperties: map (nullable = true)
 |    |-- key: string
 |    |-- value: struct (valueContainsNull = false)
 |-- aqi: integer (nullable = true)
 |-- category: struct (nullable = true)
 |    |-- additionalProperties: map (nullable = true)
 |    |    |-- key: string
 |    |    |-- value: struct (valueContainsNull = false)
 |    |-- name: string (nullable = true)
 |    |-- number: integer (nullable = true)
 |-- dateObserved: string (nullable = true)
 |-- hourObserved: integer (nullable = true)
 |-- latitude: double (nullable = true)
 |-- localTimeZone: string (nullable = true)
 |-- longitude: double (nullable = true)
 |-- parameterName: string (nullable = true)
 |-- reportingArea: string (nullable = true)
 |-- stateCode: string (nullable = true)
 |-- __key: binary (nullable = true)
 |-- __topic: string (nullable = true)
 |-- __messageId: binary (nullable = true)
 |-- __publishTime: timestamp (nullable = true)
 |-- __eventTime: timestamp (nullable = true)
 |-- __messageProperties: map (nullable = true)
 |    |-- key: string
 |    |-- value: string (valueContainsNull = true)



## Example Queries

val pQuery = dfPulsar.selectExpr("*").writeStream.format("console").option("truncate", false).start()

val pQuery = dfPulsar.selectExpr("CAST(__key AS STRING)", 
                                 "CAST(aqi AS INTEGER)",
                                 "CAST(dateObserved AS STRING)",
                                 "CAST(hourObserved AS INTEGER)",
                                 "CAST(latitude AS DOUBLE)",
                                 "CAST(localTimeZone AS STRING)",
                                 "CAST(longitude AS DOUBLE)",
                                 "CAST(parameterName AS STRING)",
                                 "CAST(reportingArea AS STRING)",
                                 "CAST(stateCode AS STRING)")
                                 .as[(String, Integer, String, Integer, Double, String, Double, String, String, String)]
            .writeStream.format("csv")
            .option("truncate", "false")
            .option("header", true)
            .option("path", "/opt/demo/airquality")
            .option("checkpointLocation", "/tmp/checkpoint")
            .start()


pQuery.explain()


pQuery: org.apache.spark.sql.DataFrame = [__key: string, aqi: int ... 8 more fields]

scala>                                  .as[(String, Integer, String, Integer, Double, String, Double, String, String, String)]
res7: org.apache.spark.sql.Dataset[(String, Integer, String, Integer, Double, String, Double, String, String, String)] = [__key: string, aqi: int ... 8 more fields]

scala>             .writeStream.format("csv")
res8: org.apache.spark.sql.streaming.DataStreamWriter[(String, Integer, String, Integer, Double, String, Double, String, String, String)] = org.apache.spark.sql.streaming.DataStreamWriter@28af99b4

scala>             .option("truncate", "false")
res9: org.apache.spark.sql.streaming.DataStreamWriter[(String, Integer, String, Integer, Double, String, Double, String, String, String)] = org.apache.spark.sql.streaming.DataStreamWriter@28af99b4

scala>             .option("header", true)
res10: org.apache.spark.sql.streaming.DataStreamWriter[(String, Integer, String, Integer, Double, String, Double, String, String, String)] = org.apache.spark.sql.streaming.DataStreamWriter@28af99b4

scala>             .option("path", "/opt/demo/airquality")
res11: org.apache.spark.sql.streaming.DataStreamWriter[(String, Integer, String, Integer, Double, String, Double, String, String, String)] = org.apache.spark.sql.streaming.DataStreamWriter@28af99b4

scala>             .option("checkpointLocation", "/tmp/checkpoint")
res12: org.apache.spark.sql.streaming.DataStreamWriter[(String, Integer, String, Integer, Double, String, Double, String, String, String)] = org.apache.spark.sql.streaming.DataStreamWriter@28af99b4

scala>             .start()
22/04/07 17:43:34 WARN ResolveWriteToStream: spark.sql.adaptive.enabled is not supported in streaming DataFrames/Datasets and will be disabled.
res13: org.apache.spark.sql.streaming.StreamingQuery = org.apache.spark.sql.execution.streaming.StreamingQueryWrapper@29b96be0

pQuery.explain()                                                                
== Physical Plan ==
*(1) Project [cast(__key#28 as string) AS __key#156, aqi#18, dateObserved#20, hourObserved#21, latitude#22, localTimeZone#23, longitude#24, parameterName#25, reportingArea#26, stateCode#27]
+- StreamingRelation pulsar, [additionalProperties#17, aqi#18, category#19, dateObserved#20, hourObserved#21, latitude#22, localTimeZone#23, longitude#24, parameterName#25, reportingArea#26, stateCode#27, __key#28, __topic#29, __messageId#30, __publishTime#31, __eventTime#32, __messageProperties#33]



drwxr-xr-x 2 root root 4096 Apr  7 17:44 _spark_metadata
-rw-r--r-- 1 root root  282 Apr  7 17:44 part-00000-249b23ad-ed90-4cd6-a8d1-3adbe7976815-c000.csv
-rw-r--r-- 1 root root  192 Apr  7 17:44 part-00000-24a6f1a6-1b71-4087-ac53-67739a1090d1-c000.csv
-rw-r--r-- 1 root root  282 Apr  7 17:44 part-00000-b3c5c7e8-a06e-4aa2-a321-45ce213e8bd4-c000.csv
-rw-r--r-- 1 root root  192 Apr  7 17:44 part-00000-ad4e60c8-6d55-4cd8-aea4-a5314272dc25-c000.csv
-rw-r--r-- 1 root root  107 Apr  7 17:43 part-00000-3c10eaf9-17e9-46fe-b58b-d8121f02c850-c000.csv
root@pulsar1:/opt/demo/airquality# cat part-00000-249b23ad-ed90-4cd6-a8d1-3adbe7976815-c000.csv
__key,aqi,dateObserved,hourObserved,latitude,localTimeZone,longitude,parameterName,reportingArea,stateCode
8107a050-1c59-4d67-aabd-9752156662c5,16,2022-04-07,16,33.65,EST,-84.43,PM2.5,Atlanta,GA
11bd646a-f464-4035-9095-3376e5a55c8e,14,2022-04-07,16,33.65,EST,-84.43,PM10,Atlanta,GA



````

### Example Run

````

export AIRPORTNOWAPIURL="https://www.airnowapi.org/aq/observation/zipCode/current/?format=application/json&zipCode=30313&distance=25&API_KEY=THISISMYKEYITISCOOL

[INFO] --- spring-boot-maven-plugin:2.7.0:run (default-cli) @ airquality ---
[INFO] Attaching agents: []

  .   ____          _            __ _ _
 /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
 \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
  '  |____| .__|_| |_|_| |_\__, | / / / /
 =========|_|==============|___/=/_/_/_/
 :: Spring Boot ::                (v2.7.0)

2022-04-07 14:21:25.852  INFO 74819 --- [           main] d.d.airquality.airquality.AirQualityApp  : Starting AirQualityApp using Java 17.0.1 on Timothys-mbp.fios-router.home with PID 74819 (/Users/tspann/Documents/code/airquality/airquality/target/classes started by tspann in /Users/tspann/Documents/code/airquality/airquality)
2022-04-07 14:21:25.854  INFO 74819 --- [           main] d.d.airquality.airquality.AirQualityApp  : No active profile set, falling back to 1 default profile: "default"
2022-04-07 14:21:26.580  WARN 74819 --- [           main] .i.n.r.d.DnsServerAddressStreamProviders : Can not find org.apache.pulsar.shade.io.netty.resolver.dns.macos.MacOSDnsServerAddressStreamProvider in the classpath, fallback to system defaults. This may result in incorrect DNS resolutions on MacOS.
2022-04-07 14:21:26.922  INFO 74819 --- [           main] o.s.b.web.embedded.netty.NettyWebServer  : Netty started on port 8080
2022-04-07 14:21:26.928  INFO 74819 --- [           main] d.d.airquality.airquality.AirQualityApp  : Started AirQualityApp in 1.312 seconds (JVM running for 1.553)
2022-04-07 14:21:28.412  INFO 74819 --- [r-client-io-1-1] o.a.pulsar.client.impl.ConnectionPool    : [[id: 0xd6d7c47b, L:/192.168.1.63:57337 - R:pulsar1.fios-router.home/192.168.1.230:6650]] Connected to server
2022-04-07 14:21:28.466  INFO 74819 --- [r-client-io-1-1] o.a.p.c.impl.ProducerStatsRecorderImpl   : Starting Pulsar producer perf with config: {"topicName":"persistent://public/default/airquality","producerName":"airquality","sendTimeoutMs":600000,"blockIfQueueFull":false,"maxPendingMessages":1000,"maxPendingMessagesAcrossPartitions":50000,"messageRoutingMode":"RoundRobinPartition","hashingScheme":"JavaStringHash","cryptoFailureAction":"FAIL","batchingMaxPublishDelayMicros":1000,"batchingPartitionSwitchFrequencyByPublishDelay":10,"batchingMaxMessages":1000,"batchingMaxBytes":131072,"batchingEnabled":true,"chunkingEnabled":false,"compressionType":"NONE","initialSequenceId":null,"autoUpdatePartitions":true,"autoUpdatePartitionsIntervalSeconds":60,"multiSchema":true,"accessMode":"Shared","lazyStartPartitionedProducers":false,"properties"urrentLookupRequest":5000,"maxLookupRequest":50000,"maxLookupRedirects":20,"maxNumberOfRejectedRequestPerConnection":50,"keepAliveIntervalSeconds":30,"connectionTimeoutMs":10000,"requestTimeoutMs":60000,"initialBackoffIntervalNanos":100000000,"maxBackoffIntervalNanos":60000000000,"enableBusyWait":false,"listenerName":null,"useKeyStoreTls":false,"sslProvider":null,"tlsTrustStoreType":"JKS","tlsTrustStorePath":null,"tlsTrustStorePassword":null,"tlsCiphers":[],"tlsProtocols":[],"memoryLimitBytes":0,"proxyServiceUrl":null,"proxyProtocol":null,"enableTransaction":false,"socks5ProxyAddress":null,"socks5ProxyUsername":null,"socks5ProxyPassword":null}
2022-04-07 14:21:28.486  INFO 74819 --- [r-client-io-1-1] o.a.pulsar.client.impl.ConnectionPool    : [[id: 0xcbd932d6, L:/192.168.1.63:57338 - R:pulsar1.fios-router.home/192.168.1.230:6650]] Connected to server
2022-04-07 14:21:28.487  INFO 74819 --- [r-client-io-1-1] org.apache.pulsar.client.impl.ClientCnx  : [id: 0xcbd932d6, L:/192.168.1.63:57338 - R:pulsar1.fios-roeated producer on cnx [id: 0xcbd932d6, L:/192.168.1.63:57338 - R:pulsar1.fios-router.home/192.168.1.230:6650]
2022-04-07 14:21:28.525  WARN 74819 --- [           main] c.s.circe.checksum.Crc32cIntChecksum     : Failed to load Circe JNI library. Falling back to Java based CRC32c provider
2022-04-07 14:21:28.537  INFO 74819 --- [r-client-io-1-1] o.a.pulsar.client.impl.ProducerImpl      : [persistent://public/default/airquality] [airquality] Closed Producer
2022-04-07 14:21:28.546  INFO 74819 --- [r-client-io-1-1] o.a.p.c.impl.ProducerStatsRecorderImpl   : Starting Pulsar producer perf with config: {"topicName":"persistent://public/default/airquality","producerName":"airquality","sendTimeoutMs":600000,"blockIfQueueFull":false,"maxPendingMessages":1000,"maxPendingMessagesAcrossPartitions":50000,"messageRoutingMode":"RoundRobinPartition","hashingScheme":"JavaStringHash","cryptoFailureAction":"FAIL","batchingMaxPublishDelayMicros":1000,"batchingPartitionSwitchFrequencyByPublishDelay":10,"batchingMaxMessages":1000,"operationTimeoutMs":30000,"lookupTimeoutMs":30000,"statsIntervalSeconds":60,"numIoThreads":1,"numListenerThreads":1,"connectionsPerBroker":1,"useTcpNoDelay":true,"useTls":false,"tlsTrustCertsFilePath":"","tlsAllowInsecureConnection":false,"tlsHostnameVerificationEnable":false,"concurrentLookupRequest":5000,"maxLookupRequest":50000,"maxLookupRedirects":20,"maxNumberOfRejectedRequestPerConnection":50,"keepAliveIntervalSeconds":30,"connectionTimeoutMs":10000,"requestTimeoutMs":60000,"initialBackoffIntervalNanos":100000000,"maxBackoffIntervalNanos":60000000000,"enableBusyWait":false,"listenerName":null,"useKeyStoreTls":false,"sslProvider":null,"tlsTrustStoreType":"JKS","tlsTrustStorePath":null,"tlsTrustStorePassword":null,"tlsCiphers":[],"tlsProtocols":[],"memoryLimitBytes":0,"proxyServiceUrl":null,"proxyProtocol":null,"enableTransaction":false,"socks5ProxyAddress":null,"socks5ProxyUsername":null,"socks5ProxyPassword":null}
2022-04-07 14:21:28.552  INFO 74819 --- [r-client-io-1-1] o.a.pulsar.client.impl.Produsar.client.impl.ProducerImpl      : [persistent://public/default/airquality] [airquality] Closed Producer
2022-04-07 14:21:28.571  INFO 74819 --- [r-client-io-1-1] o.a.p.c.impl.ProducerStatsRecorderImpl   : Starting Pulsar producer perf with config: {"topicName":"persistent://public/default/airquality","producerName":"airquality","sendTimeoutMs":600000,"blockIfQueueFull":false,"maxPendingMessages":1000,"maxPendingMessagesAcrossPartitions":50000,"messageRoutingMode":"RoundRobinPartition","hashingScheme":"JavaStringHash","cryptoFailureAction":"FAIL","batchingMaxPublishDelayMicros":1000,"batchingPartitionSwitchFrequencyByPublishDelay":10,"batchingMaxM"initialSequenceId":null,"autoUpdatePartitions":true,"autoUpdats":20,"maxNumberOfRejectedRequestPerConnection":50,"keepAliveIntervalSeconds":30,"connectionTimeoutMs":10000,"requestTimeoutMs":60000,"initialBackoffIntervalNanos":100000000,"maxBackoffInte0xcbd932d6, L:/192.168.1.63:57338 - R:pulsar1.fios-router.home/192.168.1.230:6650]
2022-04-07 14:21:28.582  INFO 74819 --- [r-client-io-1-1] o.a.pulsar.client.impl.ProducerImpl     

````

### Flink

````
CREATE CATALOG pulsar WITH (
   'type' = 'pulsar',
   'service-url' = 'pulsar://pulsar1:6650',
   'admin-url' = 'http://pulsar1:8080',
   'format' = 'json'
);

USE CATALOG pulsar;

SHOW TABLES;

Flink SQL> describe airquality;
+----------------------+-----------------------------------------------------------------------------------------------+------+-----+--------+-----------+
|                 name |                                                                                          type | null | key | extras | watermark |
+----------------------+-----------------------------------------------------------------------------------------------+------+-----+--------+-----------+
| additionalProperties |                                                          MAP<STRING NOT NULL, ROW<> NOT NULL> | true |     |        |           |
|                  aqi |                                                                                           INT | true |     |        |           |
|             category | ROW<`additionalProperties` MAP<STRING NOT NULL, ROW<> NOT NULL>, `name` STRING, `number` INT> | true |     |        |           |
|         dateObserved |                                                                                        STRING | true |     |        |           |
|         hourObserved |                                                                                           INT | true |     |        |           |
|             latitude |                                                                                        DOUBLE | true |     |        |           |
|        localTimeZone |                                                                                        STRING | true |     |        |           |
|            longitude |                                                                                        DOUBLE | true |     |        |           |
|        parameterName |                                                                                        STRING | true |     |        |           |
|        reportingArea |                                                                                        STRING | true |     |        |           |
|            stateCode |                                                                                        STRING | true |     |        |           |
+----------------------+-----------------------------------------------------------------------------------------------+------+-----+--------+-----------+

select aqi, parameterName, dateObserved, hourObserved, latitude, longitude, localTimeZone, stateCode, reportingArea from airquality

select max(aqi) as MaxAQI, parameterName, reportingArea from airquality group by parameterName, reportingArea;

select max(aqi) as MaxAQI, min(aqi) as MinAQI, avg(aqi) as AvgAQI, count(aqi) as RowCount, parameterName, reportingArea from airquality group by parameterName, reportingArea;

````


### Command Line Consumer

````
----- got message -----
key:[2e725251-c977-46b4-ae81-45a675a1a473], properties:[], content:{"dateObserved":"2022-04-07 ","hourObserved":13,"localTimeZone":"EST","reportingArea":"Atlanta","stateCode":"GA","latitude":33.65,"longitude":-84.43,"parameterName":"O3","aqi":40,"category":{"number":1,"name":"Good","additionalProperties":{}},"additionalProperties":{}}
----- got message -----
key:[c0ec765d-38b9-4416-bb27-daa80e7654ff], properties:[], content:{"dateObserved":"2022-04-07 ","hourObserved":13,"localTimeZone":"EST","reportingArea":"Atlanta","stateCode":"GA","latitude":33.65,"longitude":-84.43,"parameterName":"PM2.5","aqi":18,"category":{"number":1,"name":"Good","additionalProperties":{}},"additionalProperties":{}}
----- got message -----
key:[7a7f567a-b9d7-470e-992e-86a2c24c9ce8], properties:[], content:{"dateObserved":"2022-04-07 ","hourObserved":13,"localTimeZone":"EST","reportingArea":"Atlanta","stateCode":"GA","latitude":33.65,"longitude":-84.43,"parameterName":"PM10","aqi":17,"category":{"number":1,"name":"Good","additionalProperties":{}},"additionalProperties":{}}

````

### Connecting to Data for Updates/Lookup - ScyllaDB

* https://github.com/tspannhw/airquality-datastore

### Resources

* https://docs.airnowapi.org/
* https://docs.airnowapi.org/webservices
* https://www.airnow.gov/
* https://community.cloudera.com/t5/Community-Articles/Tracking-Air-Quality-with-HDP-and-HDF-Part-1-Apache-NiFi/ta-p/248265


### Other Data Sources Available

* http://feeds.enviroflash.info/rss/forecast/479.xml
* http://feeds.enviroflash.info/

### Other Projects

* https://github.com/tspannhw/FLiPN-AirQuality-REST

### CVE Note

We are using Spring Boot 2.7.0 which does not have this issue.

https://thenewstack.io/springshell-brings-hell-to-java-developers/


