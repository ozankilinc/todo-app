package com.todo.app.userservice.config;
import com.todo.app.userservice.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.couchbase.CouchbaseClientFactory;
import org.springframework.data.couchbase.SimpleCouchbaseClientFactory;
import org.springframework.data.couchbase.config.AbstractCouchbaseConfiguration;
import org.springframework.data.couchbase.core.CouchbaseTemplate;
import org.springframework.data.couchbase.core.convert.MappingCouchbaseConverter;
import org.springframework.data.couchbase.repository.config.RepositoryOperationsMapping;

@Configuration
@ConditionalOnProperty(name = "unittest", havingValue = "false", matchIfMissing = true)
@RequiredArgsConstructor
public class CouchbaseConfig extends AbstractCouchbaseConfiguration {

    private final CouchBasePropertyConfig couchBasePropertyConfig;

    @Override
    public String getConnectionString() {
        return couchBasePropertyConfig.getConnection();
    }

    @Override
    public String getUserName() {
        return couchBasePropertyConfig.getUsername();
    }

    @Override
    public String getPassword() {
        return couchBasePropertyConfig.getPassword();
    }

    @Override
    public String getBucketName() {
        return couchBasePropertyConfig.getBucketUser().getName();
    }

    @Override
    protected boolean autoIndexCreation() {
        return true;
    }

    @Override
    public void configureRepositoryOperationsMapping(RepositoryOperationsMapping baseMapping) {
        baseMapping
                .mapEntity(User.class, userTemplate());
    }

    @Bean
    public CouchbaseClientFactory userClientFactory() {
        return new SimpleCouchbaseClientFactory(getConnectionString(), authenticator(), couchBasePropertyConfig.getBucketUser().getName());
    }

    @Bean
    public CouchbaseTemplate userTemplate(){
        return new CouchbaseTemplate(userClientFactory(), new MappingCouchbaseConverter());
    }

}
