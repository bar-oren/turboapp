package turbo.app.truboapp;

import controller.ProductController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;


@ComponentScan(basePackageClasses = ProductController.class)
@SpringBootApplication
public class TruboappApplication {

	public static void main(String[] args) {
		SpringApplication.run(TruboappApplication.class, args);
	}

}
