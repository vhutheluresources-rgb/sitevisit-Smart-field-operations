package com.investhoodit.gr12webapp.service;

import com.investhoodit.gr12webapp.model.Tutorial;
import com.investhoodit.gr12webapp.repository.TutorialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class TutorialService {

    @Autowired
    private TutorialRepository tutorialRepository;

    // Save tutorial (No need to find user again, user already exists in session)
    public Tutorial saveTutorial(Tutorial tutorial) {
        return tutorialRepository.save(tutorial);
    }

    public List<Tutorial> getTutorialsByUser(Long userId) {
        return tutorialRepository.findByUserId(userId);
    }
}
