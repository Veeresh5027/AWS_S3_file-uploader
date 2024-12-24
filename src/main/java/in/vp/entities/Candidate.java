package in.vp.entities;

import jakarta.persistence.*;
import lombok.Data;

import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.ToString;

@Entity
@Data
@ToString(exclude = "resumeUrl")
public class Candidate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Name is required")
    @Column(nullable = false, length = 100)
    private String name;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    @Column(nullable = false, unique = true, length = 100)
    private String email;

    @NotBlank(message = "Phone number is required")
    @Pattern(regexp = "^\\d{10}$", message = "Phone number must be 10 digits")
    @Column(nullable = false, length = 10)
    private String phno;

    private String resumeUrl; // Populated after upload
}

