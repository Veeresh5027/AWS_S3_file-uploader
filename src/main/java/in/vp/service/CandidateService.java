package in.vp.service;

import in.vp.entities.Candidate;
import org.springframework.web.multipart.MultipartFile;

public interface CandidateService {

    // Save candidate details and upload resume to S3
    void saveCandidate(Candidate candidate, MultipartFile resume);
}
