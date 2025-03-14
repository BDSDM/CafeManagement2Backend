package societedestin.cafemanagement2.pojo;

import jakarta.persistence.*;

@Entity
@Table(name = "tablecafemanagement2") // Nom de la table MySQL
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-incrémentation
    private Long id;

    private String name;
    @Column(name = "contact_number")
    private String contactNumber;
    private String email;
    private String password;
    private String status;
    private String role;

    // Constructeurs
    public User() {}

    public User(String name, String contactNumber, String email, String password, String status, String role) {
        this.name = name;
        this.contactNumber = contactNumber;
        this.email = email;
        this.password = password;
        this.status = status;
        this.role = role;
    }

    // Getters et Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getContactNumber() { return contactNumber; }
    public void setContactNumber(String contactNumber) { this.contactNumber = contactNumber; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
}
