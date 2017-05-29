package net.teufel.sidplay;

import org.springframework.jdbc.datasource.UserCredentialsDataSourceAdapter;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class Util {

    public static UserCredentialsDataSourceAdapter getDataSource() {
        DataSource ds = null;
        try {
            Context initContext = new InitialContext();
            Context envContext = (Context) initContext.lookup("java:jboss/");
            ds = (DataSource) envContext.lookup("datasources/hsqldb-ds");

            UserCredentialsDataSourceAdapter adapter = new UserCredentialsDataSourceAdapter();
            adapter.setTargetDataSource(ds);

            adapter.setUsername("SA");
            adapter.setPassword("");

            return adapter;
        }
        catch (final Exception e) {
            return null;
        }
    }

}
