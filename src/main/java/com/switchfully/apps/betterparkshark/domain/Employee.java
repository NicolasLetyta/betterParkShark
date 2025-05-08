package com.switchfully.apps.betterparkshark.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "employee")
public class Employee {

    @Id
    private long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "mobile_phone")
    private Long mobilePhone;

    @Column(name = "telephone")
    private Long telephone;

    @Column
    private String email;

    @Column
    private String password;

    @Column(name = "type_employee")
    private EmployeeCategory typeEmployee;

    @Column(name = "address_id")
    private long addressId;

    public Employee() {
    }

    public Employee(String firstName, String lastName, Long mobilePhone, Long telephone, String email, String password, EmployeeCategory typeEmployee, long addressId) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.mobilePhone = mobilePhone;
        this.telephone = telephone;
        this.email = email;
        this.password = password;
        this.typeEmployee = typeEmployee;
        this.addressId = addressId;
    }

    public Employee(long id, String firstName, String lastName, Long mobilePhone, String email, String password, EmployeeCategory typeEmployee, long addressId) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.mobilePhone = mobilePhone;
        this.email = email;
        this.password = password;
        this.typeEmployee = typeEmployee;
        this.addressId = addressId;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Long getMobilePhone() {
        return mobilePhone;
    }

    public Long getTelephone() {
        return telephone;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public EmployeeCategory getTypeEmployee() {
        return typeEmployee;
    }

    public long getAddressId() {
        return addressId;
    }
}
