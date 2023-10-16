package teamdropin.server.aws.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api")
@RestController
public class AwsLoadBalancerHeathCheckController {

    @Operation(hidden = true)
    @GetMapping("/health-check")
    public void check(){
    }
}
