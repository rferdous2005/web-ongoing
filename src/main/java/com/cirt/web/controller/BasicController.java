package com.cirt.web.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.net.http.HttpHeaders;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.springframework.core.io.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.cirt.web.dto.HighlightsDto;
import com.cirt.web.dto.IncidentDto;
import com.cirt.web.entity.Homepage;
import com.cirt.web.entity.Incident;
import com.cirt.web.entity.Media;
import com.cirt.web.entity.Post;
import com.cirt.web.repository.MediaRepository;
import com.cirt.web.service.IncidentService;
import com.cirt.web.service.PostService;
import com.cirt.web.service.RecaptchaService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Controller
@RequestMapping
public class BasicController {
    @Value("${app.file.location}")
    private String UPLOAD_DIR;
    @Autowired
    MediaRepository mediaRepository;
    @Autowired
    RecaptchaService recaptchaService;
    @Autowired
    PostService postService;
    private final List<String> acceptedDirectories = List.of(".docx", ".pdf", ".jpg", ".jpeg", ".zip", ".png",
            "incident");

    @Autowired
    IncidentService incidentService;

    @Autowired
    ObjectMapper objectMapper;
    private final int PAGE_SIZE = 6;

    @GetMapping
    public String getMethodName(Model model) {

        Page<Post> alertsList = postService.getPaginatedPostsForPublic("alerts",
                PageRequest.of(0, 3, Sort.by(Sort.Direction.DESC, "publishedAt")));
        Page<Post> magazinesList = postService.getPaginatedPostsForPublic("magazines",
                PageRequest.of(0, PAGE_SIZE, Sort.by(Sort.Direction.DESC, "publishedAt")));
        Page<Post> newsList = postService.getPaginatedPostsForPublic("news",
                PageRequest.of(0, 6, Sort.by(Sort.Direction.DESC, "publishedAt")));
        // if(alertsList.getTotalElements() == 0) {
        // model.addAttribute("postList", new LinkedList<>());
        // } else {
        // model.addAttribute("postList", postListPaged.getContent());
        // }
        Homepage homepageContent = this.postService.getOnlyOneHomepageContent();
        List<HighlightsDto> highlightsDtos = new ArrayList<>();
        try {
            JsonNode arrayNode = objectMapper.readTree(homepageContent.getHighlights());
            for (JsonNode jsonNode : arrayNode) {
                HighlightsDto highlightsDto = new HighlightsDto();
                highlightsDto.setTitle(jsonNode.get("title").asText());
                highlightsDto.setBody(jsonNode.get("body").asText());
                highlightsDto.setUrl(jsonNode.get("url").asText());
                highlightsDtos.add(highlightsDto);
            }
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        model.addAttribute("warningLabel", homepageContent.getWarningLabel().split("\\|")[0]);
        model.addAttribute("warningColorStyle",
                "margin-left: 1.5rem; animation: warningpulse 1.5s infinite;color: #000;border-left: 55px solid "
                        + homepageContent.getWarningColor());
        model.addAttribute("highlights", highlightsDtos);
        model.addAttribute("alertsList", alertsList);
        model.addAttribute("magazinesList", magazinesList);
        model.addAttribute("newsList", newsList);
        // latest news or events
        return "home";
    }

    @GetMapping("/who-we-are")
    public String getIntroWho() {
        return "basic/who";
    }

    @GetMapping("/what-we-do")
    public String getIntroWhat() {
        return "basic/what";
    }

    @GetMapping("/mission-vision")
    public String getIntroMisVis() {
        return "basic/mis-vis";
    }

    @GetMapping("/media")
    public void viewFile(HttpServletResponse response, HttpServletRequest request, @RequestParam("id") String fileName)
            throws Exception {
        File file = new File(UPLOAD_DIR + fileName);

        if (!acceptedDirectories.contains(fileName.split("/")[0]) || fileName.split("/").length > 2)
            return;

        FileInputStream inStream = new FileInputStream(file);
        // gets MIME type of the file
        String mimeType = "application/octet-stream";
        OutputStream outStream = response.getOutputStream();

        // modifies response
        response.setContentType(mimeType);
        response.setContentLength((int) file.length());

        // forces download
        String headerKey = "Content-Disposition";
        String headerValue = String.format("inline; filename=\"%s\"", fileName);
        response.setHeader(headerKey, headerValue);

        byte[] buffer = new byte[4096];
        int bytesRead = -1;

        while ((bytesRead = inStream.read(buffer)) != -1) {
            outStream.write(buffer, 0, bytesRead);
        }

        inStream.close();
        outStream.close();
    }

    @GetMapping("/login")
    public String loginPage(Model model, HttpServletRequest request) {
        return "basic/login-form";
    }

    @GetMapping("/service")
    public String serviceDescription(Model model, HttpServletRequest request) {
        return "basic/services";
    }

    @GetMapping("/report-incident")
    public String reportIncidentForm(Model model, HttpServletRequest request) {
        IncidentDto incidentDto = new IncidentDto();
        incidentDto.setReportType("individual");
        model.addAttribute("incidentDto", incidentDto);
        return "fragments/report-incident";
    }

    @PostMapping("/report-incident")
    public String reportIncidentSave(Model model, @ModelAttribute("incidentDto") IncidentDto incidentDto,
            BindingResult br, HttpServletRequest request) {
        String token = request.getParameter("g-recaptcha-response");
        String action = request.getParameter("recaptcha-action"); // "incident_submit"

        var result = recaptchaService.verify(token, request.getRemoteAddr());
        boolean ok = result != null
                && result.success()
                && "incident_submit".equals(result.action())
                && result.score() != null
                && result.score() >= 0.7f; // adjust threshold if needed

        if (!ok) {
            br.reject("recaptcha.invalid", "Please verify you’re not a bot.");
            // ensure Step 2 stays open if you use step logic:
            model.addAttribute("step", 2);
            return "fragments/report-incident"; // your Thymeleaf view name
        }
        this.incidentService.saveIncident(incidentDto);
        return "fragments/incident-success";
    }

    @GetMapping("/tlp")
    public String showTlpPage(Model model, HttpServletRequest request) {
        return "basic/tlp";
    }
}
