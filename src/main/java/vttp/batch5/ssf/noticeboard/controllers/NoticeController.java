package vttp.batch5.ssf.noticeboard.controllers;

import org.springframework.beans.factory.annotation.Value;
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
import vttp.batch5.ssf.noticeboard.services.NoticeService;

// Use this class to write your request handlers

@Controller
@RequestMapping
public class NoticeController {

    @Value("${publishing.server.hostname}")
	private String noticeServerUrl;

    @GetMapping("/")
    public String noticeBoardSubmission(Model model) {
        Notice notice = new Notice();
        model.addAttribute("notice", notice);
        return "notice";
    }

    @PostMapping("/notice")
    public String postNoticeBoardSubmission(@Valid @ModelAttribute("notice") Notice notice, BindingResult result,
            Model model) {

        if (result.hasErrors()) {
            return "notice";
        }

        if (notice.getCategories().size() < 1) {
            FieldError err = new FieldError("notice", "categories", "Need at least 1 category");
            result.addError(err);
            return "notice";
        }

        // System.out.println(noticeServerUrl);
        // System.out.println(notice.toString());

        try {
            String noticeString = notice.toJson(notice);

            NoticeService.postToNoticeServer(noticeString, noticeServerUrl);

            
            // model.addAttribute("id", id);
            return "noticesuccess";
        } catch (Exception e) {
            System.err.println("Post failed (exception): " + e.getMessage());
        }

        return "noticeerror";
    }

}
