package dev.datainmotion.airquality.config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.cassandra.config.AbstractCassandraConfiguration;
import org.springframework.data.cassandra.config.SchemaAction;
import org.springframework.data.cassandra.repository.config.EnableCassandraRepositories;

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

    @Override
    public SchemaAction getSchemaAction() {
        return SchemaAction.NONE;
    }
}