package kh.edu.paragoniu.court_shared.repository;

import kh.edu.paragoniu.court_shared.entity.CaseStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CaseStatusRepository
    extends JpaRepository<CaseStatus, Integer> {}
