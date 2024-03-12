package org.example.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
//@OptimisticLocking(type = OptimisticLockType.ALL)
//@DynamicUpdate
public class Payment implements BaseEntity<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    @Version
//    private Long version;

    @Column(nullable = false)
    private Integer amount;

    @ManyToOne(optional = false)
    @JoinColumn(name = "receiver_id")
    private User receiver;

}
