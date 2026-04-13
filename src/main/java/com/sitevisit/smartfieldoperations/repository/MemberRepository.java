package com.sitevisit.smartfieldoperations.repository;

import com.sitevisit.smartfieldoperations.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
}