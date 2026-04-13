package com.sitevisit.smartfieldoperations.controller;

import com.sitevisit.smartfieldoperations.entity.Company;
import com.sitevisit.smartfieldoperations.entity.Member;
import com.sitevisit.smartfieldoperations.repository.CompanyRepository;
import com.sitevisit.smartfieldoperations.repository.MemberRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/members")
@CrossOrigin(origins = "*")
public class MemberController {

    private final MemberRepository memberRepo;
    private final CompanyRepository companyRepo;

    public MemberController(MemberRepository memberRepo, CompanyRepository companyRepo) {
        this.memberRepo = memberRepo;
        this.companyRepo = companyRepo;
    }

    @PostMapping
    public Member createMember(@RequestBody Map<String, String> data) {
        Member m = new Member();

        m.setFullName(data.get("fullName"));
        m.setEmail(data.get("email"));
        m.setPhoneNumber(data.get("phoneNumber"));
        m.setRole(data.get("role"));
        m.setStatus(data.get("status"));

        Long companyId = Long.parseLong(data.get("companyId"));
        Company company = companyRepo.findById(companyId)
                .orElseThrow(() -> new RuntimeException("Company not found"));

        m.setCompany(company);

        return memberRepo.save(m);
    }

    @GetMapping
    public List<Member> getAllMembers() {
        return memberRepo.findAll();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Member> updateMember(@PathVariable Long id, @RequestBody Map<String, String> data) {
        Member member = memberRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Member not found"));

        member.setFullName(data.get("fullName"));
        member.setEmail(data.get("email"));
        member.setPhoneNumber(data.get("phoneNumber"));
        member.setRole(data.get("role"));
        member.setStatus(data.get("status"));

        Long companyId = Long.parseLong(data.get("companyId"));
        Company company = companyRepo.findById(companyId)
                .orElseThrow(() -> new RuntimeException("Company not found"));

        member.setCompany(company);

        Member updatedMember = memberRepo.save(member);
        return ResponseEntity.ok(updatedMember);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteMember(@PathVariable Long id) {
        Member member = memberRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Member not found"));

        memberRepo.delete(member);
        return ResponseEntity.ok("Member deleted successfully");
    }
}