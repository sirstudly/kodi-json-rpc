
package kodi.jsonrpc.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@Configuration
@ComponentScan( "kodi" )
@PropertySource( "classpath:config.properties" )
public class KodiConfig {

    @Bean
    public Gson getGsonSingleton() {
        // these are thread safe
        return new GsonBuilder().setPrettyPrinting().create();
    }

}
