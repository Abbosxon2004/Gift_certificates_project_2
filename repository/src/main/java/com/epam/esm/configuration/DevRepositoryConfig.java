package com.epam.esm.configuration;

import lombok.RequiredArgsConstructor;
import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@Configuration
@Profile("dev")
@RequiredArgsConstructor
@EnableTransactionManagement
@ComponentScan("com.epam.esm")
@PropertySource("classpath:application_dev.properties")
public class DevRepositoryConfig {
    @Value("10")
    public String POOL_SIZE;
    @Value("org.postgresql.Driver")
    public String DRIVER;
    @Value("${db.username}")
    public String USERNAME;
    @Value("${db.password}")
    public String PASSWORD;
    @Value("${db.url}")
    public String URL;

    @Bean
    public DataSource dataSource() {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setInitialSize(Integer.parseInt(POOL_SIZE));
        dataSource.setDriverClassName(DRIVER);
        dataSource.setUrl(URL);
        dataSource.setUsername(USERNAME);
        dataSource.setPassword(PASSWORD);

        return dataSource;
    }

    /**
     * bean for handling transactional methods
     *
     * @param dataSource DataSource
     * @return instance of DataSourceTransactionManager for given datasource
     */
    @Bean
    public DataSourceTransactionManager dataSourceTransactionManager(DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    /**
     * bean for field jdbcTemplate in CertificateRepositoryImpl and TagRepositoryImpl classes
     *
     * @param dataSource DataSource
     * @return instance of JdbcTemplate for given datasource
     */
    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }
}
