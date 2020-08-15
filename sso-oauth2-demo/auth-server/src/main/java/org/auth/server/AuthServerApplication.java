package org.auth.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

/**
 * 启动类
 *
 */
@SpringBootApplication
@EnableResourceServer
public class  AuthServerApplication 
{
    public static void main( String[] args )
    {
     SpringApplication.run(AuthServerApplication.class, args);
    }
}
