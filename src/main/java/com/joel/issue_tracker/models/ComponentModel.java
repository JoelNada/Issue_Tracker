package com.joel.issue_tracker.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "components")
public class ComponentModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "component_name", unique = true)
    private String componentName;
    @Column(name = "component_id", unique = true)
    private String componentId;
    @Column(name = "description", unique = true, length = 5000)
    private String componentDescription;
    @Column(length = 5000)
    private String bugTemplate;

}
