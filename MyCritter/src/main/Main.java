/* Critter World by
 * <Rohan Tripathi>
 */


package main;
	
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class Main extends Application {
	
	
	@Override
	public void start(Stage primaryStage) {
		try {
			Parent root = FXMLLoader.load(getClass().getResource("/View/StartView.fxml"));
			Scene scene = new Scene(root,600,400);
			mainController.scene01 = root.getScene();
			
			primaryStage.setScene(scene);
			primaryStage.setTitle("Critter World");
						
			Stage stage2=new Stage();
			Parent root2=FXMLLoader.load(getClass().getResource("/View/GameView.fxml"));
			Scene scene2=new Scene(root2,1300,800);
			mainController.scene02 = root2.getScene();
			stage2.setTitle("Real Critter World");
			stage2.setScene(scene2);
				mainController.actionList.add("#text1");
				mainController.actionList.add("#text2");
				mainController.actionList.add("#text3");
				mainController.actionList.add("#text4");
				mainController.actionList.add("#text5");
				mainController.actionList.add("#text6");
				mainController.labelList.add("#label1");
				mainController.labelList.add("#label2");
				mainController.labelList.add("#label3");
				mainController.labelList.add("#label4");
				mainController.labelList.add("#label5");
				mainController.labelList.add("#label6");
			stage2.show();
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
