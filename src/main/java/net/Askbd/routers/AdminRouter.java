package net.Askbd.routers;

import java.security.Principal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import net.Askbd.dtos.FileUploadDto;

@Controller
@RequestMapping("/admin")
public class AdminRouter {
    @GetMapping("/upload")
    public String loadFileUploaderPage(Model model, Principal principal) {
        FileUploadDto fileUploaderDto = new FileUploadDto();
        model.addAttribute("fileUploader", fileUploaderDto);
        System.out.println("AdminRouter");
        return "admin/uploader";
    }

    @GetMapping("/login")
    public String showLoginPage(Model model, Principal principal) {
        
        return "admin/login-form";
    }

    @PostMapping("/upload")
    public String saveFileAndGenerateLink(@ModelAttribute("fileUploader") FileUploadDto fileUploaderDto, BindingResult result, Model model, Principal principal) {
        System.out.println(fileUploaderDto.getDescription()+" "+fileUploaderDto.getFile().getOriginalFilename());
        // redirectAttributes.addFlashAttribute("type", "success");
        // redirectAttributes.addFlashAttribute("message", "File is successfully saved");
        return "admin/uploader";
    }
}
