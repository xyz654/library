package library.lib.frontend.state;

import library.lib.backend.models.Member;

public class UserState {
    private static final UserState INSTANCE = new UserState();
    private Member loggedInUser;

    private UserState() {
    }

    public static UserState getInstance() {
        return INSTANCE;
    }

    public Member getLoggedInUser() {
        return loggedInUser;
    }

    public void setLoggedInUser(Member loggedInUser) {
        this.loggedInUser = loggedInUser;
    }
}
