package co.id.jss.jssrestapi.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "P_AUDIT_TRAIL")
public class AuditTrail implements Serializable {

    @Id
    @GeneratedValue(generator = "P_AUDIT_TRAIL_SEQ")
    @SequenceGenerator(name = "P_AUDIT_TRAIL_SEQ", sequenceName = "P_AUDIT_TRAIL_SEQ", allocationSize = 1)
    @Column(name = "ID", nullable = false)
    private Long id;

    @Basic
    @Column(name = "DATE_TIME")
    private LocalDateTime dateTime;

    @Basic
    @Column(name = "USER_ID")
    private Long userId;

    @Basic
    @Column(name = "CLIENT_ID")
    private Long clientId;

    @Basic
    @Column(name = "ACTIVITY")
    private String activity;

    @Basic
    @Column(name = "HTTP_STATUS")
    private Integer httpStatus;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    public String getActivity() {
        return activity;
    }

    public void setActivity(String activity) {
        this.activity = activity;
    }

    public Integer getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(Integer httpStatus) {
        this.httpStatus = httpStatus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AuditTrail that = (AuditTrail) o;
        return Objects.equals(id, that.id) && Objects.equals(dateTime, that.dateTime) && Objects.equals(userId, that.userId) && Objects.equals(clientId, that.clientId) && Objects.equals(activity, that.activity) && Objects.equals(httpStatus, that.httpStatus);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, dateTime, userId, clientId, activity, httpStatus);
    }
}