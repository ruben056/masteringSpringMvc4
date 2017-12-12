package be.rd.msmvc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import be.rd.msmvc.config.upload.PicturesUploadProperties;

@EnableConfigurationProperties(PicturesUploadProperties.class)
@SpringBootApplication
public class MasteringSpringMvc4Application {

	public static void main(String[] args) {
		SpringApplication.run(MasteringSpringMvc4Application.class, args);
	}
}
