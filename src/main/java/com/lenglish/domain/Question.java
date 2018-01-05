package com.lenglish.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import com.lenglish.domain.enumeration.QuestionType;

/**
 * A Question.
 */
@Entity
@Table(name = "question")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Question implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "create_date")
    private ZonedDateTime createDate;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "question_type", nullable = false)
    private QuestionType questionType;

    @NotNull
    @Column(name = "content", nullable = false)
    private String content;

    @Lob
    @Column(name = "image")
    private byte[] image;

    @Column(name = "image_content_type")
    private String imageContentType;

    @Lob
    @Column(name = "jhi_resource")
    private byte[] resource;

    @Column(name = "jhi_resource_content_type")
    private String resourceContentType;

    @ManyToOne
    private Lesson lesson;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "question_exam",
               joinColumns = @JoinColumn(name="questions_id", referencedColumnName="id"),
               inverseJoinColumns = @JoinColumn(name="exams_id", referencedColumnName="id"))
    private Set<Exam> exams = new HashSet<>();

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

    public Question createDate(ZonedDateTime createDate) {
        this.createDate = createDate;
        return this;
    }

    public void setCreateDate(ZonedDateTime createDate) {
        this.createDate = createDate;
    }

    public QuestionType getQuestionType() {
        return questionType;
    }

    public Question questionType(QuestionType questionType) {
        this.questionType = questionType;
        return this;
    }

    public void setQuestionType(QuestionType questionType) {
        this.questionType = questionType;
    }

    public String getContent() {
        return content;
    }

    public Question content(String content) {
        this.content = content;
        return this;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public byte[] getImage() {
        return image;
    }

    public Question image(byte[] image) {
        this.image = image;
        return this;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getImageContentType() {
        return imageContentType;
    }

    public Question imageContentType(String imageContentType) {
        this.imageContentType = imageContentType;
        return this;
    }

    public void setImageContentType(String imageContentType) {
        this.imageContentType = imageContentType;
    }

    public byte[] getResource() {
        return resource;
    }

    public Question resource(byte[] resource) {
        this.resource = resource;
        return this;
    }

    public void setResource(byte[] resource) {
        this.resource = resource;
    }

    public String getResourceContentType() {
        return resourceContentType;
    }

    public Question resourceContentType(String resourceContentType) {
        this.resourceContentType = resourceContentType;
        return this;
    }

    public void setResourceContentType(String resourceContentType) {
        this.resourceContentType = resourceContentType;
    }

    public Lesson getLesson() {
        return lesson;
    }

    public Question lesson(Lesson lesson) {
        this.lesson = lesson;
        return this;
    }

    public void setLesson(Lesson lesson) {
        this.lesson = lesson;
    }

    public Set<Exam> getExams() {
        return exams;
    }

    public Question exams(Set<Exam> exams) {
        this.exams = exams;
        return this;
    }

    public Question addExam(Exam exam) {
        this.exams.add(exam);
        exam.getQuestions().add(this);
        return this;
    }

    public Question removeExam(Exam exam) {
        this.exams.remove(exam);
        exam.getQuestions().remove(this);
        return this;
    }

    public void setExams(Set<Exam> exams) {
        this.exams = exams;
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
        Question question = (Question) o;
        if (question.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), question.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Question{" +
            "id=" + getId() +
            ", createDate='" + getCreateDate() + "'" +
            ", questionType='" + getQuestionType() + "'" +
            ", content='" + getContent() + "'" +
            ", image='" + getImage() + "'" +
            ", imageContentType='" + imageContentType + "'" +
            ", resource='" + getResource() + "'" +
            ", resourceContentType='" + resourceContentType + "'" +
            "}";
    }
}
