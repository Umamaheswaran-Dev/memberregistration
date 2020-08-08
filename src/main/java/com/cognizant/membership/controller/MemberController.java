package com.cognizant.membership.controller;

import com.cognizant.membership.beans.MemberRequest;
import com.cognizant.membership.beans.MemberResponse;
import com.cognizant.membership.service.MemberService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
@RequestMapping("/cms")
public class MemberController {

    Logger logger = LoggerFactory.getLogger(MemberController.class);

    @Autowired
    private MemberService service;

    @RequestMapping(path = "/register", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public @ResponseBody
    MemberResponse register(@Valid @RequestBody MemberRequest member) {

        logger.info("Member Request:::" + member);

        MemberResponse response = service.registerMember(member);

        logger.info("Member Response:::" + response);

        return response;
    }
}
