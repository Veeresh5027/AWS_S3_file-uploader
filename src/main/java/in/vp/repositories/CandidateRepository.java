package in.vp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import in.vp.entities.Candidate;

public interface CandidateRepository extends  JpaRepository<Candidate, Long>{

}
