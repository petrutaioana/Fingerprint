package com.summer.school.retrofitcourse.model;

import com.google.gson.annotations.SerializedName;

public class EmployeeData {

    private int id;

    @SerializedName("employee_name")
    private String employeeName;

    @SerializedName("employee_salary")
    private int employeeSalary;

    @SerializedName("employee_age")
    private int employeeAge;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public int getEmployeeSalary() {
        return employeeSalary;
    }

    public void setEmployeeSalary(int employeeSalary) {
        this.employeeSalary = employeeSalary;
    }

    public int getEmployeeAge() {
        return employeeAge;
    }

    public void setEmployeeAge(int employeeAge) {
        this.employeeAge = employeeAge;
    }
}
