package com.study.application_test.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "username", nullable = false, unique = true)
    private String username;
    @Column(name = "password", nullable = false)
    private String password;
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST) // 엔티티를 영속화(저장)할 때 하위 엔티티도 같이 유지
    // cf) Lazy(지연로딩) VS Eager(즉시로딩)
    // 지연로딩: 해당 데이터와 해당 필드가 같이 사용되지 않는 경우
    // 즉시로딩: 해당 데이터와 해당 필드가 동시에 사용되는 경우
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Post> posts = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Notice> notices = new ArrayList<>();


    @Builder
    public User(String username, String password, Set<Role> roles){
        this.username = username;
        this.password = password;
        this.roles = roles;
    }

    // --------------------------------------------------- //
    // UserDetails 인터페이스 구현

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // 사용자 권한을 반환하는 메서드
        // - GrantedAuthority: 인증된 사용자가 가지고 있는 권한을 표현
        // ex) ROLE_USER, ROLE_ADMIN 등의 역할을 설정하여 반환

        // 해당 코드는 "user"라는 기본 권한만 설정
//        return List.of(new SimpleGrantedAuthority("user"));

        // 사용자의 Role을 Spring Security가 인식할 수 있도록 변환
        // ex) "USER" -> "ROLE_USER" (ROLE_을 인식)
        return roles.stream()
                // 로그인 후 인증된 사용자의 권한을 Spring Security에 전달
                // : hasRole() 방식과 호환되기 위한 설정
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role.getRoleName()))
                .collect(Collectors.toSet()); // toSet() 주의
    }

    @Override
    public String getPassword() {
        // 사용자 비밀번호를 반환하는 메서드
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    // 계정 만료 여부(기본값 true: 만료되지 않음)
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    // 계정 잠김 여부(기본값 true: 잠기지 않음)
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    // 인증 정보 만료 여부
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    // 계정 활성화 여부
    @Override
    public boolean isEnabled() {
        return true;
    }
}

