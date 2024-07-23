package sjs.instagram.domain.user;

import org.springframework.data.jpa.repository.JpaRepository;
import sjs.instagram.db.user.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
}
