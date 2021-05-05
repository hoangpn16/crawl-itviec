package crawlitviec.controller;

import crawlitviec.service.CrawlService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;



@RequestMapping(value = "/itviec")
@RestController
public class JobController {
    private static Logger logger = LogManager.getLogger(JobController.class);
    @Autowired
    CrawlService service;

    @GetMapping(value = "/crawldata")
    public String crawlData(@RequestParam(name = "type") String type){
        String [] list_type = type.split(",");
        for (String tp: list_type) {
            logger.info("Crawling type = "+ tp);
            service.parserAllJob(tp);
        }
        return "Completed";
    }
    @GetMapping(value = "/genfile")
    public String genFile(){
        service.genFile();
        return "Completed Gen File HTML";
    }


}
