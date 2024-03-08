package org.example.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@EqualsAndHashCode(of = "name")
@ToString(exclude = "userChats")
@AllArgsConstructor
@Builder
@Entity
public class Chat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @OneToMany(mappedBy = "chat")
    @Builder.Default
    private List<UserChat> userChats = new ArrayList<>();
}
