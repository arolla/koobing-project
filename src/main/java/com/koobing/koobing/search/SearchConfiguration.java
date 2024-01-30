package com.koobing.koobing.search;

import com.koobing.koobing.search.repository.SqlSearchRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import javax.sql.DataSource;

@Configuration
public class SearchConfiguration {

    @Bean
    @Profile("jdbc")
    public SqlSearchRepository sqlSearchRepository(DataSource dataSource) {
        return new SqlSearchRepository(dataSource);
    }
}
