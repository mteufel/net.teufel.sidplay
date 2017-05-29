package com.example;

import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.wildfly.swarm.Swarm;
import org.wildfly.swarm.datasources.DatasourcesFraction;
import org.wildfly.swarm.undertow.WARArchive;

import java.io.File;

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
        deployment.setWebXML(new File("src/main/webapp","WEB-INF/web.xml"));
        deployment.addAllDependencies();
        deployment.addPackage("com.example");
        deployment.addModule("org.hsqldb");
        deployment.addModule("org.eclipse.rap");
        swarm.deploy(deployment);





    }


}
