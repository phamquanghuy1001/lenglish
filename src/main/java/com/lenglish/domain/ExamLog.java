package com.lenglish.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A ExamLog.
 */
@Entity
@Table(name = "exam_log")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ExamLog implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "create_date")
    private ZonedDateTime createDate;

    @NotNull
    @Min(value = 0)
    @Max(value = 5)
    @Column(name = "jhi_complete", nullable = false)
    private Integer complete;

    @Column(name = "point")
    private Integer point;

    @ManyToOne
    private User user;

    @ManyToOne
    private Exam exam;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getCreateDate() {
        return createDate;
    }

    public ExamLog createDate(ZonedDateTime createDate) {
        this.createDate = createDate;
        return this;
    }

    public void setCreateDate(ZonedDateTime createDate) {
        this.createDate = createDate;
    }

    public Integer getComplete() {
        return complete;
    }

    public ExamLog complete(Integer complete) {
        this.complete = complete;
        return this;
    }

    public void setComplete(Integer complete) {
        this.complete = complete;
    }

    public Integer getPoint() {
        return point;
    }

    public ExamLog point(Integer point) {
        this.point = point;
        return this;
    }

    public void setPoint(Integer point) {
        this.point = point;
    }

    public User getUser() {
        return user;
    }

    public ExamLog user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Exam getExam() {
        return exam;
    }

    public ExamLog exam(Exam exam) {
        this.exam = exam;
        return this;
    }

    public void setExam(Exam exam) {
        this.exam = exam;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ExamLog examLog = (ExamLog) o;
        if (examLog.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), examLog.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ExamLog{" +
            "id=" + getId() +
            ", createDate='" + getCreateDate() + "'" +
            ", complete='" + getComplete() + "'" +
            ", point='" + getPoint() + "'" +
            "}";
    }
}
