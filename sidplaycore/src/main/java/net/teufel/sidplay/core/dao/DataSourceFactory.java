package net.teufel.sidplay.core.dao;

import org.sqlite.SQLiteDataSource;

import javax.sql.DataSource;

public class DataSourceFactory {

    public static DataSource erzeugeDataSource() {

        SQLiteDataSource ds = new SQLiteDataSource();
        ds.setUrl("jdbc:sqlite:/home/marc/sid.db");
        return ds;

    }

}
