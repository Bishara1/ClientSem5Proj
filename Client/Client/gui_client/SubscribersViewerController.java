package gui_client;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import logic.Subscriber;
import client.ChatClient;
import client.ClientUI;
import common.Command;
import common.Message;

public class SubscribersViewerController implements Initializable{
	ChatClient client;
	
	private ObservableList<Subscriber> obs;
	Message messageToServer = new Message(null, null);

	@FXML
	private TableView<Subscriber> tableSub;
	@FXML
	private TableColumn<Subscriber,String> fnamecol;
	@FXML
	private TableColumn<Subscriber,String> lnamecol;
	@FXML
	private TableColumn<Subscriber,String> idcol;
	@FXML
	private TableColumn<Subscriber,String> phonecol;
	@FXML
	private TableColumn<Subscriber,String> emailcol;
	@FXML
	private TableColumn<Subscriber,String> visacol;
	@FXML
	private TableColumn<Subscriber,String> subnumcol;
	@FXML
	private TableColumn<Subscriber,String> usercol;
	@FXML
	private TableColumn<Subscriber,String> passwordcol;
	
	@FXML
	private TextField SubscriberIDtxt;
	@FXML
	private TextField SubscriberCreditNumtxt;
	@FXML
	private TextField SubscriberSubNumtxt;
	
	public void start(Stage primaryStage) throws Exception { 
		
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui_client_windows/SubscribersViewer.fxml"));
		Parent root = loader.load();	
		Scene scene = new Scene(root);
		//scene.getStylesheets().add(getClass().getResource("/gui/ServerPort.css").toExternalForm());  //css
		primaryStage.setTitle("Subscriber Viewer");
		primaryStage.setScene(scene);
		
		primaryStage.show();	
	}
	
	// Initialize table contents with database 
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		RefreshTable();
	}
	
	public void UpdatBtn() {
		String details = SubscriberIDtxt.getText() + " " + SubscriberCreditNumtxt.getText() + " " + SubscriberSubNumtxt.getText();
		messageToServer.setCommand(Command.DatabaseUpdate);
		messageToServer.setContent(details);
		System.out.println("Updating");
		ClientUI.chat.accept(messageToServer);
		ClientUI.chat.display("Updated");
		RefreshTable();	
	}
	
	public void LoadAndSetTable() {
		fnamecol.setCellValueFactory(new PropertyValueFactory<>("Fname"));
		lnamecol.setCellValueFactory(new PropertyValueFactory<>("LName"));
		idcol.setCellValueFactory(new PropertyValueFactory<>("Id"));
		phonecol.setCellValueFactory(new PropertyValueFactory<>("PhoneNum"));
		emailcol.setCellValueFactory(new PropertyValueFactory<>("Email"));
		visacol.setCellValueFactory(new PropertyValueFactory<>("Visa"));
		subnumcol.setCellValueFactory(new PropertyValueFactory<>("SubNum"));
		
		tableSub.setItems(obs);
	}
	
	public void RefreshTable() {
		messageToServer.setCommand(Command.ReadUsers);
		messageToServer.setContent(0);
		ClientUI.chat.accept(messageToServer);  // read from database
		obs = FXCollections.observableArrayList(ChatClient.subscribers);  // insert database details to obs
		LoadAndSetTable(); // load database colummns into table and display them
	}
	
	public void ExitBtn(ActionEvent event) throws Exception {
		((Node)event.getSource()).getScene().getWindow().hide();
		Parent root = FXMLLoader.load(getClass().getResource("/gui_client_windows/CEOReports.fxml"));
		Stage primaryStage = new Stage();
		Scene scene = new Scene(root);
		primaryStage.setTitle("CEO Reports");
		primaryStage.setScene(scene);		
		primaryStage.show();		
	}
}
