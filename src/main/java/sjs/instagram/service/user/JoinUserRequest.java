package sjs.instagram.service.user;

import sjs.instagram.domain.user.JoinUser;

public record JoinUserRequest(
        String instagramId,
        String password
) {
    public JoinUser toJoinUser() {
        return new JoinUser(instagramId, password);
    }
}
