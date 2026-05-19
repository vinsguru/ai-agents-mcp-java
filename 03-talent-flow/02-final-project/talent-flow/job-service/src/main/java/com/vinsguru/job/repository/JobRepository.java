package com.vinsguru.job.repository;

import com.vinsguru.job.entity.Job;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JobRepository extends JpaRepository<Job, Integer> {

    @Query("""
                SELECT DISTINCT j
                FROM Job j
                JOIN j.skills s
                WHERE LOWER(s.skill) IN :skills
            """)
    List<Job> findByAnySkill(List<String> skills);

}
