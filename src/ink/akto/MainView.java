package ink.akto;

import com.sun.istack.internal.NotNull;
import com.sun.istack.internal.Nullable;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Created by Ruben on 08.07.2016.
 */
public class MainView extends Application implements Contracts.EncryptorView
{
    private Button encryptButton;
    private Button decryptButton;

    private Scene scene;
    private EncryptorPresenter presenter;

    @Override
    public void start(Stage stage) throws Exception
    {
        presenter = new EncryptorPresenter(this);

        // View
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/MainView.fxml"));
        Parent rootView = null;
        try {
            rootView = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //Scene
        scene = new Scene(rootView);
        setUserAgentStylesheet(STYLESHEET_CASPIAN);
        stage.setResizable(true);
        stage.setTitle("Encryptor");
        stage.getIcons().add(new javafx.scene.image.Image(getClass().getResourceAsStream("/lock.png")));
        stage.setScene(scene);
        stage.show();

        encryptButton = (Button) scene.lookup("#encryptButton");
        decryptButton = (Button) scene.lookup("#decryptButton");


        encryptButton.setOnAction(event ->
                presenter.encrypt(presenter.openFileChooser(scene.getWindow(), null), getKey()));
        decryptButton.setOnAction(event ->
                presenter.decrypt(presenter.openFileChooser(scene.getWindow(), presenter.getEncryptedDir()), getKey()));
    }

    @Override
    public void showError(@NotNull String error)
    {
        Stage popupStage;
        Parent root = null;

        popupStage = new Stage();
        try {
            root = FXMLLoader.load(getClass().getResource("/ErrorView.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene popupScene = new Scene(root);
        popupStage.setScene(popupScene);
        popupStage.getIcons().add(new javafx.scene.image.Image(getClass().getResourceAsStream("/lock.png")));
        popupStage.setTitle("Ошибка");
        popupStage.initModality(Modality.APPLICATION_MODAL);

        ((TextArea)popupScene.lookup("#errorViewTextArea")).setText(error);

        popupStage.showAndWait();
    }

    @Override
    @Nullable
    public String getKey()
    {
        Stage popupStage;
        Parent root = null;

        popupStage = new Stage();
        try {
            root = FXMLLoader.load(getClass().getResource("/PasswordView.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene popupScene = new Scene(root);
        popupStage.setScene(popupScene);
        popupStage.getIcons().add(new javafx.scene.image.Image(getClass().getResourceAsStream("/lock.png")));
        popupStage.setTitle("Пароль");
        popupStage.initModality(Modality.APPLICATION_MODAL);

        ((Button) popupScene.lookup("#popupButtonOk")).setOnAction(event -> popupStage.close());

        PasswordField passwordField = ((PasswordField)popupScene.lookup("#popupPasswordField"));
        ToggleButton toggleButton = ((ToggleButton) popupScene.lookup("#popupButtonShowPassword"));

        passwordField.managedProperty().bind(toggleButton.selectedProperty().not());
        passwordField.visibleProperty().bind(toggleButton.selectedProperty().not());

        ((TextField)popupScene.lookup("#popupDecryptedPasswordField"))
                .textProperty().bindBidirectional(passwordField.textProperty());

        popupStage.showAndWait();

        return passwordField.getText();
    }
}
