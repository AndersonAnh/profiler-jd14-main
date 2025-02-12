package by.javaguru.profiler.persistence.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@Table(name = "contacts")
public class Contacts {

    @Id
    @Column(name = "cv_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "phone_code_id")
    private PhoneCode phoneCode;

    @Column(name = "phone_number", length = 25, nullable = false)
    private String phoneNumber;

    @Column(length = 50, nullable = false)
    private String email;

    @Column(length = 25)
    private String skype;

    @Column(nullable = false)
    private String linkedin;

    @Column(name = "portfolio_link")
    private String portfolio;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Contacts contacts)) return false;
        return Objects.equals(id, contacts.id) && Objects.equals(phoneCode, contacts.phoneCode) &&
                Objects.equals(phoneNumber, contacts.phoneNumber) && Objects.equals(email, contacts.email) &&
                Objects.equals(skype, contacts.skype) && Objects.equals(linkedin, contacts.linkedin) &&
                Objects.equals(portfolio, contacts.portfolio);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, phoneCode, phoneNumber, email, skype, linkedin, portfolio);
    }

    @Override
    public String toString() {
        return "Contacts{" +
                "id=" + id +
                ", phoneCode=" + phoneCode +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", email='" + email + '\'' +
                ", skype='" + skype + '\'' +
                ", linkedin='" + linkedin + '\'' +
                ", portfolio='" + portfolio + '\'' +
                '}';
    }
}
