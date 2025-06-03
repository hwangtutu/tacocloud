package tacos;

// JPA용 import 제거
// import jakarta.persistence.*;

// MongoDB 전용 어노테이션 import
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

/**
 * Spring Security의 UserDetails를 구현한 User 엔티티 (MongoDB 전용).
 */
@Document(collection = "users")  // JPA @Entity/@Table 대신 MongoDB @Document만 사용
public class User implements UserDetails {

    @Id                   // MongoDB용 @Id
    private String id;     // Long → String으로 변경

    private String username;
    private String password;
    private String fullname;
    private String street;
    private String city;
    private String state;
    private String zip;
    private String phone;

    public User() {}

    /**
     * RegistrationForm 으로부터 User 엔티티를 생성할 때 사용합니다.
     */
    public User(String username, String password,
                String fullname, String street,
                String city, String state,
                String zip, String phone) {
        this.username = username;
        this.password = password;
        this.fullname = fullname;
        this.street = street;
        this.city = city;
        this.state = state;
        this.zip = zip;
        this.phone = phone;
    }

    // --- UserDetails 인터페이스 구현 --- //
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // 여기서는 ROLE_USER 같은 권한을 설정해주시면 됩니다.
        return null;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    // --- User 엔티티 Getter / Setter --- //
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    // (추가 구성이 필요하면 equals, hashCode, toString 등을 오버라이드)
}
