package library.lib;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import library.lib.frontend.state.SpringContext;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
public class LibraryApp extends Application {
    private Parent rootLayout;

    private ConfigurableApplicationContext springContext;

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/library/lib/register-view.fxml"));
        fxmlLoader.setControllerFactory(springContext::getBean);
        SpringContext.getInstance().setContext(springContext);
        rootLayout = fxmlLoader.load();
        configureStage(primaryStage);
        primaryStage.show();

    }
    private void configureStage(Stage primaryStage) {
        var scene = new Scene(rootLayout);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Library app");
    }

    @Override public void init(){
        springContext = SpringApplication.run(LibraryApp.class);
        springContext.getAutowireCapableBeanFactory().autowireBean(this);
    }
    public static void main(String[] args){
        launch(args);
    }
}