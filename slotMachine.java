import javafx.application.Application;
import java.io.FileNotFoundException;
import java.util.Optional;
import java.util.Random;
import static javafx.application.Application.launch;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javax.swing.*;
//************************************************************************
// slotMachine.java Author: Zixi Zhong
//
//  This program is a simulation for a slot machine with 27 different possible results
//************************************************************************
public class slotMachine extends Application {
    //declare an array for all the image names
    //keep all image files in the same directory as the program
    private String[] imgs_arr = {"aaa","aac","aao","aca","acc","aco",
            "aoa","aoc","aoo","caa","cac","cao",
            "cca","ccc","cco","coa","coc","coo",
            "oaa","oac","oao","oca","occ","oco",
            "ooa","ooc","ooo"};

    //an array of integers to match the pictures
    private int[] values = {5,4,1,2,3,1,
            1,1,1,3,2,1,
            4,10,2,1,2,1,
            1,1,-1,1,1,-1,
            1,1,-5};

    private int token = 5;
    private int spinResult = 0;
    private String imgName = "aaa";
    private Label tokenLabel = new Label("Current tokens: "+token);
    private Label spinLabel = new Label("Spin result: "+spinResult);
    private Label printText = new Label("No Button Pushed");
    private Image elementPic = new Image(imgName+".png");
    private ImageView imgView = new ImageView(elementPic);
    private Label label;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        //config for 3 labels
        spinLabel.setFont(Font.font("Helvetcia", FontWeight.BOLD, 30));//label for spin
        spinLabel.setTextFill(Color.BLUE);

        tokenLabel.setFont(Font.font("Helvetcia", FontWeight.BOLD, 30));//label for token number
        tokenLabel.setTextFill(Color.RED);

        printText.setFont(Font.font("Helvetcia", FontWeight.BOLD, 30));//label for printed text
        printText.setTextFill(Color.WHITE);

        Button spinButton = new Button("Spin");  //Spin button
        spinButton.setPrefSize(250,80);
        spinButton.setFont(Font.font("Arial",FontWeight.NORMAL,26));
        spinButton.setOnAction(this::spinTheMachine);

        Button cashOutButton = new Button("Cash Out");//Cash out button
        cashOutButton.setPrefSize(250,80);
        cashOutButton.setFont(Font.font("Arial",FontWeight.NORMAL,26));
        cashOutButton.setOnAction(this::cashOutAction);

        // config for imageView object
        imgView.setFitHeight(100);
        imgView.setFitWidth(300);

        FlowPane slotPane = new FlowPane(tokenLabel,spinLabel,imgView); //Slot pane (Upper one)
        slotPane.setStyle("-fx-background-color: White");
        slotPane.setPrefSize(320, 250);
        slotPane.setPadding(new Insets(10,50,30,50));
        slotPane.setHgap(20);
        slotPane.setVgap(15);
        slotPane.setAlignment(Pos.TOP_CENTER);

        FlowPane buttonPane = new FlowPane(spinButton, cashOutButton, printText); //Button Pane
        buttonPane.setHgap(20);
        buttonPane.setVgap(15);
        buttonPane.setStyle("-fx-background-color: Blue");
        buttonPane.setPrefSize(320, 250);
        buttonPane.setPadding(new Insets(10,50,30,50));
        buttonPane.setAlignment(Pos.TOP_CENTER);

        VBox primary = new VBox();      //an instance of VBox as primary pane
        primary.getChildren().addAll(slotPane, buttonPane);
        primary.setStyle("-fx-background-color: Red");
        primary.setSpacing(10);
        primary.setPadding(new Insets(15, 85, 150, 85));

        Scene scene = new Scene(primary,1000,600); //Config for scene
        primaryStage.setScene(scene);
        primaryStage.setTitle("Slot Machine");
        primaryStage.show();

    }
    public void spinTheMachine (ActionEvent event) { //action event for every spin
        if (token >0) {
            token -= 1;
            Random randNum = new Random();
            int rand_index = randNum.nextInt(27);

            imgName = imgs_arr[rand_index];
            elementPic = new Image(imgName + ".png");
            imgView.setImage(elementPic);


            spinResult = values[rand_index];
            token += spinResult;
            spinLabel.setText("Spin result: " + spinResult);
            tokenLabel.setText("Current tokens: " + token);
            printText.setText("");

        }else{  //a cool feature added to make it more realistic
            printText.setText("Game Over!");
            return;
        }
    }
    public void cashOutAction(ActionEvent event) { //action event for cash out
        int cashOut = token;
        token = 5; //reset token value
        printText.setText("Your cash out value is " + cashOut + " tokens");
        spinLabel.setText("Spin result: " + 0);
        tokenLabel.setText("Current tokens: " + token);

        //The following is for extra credit
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION); //using an alert to confirm with user
        alert.setContentText("Would you like to play again?");
        alert.setTitle("Continue");

        Optional<ButtonType> option = alert.showAndWait();
        //exam the feedback from users
        if (option.get() == ButtonType.OK) {  //the message for user if they choose to continue
            printText.setText("Welcome back!");
        } else{                             //the message for user if they choose to leave
            printText.setText("Good-Bye"); //and will stop the program
            printText.setPrefSize(200,100);
            token = 0;
        }
    }

}
