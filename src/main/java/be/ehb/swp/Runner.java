/**
 * Created by arnaudcoel on 08/10/15.
 */

package be.ehb.swp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@SpringBootApplication
public class Runner/* extends WebMvcConfigurerAdapter*/ {
    public static void main(String[] args) {
        SpringApplication.run(Runner.class, args);
    }
}
