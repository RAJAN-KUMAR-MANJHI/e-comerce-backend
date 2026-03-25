package in.com.furniturebackend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.ConfigurableEnvironment;

import io.github.cdimascio.dotenv.Dotenv;
import jakarta.annotation.PostConstruct;

@Configuration
public class DotenvConfig {
	
	 @Autowired
	    private ConfigurableEnvironment env;

	    @PostConstruct
	    public void loadEnv() {
	        Dotenv dotenv = Dotenv.load();
	        env.getSystemProperties().put("spring.datasource.url", dotenv.get("DB_URL"));
	        env.getSystemProperties().put("spring.datasource.username", dotenv.get("DB_USERNAME"));
	        env.getSystemProperties().put("spring.datasource.password", dotenv.get("DB_PASSWORD"));
	    }
	
	
}
