package com.koobing.koobing.search;

import com.koobing.koobing.search.repository.JpaSearchRepository;
import com.koobing.koobing.search.repository.jpa.HostelJpaRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("jpa")
public class SearchConfiguration {
    @Bean
    public JpaSearchRepository jpaSearchRepository(HostelJpaRepository hostelJpaRepository) {
        return new JpaSearchRepository(hostelJpaRepository);
    }
}
