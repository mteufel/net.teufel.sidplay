package net.teufel.sidplay.core.dao.tests;

import static org.junit.Assert.*;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

import net.teufel.sidplay.core.dao.DataSourceFactory;
import net.teufel.sidplay.core.dao.SidDaoJdbc;
import net.teufel.sidplay.core.domain.Type;

public class SidDaoJdbcTest {

	private SidDaoJdbc sidDaoJdbc;

	@Before
	public void setUp() {
		this.sidDaoJdbc = new SidDaoJdbc(DataSourceFactory.erzeugeDataSource());
	}

	@Test
	public void testTypesAvailable() {
		List<Type> result = this.sidDaoJdbc.getTypes();
		assertNotNull(result);
		assertEquals(3, result.size());
	}

	@Test
	public void testTypeContents() {

		List<Type> result = this.sidDaoJdbc.getTypes();

		Type type = result.get(0);
		assertEquals(1, type.getId());
		assertEquals("DEMOS", type.getType());

		type = result.get(1);
		assertEquals(2, type.getId());
		assertEquals("MUSICIANS", type.getType());

		type = result.get(2);
		assertEquals(3, type.getId());
		assertEquals("GAMES", type.getType());
	}

}
