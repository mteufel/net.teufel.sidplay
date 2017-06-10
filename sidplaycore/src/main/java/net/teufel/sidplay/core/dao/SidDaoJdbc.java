package net.teufel.sidplay.core.dao;

import net.teufel.sidplay.core.domain.Type;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.jdbc.core.support.SqlLobValue;
import org.springframework.jdbc.support.lob.DefaultLobHandler;
import org.springframework.jdbc.support.lob.LobHandler;

import javax.sql.DataSource;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
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
				result.add(new Type(rs.getInt("ID"), rs.getString("TYPE")));
			}
		});

		return result;
	}

	public void insertSid() {

		try {

			final int fileId = getJdbcTemplate().queryForObject("call next value for file_id", Integer.class);

			final File sidFile = new File("/Users/marcteufel/Desktop/C64Music/MUSICIANS/A/A-Man/4_Kanal_Zak.sid");

			final InputStream is = new FileInputStream(sidFile);

			LobHandler lobHandler = new DefaultLobHandler();

			getJdbcTemplate().update("INSERT INTO sid_files (id, path, file_name, file) VALUES (?, ?, ?, ?)",
					new Object[] { fileId, sidFile.getPath(), sidFile.getName(),
							new SqlLobValue(is, (int) sidFile.length(), lobHandler), },
					new int[] { Types.NUMERIC, Types.VARCHAR, Types.VARCHAR, Types.BLOB });

		} catch (DataAccessException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

	}

	public InputStream getSid() {
		String sql = "select file from sid_files where id=2";
		return getJdbcTemplate().queryForObject(sql, new RowMapper<InputStream>(){
			public InputStream mapRow(ResultSet rs, int rowNum) throws SQLException {
				return rs.getBlob("file").getBinaryStream();
			}
			
		});
	}

}
