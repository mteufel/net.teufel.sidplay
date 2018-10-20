package net.teufel.sidplay.dao;

import net.teufel.sidplay.domain.Sid;
import net.teufel.sidplay.domain.SidDetail;
import net.teufel.sidplay.domain.Subtune;
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


    public List<Sid> findSids(String search) {

        String sql = "select s.id, s.title, s. author, s.release " +
                     "from sid_idx i, sid s where i.sid_id=s.id and i.value like '%" + search + "%' " +
                     "order by s.release desc ";

        final List<Sid> result = new ArrayList<>();

        jdbcTemplate.query(sql, rs -> {

            Sid sid = new Sid();
            sid.setSidId(rs.getInt("ID"));
            sid.setTitle(rs.getString("TITLE").trim());
            sid.setAuthor(rs.getString("AUTHOR").trim());
            sid.setRelease(rs.getString("RELEASE").trim());
            result.add(sid);

        });

        if (result.size()==0)
            return null;

        return result;

    }

    public SidDetail getSidDetail(int sidId) {

        String sql = "select s.id, s.title, s.author, s.release, s.no_subtunes, s.preferred_model, " +
                "      sf.path, sf.file_name, si.type_id, t.type, si.value " +
                "from sid s, sid_files sf, sid_idx si, type t " +
                "where s.id=sf.sid_id and s.id=si.sid_id and si.type_id=t.id " +
                "and s.id=" + sidId;

        final SidDetail sidDetail = new SidDetail();

        jdbcTemplate.query(sql, rs -> {
            sidDetail.setId(rs.getInt("ID"));
            sidDetail.setTitle(rs.getString("TITLE").trim());
            sidDetail.setAuthor(rs.getString("AUTHOR").trim());
            sidDetail.setRelease(rs.getString("RELEASE").trim());
            sidDetail.setNoSubtunes(rs.getInt("NO_SUBTUNES"));
            sidDetail.setPreferredModel(rs.getString("PREFERRED_MODEL"));
            sidDetail.setHvscPath(rs.getString("PATH"));
            sidDetail.setFileName(rs.getString("FILE_NAME"));
            sidDetail.setTypeId(rs.getInt("TYPE_ID"));
            sidDetail.setType(rs.getString("TYPE"));
            sidDetail.setFilterString(rs.getString("VALUE"));
        });

        sidDetail.setSubtunes(getSubtunes(sidId));

        return sidDetail;

    }

    private List<Subtune> getSubtunes(int sidId) {

        String sql = "select no_subtune, length from sid_songlength where sid_id=" + sidId;

        final List<Subtune> subtunes = new ArrayList<>();

        jdbcTemplate.query(sql, rs -> {

            Subtune subtune = new Subtune();
            subtune.setSubtuneNo(rs.getInt("NO_SUBTUNE"));
            subtune.setLength(rs.getString("LENGTH"));
            subtunes.add(subtune);

        });

        if (subtunes.size()==0)
            return null;

        return subtunes;

    }

    public boolean isSidAvailable(int sidId) {

        String sql = "select count(*) from sid where id=" + sidId;

        int rows = jdbcTemplate.queryForObject(sql, Integer.class);

        if (rows==1) {
            return true;
        }
        return false;

    }


    public List<Type> getTypes() {

        String sql = "select * from type";
        final List<Type> result = new ArrayList<>();
        jdbcTemplate.query(sql, rs -> {
            result.add(new Type(rs.getInt("ID"), rs.getString("TYPE")));
        });
        return result;
    }


    public InputStream getSidFile(int sidId) {
        String sql = "select file from sid_files where sid_id=" + sidId;
        return jdbcTemplate.queryForObject(sql, (rs, rowNum) -> rs.getBinaryStream("file") );
    }

}
