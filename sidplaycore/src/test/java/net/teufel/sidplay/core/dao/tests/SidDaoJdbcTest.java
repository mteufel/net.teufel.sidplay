package net.teufel.sidplay.core.dao.tests;

import net.teufel.sidplay.core.dao.DataSourceFactory;
import net.teufel.sidplay.core.dao.SidDaoJdbc;
import net.teufel.sidplay.core.domain.Type;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.sql.DataSource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SidDaoJdbcTest {

    private SidDaoJdbc sidDaoJdbc;

    @BeforeEach
    void setUp() {
        this.sidDaoJdbc =  new SidDaoJdbc(DataSourceFactory.erzeugeDataSource());
    }

    @Test
    void testTypesAvailable() {
        List<Type> result = this.sidDaoJdbc.getTypes();
        assertNotNull(result);
        assertEquals(3, result.size());
    }

    @Test
    void testTypeContents() {

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