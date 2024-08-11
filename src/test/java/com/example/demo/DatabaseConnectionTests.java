package com.example.demo;

import static org.assertj.core.api.Assertions.assertThat;

import javax.sql.DataSource;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

@SpringBootTest
class DatabaseConnectionTest {

    private static final Logger logger = LoggerFactory.getLogger(DatabaseConnectionTest.class);

    @Autowired
    private DataSource dataSource;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    void contextLoads() {
        logger.info("=======START");
        // Check if DataSource is not null
        assertThat(dataSource).isNotNull();
        logger.info("=======END");
    }

    @Test
    void testDatabaseConnection() throws Exception {
        logger.info("=======START");
        // Try to execute a simple query to validate connection
        Integer result = jdbcTemplate.queryForObject("SELECT 1", Integer.class);
        logger.info("Result ::  {} ",result);
        assertThat(result).isEqualTo(1);

    }
}
