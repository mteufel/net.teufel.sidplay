package net.teufel.sidplay.web;


import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.wildfly.swarm.Swarm;
import org.wildfly.swarm.datasources.DatasourcesFraction;
import org.wildfly.swarm.jaxrs.JAXRSArchive;

public class Application {

    public static void main(String[] args) throws Exception {

        Swarm swarm = new Swarm();

        swarm.fraction(new DatasourcesFraction().jdbcDriver("hsqldb", ( driver ) -> {
            driver.driverClassName("org.hsqldb.jdbc.JDBCDriver");
            driver.driverModuleName("org.hsqldb");
        }).dataSource("hsqldb-ds", (ds) -> {
            ds.driverName("hsqldb");
            ds.connectionUrl("jdbc:hsqldb:hsql://192.168.2.45:9001/sidplaydb");
            ds.userName("SA");
        }));

        swarm.start();

//        WARArchive deployment = ShrinkWrap.create(WARArchive.class);
//        deployment.addClass(HelloWorldServlet.class);
//        deployment.addAllDependencies();
//        deployment.addPackage("net.teufel.sidplay");
//        deployment.addModule("org.hsqldb");


        JAXRSArchive rsDeployment = ShrinkWrap.create(JAXRSArchive.class).setContextRoot("/api");
        rsDeployment.addResource(GetSidResource.class);
        rsDeployment.addAllDependencies();
        rsDeployment.addPackage("net.teufel.sidplay.web.resources");
        rsDeployment.addModule("org.hsqldb");

        swarm.deploy(rsDeployment);


    }


}
