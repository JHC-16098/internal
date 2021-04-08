package sample;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.scene.layout.GridPane;
import org.apache.commons.lang3.ArrayUtils;

public class Main extends Application {

    public static class encryption {
        //Char array which holds all characters supported by the cipher
        private static char[] cipher = {'\t', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', ' ', '.', '_', ',', ':', '"', '-', '\'', '/', '\\', '=', '+', '?' , '!', '$', '*', '>', '<', '@', '{', '}', '(', ')', '[', ']', ';', '\n', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};

        //Constant which holds the length of the char array
        static int ARRAY_LENGTH = cipher.length;

        // Method which replaces a character in a specific position in a string. Takes the position of
        // the character to replace, which character to replace it with, and the string which you want
        // to replace the character in
        public static String replaceCharInPosition(int position, char ch, String str) {
            char[] charArray = str.toCharArray();
            charArray[position] = ch;
            return new String(charArray);
        }

        public static char encryptNewChar (char ch, int offset) {
            int newCharIndex = ArrayUtils.indexOf(encryption.cipher, ch);
            System.out.println("Detected letter at method / index of letter: " + encryption.cipher[newCharIndex] + " / " + newCharIndex);
            int offsetIndex = newCharIndex+offset;

            int isOffsetCharNegative = checkPositive(offsetIndex);
            int newOffsetIndex;
            if (isOffsetCharNegative == (0-1)) {
                newOffsetIndex = ARRAY_LENGTH + offsetIndex;
                //System.out.println("newOffsetIndex: " + newOffsetIndex);
            }
            else {
                newOffsetIndex = offsetIndex;
            }

            System.out.println("Offset at encryptNewChar: " + offset);
            System.out.println("newOffsetIndex: " + newOffsetIndex);
            char offsetChar = encryption.cipher[newOffsetIndex];

            System.out.println("Char at method: " + offsetChar);
            return offsetChar;
        }

        public static int getOffset(long key) {
            long offset = key * 314159265;
            System.out.println("Offset : " + offset);
            while (offset > (ARRAY_LENGTH-25)) {
                offset = offset / 2;
                System.out.println("Offset long during division: " + offset);
            }
            int offsetInt = Math.toIntExact(offset);
            int checkPositive = checkPositive(offsetInt);
            if (checkPositive == 1) {
                offsetInt = offsetInt * (0-1);
            }
            System.out.println("Final offset int: " + offsetInt);
            return offsetInt;
        }

        public static int getDecryptOffset(int offset) {
            int decryptOffset = ((ARRAY_LENGTH + offset)*(0-1));
            return decryptOffset;
        }

        static int checkPositive(int num) {
            int result = Integer.signum(num);
            return result;
        }

        public static String encrypt(String cleartext, long key) {
            int charOffset = encryption.getOffset(key);

            for (int c=0; c < cleartext.length(); c++) {
                char[] tempCharArray = {cleartext.charAt(c)};
                System.out.println("tempCharArray: " + tempCharArray[0]);
                char charAtOffset = encryption.encryptNewChar(tempCharArray[0], charOffset);
                    System.out.println("Offset char in function: " + charAtOffset + "\n--------------------------");
                    cleartext = encryption.replaceCharInPosition(c, charAtOffset, cleartext);
            }

            System.out.println(cleartext);
            return cleartext;
        }

        public static String decrypt(String encrypted, long key) {
            int charOffset = (encryption.getDecryptOffset(encryption.getOffset(key)));
            for (int c=0; c < encrypted.length(); c++) {
                char[] tempCharArray = {encrypted.charAt(c)};
                System.out.println("tempCharArray: " + tempCharArray[0]);
                char charAtOffset = encryption.encryptNewChar(tempCharArray[0], charOffset);
                System.out.println("Offset char in function: " + charAtOffset + "\n--------------------------");
                encrypted = encryption.replaceCharInPosition(c, charAtOffset, encrypted);
            }
            System.out.println(encrypted);
            return encrypted;
        }
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        GridPane grid = new GridPane();
        primaryStage.setTitle("Caesar Encryption");
        grid.setPadding(new Insets(25, 25, 25, 25));

        Label messageLabel = new Label("Message:");
        messageLabel.setPadding(new Insets(0, 0, 5, 0));
        grid.add(messageLabel, 0, 0);

        TextArea message = new TextArea();
        message.setWrapText(true);
        grid.add(message, 0, 1);

        Label keyLabel = new Label("Key (Must be 10 digits or less):");
        keyLabel.setPadding(new Insets(20, 0, 5, 0));
        grid.add(keyLabel, 0, 2);

        TextField keyField = new TextField();
        grid.add(keyField, 0, 3);

        HBox buttons = new HBox();
        buttons.setSpacing(10);


        Button encryptButton = new Button("Encrypt");
     //   grid.add(encryptButton, 0, 4);

        Button decryptButton = new Button("Decrypt");
       // grid.add(decryptButton, 0, 5);

        buttons.setMargin(encryptButton, new Insets(15,0,0,0));
        buttons.setMargin(decryptButton, new Insets(15,0,0,0));
        ObservableList list = buttons.getChildren();
        list.addAll(encryptButton, decryptButton);
        grid.add(buttons, 0, 4);
        encryptButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                String cleartext = message.getText();
                long key = Integer.parseInt(keyField.getText());
                String encryptedText = encryption.encrypt(cleartext, key);
                message.setText(encryptedText);
            }
        });



        decryptButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                String cleartext = message.getText();
                long key = Integer.parseInt(keyField.getText());
                String encryptedText = encryption.decrypt(cleartext, key);
                message.setText(encryptedText);
            }
        });

        Scene scene = new Scene(grid, 500, 350);
        primaryStage.setScene(scene);
        scene.getStylesheets().add(Main.class.getResource("sample.css").toExternalForm());
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
