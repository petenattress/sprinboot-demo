package com.fivium.springboot.model.persistence;

import com.fivium.springboot.model.enums.ReleaseType;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
@EntityListeners(AuditingEntityListener.class)
public class Pon1ApplicationVersion {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "pon1_application_id", referencedColumnName = "id")
  private Pon1Application pon1Application;

  private String operatorReference;

  @Enumerated(EnumType.STRING)
  private ReleaseType releaseType;

  private String whateverField;

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

  public Pon1Application getPon1Application() {
    return pon1Application;
  }

//  public void setPon1Application(Pon1Application pon1Application) {
//    this.pon1Application = pon1Application;
//  }

  public String getOperatorReference() {
    return operatorReference;
  }

  public void setOperatorReference(String operatorReference) {
    this.operatorReference = operatorReference;
  }


  public ReleaseType getReleaseType() {
    return releaseType;
  }

  public void setReleaseType(ReleaseType releaseType) {
    this.releaseType = releaseType;
  }

  public String getWhateverField() {
    return whateverField;
  }

  public void setWhateverField(String whateverField) {
    this.whateverField = whateverField;
  }
}
