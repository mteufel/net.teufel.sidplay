package net.teufel.sidplay.core.dao;

import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

public class DataSourceFactory {

    public static DataSource erzeugeDataSource() {
        DriverManagerDataSource ds = new DriverManagerDataSource();
        ds.setDriverClassName("org.hsqldb.jdbc.JDBCDriver");
        ds.setUrl("jdbc:hsqldb:hsql://192.168.2.45:9002/sidplaydb");
        ds.setUsername("SA");
        ds.setPassword("");
        return ds;
    }

}
