package library.lib;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class LibraryApp extends Application {
    private Parent rootLayout;

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/library/lib/hello-view.fxml"));
        rootLayout = fxmlLoader.load();
        configureStage(primaryStage);
        primaryStage.show();

    }
    private void configureStage(Stage primaryStage) {
        var scene = new Scene(rootLayout);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Gallery app");
    }
    public static void main(String[] args){
        launch(args);
    }
}