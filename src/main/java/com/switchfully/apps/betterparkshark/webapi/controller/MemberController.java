package com.switchfully.apps.betterparkshark.webapi.controller;

import com.switchfully.apps.betterparkshark.service.AuthenticationService;
import com.switchfully.apps.betterparkshark.service.MemberService;
import com.switchfully.apps.betterparkshark.webapi.dto.MemberDtoInput;
import com.switchfully.apps.betterparkshark.webapi.dto.MemberDtoOutput;
import com.switchfully.apps.betterparkshark.webapi.dto.MemberDtoOutputLight;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public MemberDtoOutput registerAsMember(@RequestBody MemberDtoInput memberDtoInput) {
        //memberService takes care of input validation, just call method from service
        return memberService.registerAsMember(memberDtoInput);
    }

    @GetMapping(produces = "application/json")
    public List<MemberDtoOutputLight> findAllMembers(@RequestHeader(value = "Authorization") String authToken ) {
        //AUTHORIZE AS ADMIN
        //Member admin = !(authorize member as admin from authorizationservice)!
        return memberService.findAllMembers();
    }

    @GetMapping(path = "/{userId}", produces = "application/json")
    public MemberDtoOutput findMemberById(@PathVariable Long userId,
                                          @RequestHeader(value = "Authorization") String authToken ) {
        //AUTHORIZE AS ADMIN
        //Member admin = !(authorize user as admin from authorizationservice)!
        return memberService.findMemberById(userId);
    }

    @PatchMapping(path = "/{userId}",consumes = "application/json",produces = "application/json")
    public MemberDtoOutput updateMembershipLevel(@PathVariable("userId") long userId,
                                                 @RequestBody int memberShipLevel,
                                                 @RequestHeader(value = "Authorization") String authToken ) {
        //AUTHORIZE AS MEMBER
        //Member member = !(authorize user as member from authorizationservice)!
        return memberService.updateMemberShipLevel(userId, memberShipLevel);
    }
}
