package tacos;

import jakarta.persistence.*;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

/**
 * Spring Security의 UserDetails를 구현한 User 엔티티.
 */
@Entity
@Table(name="users")
@Document(collection="users")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;

    private String username;    // 로그인 ID
    private String password;    // 암호화된 비밀번호

    private String fullname;    // 실명
    private String street;      // 배송지 - 도로명
    private String city;        // 배송지 - 도시
    private String state;       // 배송지 - 주/도
    private String zip;         // 배송지 - 우편번호
    private String phone;       // 연락처

    public User() {}  // JPA, Jackson 등을 위해 꼭 필요합니다.

    /**
     * RegistrationForm 으로부터 User 엔티티를 생성할 때 사용합니다.
     */
    public User(String username, String password,
                String fullname, String street, String city, String state, String zip, String phone) {
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
        return null; // 예제에서는 권한 구분을 하지 않으므로 null 또는 빈 컬렉션을 반환해도 무방합니다.
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

    // --- 사용자 프로필용 getter 메서드 (OrderController 등에서 호출) --- //

    public String getFullname() {
        return fullname;
    }

    public String getStreet() {
        return street;
    }

    public String getCity() {
        return city;
    }

    public String getState() {
        return state;
    }

    public String getZip() {
        return zip;
    }

    public String getPhone() {
        return phone;
    }

    // --- (선택) setter 메서드 --- //
    // JPA/Hibernate가 필요로 하는 경우나, 테스트 코드에서 값을 주입할 때 사용합니다.

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    // (추가필요) equals, hashCode, toString 등
}
