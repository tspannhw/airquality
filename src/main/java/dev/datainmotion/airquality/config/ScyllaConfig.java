package dev.datainmotion.airquality.config;

import com.datastax.oss.driver.api.core.CqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.cassandra.config.AbstractCassandraConfiguration;
import org.springframework.data.cassandra.config.CqlSessionFactoryBean;
import org.springframework.data.cassandra.config.SchemaAction;
import org.springframework.data.cassandra.core.CassandraOperations;
import org.springframework.data.cassandra.core.CassandraTemplate;
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

    @Autowired
    private CassandraOperations cassandraTemplate;


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


    public CqlSession session() {
        return CqlSession.builder()
                .addContactPoint(InetSocketAddress.createUnresolved(getContactPoints(), 9042))
                .withAuthCredentials(scyllaUserName, scyllaPassword)
                .withKeyspace(getKeyspaceName()).build();
    }


    @Bean
    @ConditionalOnMissingBean
    public CassandraTemplate cassandraTemplate(CqlSession session) throws Exception {
        return new CassandraTemplate(session);
    }

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