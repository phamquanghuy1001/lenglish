package com.lenglish.service.dto;


import java.time.ZonedDateTime;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the ExamLog entity.
 */
public class ExamLogDTO implements Serializable {

    private Long id;

    private ZonedDateTime createDate;

    @NotNull
    @Min(value = 0)
    @Max(value = 5)
    private Integer complete;

    private Integer point;

    private Long userId;

    private Long examId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(ZonedDateTime createDate) {
        this.createDate = createDate;
    }

    public Integer getComplete() {
        return complete;
    }

    public void setComplete(Integer complete) {
        this.complete = complete;
    }

    public Integer getPoint() {
        return point;
    }

    public void setPoint(Integer point) {
        this.point = point;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getExamId() {
        return examId;
    }

    public void setExamId(Long examId) {
        this.examId = examId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ExamLogDTO examLogDTO = (ExamLogDTO) o;
        if(examLogDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), examLogDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ExamLogDTO{" +
            "id=" + getId() +
            ", createDate='" + getCreateDate() + "'" +
            ", complete='" + getComplete() + "'" +
            ", point='" + getPoint() + "'" +
            "}";
    }
}
