package com.financeSystem.listener;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class InterestCalServletContextListener implements ServletContextListener {
    private ScheduledExecutorService scheduler;

    @Override
    public void contextInitialized(ServletContextEvent event) {
        scheduler = Executors.newScheduledThreadPool(1);

        // Schedule your task with a fixed delay
        scheduler.scheduleAtFixedRate(new InterestCalScheduledTask(), 0, 1, TimeUnit.DAYS);
    }

    @Override
    public void contextDestroyed(ServletContextEvent event) {
        if (scheduler != null) {
            scheduler.shutdownNow();
        }
    }
}
