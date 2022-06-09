package dev.datainmotion.airquality.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.cassandra.config.AbstractCassandraConfiguration;
import org.springframework.data.cassandra.config.CqlSessionFactoryBean;
import org.springframework.data.cassandra.config.SchemaAction;
import org.springframework.data.cassandra.repository.config.EnableCassandraRepositories;

import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.Collection;

@Configuration
@EnableCassandraRepositories(
        basePackages = "dev.datainmotion.airquality.repository")
public class ScyllaConfig extends AbstractCassandraConfiguration {
    private static final Logger log = LoggerFactory.getLogger(ScyllaConfig.class);

    public static final String CLOUD = "cloud";
    @Value("${spring.data.cassandra.keyspace-name:airquality}")
    String keyspace;

    @Value("${spring.data.cassandra.port:9042}")
    int scyllaPort;

    @Value("${spring.data.cassandra.contact-points:172.17.0.2}")
    String serverName;

    @Value("${scylla.environment:docker}")
    String scyllaEnvironment;

    @Value("${spring.data.cassandra.username:scylla}")
    String scyllaUserName;

    @Value("${spring.data.cassandra.password:password}")
    String scyllaPassword;

//    @Value("${scylla.local.dc:AWS_US_EAST_1}")
//    String localDataCenter;

    @Override
    protected String getKeyspaceName() {
        return keyspace;
    }

    @Override
    protected String getContactPoints() {
        return serverName;
    }

    @Override
    protected int getPort() {
        return scyllaPort;
    }

    //    public CqlSession session() {

    @Override
    public CqlSessionFactoryBean cassandraSession() {
        CqlSessionFactoryBean factory = new CqlSessionFactoryBean();

        if (scyllaEnvironment.equalsIgnoreCase(CLOUD)) {
            Collection<InetSocketAddress> serverList = new ArrayList<>();
            try {
                String[] containerIpAddress = getContactPoints().split(",");
                for (String ipAddress:
                     containerIpAddress) {
                    InetSocketAddress containerEndPoint = new InetSocketAddress(ipAddress, getPort());
                    serverList.add(containerEndPoint);
                }
            } catch (Throwable e) {
                log.error("Broken ips {}",e);
            }

            log.error("{}={} for {} ", scyllaUserName, scyllaPassword, getKeyspaceName());

            factory.setUsername(scyllaUserName);
            factory.setPassword(scyllaPassword);
            factory.setPort(getPort());
            factory.setKeyspaceName(getKeyspaceName());
            factory.setContactPoints(serverList);
        }
        else {
            factory.setPort(getPort());
            factory.setKeyspaceName(getKeyspaceName());
            factory.setContactPoints(getContactPoints());
        }

        return factory;
    }


    @Override
    public SchemaAction getSchemaAction() {
        return SchemaAction.NONE;
    }
}