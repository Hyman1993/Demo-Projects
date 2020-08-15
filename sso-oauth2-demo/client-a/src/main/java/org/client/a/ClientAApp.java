package org.client.a;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Client A
 *
 */
@SpringBootApplication
public class ClientAApp 
{
    public static void main( String[] args )
    {
        System.out.println( "A Hello World!" );
        SpringApplication.run(ClientAApp.class, args);
    }
}
