package com.turborvip.core.model.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.turborvip.core.model.entity.base.AbstractBase;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import java.sql.Timestamp;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Table(name = "jobs",schema = "job")
public class Job extends AbstractBase {

    @Column(name = "name")
    private String name;

    @Column(name = "thumbnail")
    private String thumbnail;

    @Column(name = "images")
    private String[] images;

    @Column(name = "description",columnDefinition = "TEXT")
    private String description;

    @Column(name = "quantity_current")
    private int quantityCurrent = 0;

    @Column(name = "quantity_total")
    private int quantityTotal;

    @NotEmpty(message = "Start date must not be empty")
    @Column(name = "start_date")
    private Timestamp startDate;

    @NotEmpty(message = "Due date must not be empty")
    @Column(name = "due_date")
    private Timestamp dueDate;

    @Column(name = "requirement", columnDefinition = "jsonb")
    private ObjectNode requirement;

    @Column(name = "viewer_num")
    private Double viewer_num;

    @Column(name = "status")
    private String status;

//    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.DETACH})
//    @JoinTable(
//            name = "user_job",schema = "job",
//            joinColumns = @JoinColumn(name = "user_id"),
//            inverseJoinColumns = @JoinColumn(name = "job_id")
//    )
//    private Set<User> user = new HashSet<>();

}
