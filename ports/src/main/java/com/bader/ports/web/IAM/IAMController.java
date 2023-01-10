package com.bader.ports.web.IAM;

import com.bader.domain.IAM.IAMService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/iam")
public class IAMController {
    private final IAMService iamService;

    public IAMController(IAMService iamService) {
        this.iamService = iamService;
    }
}
