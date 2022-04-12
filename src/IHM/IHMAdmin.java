package IHM;


import DAO.RestaurantBD;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import oo.Restaurant;

public class IHMAdmin implements EventHandler<ActionEvent> {
	// Variables ? utiliser par plusieurs m?thodes de la classe
	Stage stageAdmin;

	TableView table;
	TableColumn idCol,nomCol,emailCol,phoneCol,avisCol,latitudeCol,longitudeCol;
	TextField rechInput;
	ObservableList<Restaurant> dataList = FXCollections.observableArrayList();


	//Constructeur qui va faire la conception de l'interface d'ajout d'une formation
	public IHMAdmin()  {
		stageAdmin = new Stage();
		stageAdmin.setTitle("Admin");
		stageAdmin.addEventHandler(ActionEvent.ACTION,this);


		table = new TableView();

		idCol = new TableColumn("Id");
		idCol.setCellValueFactory(new PropertyValueFactory<>("id"));

		nomCol = new TableColumn("Nom");
		nomCol.setCellValueFactory(new PropertyValueFactory<>("nom"));
		emailCol = new TableColumn("Email");
		emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));
		avisCol = new TableColumn("Avis");
		avisCol.setCellValueFactory(new PropertyValueFactory<>("avis"));
		latitudeCol = new TableColumn("Latitude");
		latitudeCol.setCellValueFactory(new PropertyValueFactory<>("latitude"));
		longitudeCol =new TableColumn("Longitude");
		longitudeCol.setCellValueFactory(new PropertyValueFactory<>("longitude"));
		phoneCol = new TableColumn("Phone");
		phoneCol.setCellValueFactory(new PropertyValueFactory<>("phone"));
		idCol.setMinWidth(99);
		nomCol.setMinWidth(149);
		emailCol.setMinWidth(149);
		phoneCol.setMinWidth(149);
		avisCol.setMinWidth(149);
		latitudeCol.setMinWidth(149);
		longitudeCol.setMinWidth(149);
		table.getColumns().addAll(idCol, nomCol,emailCol, phoneCol,avisCol,latitudeCol,longitudeCol);
		//Restaurant person = new Restaurant(6, "fddfdf","fff@gmail.com",(float) 15.2,5654654,47747,"a");
		//Restaurant person2 = new Restaurant(2, "aaaaaad","aaaaaa@gmail.com",(float) 15.2,84654654,58822,"b");
		//Restaurant person3 = new Restaurant(10, "cccccccc","cccc@gmail.com",(float) 15.2,6848684,2825252,"c");
		//dataList.addAll(person,person2,person3);
		// Trier selon la colonne moyenne
		RestaurantBD resto=new RestaurantBD();
		table.setEditable(true);
		//for (Restaurant emp : resto.getResto()){
			//table.getItems().add(emp);
		//}
		dataList=resto.getResto();
//
		// Afficher les lignes de donn?es



		VBox root = new VBox(30);
		Label tAdmin=new Label("Admin Page");
		root.setAlignment(Pos.CENTER);
		tAdmin.setStyle(" -fx-text-fill: white;");
		tAdmin.setFont(Font.font("System",FontWeight.BOLD,25));

		HBox Rech = new HBox(10);
		rechInput = new TextField();
		Button RechBtn = new Button("Rechercher");
		RechBtn.setOnMouseEntered(e -> RechBtn.setStyle("-fx-background-color: #05F29B;-fx-background-insets: 0 0 -1 0, 0, 1, 2;-fx-background-radius: 3px, 3px, 2px, 1px;-fx-padding: 0.333333em 0.666667em 0.333333em 0.666667em;-fx-text-fill: white; -fx-alignment: CENTER;  -fx-content-display: LEFT;"));
		RechBtn.setOnMouseExited(e -> RechBtn.setStyle("-fx-background-color:white;"));
		Rech.setAlignment(Pos.CENTER);
		Rech.getChildren().addAll(rechInput,RechBtn);
//		 RechBtn.setOnAction(e->{
//			 table.getSortOrder().add(nomCol);
//
//		 });



		HBox Sort = new HBox(15);
		Label Srt = new Label("Delete Selected Row :");
		Srt.setStyle(" -fx-text-fill: white;");
		Srt.setFont(Font.font("System",FontWeight.EXTRA_BOLD,22));

		Button DeleteBtn = new Button("Delete");
		DeleteBtn.setOnMouseEntered(e -> DeleteBtn.setStyle("-fx-background-color: #05F29B;-fx-background-insets: 0 0 -1 0, 0, 1, 2;-fx-background-radius: 3px, 3px, 2px, 1px;-fx-padding: 0.333333em 0.666667em 0.333333em 0.666667em;-fx-text-fill: white; -fx-alignment: CENTER;  -fx-content-display: LEFT;"));
		DeleteBtn.setOnMouseExited(e -> DeleteBtn.setStyle("-fx-background-color:white;"));
		DeleteBtn.setMinWidth(100);
		Sort.setAlignment(Pos.CENTER);
		Sort.getChildren().addAll(Srt,DeleteBtn);


		FilteredList<Restaurant> filteredData = new FilteredList<>(dataList, b -> true);

		// 2. Set the filter Predicate whenever the filter changes.
		rechInput.textProperty().addListener((observable, oldValue, newValue) -> {
			filteredData.setPredicate(Resta -> {
				// If filter text is empty, display all persons.

				if (newValue == null || newValue.isEmpty()) {
					return true;
				}

				// Compare first name and last name of every person with filter text.
				String lowerCaseFilter = newValue.toLowerCase();

				if (Resta.getNom().toLowerCase().indexOf(lowerCaseFilter) != -1 ) {
					return true; // Filter matches first name.
				} else if (Resta.getEmail().toLowerCase().indexOf(lowerCaseFilter) != -1) {
					return true; // Filter matches last name.
				}else if (String.valueOf(Resta.getId()).indexOf(lowerCaseFilter) != -1) {
					return true; // Filter matches last name.
				}
				else if (String.valueOf(Resta.getPhone()).indexOf(lowerCaseFilter)!=-1)
					return true;
				else
					return false; // Does not match.
			});
			// 3. Wrap the FilteredList in a SortedList.
			SortedList<Restaurant> sortedData = new SortedList<>(filteredData);

			// 4. Bind the SortedList comparator to the TableView comparator.
			// 	  Otherwise, sorting the TableView would have no effect.
			sortedData.comparatorProperty().bind(table.comparatorProperty());

			// 5. Add sorted (and filtered) data to the table.
			table.setItems(sortedData);
		});
		//--------------------------------------------------------------------
		DeleteBtn.setOnAction(e->{
			table.getSortOrder().add(nomCol);

			Restaurant pr=(Restaurant) table.getSelectionModel().getSelectedItem();
			// System.out.println(pr.getId());
			RestaurantBD r=new RestaurantBD();
			r.delete(pr.getId());
			table.setItems(r.getResto());


		});


		table.getItems().addAll(dataList);
		//-----------------

		// root.setStyle("-fx-background-image:url('roadmap.jpg'); -fx-background-repeat: no-repeat; -fx-background-size: 1000 800; -fx-background-position: center center;");
		Image img = new Image("C:/Users/Asus/Desktop/ppt/sample/src/img/roadmap.jpg");
		BackgroundImage bImg = new BackgroundImage(img,
				BackgroundRepeat.NO_REPEAT,
				BackgroundRepeat.NO_REPEAT,
				BackgroundPosition.CENTER,
				new BackgroundSize(1000,600,false,false,false,false));
		Background bGround = new Background(bImg);
		root.setBackground(bGround);
		root.getChildren().addAll(tAdmin,Rech,table,Sort);
		root.setPadding(new Insets(5,5,5,5));

		// params de la fenete
		Scene sceneAdmin = new Scene(root,1000,600);
		stageAdmin.setScene(sceneAdmin);
		stageAdmin.show();
	}



	@Override
	public void handle(ActionEvent arg0) {
		// TODO Auto-generated method stub

	}


}
