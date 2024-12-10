package net.Askbd.routers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import net.Askbd.documents.Content;
import net.Askbd.documents.ContentSummary;
import net.Askbd.services.BasicService;
import net.Askbd.services.CachingService;

@Controller
public class BasicRouter {
    @Value("${design.index}")
    private String indexDesign;
    @Autowired
    private BasicService basicService;
    @Autowired
    private CachingService cachingService;

    @GetMapping("")
    public String index(Model model) {
        //System.out.println(cachingService.getCategoriesInfo().getCategories().get(0).getUri());
        List<ContentSummary> popularContents = basicService.getTopPopularContents();
        List<ContentSummary> recentContents = basicService.getTopRecentCreatedContents();
        System.out.println("BasicRouter: "+ popularContents.size());
        // for(Content content: basicService.getTop10LatestCreatedContents()) {
        //     System.out.println(content.getPrompt());
        //     System.out.println(content.getCreatedAt());
        //     System.out.println(content.getTitle());
        //     System.out.println(content.getId());
        // }
        model.addAttribute("recentContents", recentContents);
        model.addAttribute("popularContents", popularContents);
        
        return "public/index-"+ indexDesign;
    }
    
}
