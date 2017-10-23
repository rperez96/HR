/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr;

import java.util.Date;

/**
 *
 * @author usuario
 */
public class JobHistory {
    private Employee employee;
    private Date startDate;
    private Date endDate;
    private Job job;
    private Department department;

    public JobHistory() {
    }

    public JobHistory(Employee employee, Date startDate, Date endDate, Job job, Department department) {
        this.employee = employee;
        this.startDate = startDate;
        this.endDate = endDate;
        this.job = job;
        this.department = department;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Job getJob() {
        return job;
    }

    public void setJob(Job job) {
        this.job = job;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }
    
    
}
