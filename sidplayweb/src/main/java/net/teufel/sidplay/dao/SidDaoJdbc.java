package net.teufel.sidplay.dao;

import net.teufel.sidplay.domain.Sid;
import net.teufel.sidplay.domain.Type;
import org.springframework.jdbc.core.JdbcTemplate;
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


    public List<Sid> findSids(String search) {

        String sql = "select s.id, s.title, s. author, s.release " +
                     "from sid_idx i, sid s where i.sid_id=s.id and i.value like '%" + search + "%' " +
                     "order by s.release desc ";

        final List<Sid> result = new ArrayList<>();

        jdbcTemplate.query(sql, rs -> {

            Sid sid = new Sid();
            sid.setSidId(rs.getInt("ID"));
            sid.setTitle(rs.getString("TITLE"));
            sid.setAuthor(rs.getString("AUTHOR"));
            sid.setRelease(rs.getString("RELEASE"));
            result.add(sid);

        });

        if (result.size()==0)
            return null;

        return result;

    }

    public List<Type> getTypes() {

        String sql = "select * from type";
        final List<Type> result = new ArrayList<>();
        jdbcTemplate.query(sql, rs -> {
            result.add(new Type(rs.getInt("ID"), rs.getString("TYPE")));
        });
        return result;
    }



    public InputStream getSid(int sidId) {
        String sql = "select file from sid_files where sid_id=?";
        return jdbcTemplate.queryForObject(sql, (rs, rowNum) -> rs.getBlob("file").getBinaryStream(), sidId );
    }

}
