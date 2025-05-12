package com.switchfully.apps.betterparkshark.repository;

import com.switchfully.apps.betterparkshark.domain.Employee;
import com.switchfully.apps.betterparkshark.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository
extends JpaRepository<Employee, Long> {

    Employee findEmployeeById(long id);

    Employee findByEmail(String email);
}
