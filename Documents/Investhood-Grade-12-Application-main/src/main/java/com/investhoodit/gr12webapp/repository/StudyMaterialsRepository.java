package com.investhoodit.gr12webapp.repository;


import com.investhoodit.gr12webapp.model.StudyMaterials;
import com.investhoodit.gr12webapp.model.User;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;


public interface StudyMaterialsRepository extends JpaRepository<StudyMaterials, Long> {

	List<StudyMaterials> findByTeacher(User user);

}
