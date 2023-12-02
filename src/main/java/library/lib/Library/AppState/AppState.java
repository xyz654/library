package library.lib.Library.AppState;

import library.lib.Backend.models.Member;

public class AppState {
    private static final AppState INSTANCE = new AppState();

    private Member loggedInUser;

    private AppState() {
    }

    public static AppState getInstance() {
        return INSTANCE;
    }

    public Member getLoggedInUser() {
        return loggedInUser;
    }

    public void setLoggedInUser(Member loggedInUser) {
        this.loggedInUser = loggedInUser;
    }
}
