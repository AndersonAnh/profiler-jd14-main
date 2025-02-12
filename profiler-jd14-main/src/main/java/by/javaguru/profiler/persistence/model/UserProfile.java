package by.javaguru.profiler.persistence.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@Builder(setterPrefix = "with")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "profiles")
public class UserProfile {

    @Id
    @Column(name = "user_id")
    private Long id;

    @Column(length = 50)
    private String name;

    @Column(length = 50)
    private String surname;

    @ManyToOne
    @JoinColumn(name = "country_id")
    private Country country;

    @Column(name = "email", length = 50)
    private String email;

    @ManyToOne
    @JoinColumn(name = "phone_code_id")
    private PhoneCode phoneCode;

    @Column(name = "phone_number", length = 25)
    private String cellPhone;

    @ManyToOne
    @JoinColumn(name = "position_id")
    private Position position;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "image_id", unique = true)
    private Image profileImage;

    @Column(name = "unique_student_identifier", length = 30)
    private String uniqueStudentIdentifier;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserProfile that)) return false;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name) &&
                Objects.equals(surname, that.surname) && Objects.equals(country, that.country) &&
                Objects.equals(email, that.email) && Objects.equals(phoneCode, that.phoneCode) &&
                Objects.equals(cellPhone, that.cellPhone) && Objects.equals(position, that.position) &&
                Objects.equals(profileImage, that.profileImage) &&
                Objects.equals(uniqueStudentIdentifier, that.uniqueStudentIdentifier);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, surname, country, email, phoneCode, cellPhone, position, profileImage,
                uniqueStudentIdentifier);
    }
}