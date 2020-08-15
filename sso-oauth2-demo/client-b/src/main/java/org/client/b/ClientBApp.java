package org.client.b;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Client B
 *
 */
@SpringBootApplication
public class ClientBApp 
{
    public static void main( String[] args )
    {
        System.out.println( "B Hello World!" );
        SpringApplication.run(ClientBApp.class, args);
    }
}
