// Import necessary packages
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

// Main application class
@SpringBootApplication
public class HelloWorldApplication {
    public static void main(String[] args) {
        SpringApplication.run(HelloWorldApplication.class, args);
        helloWorld();
    }
}

// Controller class
@RestController
class HelloWorldController {
    // Handle HTTP GET request to "/"
    @GetMapping("/")
    public String helloWorld() {
        return "Hello, World!";
    }
}
