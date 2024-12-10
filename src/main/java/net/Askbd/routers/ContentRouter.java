package net.Askbd.routers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import net.Askbd.documents.Content;
import net.Askbd.services.ContentService;

@Controller
public class ContentRouter {
    @Autowired
    private ContentService contentService;

    @GetMapping(value = {"/{category}/{postURI}"})
    public String showSinglePost(@PathVariable("category") String category, @PathVariable("postURI") String postURI,
                            Model model, final RedirectAttributes redirectAttributes) {
        Content content = contentService.getSingleContentByCategoryAndPostURI(category, postURI);
        System.out.println("ContentRouter");
        System.out.println(content.getTitle());
        model.addAttribute("post", content);
        return "public/single-post-1";
    }
}
