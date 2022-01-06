package crawlitviec.service;

import crawlitviec.repository.entity.JobModel;
import crawlitviec.repository.JobRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Service
public class CrawlService {

    Logger logger = LogManager.getLogger(CrawlService.class);

    @Autowired
    JobRepository jobRepository;


    public void parserAllJob(String type) {
        String url = "https://itviec.com/it-jobs/" + type;
        List<String> listLink = parserListLink(url);
        for (String link : listLink) {
            logger.info("Crawling link =" + link);
            JobModel jobModel = parserDetailJob(link, type.toLowerCase());
            jobRepository.save(jobModel);
        }


    }

    public List<String> parserListLink(String url) {
        List<String> listLink = new ArrayList<>();
        Document html = getHtmlContent(url);
        Elements elements = html.select("h3.title");
        for (int i = 0; i < elements.size(); i++) {
            Element element = elements.get(i);
            String link = element.select("a").first().attr("href");
            listLink.add("https://itviec.com/" + link);
        }
        return listLink;
    }

    public JobModel parserDetailJob(String url, String type) {
        JobModel jobModel = jobRepository.findByLinkJob(url);
        if (jobModel == null) {
            jobModel = new JobModel();
        }

        Document html = getHtmlContent(url);

        String jobTitle = html.select("div.job-details__header").select("h1").text();
        String companyLogo = html.select("div.employer-long-overview__logo").select("picture").first().select("img").attr("data-src");
        String companyAddress = html.getElementsByClass("svg-icon__text").select("span").text();
        String companyName = html.select("div.employer-long-overview__top-left").select("a").text();

        if (jobTitle != null)
            jobModel.setJobTitle(jobTitle);
        if (companyLogo != null)
            jobModel.setCompanyLogo(companyLogo);
        if (companyAddress != null)
            jobModel.setCompanyAddress(companyAddress);
        if (companyName != null)
            jobModel.setCompanyName(companyName);
        if (url != null)
            jobModel.setLinkJob(url);
        if (type != null)
            jobModel.setType(type);

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

    public void genFile() {
        List<JobModel> listJob = jobRepository.findAll();
        String content = Content.header + "";
        for (JobModel job : listJob) {
            content = content + "<div class=\"col-lg-4 col-md-6 grid-item filter1 " + job.getType() + "\">\n" +
                    "                        <div class=\"courses-item1 mb-30\" id=\"list-it\">\n" +
                    "                            <div class=\"img-part1\">\n" +
                    "                                <img src=\"" + job.getCompanyLogo() + "\"\n" +
                    "                                    alt=\"" + job.getCompanyName() + "\">\n" +
                    "                            </div>\n" +
                    "                            <div class=\"content-part\">\n" +
                    "                                <h3 class=\"title mb-10\"><a target=\"_blank\" href=\"#\">" + job.getCompanyName() + "</a>\n" +
                    "                                </h3>\n" +
                    "                                <div class=\"bottom-part\">\n" +
                    "                                    <div class=\"info-meta mb-10\">\n" +
                    "                                        <ul>\n" +
                    "                                            <li>\n" +
                    "                                                <h4>Vị trí: " + job.getJobTitle() + "</h4>\n" +
                    "                                            </li>\n" +
                    "                                            <li>\n" +
                    "                                                <h4>Địa điểm: " + job.getCompanyAddress() + "</h4>\n" +
                    "                                            </li>\n" +
                    "                                        </ul>\n" +
                    "                                    </div>\n" +
                    "                                    <div class=\"btn-part\">\n" +
                    "                                        <a target=\"_blank\" href=\"" + job.getLinkJob() + "\"><i class=\"flaticon-right-arrow\"></i></a>\n" +
                    "                                    </div>\n" +
                    "                                </div>\n" +
                    "                            </div>\n" +
                    "                        </div>\n" +
                    "                    </div>";
        }
        content = content + Content.footer;
        writeToFile(content);
    }

    public void writeToFile(String content) {
        String configPath = "";
        File file = new File("");
        configPath = file.getAbsolutePath() + "/frontend/";
        try {
            String filepath = configPath + "list_it.html";
            File listitFile = new File(filepath);
            FileWriter fileWriter = new FileWriter(listitFile);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write(content);

            bufferedWriter.close();
            fileWriter.close();

        } catch (Exception e) {
            System.out.println(e);
        }
    }


}
