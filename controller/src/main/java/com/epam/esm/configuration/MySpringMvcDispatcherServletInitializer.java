package com.epam.esm.configuration;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

/**
 * This class is used to configure the dispatcher servlet that dispatches incoming requests to the appropriate controller.
 * It extends the AbstractAnnotationConfigDispatcherServletInitializer class that provides an easy way to configure the
 * DispatcherServlet programmatically instead of via web.xml.
 */
public class MySpringMvcDispatcherServletInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

    /**
     * This method returns the configuration classes that provide the root configuration for the application context.
     * In this case, it returns the ServiceConfig class.
     *
     * @return an array of Class objects that represent the root configuration classes
     */
    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[]{ServiceConfig.class};
    }

    /**
     * This method returns the configuration classes that provide the servlet-specific configuration for the application context.
     * In this case, it returns the WebLayerConfig class.
     *
     * @return an array of Class objects that represent the servlet-specific configuration classes
     */
    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class[]{WebLayerConfig.class};
    }

    /**
     * This method returns an array of String objects that represent the servlet mapping(s) for the DispatcherServlet.
     * In this case, it returns an array with a single "/" which means that the DispatcherServlet will handle all requests.
     *
     * @return an array of String objects that represent the servlet mapping(s)
     */
    @Override
    protected String[] getServletMappings() {
        return new String[]{"/"};
    }

    /**
     * This method is called during application startup and is used to set the value of the "spring.profiles.active"
     * context parameter to "prod" which activates the production profile.
     *
     * @param servletContext the ServletContext of the application
     * @throws ServletException if there is a servlet-related problem
     */
    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        super.onStartup(servletContext);
        servletContext.setInitParameter(
                "spring.profiles.active", "prod");
    }
}
