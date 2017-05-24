package net.teufel.sidplay.core.dao;

import net.teufel.sidplay.core.domain.Type;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SidDaoJdbc extends JdbcDaoSupport {

    public SidDaoJdbc(DataSource ds) {
        setDataSource(ds);
    }

    public List<Type> getTypes() {

        String sql = "select * from type";
        final List<Type> result = new ArrayList<Type>();

        getJdbcTemplate().query(sql, new RowCallbackHandler() {
            public void processRow(ResultSet rs) throws SQLException {
                result.add( new Type(rs.getInt("ID"),
                                     rs.getString("TYPE") ));
            }
        });

        return result;
    }
}
