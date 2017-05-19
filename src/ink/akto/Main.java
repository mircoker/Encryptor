package ink.akto;

import javafx.application.Application;
import javafx.stage.Stage;

/**
 * Created by Ruben on 02.05.2017.
 */
public class Main extends Application
{
    public static void main(String[] args)  {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        new MainView().start(primaryStage);
    }
}
