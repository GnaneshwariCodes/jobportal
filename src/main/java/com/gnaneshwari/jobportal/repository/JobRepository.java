package com.gnaneshwari.jobportal.repository;

import com.gnaneshwari.jobportal.model.Job;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface JobRepository extends JpaRepository<Job, Long> {

    List<Job> findByTitleContainingIgnoreCase(String title);

    @Query("""
        SELECT j FROM Job j
        WHERE LOWER(j.title) LIKE LOWER(CONCAT('%', :title, '%'))
          AND LOWER(j.location) LIKE LOWER(CONCAT('%', :location, '%'))
          AND COALESCE(j.salary, 0) >= :minSalary
        """)
    Page<Job> search(@Param("title") String title,
                     @Param("location") String location,
                     @Param("minSalary") Double minSalary,
                     Pageable pageable);
}