package com.cognizant.membership.service;

import com.cognizant.membership.beans.Address;
import com.cognizant.membership.beans.MemberRequest;
import com.cognizant.membership.beans.MemberResponse;
import com.cognizant.membership.beans.Plan;
import com.cognizant.membership.model.Member;
import com.cognizant.membership.repository.AddressRepository;
import com.cognizant.membership.repository.MemberRepository;
import com.cognizant.membership.repository.PlanRepository;
import org.jasypt.util.password.BasicPasswordEncryptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MemberService {

    Logger logger = LoggerFactory.getLogger(MemberService.class);

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private PlanRepository planRepository;

    public MemberResponse registerMember(MemberRequest memberRequest) {
        MemberResponse response = new MemberResponse();
        if (!memberRepository.existsByUserid(memberRequest.getUserid())) {

            try {
                Member member = populateEntityData(memberRequest);
                logger.info("Member ID:" + member.getId());
                response.setMemberId(member.getId());
                response.setPlanId(member.getPlan().getPlanid());
                response.setStatusCode(201);
                response.setStatusMessage("SUCCESS");
            } catch (Exception e) {
                logger.error(e.getMessage());
                response.setMemberId(0);
                response.setPlanId(0);
                response.setStatusCode(500);
                response.setStatusMessage(e.getMessage());
            }
        } else {
            logger.info("User already exists");
            List<Member> memberList = memberRepository.findByUserid(memberRequest.getUserid());
            if (!memberList.isEmpty()) {
                response.setMemberId(memberList.get(0).getId());
                response.setPlanId(memberList.get(0).getPlan().getPlanid());
            } else {
                response.setMemberId(0);
                response.setPlanId(0);
            }

            response.setStatusMessage("User already exists");
            response.setStatusCode(409);
        }

        return response;
    }

    private Member populateEntityData(MemberRequest memberRequest) {

        Member member = new Member();
        BeanUtils.copyProperties(memberRequest, member);
        logger.info("Member Table Temp Data:::" + member);

        BasicPasswordEncryptor passwordEncryptor = new BasicPasswordEncryptor();
        String encryptedPassword = passwordEncryptor.encryptPassword(member.getPassword());

        member.setPassword(encryptedPassword);
        Address addressRequest = memberRequest.getAddress();
        com.cognizant.membership.model.Address address = new com.cognizant.membership.model.Address();
        BeanUtils.copyProperties(addressRequest, address);
        logger.info("Address Table Data:::" + address);

        Plan planRequest = memberRequest.getPlan();
        com.cognizant.membership.model.Plan plan = new com.cognizant.membership.model.Plan();
        BeanUtils.copyProperties(planRequest, plan);
        logger.info("Plan Table Data:::" + plan);

        member.setPlan(plan);
        member.setAddress(address);
        memberRepository.save(member);
        logger.info("Member Table Data:::" + member);

        return member;
    }


}
