package com.koobing.koobing.search;

import com.koobing.koobing.search.repository.JpaSearchRepository;
import com.koobing.koobing.search.repository.SqlSearchRepository;
import com.koobing.koobing.search.repository.jpa.HostelJpaRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import javax.sql.DataSource;

@Configuration
public class SearchConfiguration {
    @Bean
    @Profile("jpa")
    public JpaSearchRepository jpaSearchRepository(HostelJpaRepository hostelJpaRepository) {
        return new JpaSearchRepository(hostelJpaRepository);
    }

    @Bean
    @Profile("jdbc")
    public SqlSearchRepository sqlSearchRepository(DataSource dataSource) {
        return new SqlSearchRepository(dataSource);
    }
}
