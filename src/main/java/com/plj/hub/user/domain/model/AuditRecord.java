package com.plj.hub.user.domain.model;

import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@MappedSuperclass
@Getter
public class AuditRecord {

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt;
    @LastModifiedDate
    private LocalDateTime updatedAt;
    private LocalDateTime deletedAt;
    @ManyToOne
    @JoinColumn(name="updated_by")
    private User updatedBy;

    @ManyToOne
    @JoinColumn(name="deleted_by")
    private User deletedBy;
    private Boolean isDeleted = false;

    protected void delete(User user) {
        deletedAt = LocalDateTime.now();
        deletedBy = user;
    }
}
