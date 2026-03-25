package in.com.furniturebackend.config;

import java.io.File;

import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.unit.DataSize;

import jakarta.annotation.PostConstruct;
import jakarta.servlet.MultipartConfigElement;

@Configuration
public class FileUploadConfig {

	 private final String uploadDir = "uploads";

	    // 1️⃣ Folder auto create
	    @PostConstruct
	    public void init() {
	        File folder = new File(uploadDir);
	        if (!folder.exists()) {
	            folder.mkdir();
	        }
	    }

	    // 2️⃣ File size limit
	    @Bean
	    public MultipartConfigElement multipartConfigElement() {
	        MultipartConfigFactory factory = new MultipartConfigFactory();
	        factory.setMaxFileSize(DataSize.ofMegabytes(10));
	        factory.setMaxRequestSize(DataSize.ofMegabytes(10));
	        return factory.createMultipartConfig();
	    }
	
}
