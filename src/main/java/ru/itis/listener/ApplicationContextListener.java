package ru.itis.listener;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.itis.config.DatabaseConfig;
import ru.itis.service.test.common.api.UserCompletedTestsService;
import ru.itis.service.test.common.impl.UserCompletedTestsServiceImpl;
import ru.itis.service.test.general.api.*;
import ru.itis.service.test.general.impl.*;
import ru.itis.service.user.common.api.UserService;
import ru.itis.service.user.common.impl.BaseUserService;

@WebListener
public class ApplicationContextListener implements ServletContextListener {

    final static Logger logger = LogManager.getLogger(ApplicationContextListener.class);

    public void contextInitialized(ServletContextEvent sce) {
        logger.info("Context start");

        GetUserStatsService getUserStatsService = new GetUserStatsServiceImpl();
        sce.getServletContext().setAttribute("getUserStatsService", getUserStatsService);

        SaveTestService saveTestService =  new SaveTestServiceImpl();
        sce.getServletContext().setAttribute("saveTestService", saveTestService);

        UserService userService = new BaseUserService();
        sce.getServletContext().setAttribute("userService", userService);

        GetTestService getTestService = new GetTestServiceImpl();
        sce.getServletContext().setAttribute("getTestService", getTestService);

        UserCompletedTestsService userCompletedTestsService =  new UserCompletedTestsServiceImpl();
        sce.getServletContext().setAttribute("userCompletedTestsService", userCompletedTestsService);

        GetUserTestResultService getUserTestResultService =  new GetUserTestResultServiceImpl();
        sce.getServletContext().setAttribute("getUserTestResultService", getUserTestResultService);

        SearchTestsService searchTestsService = new BaseSearchTestsService();
        sce.getServletContext().setAttribute("searchTestsService", searchTestsService);

        logger.info("Context initialized");
        
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        DatabaseConfig.getInstance().close();
        logger.info("Context destroyed");
    }
}
