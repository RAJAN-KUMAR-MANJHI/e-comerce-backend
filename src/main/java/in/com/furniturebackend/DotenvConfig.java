package in.com.furniturebackend;

import javax.sql.DataSource;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.jdbc.DataSourceBuilder;


@Configuration
public class DotenvConfig {

	 @Bean
	    public DataSource dataSource() {
	        Dotenv dotenv = Dotenv.load();
	        return DataSourceBuilder.create()
	                .url(dotenv.get("DB_URL"))
	                .username(dotenv.get("DB_USERNAME"))
	                .password(dotenv.get("DB_PASSWORD"))
	                .driverClassName("org.postgresql.Driver")
	                .build();
	    }
}
