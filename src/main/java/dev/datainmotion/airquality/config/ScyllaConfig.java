package dev.datainmotion.airquality.config;
import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.CqlSessionBuilder;
import com.datastax.oss.driver.api.core.auth.AuthProvider;
import com.datastax.oss.driver.internal.core.auth.PlainTextAuthProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.cassandra.config.AbstractCassandraConfiguration;
import org.springframework.data.cassandra.config.SchemaAction;
import org.springframework.data.cassandra.repository.config.EnableCassandraRepositories;

import java.net.InetSocketAddress;

@Configuration
@EnableCassandraRepositories(
        basePackages = "dev.datainmotion.airquality.repository")
public class ScyllaConfig extends AbstractCassandraConfiguration {

    @Value("${spring.data.cassandra.keyspace-name:airquality}")
    String keyspace;

    @Value("${spring.data.cassandra.port:9042}")
    int scyllaPort;

    @Value("${spring.data.cassandra.contact-points:172.17.0.2}")
    String serverName;

    @Value("${scylla.environment:off}")
    String scyllaEnvironment;

    @Value("${spring.data.cassandra.username:scylla}")
    String scyllaUserName;

    @Value("${spring.data.cassandra.password:password}")
    String scyllaPassword;

    @Value("${scylla.local.dc:AWS_US_EAST_1")
    String localDataCenter;

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

    @Bean(name = "dbSession")
    @Primary
    public CqlSession session() {
        String containerIpAddress = getContactPoints();
        int containerPort = getPort();
        InetSocketAddress containerEndPoint = new InetSocketAddress(containerIpAddress, containerPort);

        CqlSessionBuilder builder = CqlSession.builder().withLocalDatacenter(localDataCenter).addContactPoint(containerEndPoint)
                .withAuthCredentials(scyllaUserName, scyllaPassword)
                .withKeyspace(getKeyspaceName());

        return builder.build();
    }

    @Override
    public SchemaAction getSchemaAction() {
        return SchemaAction.NONE;
    }
}