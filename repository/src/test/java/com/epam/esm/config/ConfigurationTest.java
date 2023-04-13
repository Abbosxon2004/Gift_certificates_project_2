package com.epam.esm.config;

import com.epam.esm.repository.implementation.CertificateRepositoryImpl;
import com.epam.esm.repository.implementation.TagRepositoryImpl;
import com.epam.esm.repository.repository.CertificateRepository;
import com.epam.esm.repository.repository.TagRepository;
import com.epam.esm.util.builder.CertificateBuilder;
import com.epam.esm.util.builder.TagBuilder;
import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

@Configuration
public class ConfigurationTest {

    @Bean
    public DataSource dataSource() {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setUrl("jdbc:postgresql://localhost:5432/gift_certificates");
        dataSource.setDriverClassName("org.postgresql.Driver");
        dataSource.setUsername("postgres");
        dataSource.setPassword("2801");
        return dataSource;
    }

    @Bean
    public DataSourceTransactionManager dataSourceTransactionManager(DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

    @Bean
    public TagBuilder tagBuilder() {
        return new TagBuilder();
    }

    @Bean
    public CertificateBuilder certificateBuilder() {
        return new CertificateBuilder();
    }

    @Bean
    public TagRepository tagRepository(JdbcTemplate jdbcTemplate, TagBuilder tagBuilder) {
        return new TagRepositoryImpl(jdbcTemplate, tagBuilder);
    }

    @Bean
    public CertificateRepository certificateRepository(JdbcTemplate jdbcTemplate, CertificateBuilder certificateBuilder, TagBuilder tagBuilder) {
        return new CertificateRepositoryImpl(jdbcTemplate, tagBuilder, certificateBuilder);
    }

}