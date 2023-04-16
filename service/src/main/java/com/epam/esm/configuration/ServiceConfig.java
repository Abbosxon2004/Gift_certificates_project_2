package com.epam.esm.configuration;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * This class is a Spring configuration class that enables component scanning for the com.epam.esm package.
 */
@Configuration
@ComponentScan("com.epam.esm")
public class ServiceConfig {

}
