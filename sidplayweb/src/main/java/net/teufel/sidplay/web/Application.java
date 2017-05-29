package net.teufel.sidplay.web;



import net.teufel.sidplay.web.servlets.HelloWorldServlet;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.UserCredentialsDataSourceAdapter;
import org.wildfly.swarm.Swarm;
import org.wildfly.swarm.datasources.DatasourcesFraction;
import org.wildfly.swarm.jaxrs.JAXRSArchive;
import org.wildfly.swarm.undertow.WARArchive;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class Application {

    public static void main(String[] args) throws Exception {

        Swarm swarm = new Swarm();

        swarm.fraction(new DatasourcesFraction().jdbcDriver("hsqldb", ( driver ) -> {
            driver.driverClassName("org.hsqldb.jdbc.JDBCDriver");
            driver.driverModuleName("org.hsqldb");
        }).dataSource("hsqldb-ds", (ds) -> {
            ds.driverName("hsqldb");
            ds.connectionUrl("jdbc:hsqldb:hsql://localhost:9001/sidplaydb");
            ds.userName("SA");
        }));

        swarm.start();

        WARArchive deployment = ShrinkWrap.create(WARArchive.class);
        deployment.addClass(HelloWorldServlet.class);
        deployment.addAllDependencies();
        deployment.addPackage("net.teufel.sidplay");
        deployment.addModule("org.hsqldb");



        swarm.deploy(deployment);

//                WarDeployment deployment = new DefaultWarDeployment(swarm);
//        deployment.getArchive().addClasses(Employee.class);
//        deployment.getArchive().addClass(EmployeeServlet.class);
//        deployment.getArchive().addAsWebInfResource(new ClassLoaderAsset("META-INF/persistence.xml", Main.class.getClassLoader()), "classes/META-INF/persistence.xml");
//        deployment.getArchive().addAsWebInfResource(new ClassLoaderAsset("META-INF/load.sql", Main.class.getClassLoader()), "classes/META-INF/load.sql");
//
//        container.deploy(deployment);


    }


}
