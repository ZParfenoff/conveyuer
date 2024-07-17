package DTO;

import java.math.BigDecimal;

public class EmploymentDTO {
    private EmploymentStatus employmentStatus;
    private String employerINN;
    private BigDecimal salary;
    private Position position;
    private Integer workExperienceTotal;
    private Integer workExperienceCurrent;

    // Пустой конструктор
    public EmploymentDTO() {
    }

    // Конструктор со всеми параметрами
    public EmploymentDTO(EmploymentStatus employmentStatus, String employerINN, BigDecimal salary,
                         Position position, Integer workExperienceTotal, Integer workExperienceCurrent) {
        this.employmentStatus = employmentStatus;
        this.employerINN = employerINN;
        this.salary = salary;
        this.position = position;
        this.workExperienceTotal = workExperienceTotal;
        this.workExperienceCurrent = workExperienceCurrent;
    }

    // Геттеры и сеттеры для всех полей
    public EmploymentStatus getEmploymentStatus() {
        return employmentStatus;
    }

    public void setEmploymentStatus(EmploymentStatus employmentStatus) {
        this.employmentStatus = employmentStatus;
    }

    public String getEmployerINN() {
        return employerINN;
    }

    public void setEmployerINN(String employerINN) {
        this.employerINN = employerINN;
    }

    public BigDecimal getSalary() {
        return salary;
    }

    public void setSalary(BigDecimal salary) {
        this.salary = salary;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public Integer getWorkExperienceTotal() {
        return workExperienceTotal;
    }

    public void setWorkExperienceTotal(Integer workExperienceTotal) {
        this.workExperienceTotal = workExperienceTotal;
    }

    public Integer getWorkExperienceCurrent() {
        return workExperienceCurrent;
    }

    public void setWorkExperienceCurrent(Integer workExperienceCurrent) {
        this.workExperienceCurrent = workExperienceCurrent;
    }

    // Перечисление (Enum) для статуса занятости (employmentStatus)
    public enum EmploymentStatus {
        EMPLOYED,
        UNEMPLOYED,
        SELF_EMPLOYED,
        CONTRACTOR,
        STUDENT,
        RETIRED,
        OTHER
    }

    // Перечисление (Enum) для должности (position)
    public enum Position {
        MANAGER,
        DEVELOPER,
        ANALYST,
        DESIGNER,
        CONSULTANT,
        ADMINISTRATOR,
        OTHER
    }
}