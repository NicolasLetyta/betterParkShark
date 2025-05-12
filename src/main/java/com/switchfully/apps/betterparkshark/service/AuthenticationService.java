/*

package com.switchfully.apps.betterparkshark.service;

import com.switchfully.apps.betterparkshark.domain.Member;
import com.switchfully.apps.betterparkshark.exception.InvalidHeaderException;
import com.switchfully.apps.betterparkshark.repository.MemberRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Base64;
import java.util.Optional;
import java.util.function.Function;

import static com.switchfully.apps.betterparkshark.utility.Validation.validateArgument;

@Service
public class AuthenticationService {

    private final MemberRepository memberRepository;

    public AuthenticationService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public String encode (String email, String password) {
        String valueToEncode = email + ":" + password;
        return "Basic " + Base64.getEncoder().encodeToString(valueToEncode.getBytes());
    }

    public String[] decode(String authHeader) {
        validateArgument(authHeader,"Missing authorization header",String::isEmpty, InvalidHeaderException::new);
        validateArgument(authHeader,"Invalid authorization header", a->!a.startsWith("Basic "), InvalidHeaderException::new);

        String base64 = authHeader.substring(6);
        String decoded = new String(Base64.getDecoder().decode(base64));

        String[] decodedArray = decoded.split(":", 2);
        validateArgument(decodedArray,"Invalid Authorization format", array->array.length!=2, InvalidHeaderException::new);

        return decodedArray;
    }

    public Member authenticateUser(String authHeader) {
        String[] decodedArray = decode(authHeader);
        String email = decodedArray[0];
        String password = decodedArray[1];

        Member member = memberRepository.findByEmail(email);
        validateArgument(member,"Invalid email or password", m->!m.getPassword().equals(password));
        return member;
    }

    public Member authenticateAdmin(String authHeader) {
        Member member = authenticateUser(authHeader);
        validateArgument(member,"User does not have admin privileges",RESPONSE_STATUS_EXCEPTION,u->!u.getRole().equals(UserRole.ADMIN));
        return member;
    }
}

*/








