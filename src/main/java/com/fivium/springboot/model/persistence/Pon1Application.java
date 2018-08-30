package com.fivium.springboot.model.persistence;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
@EntityListeners(AuditingEntityListener.class)
public class Pon1Application {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String organisationId;

  @OneToMany(mappedBy = "pon1Application")
  private Set<Pon1ApplicationVersion> pon1ApplicationVersions;

  @Column(name = "created_timestamp", nullable = false, updatable = false)
  @CreatedDate
  private Instant createdTimestamp;

  @Column(name = "updated_timestamp", nullable = false)
  @LastModifiedDate
  private Instant updatedTimestamp;

  public Long getId() {
    return id;
  }

//  public void setId(Long id) {
//    this.id = id;
//  }

  public String getOrganisationId() {
    return organisationId;
  }

  public void setOrganisationId(String organisationId) {
    this.organisationId = organisationId;
  }

  public Set<Pon1ApplicationVersion> getPon1ApplicationVersions() {
    return pon1ApplicationVersions;
  }

  public void setPon1ApplicationVersions(
      Set<Pon1ApplicationVersion> pon1ApplicationVersions) {
    this.pon1ApplicationVersions = pon1ApplicationVersions;
  }
}
