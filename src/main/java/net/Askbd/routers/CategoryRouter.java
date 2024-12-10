package net.Askbd.routers;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class CategoryRouter {

    @GetMapping(value={"/sitemap.xml", "/sitemap.xml/"}, produces = MediaType.APPLICATION_XML_VALUE)
    public String getPage(Model model, final RedirectAttributes redirectAttributes) {
        return "others/sitemap.xml";
    }

    @GetMapping(value="/404")
    public String get404Page(Model model, final RedirectAttributes redirectAttributes) {
        model.addAttribute("t", "Page not found");
        return "others/404";
    }

    @GetMapping(value={"/{category}", "/{category}/"})
    public String getPage(@PathVariable("category") String category, Model model, final RedirectAttributes redirectAttributes) {
        model.addAttribute("t", this.capitalize(category) +" Related Posts");
        switch (category) {        
            case "error":
                return "public/index";
            default:
                return "public/category-home";
        }
    }

    private String capitalize(String input) {
        return input.substring(0, 1).toUpperCase() + input.substring(1);
    }
}
