package library.lib.frontend.state;

import org.springframework.context.ConfigurableApplicationContext;

public class SpringContext {
    private static final SpringContext INSTANCE = new SpringContext();
    private ConfigurableApplicationContext context;

    private SpringContext() {
    }

    public static SpringContext getInstance() {
        return INSTANCE;
    }

    public ConfigurableApplicationContext getContext() {
        return context;
    }

    public void setContext(ConfigurableApplicationContext context) {
        this.context = context;
    }
}
