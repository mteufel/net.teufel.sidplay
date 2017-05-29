package com.example;

import org.eclipse.rap.rwt.application.Application;
import org.eclipse.rap.rwt.application.ApplicationConfiguration;

public class HelloWorldConfiguration implements ApplicationConfiguration {
    public void configure( Application application ) {
        application.addEntryPoint( "/hello", HelloWorld.class, null );
    }
}