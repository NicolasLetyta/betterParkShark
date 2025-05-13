package com.switchfully.apps.betterparkshark.webapi.controller;

import com.switchfully.apps.betterparkshark.domain.Employee;
import com.switchfully.apps.betterparkshark.domain.Member;
import com.switchfully.apps.betterparkshark.service.AuthenticationService;
import com.switchfully.apps.betterparkshark.service.MemberService;
import com.switchfully.apps.betterparkshark.webapi.dto.MemberDtoInput;
import com.switchfully.apps.betterparkshark.webapi.dto.MemberDtoOutput;
import com.switchfully.apps.betterparkshark.webapi.dto.MemberDtoOutputLight;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/members")
public class MemberController {

    private final MemberService memberService;
    private final AuthenticationService authenticationService;
    public MemberController(MemberService memberService, AuthenticationService authenticationService) {
        this.memberService = memberService;
        this.authenticationService = authenticationService;
    }

    @PostMapping(consumes = "application/json", produces = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public MemberDtoOutput registerAsMember(@RequestBody MemberDtoInput memberDtoInput) {
        return memberService.registerAsMember(memberDtoInput);
    }

    @GetMapping(produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public List<MemberDtoOutputLight> findAllMembers(@RequestHeader(value = "Authorization") String authToken ) {
        authenticationService.authenticateAdmin(authToken);
        return memberService.findAllMembers();
    }

    @GetMapping(path = "/{userId}", produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public MemberDtoOutput findMemberById(@PathVariable Long userId,
                                          @RequestHeader(value = "Authorization") String authToken ) {
        authenticationService.authenticateAdmin(authToken);
        return memberService.findMemberById(userId);
    }

    @PatchMapping(path = "/update_membership",consumes = "application/json",produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public MemberDtoOutput updateMembershipLevel(@RequestBody Map<String, Long> body,
                                                 @RequestHeader(value = "Authorization") String authToken ) {
        Member member = authenticationService.authenticateMember(authToken);
        Long membershipLevelId = body.get("membershipLevel");
        System.out.println(membershipLevelId);
        return memberService.updateMemberShipLevel(member, membershipLevelId);
    }
}
