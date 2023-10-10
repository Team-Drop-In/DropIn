package teamdropin.server.test;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @Operation(hidden = true)
    @GetMapping("/hello")
    public String hello(){
        return "hello";
    }
}
