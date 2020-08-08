package com.cognizant.membership.service;


import com.cognizant.membership.beans.MemberRequest;
import com.cognizant.membership.beans.MemberResponse;
import com.cognizant.membership.model.Member;
import com.cognizant.membership.repository.MemberRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;


@RunWith(MockitoJUnitRunner.class)
public class MemberServiceTest {

    @InjectMocks
    MemberService memberService;

    @Mock
    private MemberRepository mdRepository;

    @Test
    public void testRegisterMember() {
        MemberRequest sc_request = new MemberRequest();
        sc_request.setUserid("mahes876");
        sc_request.setPassword("password");

        Mockito.when(mdRepository.existsByUserid(Mockito.any())).thenReturn(true);
        //mdRepository.findByUserid(m.getUserid())
        List<Member> mdList = new ArrayList<Member>();
        Member md = new Member();
        md.setUserid("mahes876");
        md.setId(1);
        mdList.add(md);
        Mockito.when(mdRepository.findByUserid(Mockito.any())).thenReturn(mdList);

        MemberResponse MemberResponse = memberService.registerMember(sc_request);
        Assert.assertNotNull(MemberResponse);
        Assert.assertEquals(Integer.valueOf(200), MemberResponse.getStatusCode());
        Assert.assertEquals(Integer.valueOf(1), MemberResponse.getMemberId());
    }
}
