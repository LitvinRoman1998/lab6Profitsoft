package profitsoft.dao;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public interface IDao<T> {
    @Bean
    void create(T entity);
    @Bean
    T read(long id);
    @Bean
    void update(T entity);
    @Bean
    void delete(long id);

}

