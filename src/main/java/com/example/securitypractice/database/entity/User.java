package com.example.securitypractice.database.entity;



import lombok.*;
import org.hibernate.envers.Audited;
import org.hibernate.envers.RelationTargetAuditMode;


import javax.persistence.*;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
public class User extends AuditingEntity<Long> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true, nullable = false)
    private String login;

    private String password;
    private String name;
    private LocalDate birthDate;
    //private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    //@NotAudited
    //@OneToMany
    //private List<Something>


//    private List<Location> locationList
}
