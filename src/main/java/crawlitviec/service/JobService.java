package crawlitviec.service;

import crawlitviec.repository.ListJobRepository;
import crawlitviec.repository.entity.JobModel;
import crawlitviec.repository.JobRepository;
import crawlitviec.repository.entity.ListJob;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class JobService {
    private static List<ListJob> listJobs = new ArrayList<>();
    private static List<JobModel> modelJobs = new ArrayList<>();
    @Autowired
    JobRepository jobRepository;
    @Autowired
    JobService jobService;
    @Autowired
    ListJobRepository listService;

    public void parserAllJob() {
        String url = "https://itviec.com/it-jobs/";

    }

    public List<ListJob> parserListLink(String url) {
        Document html = getHtmlContent(url);
        Elements elements = html.select("div.job_content");

        for (int i = 0; i < elements.size(); i++) {
            ListJob job = new ListJob();
            Element element = elements.get(i);
            String link_job = "https://itviec.com"+element.select("h2.title").select("a").attr("href");
            String title = element.select("a").text();
            String logo = element.select("div.logo").select("a").attr("src");
            String address = element.select("div.city").select("div.address").select("span").text();

            job.setLink(link_job);
            job.setAdress(address);
            job.setTitle(title);
            job.setLogo(logo);

            listService.save(job);
            listJobs.add(job);
        }
        return  listJobs;
    }

    public JobModel parserDetailJob(String url) {
        Document html = getHtmlContent(url);
        JobModel jobModel = new JobModel();

        String jobTitle = html.select("div.job-details__header").select("h1").text();
        String companyAddress = html.getElementsByClass("svg-icon__text").select("span").text();
//        String updatedTime = html.getElementsByClass("svg-icon--blue").select("div.svg-icon__text").text();
        String companyName = html.select("div.employer-long-overview__top-left").select("a").text();


        Elements topReasons = html.select(".job-details__top-reason-to-join-us").select("li");
        StringBuilder topReason = new StringBuilder();
        for (Element pr : topReasons) {
            String text = pr.text() + ". ";
            topReason.append(text);
        }

        Elements pros = html.select("div.job-details__paragraph");

        Elements children = pros.get(0).children();
        StringBuilder description = new StringBuilder();
        for (Element rs : children) {
            String text = rs.text();
            description.append(text);
        }

        Elements skills = pros.get(1).children();
        StringBuilder skill = new StringBuilder();
        for (Element rs: skills){
            String text = rs.text();
            skill.append(text);
        }

        Elements reasons = pros.get(2).children();
        StringBuilder reason_choosing = new StringBuilder();
        for (Element rs: reasons){
            String text = rs.text();
            reason_choosing.append(text);
        }


        jobModel.setJobTitle(jobTitle);
        jobModel.setCompanyAddress(companyAddress);
        jobModel.setReasonChoosing(reason_choosing.toString());
//        jobModel.setTimeUpdated(updatedTime);
        jobModel.setCompanyName(companyName);
        jobModel.setTopReasons(topReason.toString());
        jobModel.setJobDescription(description.toString());
        jobModel.setSkillRequirement(skill.toString());
        jobModel.setReasonChoosing(reason_choosing.toString());
        jobModel.setLinkJob(url);



        return jobModel;
    }

    private Document getHtmlContent(String url) {
        Document pageHtml;
        try {
            Connection.Response response = Jsoup.connect(url)
                    .userAgent("Mozilla/5.0 (Windows; U; WindowsNT 5.1; en-US; rv1.8.1.6) Gecko/20070725 Firefox/2.0.0.6")
                    .referrer("http://www.google.com")
                    .followRedirects(true)
                    .timeout(30000)
                    .execute();
            pageHtml = response.parse();
            return pageHtml;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
}
