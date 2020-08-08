package com.cognizant.membership.beans;

import lombok.Data;

@Data
public class MemberResponse {

    private Integer memberId;

    private Integer planId;

    private Integer statusCode;

    private String statusMessage;

}
