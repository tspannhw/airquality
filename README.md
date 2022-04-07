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

### Example Run

````

export AIRPORTNOWAPIURL="https://www.airnowapi.org/aq/observation/zipCode/current/?format=application/json&zipCode=30313&distance=25&API_KEY=THISISMYKEYITISCOOL

[INFO] --- spring-boot-maven-plugin:2.6.6:run (default-cli) @ airquality ---
[INFO] Attaching agents: []

  .   ____          _            __ _ _
 /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
 \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
  '  |____| .__|_| |_|_| |_\__, | / / / /
 =========|_|==============|___/=/_/_/_/
 :: Spring Boot ::                (v2.6.6)

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

### Command Line Consumer

````
----- got message -----
key:[2e725251-c977-46b4-ae81-45a675a1a473], properties:[], content:{"dateObserved":"2022-04-07 ","hourObserved":13,"localTimeZone":"EST","reportingArea":"Atlanta","stateCode":"GA","latitude":33.65,"longitude":-84.43,"parameterName":"O3","aqi":40,"category":{"number":1,"name":"Good","additionalProperties":{}},"additionalProperties":{}}
----- got message -----
key:[c0ec765d-38b9-4416-bb27-daa80e7654ff], properties:[], content:{"dateObserved":"2022-04-07 ","hourObserved":13,"localTimeZone":"EST","reportingArea":"Atlanta","stateCode":"GA","latitude":33.65,"longitude":-84.43,"parameterName":"PM2.5","aqi":18,"category":{"number":1,"name":"Good","additionalProperties":{}},"additionalProperties":{}}
----- got message -----
key:[7a7f567a-b9d7-470e-992e-86a2c24c9ce8], properties:[], content:{"dateObserved":"2022-04-07 ","hourObserved":13,"localTimeZone":"EST","reportingArea":"Atlanta","stateCode":"GA","latitude":33.65,"longitude":-84.43,"parameterName":"PM10","aqi":17,"category":{"number":1,"name":"Good","additionalProperties":{}},"additionalProperties":{}}

````

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
