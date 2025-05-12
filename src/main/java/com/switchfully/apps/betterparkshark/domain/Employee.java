package com.switchfully.apps.betterparkshark.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "employee")
public class Employee {

    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "employee_seq")
    @SequenceGenerator(sequenceName = "employee_seq",name = "employee_seq", allocationSize = 1)
    @Id
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "mobile_phone")
    private String mobilePhone;

    @Column(name = "telephone")
    private String telephone;

    @Column
    private String email;

    @Column
    private String password;

    @Column(name = "type_employee")
    @Enumerated(EnumType.STRING)
    private EmployeeCategory typeEmployee;

    @Column(name = "address_id")
    private Long addressId;

    public Employee() {
    }

    public Employee(String firstName, String lastName, String mobilePhone, String telephone, String email, String password, EmployeeCategory typeEmployee, Long addressId) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.mobilePhone = mobilePhone;
        this.telephone = telephone;
        this.email = email;
        this.password = password;
        this.typeEmployee = typeEmployee;
        this.addressId = addressId;
    }

    public Employee(Long id, String firstName, String lastName, String mobilePhone, String email, String password, EmployeeCategory typeEmployee, Long addressId) {
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

    public String getMobilePhone() {
        return mobilePhone;
    }

    public String getTelephone() {
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

    public Long getAddressId() {
        return addressId;
    }
}
