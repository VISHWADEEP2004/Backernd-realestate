package com.max.quizspring.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "properties")
public class Property {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "agent_name")
    private String agentName;

    @Column(name = "bhk")
    private String bhk;

    @Column(name = "contact_name")
    private String contactName;

    private String location;
    private Double price;
    private String type;

    @ManyToOne
    @JoinColumn(name = "agent_id", nullable = false)
    private Agent agent;
}
