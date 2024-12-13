package vttp.batch5.ssf.noticeboard.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.validation.Valid;
import vttp.batch5.ssf.noticeboard.models.Notice;

// Use this class to write your request handlers

@Controller
@RequestMapping
public class NoticeController {

    @GetMapping("/")
    public String noticeBoardSubmission(Model model) {
        Notice notice = new Notice();
        model.addAttribute("notice", notice);
        return "notice";
    }
    
    @PostMapping("/notice")
    public String postNoticeBoardSubmission(@Valid @ModelAttribute("notice") Notice notice, BindingResult result, Model model) {

        if (result.hasErrors()) {
            return "notice";
        }

        if (notice.getCategories().size() < 1) {
            FieldError err  = new FieldError("notice", "categories", "Need at least 1 category");
            result.addError(err);
            return "notice";
        }


        // frontcontroller.java step 3


        return "noticesuccess";
    }
    
}
