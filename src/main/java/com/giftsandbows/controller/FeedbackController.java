package com.giftsandbows.controller;

import com.giftsandbows.model.Feedback;
import com.giftsandbows.repository.FeedbackRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/feedback")
@CrossOrigin
public class FeedbackController {

    private final FeedbackRepository feedbackRepo;

    public FeedbackController(FeedbackRepository feedbackRepo) {
        this.feedbackRepo = feedbackRepo;
    }

    @PostMapping
    public Feedback submit(@RequestBody Feedback feedback) {
        feedback.setApproved(false);
        return feedbackRepo.save(feedback);
    }

    @GetMapping
    public List<Feedback> approved() {
        return feedbackRepo.findByApprovedTrue();
    }

    @PutMapping("/admin/{id}/approve")
    public void approve(@PathVariable Long id) {
        Feedback fb = feedbackRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Feedback not found"));
        fb.setApproved(true);
        feedbackRepo.save(fb);
    }

    @DeleteMapping("/admin/{id}")
    public void delete(@PathVariable Long id) {
        feedbackRepo.deleteById(id);
    }
}
