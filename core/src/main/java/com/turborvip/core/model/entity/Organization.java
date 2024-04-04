package com.turborvip.core.model.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.turborvip.core.model.entity.base.AbstractBase;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Table(name = "organizations",schema = "organization")
public class Organization extends AbstractBase {

    @NotEmpty
    @Column(name = "name")
    private String name;

    @Column(name = "thumbnail")
    private String thumbnail;

    @Column(name = "background")
    private String background;

    @Column(name = "description", columnDefinition = "text")
    private String description;

    @Column(name = "slogan")
    private String slogan;

    @Column(name = "rating")
    private float rating = 5;

//    @OneToMany(mappedBy="organizations",orphanRemoval = true)
//    private Set<Job> jobs;
}
