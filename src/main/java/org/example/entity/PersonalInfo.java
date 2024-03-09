package org.example.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Embeddable
public class PersonalInfo implements Serializable {

    @Serial
    private static final long serialVersionUID = 8823884665363962442L;

    private String firstname;
    private String lastname;

    //    @Convert(converter = BirthdayConverter.class)
    @Column(name = "birth_date")
    private LocalDate birthDate;
}
