package net.teufel.sidplay;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class Util {

    public static DataSource getDataSource() {
        try {
            InitialContext context = new InitialContext();
            DataSource ds = (DataSource) context.lookup("java:jboss/datasources/sqlite-ds");
            if (ds==null) {
                System.out.println("no Datasource found");
            }
            return ds;

        } catch (NamingException e) {
            e.printStackTrace();
        }
        return null;
    }

}
