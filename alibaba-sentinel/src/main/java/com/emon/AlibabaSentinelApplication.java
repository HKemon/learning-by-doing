package com.emon;

import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

@SpringBootApplication
public class AlibabaSentinelApplication {

    public static void main(String[] args) {
        SpringApplication.run(AlibabaSentinelApplication.class, args);
    }

}


@Configuration
class SentinelCodeConfiguration {
    @EventListener(ApplicationReadyEvent.class)
    public void configureSentinel() throws Exception {
        FlowRule flowRule = new FlowRule();
        flowRule.setResource("/code");
        flowRule.setCount(2);
        flowRule.setGrade(RuleConstant.FLOW_GRADE_QPS);
        flowRule.setLimitApp("default");
        FlowRuleManager.loadRules(Collections.singletonList(flowRule));
    }
}

@RestController
class GreetingRestController {
    //todo: error is not working
    @GetMapping("/ekk")
    public String error() {
        return "Too many request!!!!";
//        return ResponseEntity
//                .status(HttpStatus.TOO_MANY_REQUESTS)
//                .contentType(MediaType.APPLICATION_JSON)
//                .body(Collections.singletonMap("error","Too many request!!!!")).toString();
    }

    @GetMapping("/code")
    public String code() {
        return "I am from code block";
    }

    @GetMapping("/dashboard")
    public String dashboard() {
        return "I am from dashboard block";
    }
}
