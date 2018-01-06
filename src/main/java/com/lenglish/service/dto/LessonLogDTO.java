package com.lenglish.service.dto;


import java.time.ZonedDateTime;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the LessonLog entity.
 */
public class LessonLogDTO implements Serializable {

    private Long id;

    private ZonedDateTime createDate;

    @NotNull
    @Min(value = 0)
    @Max(value = 5)
    private Integer complete;

    @NotNull
    @Min(value = 0)
    @Max(value = 5)
    private Integer translation;

    @NotNull
    @Min(value = 0)
    @Max(value = 5)
    private Integer listening;

    @NotNull
    @Min(value = 0)
    @Max(value = 5)
    private Integer selection;

    @NotNull
    @Min(value = 0)
    @Max(value = 5)
    private Integer speech;

    private Integer point;

    private Long userId;

    private Long lessonId;

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

    public Integer getTranslation() {
        return translation;
    }

    public void setTranslation(Integer translation) {
        this.translation = translation;
    }

    public Integer getListening() {
        return listening;
    }

    public void setListening(Integer listening) {
        this.listening = listening;
    }

    public Integer getSelection() {
        return selection;
    }

    public void setSelection(Integer selection) {
        this.selection = selection;
    }

    public Integer getSpeech() {
        return speech;
    }

    public void setSpeech(Integer speech) {
        this.speech = speech;
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

    public Long getLessonId() {
        return lessonId;
    }

    public void setLessonId(Long lessonId) {
        this.lessonId = lessonId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        LessonLogDTO lessonLogDTO = (LessonLogDTO) o;
        if(lessonLogDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), lessonLogDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "LessonLogDTO{" +
            "id=" + getId() +
            ", createDate='" + getCreateDate() + "'" +
            ", complete='" + getComplete() + "'" +
            ", translation='" + getTranslation() + "'" +
            ", listening='" + getListening() + "'" +
            ", selection='" + getSelection() + "'" +
            ", speech='" + getSpeech() + "'" +
            ", point='" + getPoint() + "'" +
            "}";
    }
}
