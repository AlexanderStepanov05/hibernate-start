package org.example.entity;

import jakarta.persistence.*;
import lombok.*;
import org.example.listener.UserChatListener;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@EqualsAndHashCode(of = "name")
@ToString(exclude = "userChats")
@AllArgsConstructor
@Builder
@Entity
@EntityListeners(UserChatListener.class)
public class Chat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Builder.Default
    private Integer count = 0;

    @OneToMany(mappedBy = "chat")
    @Builder.Default
    private List<UserChat> userChats = new ArrayList<>();
}
