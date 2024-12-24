package in.vp.controllers;

import in.vp.entities.Candidate;
import in.vp.service.CandidateService;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class CandidateController {

    @Autowired
    private CandidateService candidateService;

    // Display the candidate form
    @GetMapping("/")
    public String showForm(Model model) {
        model.addAttribute("candidate", new Candidate());
        return "candidate-form"; // Corresponds to candidate-form.html in templates
    }

    // Handle form submission
    @PostMapping("/submit")
    public String submitForm(@Valid Candidate candidate, @RequestParam("resume") MultipartFile resume, Model model) {
        try {
            // Save candidate details and upload the resume
            candidateService.saveCandidate(candidate, resume);

            model.addAttribute("message", "Candidate details and resume uploaded successfully.");
            return "success"; // Corresponds to success.html in templates

        } catch (Exception e) {
            model.addAttribute("error", "Failed to upload candidate details: " + e.getMessage());
            return "candidate-form";
        }
    }
}

