// // package com.max.quizspring.model;

// // import jakarta.persistence.*;
// // import lombok.AllArgsConstructor;
// // import lombok.Builder;
// // import lombok.Data;
// // import lombok.NoArgsConstructor;

// // @Data
// // @Builder
// // @NoArgsConstructor
// // @AllArgsConstructor
// // @Entity
// // @Table(name = "properties")
// // public class Property {
// //     @Id
// //     @GeneratedValue(strategy = GenerationType.IDENTITY)
// //     private Long id;

// //     @Column(name = "agent_name")
// //     private String agentName;

// //     @Column(name = "bhk")
// //     private String bhk;

// //     @Column(name = "contact_name")
// //     private String contactName;

// //     private String location;

// //     @Column(name = "price")
// //     private Double price;

// //     private String type;

// //     @ManyToOne
// //     @JoinColumn(name = "agent_id", nullable = false)
// //     private Agent agent;

// //     @Lob
// //     @Column(name = "img")
// //     private String img; // Use String if storing image URLs or base64 encoded strings
// // }


// package com.max.quizspring.model;

// import jakarta.persistence.*;
// import lombok.AllArgsConstructor;
// import lombok.Builder;
// import lombok.Data;
// import lombok.NoArgsConstructor;

// @Data
// @Builder
// @NoArgsConstructor
// @AllArgsConstructor
// @Entity
// @Table(name = "properties")
// public class Property {
//     @Id
//     @GeneratedValue(strategy = GenerationType.IDENTITY)
//     private Long id;

//     @Column(name = "agent_name")
//     private String agentName;

//     @Column(name = "bhk")
//     private String bhk;

//     @Column(name = "contact_name")
//     private String contactName;

//     private String location;

//     @Column(name = "price")
//     private Double price;

//     private String type;

//     @ManyToOne
//     @JoinColumn(name = "agent_id", nullable = false)
//     private Agent agent;

//     @Lob
//     @Column(name = "img")
//     private String img; // Base64 encoded string for image
// }


package com.max.quizspring.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

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

    @Column(name = "bhk")
    private String bhk;

    @Column(name = "contact_name")
    private String contactName;

    private String location;

    @Column(name = "price")
    private Double price;

    private String type;

    @ManyToOne
    @JoinColumn(name = "agent_id", nullable = false)
     @JsonBackReference
    private Agent agent;

    @Lob
    @Column(name = "img")
    private String img;
}
