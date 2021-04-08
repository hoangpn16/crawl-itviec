package crawlitviec.controller;

import crawlitviec.service.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping(value = "/itviec")
@RestController
public class JobController {
    @Autowired
    JobService service;

    @GetMapping(value = "/crawldata")
    public String crawlData(){
        service.parserAllJob();
        return "Completed";
    }


}
