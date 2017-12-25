package com.lenglish.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A LessonLog.
 */
@Entity
@Table(name = "lesson_log")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class LessonLog implements Serializable {

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

    @NotNull
    @Min(value = 0)
    @Max(value = 5)
    @Column(name = "translation", nullable = false)
    private Integer translation;

    @NotNull
    @Min(value = 0)
    @Max(value = 5)
    @Column(name = "listening", nullable = false)
    private Integer listening;

    @NotNull
    @Min(value = 0)
    @Max(value = 5)
    @Column(name = "selection", nullable = false)
    private Integer selection;

    @NotNull
    @Min(value = 0)
    @Max(value = 5)
    @Column(name = "speech", nullable = false)
    private Integer speech;

    @ManyToOne
    private User user;

    @ManyToOne
    private Lesson lesson;

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

    public LessonLog createDate(ZonedDateTime createDate) {
        this.createDate = createDate;
        return this;
    }

    public void setCreateDate(ZonedDateTime createDate) {
        this.createDate = createDate;
    }

    public Integer getComplete() {
        return complete;
    }

    public LessonLog complete(Integer complete) {
        this.complete = complete;
        return this;
    }

    public void setComplete(Integer complete) {
        this.complete = complete;
    }

    public Integer getTranslation() {
        return translation;
    }

    public LessonLog translation(Integer translation) {
        this.translation = translation;
        return this;
    }

    public void setTranslation(Integer translation) {
        this.translation = translation;
    }

    public Integer getListening() {
        return listening;
    }

    public LessonLog listening(Integer listening) {
        this.listening = listening;
        return this;
    }

    public void setListening(Integer listening) {
        this.listening = listening;
    }

    public Integer getSelection() {
        return selection;
    }

    public LessonLog selection(Integer selection) {
        this.selection = selection;
        return this;
    }

    public void setSelection(Integer selection) {
        this.selection = selection;
    }

    public Integer getSpeech() {
        return speech;
    }

    public LessonLog speech(Integer speech) {
        this.speech = speech;
        return this;
    }

    public void setSpeech(Integer speech) {
        this.speech = speech;
    }

    public User getUser() {
        return user;
    }

    public LessonLog user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Lesson getLesson() {
        return lesson;
    }

    public LessonLog lesson(Lesson lesson) {
        this.lesson = lesson;
        return this;
    }

    public void setLesson(Lesson lesson) {
        this.lesson = lesson;
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
        LessonLog lessonLog = (LessonLog) o;
        if (lessonLog.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), lessonLog.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "LessonLog{" +
            "id=" + getId() +
            ", createDate='" + getCreateDate() + "'" +
            ", complete='" + getComplete() + "'" +
            ", translation='" + getTranslation() + "'" +
            ", listening='" + getListening() + "'" +
            ", selection='" + getSelection() + "'" +
            ", speech='" + getSpeech() + "'" +
            "}";
    }
}
