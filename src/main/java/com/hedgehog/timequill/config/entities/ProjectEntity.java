package com.hedgehog.timequill.config.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "projects", schema = "timequill")
public class ProjectEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", nullable = false)
  private Integer id;

  @Column(name = "name")
  private String name;

  @Lob
  @Column(name = "description")
  private String description;

  @ManyToOne(fetch = FetchType.LAZY)
  @OnDelete(action = OnDeleteAction.SET_NULL)
  @JoinColumn(name = "manager_id")
  private UserEntity manager;

  @Column(name = "start_date")
  private LocalDate startDate;

  @Column(name = "end_date")
  private LocalDate endDate;

  @OneToMany(mappedBy = "project")
  private Set<AssignmentEntity> assignments = new LinkedHashSet<>();

  @OneToMany(mappedBy = "project")
  private Set<InvoiceEntity> invoices = new LinkedHashSet<>();

  @OneToMany(mappedBy = "project")
  private Set<com.hedgehog.timequill.config.entities.TimeTableEntity> timeTables = new LinkedHashSet<>();

}
