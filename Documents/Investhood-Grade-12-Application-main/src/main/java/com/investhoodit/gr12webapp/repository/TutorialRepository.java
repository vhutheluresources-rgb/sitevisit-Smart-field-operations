
package com.investhoodit.gr12webapp.repository;

import com.investhoodit.gr12webapp.model.Tutorial;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface TutorialRepository extends JpaRepository<Tutorial, Long> {
    List<Tutorial> findByUserId(Long userId); // Fetch tutorials for a specific user
}

