package crawlitviec.controller;

import crawlitviec.service.CrawlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping(value = "/itviec")
@RestController
public class JobController {
    @Autowired
    CrawlService service;

    @GetMapping(value = "/crawldata")
    public String crawlData(@RequestParam(name = "type") String type){
        service.parserAllJob(type);
        return "Completed";
    }
    @GetMapping(value = "/genfile")
    public String genFile(){
        service.genFile();
        return "Completed Gen File HTML";
    }


}
