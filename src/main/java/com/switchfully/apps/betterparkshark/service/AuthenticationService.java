
package com.switchfully.apps.betterparkshark.service;

import com.switchfully.apps.betterparkshark.domain.Employee;
import com.switchfully.apps.betterparkshark.domain.EmployeeCategory;
import com.switchfully.apps.betterparkshark.domain.Member;
import com.switchfully.apps.betterparkshark.exception.InvalidHeaderException;
import com.switchfully.apps.betterparkshark.repository.EmployeeRepository;
import com.switchfully.apps.betterparkshark.repository.MemberRepository;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.Objects;

import static com.switchfully.apps.betterparkshark.utility.Validation.validateArgument;

@Service
public class AuthenticationService {
/*
    private final MemberRepository memberRepository;
    private final EmployeeRepository employeeRepository;

    public AuthenticationService(MemberRepository memberRepository, EmployeeRepository employeeRepository) {
        this.memberRepository = memberRepository;
        this.employeeRepository = employeeRepository;
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

    public Member authenticateMember(String authHeader) {
        String[] decodedArray = decode(authHeader);
        String email = decodedArray[0];
        String password = decodedArray[1];

        validateArgument(email,"Provided email not found in member repo",
                e->!memberRepository.existsByEmail(e), InvalidHeaderException::new);

        Member member = memberRepository.findByEmail(email);

        validateArgument(member,"Invalid password", m->!m.getPassword().equals(password));

        return member;
    }

    public Employee authenticateAdmin(String authHeader) {
        String[] decodedArray = decode(authHeader);
        String email = decodedArray[0];
        String password = decodedArray[1];

        validateArgument(email,"Provided email not found in employee repo",
                e->!employeeRepository.existsByEmail(e), InvalidHeaderException::new);

        Employee employee = employeeRepository.findByEmail(email);

        validateArgument(employee, "User is not a manager", e->!e.getTypeEmployee().equals(EmployeeCategory.ADMIN));
        validateArgument(employee,"Invalid password", e->!e.getPassword().equals(password));

        return employee;
    }

 */
}









