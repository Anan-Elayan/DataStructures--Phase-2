package application;
/*
* Name :Anan Elayan
* ID : 1211529
*/
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.stage.Stage;

import java.util.Date;

public class ClientView extends Pane {
    private final Label lblWelcome,lblBrand;
    private final Button btnNext,btnPrevious,btnAddToCart,btnFillter;
    private final ComboBox<String> comboBoxColor,comboBoxYear,comboBoxModel,comboBoxPrice;
    private final TableView<Car> table = new TableView<>();
    private final Stage stage;
    static DoubleLinkedList data = new DoubleLinkedList();
    static QueueList queuelist = new QueueList();
    static StackList stackList = new StackList();
    static Orders  orders;
    Font font = Font.font("Bauhaus 93", FontPosture.REGULAR, 40);


    public ClientView() {


        lblWelcome = new Label("Welcome to Car Agency System");
        lblWelcome.setStyle("-fx-text-fill: white");
        lblWelcome.setFont(font);
        lblWelcome.setLayoutX(80);
        lblWelcome.setLayoutY(40);


        table.setLayoutX(80);
        table.setLayoutY(150);
        table.setPrefHeight(300);
        table.setPrefWidth(500);

        lblBrand = new Label("Brand : ");
        lblBrand.setStyle("-fx-text-fill: white;-fx-font-size: 15");
        lblBrand.setLayoutX(250);
        lblBrand.setLayoutY(120);


        btnNext = new Button("Next");
        Image image = new Image("forward-button.png");
        ImageView view = new ImageView(image);
        view.setFitWidth(10);
        view.setFitHeight(10);
        btnNext.setGraphic(view);
        btnNext.setStyle("-fx-text-fill:  #f2bd12;-fx-background-color: black;-fx-border-color: white;-fx-background-radius: 10 50 10  50 ; -fx-border-radius: 10 50 10 50;-fx-font-size: 10 ");
        btnNext.setFont(font);
        btnNext.setPrefWidth(100);
        btnNext.setLayoutX(480);
        btnNext.setLayoutY(120);


        btnPrevious = new Button("Previous");
        btnPrevious.setStyle("-fx-text-fill:  #f2bd12;-fx-background-color: black;-fx-border-color: white;-fx-background-radius: 10 50 10  50 ; -fx-border-radius: 10 50 10 50;-fx-font-size: 10 ");
        Image image2 = new Image("backward-button.png");
        ImageView view2 = new ImageView(image2);
        view2.setFitWidth(10);
        view2.setFitHeight(10);
        btnPrevious.setGraphic(view2);


        btnPrevious.setFont(font);
        btnPrevious.setPrefWidth(100);
        btnPrevious.setLayoutX(90);
        btnPrevious.setLayoutY(120);

        btnAddToCart = new Button("Add to Cart");
        btnAddToCart.setStyle("-fx-text-fill:  #f2bd12;-fx-background-color: black;-fx-border-color: white;-fx-background-radius: 10 50 10  50 ; -fx-border-radius: 10 50 10 50;-fx-font-size: 10 ");
        btnAddToCart.setLayoutY(455);
        btnAddToCart.setLayoutX(300);
        btnAddToCart.setPrefWidth(100);
        btnAddToCart.setVisible(false);


        comboBoxColor = new ComboBox<>();
        comboBoxColor.setPromptText("Colors");
        comboBoxColor.setPrefWidth(110);
        comboBoxColor.getItems().addAll("For All");
        comboBoxColor.setLayoutX(590);
        comboBoxColor.setLayoutY(160);


        comboBoxYear = new ComboBox<>();
        comboBoxYear.setPrefWidth(110);
        comboBoxYear.setPromptText("Year");
        comboBoxYear.getItems().addAll("Year", "2010-2015", "2016-2020", "2021-2023");
        comboBoxYear.getSelectionModel().select(0);
        comboBoxYear.setLayoutX(592);
        comboBoxYear.setLayoutY(240);

        comboBoxModel = new ComboBox<>();
        comboBoxModel.setPrefWidth(110);
        comboBoxModel.setPromptText("Model");
        comboBoxModel.getItems().addAll("For All");
        comboBoxModel.setLayoutX(592);
        comboBoxModel.setLayoutY(320);

        comboBoxPrice = new ComboBox<>();
        comboBoxPrice.setPromptText("Price");
        comboBoxPrice.setPrefWidth(110);
        comboBoxPrice.getItems().addAll("Price", "Lees 100K", "Less 200K", "Less 300K", "Less 400K", "Less 500K");
        comboBoxPrice.getSelectionModel().select(0);
        comboBoxPrice.setLayoutX(592);
        comboBoxPrice.setLayoutY(400);


        btnFillter = new Button("Filter");
        btnFillter.setStyle("-fx-text-fill:  #f2bd12;-fx-background-color: black;-fx-border-color: white;-fx-background-radius: 10 50 10  50 ; -fx-border-radius: 10 50 10 50;-fx-font-size: 10 ");
        btnFillter.setPrefWidth(100);
        btnFillter.setLayoutX(592);
        btnFillter.setLayoutY(450);


        TableColumn<Car, String> modelColumn = new TableColumn<>("Model");
        TableColumn<Car, Integer> yearColumn = new TableColumn<>("Year");
        TableColumn<Car, String> colorColumn = new TableColumn<>("Color");
        TableColumn<Car, Integer> priceColumn = new TableColumn<>("Price");
        table.getColumns().addAll(modelColumn, yearColumn, colorColumn, priceColumn);

        modelColumn.setCellValueFactory(new PropertyValueFactory<>("model"));
        yearColumn.setCellValueFactory(new PropertyValueFactory<>("year"));
        colorColumn.setCellValueFactory(new PropertyValueFactory<>("color"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));


        AllView();


        this.getChildren().addAll(lblWelcome, table, lblBrand, btnPrevious, btnNext, comboBoxColor, comboBoxYear, comboBoxModel, comboBoxPrice, btnAddToCart, btnFillter);
        this.setStyle("-fx-background-color: black");
        stage = new Stage();
        Scene scene = new Scene(this, 730, 500);
        stage.setScene(scene);
        stage.setTitle("Client");
        stage.getIcons().add(new Image("icon car.png"));
        stage.show();
    }


    //---------------------------------------------------------  METHODS -----------------------------------------------


    public void AllView() {

        table.setVisible(true);
        btnNext.setVisible(true);
        btnPrevious.setVisible(true);
        comboBoxColor.setVisible(true);
        comboBoxYear.setVisible(true);
        comboBoxModel.setVisible(true);
        comboBoxPrice.setVisible(true);
        lblBrand.setVisible(true);
        btnFillter.setVisible(true);


        NodeDoubleLinkedList[] finalNewNodeDouble = {ClientView.data.getFirst()};

        AddToCart(finalNewNodeDouble[0]);//call function
        viewAndFill(finalNewNodeDouble[0]);//call function

        btnNext.setOnAction(e -> {
            AddToCart(finalNewNodeDouble[0]);//call function
            viewAndFill(finalNewNodeDouble[0]);//fill table cars
            finalNewNodeDouble[0] = finalNewNodeDouble[0].getNext();

        });

        btnPrevious.setOnAction(p -> {
            AddToCart(finalNewNodeDouble[0]);//call function
            viewAndFill(finalNewNodeDouble[0]);//fill table cars
            finalNewNodeDouble[0] = finalNewNodeDouble[0].getPrevious();
        });

        btnFillter.setOnAction(f -> {
            ActionComboBox(finalNewNodeDouble[0]);//call function
        });

        table.setVisible(true);
        lblBrand.setVisible(true);
        btnNext.setVisible(true);
        btnPrevious.setVisible(true);
        comboBoxColor.setVisible(true);
        comboBoxYear.setVisible(true);
        comboBoxModel.setVisible(true);
        comboBoxPrice.setVisible(true);
        lblWelcome.setVisible(true);
        btnAddToCart.setVisible(true);
        btnFillter.setVisible(true);
    }


    public void viewAndFill(NodeDoubleLinkedList nodeDouble) {

        table.getItems().clear();
        comboBoxColor.getItems().clear();
        comboBoxModel.getItems().clear();
        comboBoxYear.getSelectionModel().select(0);
        comboBoxPrice.getSelectionModel().select(0);
        lblBrand.setText("Brand : " + nodeDouble.getBrand());
        NodeSingleList current = nodeDouble.getListCarInfo().getFirst();
        comboBoxColor.getItems().add("Color");
        comboBoxModel.getItems().add("Model");
        for (int i = 0; i < nodeDouble.getListCarInfo().size(); ++i) {
            table.getItems().add(current.getObjectCar());//fill table cars in this brand
            if (!comboBoxColor.getItems().contains(current.getObjectCar().getColor()))//fill comboBox color all color in this brand
                comboBoxColor.getItems().add(current.getObjectCar().getColor());
            if (!comboBoxModel.getItems().contains(current.getObjectCar().getModel()))//fill comboBox model all model in this brand
                comboBoxModel.getItems().add(current.getObjectCar().getModel());
            current = current.getNext();
        }
        comboBoxColor.getSelectionModel().select(0);
        comboBoxModel.getSelectionModel().select(0);
    }


    public void ActionComboBox(NodeDoubleLinkedList nodeDouble) {

        table.getItems().clear();
        NodeSingleList current = nodeDouble.getListCarInfo().getFirst();

        String selectedColor = comboBoxColor.getSelectionModel().getSelectedItem();
        String selectedModel = comboBoxModel.getSelectionModel().getSelectedItem();
        String selectedYear = comboBoxYear.getSelectionModel().getSelectedItem();
        String selectedPrice = comboBoxPrice.getSelectionModel().getSelectedItem();
        int fromYear = 0, toYear = Integer.MAX_VALUE, price = Integer.MAX_VALUE;
        if (!selectedYear.equals("Year")) {
            String[] years = selectedYear.split("-");
            fromYear = Integer.parseInt(years[0]);
            toYear = Integer.parseInt(years[1]);
        }
        if (!selectedPrice.equals("Price")) {
            String temp = selectedPrice.substring(5);
            price = Integer.parseInt(temp.substring(0, temp.length() - 1));
        }
        for (int i = 0; i < nodeDouble.getListCarInfo().size(); ++i) {
            boolean colorExists, modelExists, yearExists, priceExists;
            colorExists = selectedColor.equals(current.getObjectCar().getColor());
            modelExists = selectedModel.equals(current.getObjectCar().getModel());
            yearExists = (current.getObjectCar().getYear() >= fromYear && current.getObjectCar().getYear() <= toYear);
            priceExists = (current.getObjectCar().getPrice() <= price);
            if (
                    (colorExists || selectedColor.equals("Color"))
                            && (modelExists || selectedModel.equals("Model"))
                            && (yearExists || selectedYear.equals("Year"))
                            && (priceExists || selectedPrice.equals("Price"))
            ) {
                table.getItems().add(current.getObjectCar());
            }
            current = current.getNext();
        }
        if (selectedColor.equals("Color") && selectedModel.equals("Model") && selectedPrice.equals("Price") && selectedYear.equals("Year")) {
            viewAndFill(nodeDouble);
        }
    }


    public void AddToCart(NodeDoubleLinkedList nodeDouble) {
        table.getItems().clear();
        lblBrand.setText("Brand : " + ClientView.data.getFirst().getBrand());

        btnAddToCart.setOnAction(e -> {
            Stage newStage = new Stage();
            Pane basePane = new Pane();
            basePane.setStyle("-fx-background-color: black");

            Label lblName = new Label("Name ");
            lblName.setStyle("-fx-text-fill: #f2bd12");
            lblName.setLayoutX(50);
            lblName.setLayoutY(50);

            Label lblPhone = new Label("Phone ");
            lblPhone.setStyle("-fx-text-fill: #f2bd12");
            lblPhone.setLayoutX(50);
            lblPhone.setLayoutY(80);

            TextField txtName = new TextField();
            txtName.setLayoutX(93);
            txtName.setLayoutY(50);

            TextField txtPhone = new TextField();
            txtPhone.setLayoutX(93);
            txtPhone.setLayoutY(80);

            Button btnSave = new Button("Save");
            btnSave.setFont(font);
            btnSave.setStyle("-fx-text-fill:  #f2bd12;-fx-background-color: black;-fx-border-color: white;-fx-background-radius: 10 50 10  50 ; -fx-border-radius: 10 50 10 50;-fx-font-size: 10 ");
            btnSave.setLayoutX(110);
            btnSave.setLayoutY(150);
            btnSave.setPrefWidth(100);

            btnSave.setOnAction(s -> {
                Client client = new Client(txtName.getText(),txtPhone.getText());
                Car car =  table.getSelectionModel().getSelectedItem();
                orders = new Orders(client, car, new Date(),"InProcess",nodeDouble.getBrand());
                queuelist.enQueue(orders);//add to queue
                viewAndFill(nodeDouble);
                newStage.close();
                newStage.setResizable(false);
                new Warning("Reserved Successfully");
            });
            
            
            table.refresh();
            basePane.getChildren().addAll(lblName, lblPhone, txtName, txtPhone, btnSave);
            Scene scene = new Scene(basePane, 300, 200);
            newStage.setScene(scene);
            newStage.setTitle("Personal Information");
            newStage.show();
        });
    }
}