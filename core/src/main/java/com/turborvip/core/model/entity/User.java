package com.turborvip.core.model.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.turborvip.core.model.dto.Profile;
import com.turborvip.core.model.dto.ProfileRequest;
import com.turborvip.core.model.entity.base.AbstractBase;
import jakarta.persistence.*;
import lombok.*;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.PrecisionModel;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import com.turborvip.core.constant.CommonConstant;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Table(name = "users", schema = "account")
public class User extends AbstractBase implements UserDetails {

    @Column(name = "fullname")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String fullName;

    @NotEmpty(message = "Username must not be empty")
    @Size(min = 6, max = 20, message = "Username must be between 6 and 20 characters")
    private String username;

    @NotEmpty(message = "Password must not be empty")
    @Column(name = "password", nullable = false)
    private String password;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String email;

    @JsonFormat(pattern = CommonConstant.FORMAT_DATE_PATTERN)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Column(name = "birthday")
    private Date birthday;

    @Column(name = "gender")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String gender;

    @Column(name = "phone")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String phone;

    @Column(name = "address")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String address;

    @Column(name = "avatar", columnDefinition = "TEXT")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String avatar;

    @Column(name = "rating")
    private float rating = 5;

    @Column(name = "count-rate")
    private long countRate = 0;

    @Column(name = "job_finnish_num")
    private long jobFinishNum = 0;

    @Column(name = "coordinates", columnDefinition = "Geometry(Point, 4326)")
    @JsonIgnore
    private Point coordinates;

//    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.DETACH})
//    @JoinTable(
//            name = "user_role", schema = "account",
//            joinColumns = @JoinColumn(name = "user_id"),
//            inverseJoinColumns = @JoinColumn(name = "role_id")
//    )
//    private Set<Role> roles = new HashSet<>();

    @OneToMany(mappedBy = "user", cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.DETACH})
    @JsonInclude
    @JsonIgnore
    private Set<UserRole> userRole;

    @OneToMany(mappedBy="user")
    @JsonIgnore
    private Set<Contact> items;

    @Column(name = "status")
    private String status = "active";
    // active, inactive, lock

    @Override
    public int hashCode() {
        return Objects.hash(fullName, username, password, email, birthday, gender, phone, address, avatar);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        userRole.forEach(i -> authorities.add(new SimpleGrantedAuthority(i.getRole().getCode().name())));
        return List.of(new SimpleGrantedAuthority(authorities.toString()));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
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

    public Profile getProfile() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
        return new Profile(this.id, this.fullName, this.email, dateFormat.format(this.birthday), this.gender, this.phone, this.address, this.avatar, this.rating, this.countRate);
    }

    public ProfileRequest getProfileAndNote(String note) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
        return new ProfileRequest(this.id, this.fullName, this.email, dateFormat.format(this.birthday), this.gender, this.phone, this.address, this.avatar, this.rating, this.countRate, note);
    }

    public User(String fullName, String username, String password, String email, Date birthday, String gender, String phone, String address, String avatar, float rating, long countRate,
                long jobFinishNum, double lat, double lng, Set<UserRole> userRole) {
        this.fullName = fullName;
        this.username = username;
        this.password = password;
        this.email = email;
        this.birthday = birthday;
        this.gender = gender;
        this.phone = phone;
        this.address = address;
        this.avatar = avatar;
        this.rating = rating;
        this.countRate = countRate;
        this.jobFinishNum = jobFinishNum;
        this.userRole = userRole;

        GeometryFactory geometryFactory = new GeometryFactory(new PrecisionModel(), 4326);
        this.coordinates = geometryFactory.createPoint(new Coordinate(lat, lng));
    }
}
