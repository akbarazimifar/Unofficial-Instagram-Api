package com.ig.unofficial;

import com.ig.unofficial.resources.Api;
import java.util.HashSet;
import java.util.Set;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

/**
 * Configures JAX-RS for the application.
 * @author Juneau
 */
@ApplicationPath("unofficial")
public class JAXRSConfiguration extends Application {
//    @Override
//    public Set<Class<?>> getClasses() {
//        Set<Class<?>> resources = new HashSet<>();
//        resources.add(Api.class);
//        return resources;
//    }
}