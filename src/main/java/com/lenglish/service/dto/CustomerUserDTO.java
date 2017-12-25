package com.lenglish.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;
import javax.persistence.Lob;

/**
 * A DTO for the CustomerUser entity.
 */
public class CustomerUserDTO implements Serializable {

    private Long id;

    @Lob
    private byte[] icon;
    private String iconContentType;

    @NotNull
    private Integer point;

    @NotNull
    private Integer level;

    private Integer todayPoint;

    private Integer dateGoal;

    private Long userId;

    private Long roomId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public byte[] getIcon() {
        return icon;
    }

    public void setIcon(byte[] icon) {
        this.icon = icon;
    }

    public String getIconContentType() {
        return iconContentType;
    }

    public void setIconContentType(String iconContentType) {
        this.iconContentType = iconContentType;
    }

    public Integer getPoint() {
        return point;
    }

    public void setPoint(Integer point) {
        this.point = point;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Integer getTodayPoint() {
        return todayPoint;
    }

    public void setTodayPoint(Integer todayPoint) {
        this.todayPoint = todayPoint;
    }

    public Integer getDateGoal() {
        return dateGoal;
    }

    public void setDateGoal(Integer dateGoal) {
        this.dateGoal = dateGoal;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getRoomId() {
        return roomId;
    }

    public void setRoomId(Long roomId) {
        this.roomId = roomId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CustomerUserDTO customerUserDTO = (CustomerUserDTO) o;
        if(customerUserDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), customerUserDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CustomerUserDTO{" +
            "id=" + getId() +
            ", icon='" + getIcon() + "'" +
            ", point='" + getPoint() + "'" +
            ", level='" + getLevel() + "'" +
            ", todayPoint='" + getTodayPoint() + "'" +
            ", dateGoal='" + getDateGoal() + "'" +
            "}";
    }
}
