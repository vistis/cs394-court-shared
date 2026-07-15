package kh.edu.paragoniu.court_shared.security;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import kh.edu.paragoniu.court_shared.entity.RolePermission;
import kh.edu.paragoniu.court_shared.entity.User;
import kh.edu.paragoniu.court_shared.entity.UserRole;
import kh.edu.paragoniu.court_shared.repository.RolePermissionRepository;
import kh.edu.paragoniu.court_shared.repository.UserRepository;
import kh.edu.paragoniu.court_shared.repository.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserRoleRepository userRoleRepository;

    @Autowired
    private RolePermissionRepository rolePermissionRepository;

    @Override
    public UserDetails loadUserByUsername(String usernameOrEmail)
        throws UsernameNotFoundException {
        User user = userRepository
            .findAuthenticatedUserByUsernameOrEmail(usernameOrEmail)
            .orElseThrow(() ->
                new UsernameNotFoundException("User not found: " + usernameOrEmail)
            );

        if (!user.isActive()) {
            throw new UsernameNotFoundException("User account is disabled");
        }

        Set<String> permissions = new HashSet<>();
        for (UserRole userRole : user.getUserRoles()) {
            List<RolePermission> rolePerms = rolePermissionRepository.findByIdSystemRoleId(
                userRole.getSystemRole().getSystemRoleId()
            );

            permissions.addAll(
                rolePerms
                    .stream()
                    .map(rp -> rp.getSystemPermission().getCode())
                    .collect(Collectors.toSet())
            );
        }

        List<GrantedAuthority> authorities = permissions
            .stream()
            .map(SimpleGrantedAuthority::new)
            .collect(Collectors.toList());

        return new org.springframework.security.core.userdetails.User(
            user.getUsername(),
            user.getPassword(),
            user.isActive(),
            true,
            true,
            true,
            authorities
        );
    }
}
