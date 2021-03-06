package sample;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.*;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.PrintStream;
import java.util.Queue;
import java.util.Random;


// TODO: (FIX ME) When falling from an object, gc can still jump.
// TODO: Refactor kick()
// TODO: (Debug) kicking sometimes doesn't work
public class Main extends Application {
    private static PrintStream p = System.out;

    final public static int SCENE_WIDTH = 1024;
    final public static int SCENE_HEIGHT = 768;
    final public static double GROUND_HEIGHT = 70;
    final public static int fps = 60;   //  max fps

    private Group root;
    private Group menuRoot;

    private MenuScene menuScene;

    private Stage stage;

    private GameCharacter gameCharacter[];
    private SceneController sceneController;
    private GameEngine gameEngine;

    private SerialController controller;

    private int numOfPlayers;

    private String gameState = "menu";

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        //root = new Group();
        //Scene scene = new Scene(root, SCENE_WIDTH, SCENE_HEIGHT, Color.WHITE);
        this.stage = stage;
        stage.setTitle("Physical Computing Midterm");

        controller = new SerialController();
        controller.initialize(this);

        stage.setOnHiding(new EventHandler<WindowEvent>() {
            public void handle(WindowEvent event) {
                controller.close();
            }
        });

        /*GameCharacter[] characters = new GameCharacter[2];
        characters[0] = new GameCharacter();
        characters[1] = null;

        gameStart(characters);*/
        menuStart();

        stage.show();
    }

    public void gameStart(GameCharacter[] characters) {
        root = new Group();
        Scene scene = new Scene(root, SCENE_WIDTH, SCENE_HEIGHT, Color.WHITE);

        this.numOfPlayers = 0;

        for(int i = 0; i < characters.length; ++i) {
            if(characters[i] != null) {
                numOfPlayers++;
            }
        }

        sceneController = new SceneController(root, numOfPlayers);
        gameCharacter = sceneController.generateCharacter();
        for (int i = 0;i < numOfPlayers;i++) {
            sceneController.generateInitialScene(i);
        }
        gameEngine = new GameEngine(scene, sceneController, gameCharacter, numOfPlayers, controller, this, root);

        if(numOfPlayers == 1) {
            for(int i = 0; i < characters.length; ++i) {
                if(characters[i] != null) {
                    characters[i] = gameCharacter[0];
                }
            }

            controller.setCharacters(characters);
        } else {
            controller.setCharacters(gameCharacter);
        }

        gameEngine.setup();
        gameEngine.run();
        stage.setScene(scene);
    }

    public void menuStart() {
        if(menuRoot == null) {
            menuRoot = new Group();
            menuScene = new MenuScene(menuRoot, this, controller);
        } else {
            menuScene.clean();
        }

        stage.setScene(menuScene);
    }
}