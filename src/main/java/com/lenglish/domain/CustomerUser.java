package com.lenglish.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A CustomerUser.
 */
@Entity
@Table(name = "customer_user")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class CustomerUser implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    @Column(name = "icon")
    private byte[] icon;

    @Column(name = "icon_content_type")
    private String iconContentType;

    @NotNull
    @Column(name = "point", nullable = false)
    private Integer point;

    @NotNull
    @Column(name = "jhi_level", nullable = false)
    private Integer level;

    @Column(name = "today_point")
    private Integer todayPoint;

    @Column(name = "date_goal")
    private Integer dateGoal;

    @OneToOne
    @JoinColumn(unique = true)
    private User user;

    @ManyToOne
    private Room room;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public byte[] getIcon() {
        return icon;
    }

    public CustomerUser icon(byte[] icon) {
        this.icon = icon;
        return this;
    }

    public void setIcon(byte[] icon) {
        this.icon = icon;
    }

    public String getIconContentType() {
        return iconContentType;
    }

    public CustomerUser iconContentType(String iconContentType) {
        this.iconContentType = iconContentType;
        return this;
    }

    public void setIconContentType(String iconContentType) {
        this.iconContentType = iconContentType;
    }

    public Integer getPoint() {
        return point;
    }

    public CustomerUser point(Integer point) {
        this.point = point;
        return this;
    }

    public void setPoint(Integer point) {
        this.point = point;
    }

    public Integer getLevel() {
        return level;
    }

    public CustomerUser level(Integer level) {
        this.level = level;
        return this;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Integer getTodayPoint() {
        return todayPoint;
    }

    public CustomerUser todayPoint(Integer todayPoint) {
        this.todayPoint = todayPoint;
        return this;
    }

    public void setTodayPoint(Integer todayPoint) {
        this.todayPoint = todayPoint;
    }

    public Integer getDateGoal() {
        return dateGoal;
    }

    public CustomerUser dateGoal(Integer dateGoal) {
        this.dateGoal = dateGoal;
        return this;
    }

    public void setDateGoal(Integer dateGoal) {
        this.dateGoal = dateGoal;
    }

    public User getUser() {
        return user;
    }

    public CustomerUser user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Room getRoom() {
        return room;
    }

    public CustomerUser room(Room room) {
        this.room = room;
        return this;
    }

    public void setRoom(Room room) {
        this.room = room;
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
        CustomerUser customerUser = (CustomerUser) o;
        if (customerUser.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), customerUser.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CustomerUser{" +
            "id=" + getId() +
            ", icon='" + getIcon() + "'" +
            ", iconContentType='" + iconContentType + "'" +
            ", point='" + getPoint() + "'" +
            ", level='" + getLevel() + "'" +
            ", todayPoint='" + getTodayPoint() + "'" +
            ", dateGoal='" + getDateGoal() + "'" +
            "}";
    }
}
