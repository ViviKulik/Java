package com.example.demo;

import com.example.demo.Repository.RepositoryNevoie;
import com.example.demo.Repository.RepositoryPersoana;
import com.example.demo.Service.ServiceNevoie;
import com.example.demo.Service.ServicePersoana;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        //Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        //stage.setTitle("Hello!");
        //stage.setScene(scene);
        AnchorPane layout = fxmlLoader.load();
        Scene scene =new Scene(layout);
        Controller controller =fxmlLoader.getController();
        String url="jdbc:postgresql://localhost:5432/faptebune";
        String user="postgres";
        String pass="kulikrav35";
        RepositoryNevoie repositoryNevoie =new RepositoryNevoie(url,user,pass);

        RepositoryPersoana repositoryPersoana = new RepositoryPersoana(url,user,pass);
        ServicePersoana servicePersoana =new ServicePersoana(repositoryPersoana);
        ServiceNevoie serviceNevoie =new ServiceNevoie(repositoryNevoie,repositoryPersoana);
        controller.SetController(servicePersoana,serviceNevoie);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
