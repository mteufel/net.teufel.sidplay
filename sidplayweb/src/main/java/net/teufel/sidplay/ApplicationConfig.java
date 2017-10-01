package net.teufel.sidplay;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.sql.DataSource;
import java.io.Serializable;

@ApplicationScoped
public class ApplicationConfig implements Serializable {

    @Produces
    public DataSource getDataSource() {
        return Util.getDataSource();
    }

}
