package fpt.uebung12;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.layout.Priority;
import javafx.scene.Node;
import javafx.stage.Stage;
import javafx.scene.control.Button;

public class App extends Application {
	public static final int hButtonSpacing = 10;
	public static final int vButtonSpacing = 10;
	public static final int separatorSize = 20;

	private static HBox evenlySpacedHBox(double spacing, Node... children) {
		var hbox = new HBox(spacing, children);
		for (var child: children) {
			hbox.setHgrow(child, Priority.ALWAYS);
		}
		return hbox;
	}

	@Override
	public void start(Stage stage) {
		stage.setTitle("Testing");

		var inputField = new Label("");

		Button button7 = new Button("7");
		Button button8 = new Button("8");
		Button button9 = new Button("9");
		HBox row1 = evenlySpacedHBox(hButtonSpacing, button7, button8, button9);

		Button button4 = new Button("4");
		Button button5 = new Button("5");
		Button button6 = new Button("6");
		HBox row2 = evenlySpacedHBox(hButtonSpacing, button4, button5, button6);

		Button button1 = new Button("1");
		Button button2 = new Button("2");
		Button button3 = new Button("3");
		HBox row3 = evenlySpacedHBox(hButtonSpacing, button1, button2, button3);

		Button buttonC = new Button("C");
		Button button0 = new Button("0");
		Button buttonCE = new Button("CE");
		HBox row4 = evenlySpacedHBox(hButtonSpacing, buttonC, button0, buttonCE);

		VBox numpad = new VBox(vButtonSpacing, row1, row2, row3, row4);
		numpad.setFillWidth(false);

		var buttonPlus = new Button("+");
		var buttonMinus = new Button("-");
		var buttonTimes = new Button("*");
		var buttonDivide = new Button("/");
		var buttonEqual = new Button("=");
		VBox opButtons = new VBox(vButtonSpacing, buttonPlus, buttonMinus, buttonTimes, buttonDivide, buttonEqual);

		var buttons = new HBox(separatorSize, numpad, opButtons);

		var calculatorApp = new VBox(inputField, buttons);

		Scene scene = new Scene(new StackPane(calculatorApp), 640, 480);
		stage.setScene(scene);
		stage.show();
	}

	public static void main(String[] args) {
		launch();
	}
}

enum Action {
	N0,
	N1,
	N2,
	N3,
	N4,
	N5,
	N6,
	N7,
	N8,
	N9,
	DELETE,
	CLEAR,
	PLUS,
	MINUS,
	MULTIPLY,
	DIVIDE,
	EQUAL
}
