package dev.datainmotion.airquality.config;
import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.CqlSessionBuilder;
import com.datastax.oss.driver.api.core.auth.AuthProvider;
import com.datastax.oss.driver.api.core.auth.PlainTextAuthProviderBase;
import com.datastax.oss.driver.api.core.auth.ProgrammaticPlainTextAuthProvider;
import com.datastax.oss.driver.api.core.context.DriverContext;
import com.datastax.oss.driver.internal.core.auth.PlainTextAuthProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.Resource;
import org.springframework.data.cassandra.config.AbstractCassandraConfiguration;
import org.springframework.data.cassandra.config.DriverConfigLoaderBuilderConfigurer;
import org.springframework.data.cassandra.config.SchemaAction;
import org.springframework.data.cassandra.repository.config.EnableCassandraRepositories;

import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.Collection;

import com.datastax.oss.driver.api.core.CqlIdentifier;
import com.datastax.oss.driver.api.core.config.ProgrammaticDriverConfigLoaderBuilder;
import com.datastax.oss.driver.api.core.config.DriverConfigLoader;
import com.datastax.oss.driver.api.core.config.DefaultDriverOption;
import com.datastax.oss.driver.internal.core.auth.PlainTextAuthProvider;
import com.datastax.oss.driver.api.core.CqlSessionBuilder;

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

    @Value("${scylla.local.dc:AWS_US_EAST_1}")
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

            log.error("{}={} for {} on {}", scyllaUserName, scyllaPassword, getKeyspaceName(), localDataCenter);

            ProgrammaticDriverConfigLoaderBuilder configLoaderBuilder = DriverConfigLoader.programmaticBuilder();
            configLoaderBuilder.withString(DefaultDriverOption.AUTH_PROVIDER_CLASS, PlainTextAuthProviderBase.class.getName());
            configLoaderBuilder.withString(DefaultDriverOption.AUTH_PROVIDER_USER_NAME, scyllaUserName);
            configLoaderBuilder.withString(DefaultDriverOption.AUTH_PROVIDER_PASSWORD, scyllaPassword);

            AuthProvider
            log.error("{} {}", PlainTextAuthProvider.class.getSimpleName(), PlainTextAuthProvider.class.getName());

            CqlSessionBuilder builder = CqlSession.builder()
                    .withLocalDatacenter(localDataCenter)
                    .withConfigLoader(configLoaderBuilder.build())
                    .addContactPoints(serverList)
                    .withKeyspace(getKeyspaceName());

            return builder.build();
        }
        else {
            InetSocketAddress localEndPoint = new InetSocketAddress(getContactPoints(), getPort());
            CqlSessionBuilder builder = CqlSession.builder()
                    .addContactPoint(localEndPoint)
                    .withKeyspace(getKeyspaceName());
            return builder.build();
        }
    }

    @Override
    public SchemaAction getSchemaAction() {
        return SchemaAction.NONE;
    }
}