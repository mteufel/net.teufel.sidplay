package net.teufel.sidplay.core.dao;

import com.ning.compress.lzf.LZFCompressingInputStream;
import com.ning.compress.lzf.LZFDecoder;
import com.ning.compress.lzf.LZFException;
import net.teufel.sidplay.core.domain.Sid;
import net.teufel.sidplay.core.domain.Type;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.jdbc.core.support.SqlLobValue;
import org.springframework.jdbc.support.lob.DefaultLobHandler;
import org.springframework.jdbc.support.lob.LobHandler;

import javax.sql.DataSource;

import java.io.*;
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

	public void insertSid(Type type, Sid sid) {

		try {

			final int fileId = getJdbcTemplate().queryForObject("call next value for file_id", Integer.class);
			//final InputStream is = new FileInputStream(sid.getFile());


			//final InputStream compressedInputStream = new LZFCompressingInputStream(new FileInputStream(sid.getFile()));

			//byte[] comp = readAll(compressedInputStream);
			//byte[] uncomp = uncompress(comp);

			final InputStream is = new FileInputStream(sid.getFile());


			LobHandler lobHandler = new DefaultLobHandler();

			// write into table SID_FILES

			//new SqlLobValue(is, (int) sid.getFile().length(), lobHandler), },

			getJdbcTemplate().update("INSERT INTO sid_files (id, path, file_name, file_size_compressed, file_size_uncompressed, file) VALUES (?, ?, ?, ?, ?, ?)",
					new Object[] { fileId, sid.getPath(), sid.getFileName(),
							0,0,
							new SqlLobValue(is, (int) sid.getFile().length(), lobHandler), },
					new int[] { Types.NUMERIC, Types.VARCHAR, Types.VARCHAR, Types.NUMERIC, Types.NUMERIC, Types.BLOB });
			
			// write into table SID
			getJdbcTemplate().update("INSERT INTO sid (id, title, author, release, no_subtunes, preferred_model) VALUES (?, ?, ?, ?, ?, ?)",
					new Object[] { fileId, sid.getTitle(), sid.getAuthor(), sid.getRelease(), sid.getNumberSubtunes(), sid.getPreferredModel() },
					new int[] { Types.NUMERIC, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.NUMERIC, Types.VARCHAR });

			// write into table SID_IDX
			String indexValue = type.getType() + " " + sid.getPath() + " " + sid.getFileName() + " " + sid.getTitle() + " " + sid.getAuthor() + " " + sid.getRelease() + " " + sid.getPreferredModel();
			getJdbcTemplate().update("INSERT INTO sid_idx (id, type_id, value) VALUES (?, ?, ?)",
					new Object[] { fileId, type.getId(), indexValue },
					new int[] { Types.NUMERIC, Types.NUMERIC, Types.VARCHAR });
			
			// write into table SID_SONGLENGTH
			int n = 1;
			for (String length : sid.getSonglengths()) {
				getJdbcTemplate().update("INSERT INTO sid_songlength (id, no_subtune, length) VALUES (?, ?, ?)",
						new Object[] { fileId, n, length },
						new int[] { Types.NUMERIC, Types.NUMERIC, Types.VARCHAR });
				n++;
			}

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

	private byte[] uncompress(byte[] input) throws LZFException {
		return LZFDecoder.safeDecode(input);
	}

}
