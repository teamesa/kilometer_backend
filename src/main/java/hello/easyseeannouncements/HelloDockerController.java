package hello.easyseeannouncements;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloDockerController {

    @GetMapping("/hello-docker")
    public String docker() {
        return "Hello Docker";
    }
}
