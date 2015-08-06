package com.insart.aci.storm.configuration;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class ApplicationContextProvider {
    private transient ConfigurableApplicationContext springContext;    
    private static transient volatile ApplicationContextProvider instance;

    private ApplicationContextProvider(){
	springContext = new AnnotationConfigApplicationContext(SpringPersistanceConfiguration.class, EsperConfiguration.class);
    }
    
    public static ApplicationContextProvider getInstance(){
	if(instance == null){
	    synchronized (ApplicationContextProvider.class) {
		if(instance == null){
		    instance = new ApplicationContextProvider();
		}
	    }
	}
	return instance;
    }
    
    public ConfigurableApplicationContext getApplicationContext() {
	return springContext;
    }

    public <T> T getBean(Class<T> clazz) {
	return springContext.getBean(clazz);
    }
    
    public static void autowireObject(Object object){
	ApplicationContextProvider.getInstance().getApplicationContext().getAutowireCapableBeanFactory().autowireBean(object);
    }
}
