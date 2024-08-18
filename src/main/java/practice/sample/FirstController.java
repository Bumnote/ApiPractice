package practice.sample;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FirstController {

    @GetMapping("/api/test")
    public String test() {
        System.out.println("input");
        return "rest api test";
    }

}
