package teamdropin.server.aws.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api")
@RestController
public class AwsLoadBalancerHeathCheckController {

    @GetMapping("/health-check")
    public void check(){
    }
}
