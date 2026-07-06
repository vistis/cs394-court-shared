package kh.edu.paragoniu.court_shared.repository;

import kh.edu.paragoniu.court_shared.entity.DispositionOutcome;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DispositionOutcomeRepository
    extends JpaRepository<DispositionOutcome, Integer> {}
