package net.teufel.sidplay.core.dao;

import net.teufel.sidplay.core.domain.Sid;
import net.teufel.sidplay.core.domain.Type;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.jdbc.core.support.SqlLobValue;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.jdbc.support.lob.DefaultLobHandler;
import org.springframework.jdbc.support.lob.LobHandler;

import javax.annotation.Generated;
import javax.sql.DataSource;

import java.io.*;
import java.sql.*;
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

	public void insertSid(Type type, Sid sid) {

		try {

			final InputStream is = new FileInputStream(sid.getFile());
			LobHandler lobHandler = new DefaultLobHandler();
			KeyHolder holder = new GeneratedKeyHolder();

			// write into table SID
			getJdbcTemplate().update(new PreparedStatementCreator() {
				@Override
				public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
					PreparedStatement ps = connection.prepareStatement("INSERT INTO sid (title, author, release, no_subtunes, preferred_model) VALUES (?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
					ps.setString(1, sid.getTitle());
					ps.setString(2, sid.getAuthor());
					ps.setString(3, sid.getRelease());
					ps.setInt(4, sid.getNumberSubtunes());
					ps.setString(5, sid.getPreferredModel());
					return ps;
				}
			}, holder);

			// write into table SID_FILES
			getJdbcTemplate().update("INSERT INTO sid_files (sid_id, path, file_name, file_size_compressed, file_size_uncompressed, file) VALUES (?, ?, ?, ?, ?, ?)",
					new Object[] { holder.getKey().intValue(), sid.getPath(), sid.getFileName(),
							0,0,
							new SqlLobValue(is, (int) sid.getFile().length(), lobHandler), },
					new int[] { Types.INTEGER, Types.VARCHAR, Types.VARCHAR, Types.NUMERIC, Types.NUMERIC, Types.BLOB });
			

			// write into table SID_IDX
			String indexValue = type.getType() + " " + sid.getPath() + " " + sid.getFileName() + " " + sid.getTitle() + " " + sid.getAuthor() + " " + sid.getRelease() + " " + sid.getPreferredModel();
			getJdbcTemplate().update("INSERT INTO sid_idx (sid_id, type_id, value) VALUES (?, ?, ?)",
					new Object[] { holder.getKey().intValue(), type.getId(), indexValue },
					new int[] { Types.INTEGER, Types.NUMERIC, Types.VARCHAR });
			
			// write into table SID_SONGLENGTH
			int n = 1;
			for (String length : sid.getSonglengths()) {
				getJdbcTemplate().update("INSERT INTO sid_songlength (sid_id, no_subtune, length) VALUES (?, ?, ?)",
						new Object[] { holder.getKey().intValue(), n, length },
						new int[] { Types.INTEGER, Types.NUMERIC, Types.VARCHAR });
				n++;
			}

			is.close();

			//compressedInputStream.close();
			
		} catch (DataAccessException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
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

	private byte[] readAll(InputStream in) throws IOException
	{
		ByteArrayOutputStream bytes = new ByteArrayOutputStream(1024);
		byte[] buf = new byte[1024];
		int count;

		while ((count = in.read(buf)) > 0) {
			bytes.write(buf, 0, count);
		}
		in.close();
		return bytes.toByteArray();
	}

	/*
	private byte[] uncompress(byte[] input) throws LZFException {
		return LZFDecoder.safeDecode(input);
	}
	*/

}
