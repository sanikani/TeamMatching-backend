package com.cbnu.teammatching.member.service;

import com.cbnu.teammatching.exception.member.MemberNotFoundException;
import com.cbnu.teammatching.member.auth.JwtUtil;
import com.cbnu.teammatching.member.domain.*;
import com.cbnu.teammatching.member.dto.*;
import com.cbnu.teammatching.member.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class MemberProfileService {

    private final JwtUtil jwtUtil;
    private final MemberRepository memberRepository;
    private final CareerRepository careerRepository;
    private final CertificationRepository certificationRepository;
    private final EducationRepository educationRepository;
    private final SkillRepository skillRepository;
    private final InterestRepository interestRepository;

    public List<CareerDto> getCareer(String token) {
        Member member = getMember(token);
        List<Career> careers = member.getCareers();
        return careers.stream()
                .map(CareerDto::of).collect(Collectors.toList());
    }

    public CareerDto saveCareer(String token, CareerDto careerDto) {
        Member member = getMember(token);
        if (careerDto.getCareerId() == null) {
            Career newCareer = Career.createCareer(member, careerDto);
            careerRepository.save(newCareer);
            return CareerDto.of(newCareer);
        } else {
            Career career = member.getCareers().stream().filter(i -> Objects.equals(i.getId(), careerDto.getCareerId())).findFirst().orElseThrow(MemberNotFoundException::new);
            career.updateCareer(careerDto);
            return careerDto;
        }
    }

    public List<CertificationDto> getCertification(String token) {
        Member member = getMember(token);
        List<Certification> certifications = member.getCertifications();
        return certifications.stream()
                .map(CertificationDto::of).collect(Collectors.toList());
    }

    public CertificationDto saveCertification(String token, CertificationDto certificationDto) {
        Member member = getMember(token);
        if (certificationDto.getCertificationId() == null) {
            Certification newCertification = Certification.createCertification(member, certificationDto);
            certificationRepository.save(newCertification);
            return CertificationDto.of(newCertification);
        } else {
            Certification certification = member.getCertifications().stream().filter(i -> Objects.equals(i.getId(), certificationDto.getCertificationId())).findFirst().orElseThrow(MemberNotFoundException::new);
            certification.updateCertification(certificationDto);
            return certificationDto;
        }
    }

    public List<EducationDto> getEducation(String token) {
        Member member = getMember(token);
        List<Education> educations = member.getEducations();
        return educations.stream()
                .map(EducationDto::of).collect(Collectors.toList());
    }

    public EducationDto saveEducation(String token, EducationDto educationDto) {
        Member member = getMember(token);
        if (educationDto.getEducationId() == null) {
            Education newEducation = Education.createEducation(member, educationDto);
            educationRepository.save(newEducation);
            return EducationDto.of(newEducation);
        } else {
            Education education = member.getEducations().stream().filter(i -> Objects.equals(i.getId(), educationDto.getEducationId())).findFirst().orElseThrow(MemberNotFoundException::new);
            education.updateEducation(educationDto);
            return educationDto;
        }
    }

    public List<SkillRequest.SkillDto> getSkill(String token) {
        Member member = getMember(token);
        List<Skill> skills = member.getSkills();
        return skills.stream()
                .map(SkillRequest::of).collect(Collectors.toList());
    }

    public List<SkillRequest.SkillDto> saveSkills(String token, List<SkillRequest.SkillDto> skills) {
        Member member = getMember(token);
        for (SkillRequest.SkillDto skillDto : skills) {
            Skill skill = Skill.createSkill(member, skillDto);
            skillRepository.save(skill);
        }
        return skills;
    }

    public List<InterestRequest.InterestDto> getInterest(String token) {
        Member member = getMember(token);
        List<Interest> interests = member.getInterests();
        return interests.stream()
                .map(InterestRequest::of).collect(Collectors.toList());
    }

    public List<InterestRequest.InterestDto> saveInterests(String token, List<InterestRequest.InterestDto> interestsDto) {
        Member member = getMember(token);
        for (InterestRequest.InterestDto interestDto : interestsDto) {
            Interest interest = Interest.createInterest(member, interestDto);
            interestRepository.save(interest);
        }
        return interestsDto;
    }

    private Member getMember(String token) {
        Long memberId = jwtUtil.getMemberId(token);
        return memberRepository.findById(memberId).orElseThrow(MemberNotFoundException::new);
    }


}