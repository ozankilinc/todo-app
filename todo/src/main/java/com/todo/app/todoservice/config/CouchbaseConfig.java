package com.todo.app.todoservice.config;
import com.todo.app.todoservice.entity.TodoList;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.couchbase.CouchbaseClientFactory;
import org.springframework.data.couchbase.SimpleCouchbaseClientFactory;
import org.springframework.data.couchbase.config.AbstractCouchbaseConfiguration;
import org.springframework.data.couchbase.core.CouchbaseTemplate;
import org.springframework.data.couchbase.core.convert.MappingCouchbaseConverter;
import org.springframework.data.couchbase.repository.config.RepositoryOperationsMapping;

@Configuration
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
        return couchBasePropertyConfig.getBucketTodo().getName();
    }

    @Override
    protected boolean autoIndexCreation() {
        return true;
    }

    @Override
    public void configureRepositoryOperationsMapping(RepositoryOperationsMapping baseMapping) {
        baseMapping
                .mapEntity(TodoList.class, todoTemplate());
    }

    @Bean
    public CouchbaseClientFactory todoClientFactory() {
        return new SimpleCouchbaseClientFactory(getConnectionString(), authenticator(), couchBasePropertyConfig.getBucketTodo().getName());
    }

    @Bean
    public CouchbaseTemplate todoTemplate(){
        return new CouchbaseTemplate(todoClientFactory(), new MappingCouchbaseConverter());
    }

}
