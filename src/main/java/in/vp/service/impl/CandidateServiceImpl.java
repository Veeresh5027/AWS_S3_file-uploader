package in.vp.service.impl;

import in.vp.entities.Candidate;
import in.vp.repositories.CandidateRepository;
import in.vp.service.CandidateService;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;
import java.util.UUID;

@Service
@Slf4j
public class CandidateServiceImpl implements CandidateService {

    private final S3Client s3Client;
    private final CandidateRepository candidateRepository;

    @Value("${aws.s3.bucket-name}")
    private String bucketName;

    public CandidateServiceImpl(S3Client s3Client, CandidateRepository candidateRepository) {
        this.s3Client = s3Client;
        this.candidateRepository = candidateRepository;
    }

    @Override
    public void saveCandidate(Candidate candidate, MultipartFile resume) {
        // Upload resume to AWS S3 and get the resume URL
        String resumeUrl = uploadResumeToS3(resume);

        // Set the resume URL to the candidate entity
        candidate.setResumeUrl(resumeUrl);

        // Save the candidate details in the database
        candidateRepository.save(candidate);
    }

    private String uploadResumeToS3(MultipartFile resume) {
        String key = "resumes/" + UUID.randomUUID() + "-" + resume.getOriginalFilename();

        try {
            // Upload the resume file to S3
            s3Client.putObject(
                    PutObjectRequest.builder()
                            .bucket(bucketName)
                            .key(key)
                            .contentType(resume.getContentType())
                            .build(),
                    RequestBody.fromBytes(resume.getBytes())
            );

            // Return the public URL of the uploaded resume
            return String.format("https://%s.s3.amazonaws.com/%s", bucketName, key);

        } catch (IOException e) {
            throw new RuntimeException("Error occurred while uploading resume to S3", e);
        }
    }
}
