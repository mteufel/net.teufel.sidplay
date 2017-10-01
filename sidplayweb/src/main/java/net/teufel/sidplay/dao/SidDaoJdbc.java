package net.teufel.sidplay.dao;

import net.teufel.sidplay.domain.Type;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.RowMapper;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.sql.DataSource;
import java.io.InputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class SidDaoJdbc {

    @Inject
    private DataSource ds;

    private JdbcTemplate jdbcTemplate;

    @PostConstruct
    public void postConstruct() {
        this.jdbcTemplate = new JdbcTemplate(ds);
    }

    public List<Type> getTypes() {

        String sql = "select * from type";
        final List<Type> result = new ArrayList<>();

        jdbcTemplate.query(sql, new RowCallbackHandler() {
            public void processRow(ResultSet rs) throws SQLException {
                result.add(new Type(rs.getInt("ID"), rs.getString("TYPE")));
            }
        });

        return result;
    }


    public InputStream getSid() {
        String sql = "select file from sid_files where id=2";
        return jdbcTemplate.queryForObject(sql, new RowMapper<InputStream>(){
            public InputStream mapRow(ResultSet rs, int rowNum) throws SQLException {
                return rs.getBlob("file").getBinaryStream();
            }
        });
    }

}
