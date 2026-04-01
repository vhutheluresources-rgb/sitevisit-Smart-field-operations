package com.investhoodit.gr12webapp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.investhoodit.gr12webapp.model.Subject;
import com.investhoodit.gr12webapp.model.User;
import com.investhoodit.gr12webapp.repository.SubjectDao;

@Service
public class SubjectService {

	private final SubjectDao subjectDao;

	@Autowired
	public SubjectService(SubjectDao subjectDao) {
		this.subjectDao = subjectDao;
	}

	public List<Subject> getAllSubjects(User user) {
		return subjectDao.findAllByUserId(user.getId());
	}

	public boolean addSubject(Subject subject, User user) {
		if (subject == null || subject.getSubjectName() == null || subject.getSubjectName().trim().isEmpty()) {
			return false; // Prevent adding a subject with a null or empty name
		}

		boolean isAvailable = verifySubject(subject.getSubjectName(), user);
		if (isAvailable) {
			subject.setUser(user);
			subjectDao.save(subject);
			return true;
		}
		return false;
	}

	public boolean verifySubject(String subjectName, User user) {
		if (subjectName == null || subjectName.trim().isEmpty()) {
			return false;
		}

		// Check if the subject exists for this user
		Subject existingSubject = subjectDao.findBySubjectNameAndUser(subjectName, user);

		return existingSubject == null; // Return true if the subject does not exist for the user
	}

	public void deleteSubject(Long id) {
		subjectDao.deleteById(id);
	}
	
	public int countSubjectsByUser(User user) {
        return subjectDao.countByUser(user);
    }
	

}
