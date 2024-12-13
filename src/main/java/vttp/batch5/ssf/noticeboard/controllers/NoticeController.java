package vttp.batch5.ssf.noticeboard.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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

    @Autowired
    NoticeService noticeService;

    @GetMapping("/")
    public String noticeBoardSubmission(Model model) {
        Notice notice = new Notice();
        model.addAttribute("notice", notice);
        return "notice";
    }

    @PostMapping("/notice")
    public String postNoticeBoardSubmission(@Valid @ModelAttribute("notice") Notice notice, BindingResult result,
            Model model) {

        if (result.hasErrors() || notice.getCategories().size() < 1) {
            if (notice.getCategories().size() < 1) {
                FieldError err = new FieldError("notice", "categories", "Need at least 1 category");
                result.addError(err);
            }
            return "notice";
        }

        try {
            ResponseEntity<String> response = noticeService.postToNoticeServer(notice);

            if (response.getStatusCode() != HttpStatus.OK) {
                String errorMsg = noticeService.responseItem(response, "message");
                model.addAttribute("errorMsg", errorMsg);
                return "noticeerror";
            }

            noticeService.insertNotices(response);

            String id = noticeService.responseItem(response, "id");
            model.addAttribute("id", id);
            return "noticesuccess";

        } catch (Exception e) {
            System.err.println("Post failed (exception): " + e.getMessage());
        }

        return "noticeerror";
    }

    @SuppressWarnings("rawtypes")
    @GetMapping(path = "/status", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity checkHealth() {
        if (noticeService.checkHealth())
            return ResponseEntity.ok("{}");

        return ResponseEntity.status(503).body("{}");
    }

}
