package com.investhoodit.gr12webapp.service;

import com.investhoodit.gr12webapp.model.StudyMaterials;
import com.investhoodit.gr12webapp.model.User;
import com.investhoodit.gr12webapp.repository.StudyMaterialsRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class StudyMaterialsService {

	@Autowired
	private StudyMaterialsRepository studyMaterialsRepository;

	// Save Study Material
	public StudyMaterials saveStudyMaterial(String subjectName, User user, byte[] fileUrl) {
		//System.out.println("Saving Study Material: " + studyMaterial);
		StudyMaterials studyMaterial = new StudyMaterials();
		studyMaterial.setSubjectName(subjectName);
		studyMaterial.setFileUrl(fileUrl);
		studyMaterial.setTeacher(user);
		studyMaterial.setUploadDate(LocalDateTime.now());
		
		return studyMaterialsRepository.save(studyMaterial);
	}

	// Get All Study Materials
	public List<StudyMaterials> getAllStudyMaterials() {
		return studyMaterialsRepository.findAll();
	}

	// Get Study Material by id
	public List<StudyMaterials> getStudyMaterialsByTeacher(User user) {
	    return studyMaterialsRepository.findByTeacher(user);
	}

	// Delete Study Material
	public void deleteStudyMaterial(Long id) {
		if (studyMaterialsRepository.existsById(id)) {
			studyMaterialsRepository.deleteById(id);
		} else {
			throw new IllegalArgumentException("Study Material not found");
		}
	}


}


