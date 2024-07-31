package sjs.instagram.config;

import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import sjs.instagram.db.user.UserEntity;
import sjs.instagram.domain.user.LoginUser;
import sjs.instagram.domain.user.UserRepository;

@Service
@AllArgsConstructor
public class LoginUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String instagramId) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.findByInstagramId(instagramId);
        if (userEntity == null) {
            throw new UsernameNotFoundException("InstagramId not found : " + instagramId);
        }
        LoginUser user = new LoginUser(
                userEntity.getId(),
                userEntity.getInstagramId(),
                userEntity.getPassword(),
                userEntity.getRole()
        );
        return new LoginUserDetails(user);
    }
}
