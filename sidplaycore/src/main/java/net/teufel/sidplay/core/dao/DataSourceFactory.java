package net.teufel.sidplay.core.dao;

import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

public class DataSourceFactory {

    public static DataSource erzeugeDataSource() {
        DriverManagerDataSource ds = new DriverManagerDataSource();
        ds.setDriverClassName("org.hsqldb.jdbc.JDBCDriver");
        ds.setUrl("jdbc:hsqldb:hsql://35.187.72.52:9001/sidplay");
        ds.setUsername("SA");
        ds.setPassword("donnerstag2");
        return ds;
    }

}
