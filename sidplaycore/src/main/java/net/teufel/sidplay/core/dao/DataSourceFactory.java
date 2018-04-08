package net.teufel.sidplay.core.dao;

import org.h2.jdbcx.JdbcDataSource;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

public class DataSourceFactory {

    public static DataSource erzeugeDataSource() {
        //DriverManagerDataSource ds = new DriverManagerDataSource();
        //ds.setDriverClassName("org.h2.Driver");
        //ds.setUrl("jdbc:h2:tcp://localhost:9101/sidhub;COMPRESS=TRUE");
        //ds.setUsername("sidhub");
        //ds.setPassword("sidhub");

        JdbcDataSource ds = new JdbcDataSource();
        ds.setUrl("jdbc:h2:tcp://localhost:9101/sidhub");
        ds.setUser("sidhub");
        ds.setPassword("sidhub");
        ds.setLoginTimeout(0);

        return ds;
    }

}
