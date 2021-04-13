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
        //private static char[] cipher = {'\t', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', ' ', '.', '_', ',', ':', '"', '-', '\'', '/', '\\', '=', '+', '?' , '!', '$', '*', '>', '<', '@', '{', '}', '(', ')', '[', ']', ';', '\n', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
        private static char[] cipher = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', ' '};
        //Constant which holds the length of the char array
        static int ARRAY_LENGTH = cipher.length;
/*
        // Method which replaces a character in a specific position in a string. Takes the position of
        // the character to replace, which character to replace it with, and the string which you want
        // to replace the character in
        public static String replaceCharInPosition(int position, char ch, String str) {
            char[] charArray = str.toCharArray();
            charArray[position] = ch;
            return new String(charArray);
        }

        // Method which takes in a char and an offset, then returns a new char by getting the index of
        // the input char in the cipher array, then decreasing it by the offset to get the new char. It
        // then returns the new char.
        public static char encryptNewChar (char ch, int offset) {
            int newCharIndex = ArrayUtils.indexOf(encryption.cipher, ch); // Get index of input char
            System.out.println("Detected letter at method / index of letter: " + encryption.cipher[newCharIndex] + " / " + newCharIndex);
            int offsetIndex = newCharIndex+offset; // Use offset to get index of new char

            // A check to make sure that the offset stays in bounds of the array by counting back to the right side if the index goes negative
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
            char offsetChar = encryption.cipher[newOffsetIndex]; // set new char

            System.out.println("Char at method: " + offsetChar);
            return offsetChar;
        }
*/
        // Function which generates the character offset by multiplying the key by the first 9 digits of pi, then dividing
        // by 2 until it is less than the length of the cipher array. This is then set as the new offset.
        public static int getOffset(long key) {
            long offset = key * 314159265;
            System.out.println("Offset : " + offset);
            while (offset > (ARRAY_LENGTH-25)) {
                offset = offset / 2;
                System.out.println("Offset long during division: " + offset);
            }
            int offsetInt = Math.toIntExact(offset);
            // A check to make sure that the offset is a negative number. If it is not, then it is multiplied by -1 to make it negative.
            int checkPositive = checkPositive(offsetInt);
            if (checkPositive == 1) {
                offsetInt = offsetInt * (0-1);
            }
            System.out.println("Final offset int: " + offsetInt);
            return offsetInt;
        }
/*
        // Method to get an inverted offset for decrypting.
        public static int getDecryptOffset(int offset) {
            int decryptOffset = ((ARRAY_LENGTH + offset)*(0-1));
            return decryptOffset;
        }
*/
        // Method which checks if an integer is positive or not
        static int checkPositive(int num) {
            int result = Integer.signum(num);
            return result;
        }
/*
        // Method whick encrypts a string using an offset. Takes the source string and the key as arguments.
        public static String encrypt(String cleartext, long key) {
            int charOffset = encryption.getOffset(key); // Run method to get offset from key

            // Iterates over each character in the source string, using the encryptNewChar method to get the
            // offset char, then uses the replaceCharInPosition method to replace the old char with the offset
            // char from encryptNewChar.
            for (int c=0; c < cleartext.length(); c++) {
                char[] tempCharArray = {cleartext.charAt(c)}; // Converts the individual character into a one-element char array to be passed to encryptNewChar
                System.out.println("tempCharArray: " + tempCharArray[0]);
                char charAtOffset = encryption.encryptNewChar(tempCharArray[0], charOffset);
                    System.out.println("Offset char in function: " + charAtOffset + "\n--------------------------");
                    cleartext = encryption.replaceCharInPosition(c, charAtOffset, cleartext);
            }

            System.out.println(cleartext);
            return cleartext;
        }

        //Similar to encrypt, except it uses getDecryptOffset to generate an inverted offset
        public static String decrypt(String encrypted, long key) {
            // get decrypt offset by passing the result off the getOffset method to the decryptOffset function, which reverses it.
            int charOffset = (encryption.getDecryptOffset(encryption.getOffset(key)));

            // Same as encrypt - iterates over each character in the encrypted string, using the encryptNewChar method
            // to get the offset char, then uses the replaceCharInPosition method to replace the old char with the offset
            // char from encryptNewChar.
            for (int c=0; c < encrypted.length(); c++) {
                char[] tempCharArray = {encrypted.charAt(c)};
                System.out.println("tempCharArray: " + tempCharArray[0]);
                char charAtOffset = encryption.encryptNewChar(tempCharArray[0], charOffset);
                System.out.println("Offset char in function: " + charAtOffset + "\n--------------------------");
                encrypted = encryption.replaceCharInPosition(c, charAtOffset, encrypted);
            }
            System.out.println(encrypted);
            return encrypted;
*/        //}
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        // Create grid for GUI, st window title, and UI padding
        GridPane grid = new GridPane();
        primaryStage.setTitle("Caesar Encryption");
        grid.setPadding(new Insets(25, 25, 25, 25));

        //Title for message box
        Label messageLabel = new Label("Message:");
        messageLabel.setPadding(new Insets(0, 0, 5, 0));
        grid.add(messageLabel, 0, 0);

        //Message box
        TextArea message = new TextArea();
        message.setWrapText(true);
        grid.add(message, 0, 1);

        //Title for key box
        Label keyLabel = new Label("Key (Must be 10 digits or less):");
        keyLabel.setPadding(new Insets(20, 0, 5, 0));
        grid.add(keyLabel, 0, 2);

        //Key box
        TextField keyField = new TextField();
        grid.add(keyField, 0, 3);

        //HBox to hold Encrypt and Decrypt buttons
        HBox buttons = new HBox();
        buttons.setSpacing(10);

        //Define encrypt and decrypt buttons
        Button encryptButton = new Button("Encrypt");
        Button decryptButton = new Button("Decrypt");

        //Configure HBox for buttons
        buttons.setMargin(encryptButton, new Insets(15,0,0,0));
        buttons.setMargin(decryptButton, new Insets(15,0,0,0));
        //Add buttons to list
        ObservableList list = buttons.getChildren();
        list.addAll(encryptButton, decryptButton);

        //Add HBox to grid
        grid.add(buttons, 0, 4);

        /*
        //Event handler for Encrypt button which calls the encrypt method
        encryptButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                String cleartext = message.getText();
                long key = Integer.parseInt(keyField.getText());
                String encryptedText = encryption.encrypt(cleartext, key);
                message.setText(encryptedText);
            }
        });


        //Event handler for Decrypt button which calls the decrypt method
        decryptButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                String cleartext = message.getText();
                long key = Integer.parseInt(keyField.getText());
                String encryptedText = encryption.decrypt(cleartext, key);
                message.setText(encryptedText);
            }
        });

        */

        //Set window size, add scene to window
        Scene scene = new Scene(grid, 500, 350);
        primaryStage.setScene(scene);
        //Import CSS file
        //scene.getStylesheets().add(Main.class.getResource("sample.css").toExternalForm());
        //Show stage on window
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
