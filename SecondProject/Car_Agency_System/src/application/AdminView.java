package application;
/*
 * Name :Anan Elayan
 * ID : 1211529
 *
 * */
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.stage.Stage;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class AdminView extends Pane {

    private Stage stage;
    private Pane leftPane, paneAdd, paneUpdate, paneVeiwTable, paneSummary, paneSold, paneDelete, fieldPane;
    private Button btnAdd, btnFinish, btnSave, btnCancel, btnInProcess, btnDone, btnDelete, btnUpdate, btnTopten, btn, btnSelectedCar;
    private Label lblbrand, lblyear, lblModel, lblPrice, lblColor;
    private ComboBox<String> comboBoxBrand;
    private TextField txtModel, txtYear, txtPrice, txtColor;
    private TableView<Orders> tabelOrder = new TableView<>();
    TableView<Car> tableViewCars = new TableView<>();
    private TabPane tabPane = new TabPane();
    private VBox vbox;
    private String lblStyle = "-fx-text-fill:cd9b05;-fx-font-size:15";
    private String btnStyle = "-fx-background-color:black;-fx-text-fill:f2bd12;-fx-border-radius:15;-fx-background-radius:15";
    private Font font = Font.font("Bauhaus 93", FontPosture.REGULAR, 40);


    public AdminView() {

        leftPane = new Pane();
        leftPane.setStyle("-fx-background-color:white;-fx-background-radius:0 30 30 0");
        leftPane.setLayoutX(0);
        leftPane.setLayoutY(-1);
        leftPane.setPrefHeight(500);
        leftPane.setPrefWidth(211);

        vbox = new VBox(15);
        vbox.setPrefHeight(347);
        vbox.setPrefWidth(159);
        vbox.setLayoutX(14);
        vbox.setLayoutY(8);
        vbox.setAlignment(Pos.CENTER);
        vbox.setSpacing(55);
        vbox.setPadding(new Insets(40, 0, 0, 0));

        btnAdd = new Button("Add");
        btnAdd.setPrefWidth(100);
        btnAdd.setStyle(btnStyle);

        btnDelete = new Button("Delete");
        btnDelete.setPrefWidth(100);
        btnDelete.setStyle(btnStyle);

        btnUpdate = new Button("Update");
        btnUpdate.setPrefWidth(100);
        btnUpdate.setStyle(btnStyle);

        btnSave = new Button("Save");
        btnSave.setPrefWidth(100);
        btnSave.setStyle(btnStyle);
       
        btnSave.setOnAction(e -> { //to save to file order//////////////////
            saveFile();
            new Warning("Saved Successfully");
        });


        vbox.getChildren().addAll(btnAdd, btnDelete, btnUpdate, btnSave);
        this.leftPane.getChildren().addAll(vbox);

        fieldPane = new Pane();

        paneAdd = new Pane();
        paneAdd.setVisible(true);
        paneAdd.getChildren().addAll(leftPane);


        paneDelete = new Pane();
        paneDelete.setVisible(true);
        paneDelete.getChildren().addAll(fieldPane);

        paneUpdate = new Pane();
        paneUpdate.setVisible(true);
        paneUpdate.getChildren().addAll(fieldPane);


        Tab tab1 = new Tab("Manage Cars", paneAdd);
        tab1.setClosable(false);
        tab1.setContent(paneAdd);


        paneVeiwTable = new Pane();
        Pane paneViewOrder = new Pane();
        paneViewOrder.getChildren().add(paneVeiwTable);
        Tab tab2 = new Tab("View Orders");
        tab2.setClosable(false);
        tab2.setContent(paneViewOrder);


        paneSold = new Pane();
        Pane pane = new Pane();
        pane.getChildren().add(paneSold);
        Tab tab3 = new Tab("Sold Orders");
        tab3.setClosable(false);
        tab3.setContent(paneSold);

        paneSummary = new Pane();
        Pane panesummary = new Pane();
        panesummary.getChildren().add(paneSummary);
        Tab tab4 = new Tab("Summary");
        tab4.setClosable(false);
        tab4.setContent(paneSummary);

        Pane paneMangeCars = new Pane();
        Tab tab5 = new Tab("Manege Brands");
        tab5.setClosable(false);
        tab5.setContent(MangeBrands(paneMangeCars));

        tabPane.getSelectionModel().selectedItemProperty().addListener((observableValue, oldTab, newTab) -> {
            if (newTab.equals(tab2)) {
                viewTable();
            } else if (newTab.equals(tab3)) {
                viewSoldCar();
            } else if (newTab.equals(tab4)) {
                summary();
            }
        });

        tabPane.setPrefWidth(1200);
        tabPane.getTabs().addAll(tab1, tab2, tab3, tab4, tab5);

        ActionPresses();//action left button////////////////////////
        viewTable();//show table ///////////////////////////////////////////////

        this.getChildren().addAll(tabPane);
        this.setStyle("-fx-background-color: black");
        stage = new Stage();
        Scene scene = new Scene(this, 1200, 500);
        stage.setScene(scene);
        stage.setTitle("Admin");
        stage.setResizable(false);
        stage.show();
    }

//------------------------------------------------- METHODS -----------------------------------------
   
    //update/delete car from brand
    public void ActionPresses() {

        btnAdd.setOnAction(e -> { //action left button
            tableViewCars.getItems().clear();
            fieldPane.setVisible(true);

            lblbrand = new Label("Brand");
            lblbrand.setStyle(lblStyle);
            lblbrand.setLayoutX(240);
            lblbrand.setLayoutY(80);
            comboBoxBrand = new ComboBox<>();
            comboBoxBrand.setPromptText("Select Brand");
            comboBoxBrand.setPrefHeight(25);
            comboBoxBrand.setPrefWidth(330);
            comboBoxBrand.setLayoutX(300);
            comboBoxBrand.setLayoutY(80);


            lblModel = new Label("Model");
            lblModel.setStyle(lblStyle);
            lblModel.setLayoutX(240);
            lblModel.setLayoutY(120);
            txtModel = new TextField();
            txtModel.setPrefHeight(25);
            txtModel.setPrefWidth(330);
            txtModel.setLayoutX(300);
            txtModel.setLayoutY(120);

            lblyear = new Label("Year");
            lblyear.setStyle(lblStyle);
            lblyear.setLayoutX(240);
            lblyear.setLayoutY(160);
            txtYear = new TextField();
            txtYear.setPrefHeight(25);
            txtYear.setPrefWidth(330);
            txtYear.setLayoutX(300);
            txtYear.setLayoutY(160);

            lblColor = new Label("Color");
            lblColor.setStyle(lblStyle);
            lblColor.setLayoutX(240);
            lblColor.setLayoutY(200);
            txtColor = new TextField();
            txtColor.setPrefHeight(25);
            txtColor.setPrefWidth(330);
            txtColor.setLayoutX(300);
            txtColor.setLayoutY(200);

            lblPrice = new Label("Price");
            lblPrice.setStyle(lblStyle);
            lblPrice.setLayoutX(240);
            lblPrice.setLayoutY(240);
            txtPrice = new TextField();
            txtPrice.setPrefHeight(25);
            txtPrice.setPrefWidth(330);
            txtPrice.setLayoutX(300);
            txtPrice.setLayoutY(240);

            btnDone = new Button("Done");
            btnDone.setStyle("-fx-text-fill:  #f2bd12;-fx-background-color: black;-fx-border-color: white;-fx-background-radius: 10 50 10  50 ; -fx-border-radius: 10 50 10 50;-fx-font-size: 15 ");
            btnDone.setLayoutX(400);
            btnDone.setLayoutY(300);
            btnDone.setPrefWidth(150);
            paneAdd.getChildren().addAll(lblbrand, lblyear, lblModel, lblPrice, lblColor, comboBoxBrand, txtModel, txtYear, txtColor, txtPrice, btnDone);
            paneAdd.getChildren().remove(btn);
            showCarFromBrand();
        });


        btnDelete.setOnAction(d -> {//action button left delete
            fieldPane.setVisible(true);
            paneDelete.setVisible(true);

            txtColor.setVisible(false);
            txtPrice.setVisible(false);
            txtYear.setVisible(false);
            txtModel.setVisible(false);
            lblColor.setVisible(false);
            lblyear.setVisible(false);
            lblModel.setVisible(false);
            lblPrice.setVisible(false);

            btn = new Button("Selected Car");
            btn.setStyle("-fx-text-fill:  #f2bd12;-fx-background-color: black;-fx-border-color: white;-fx-background-radius: 10 50 10  50 ; -fx-border-radius: 10 50 10 50;-fx-font-size: 15 ");
            btn.setLayoutX(400);
            btn.setLayoutY(300);
            btn.setPrefWidth(150);
            btn.setOnAction(e -> { //action when clicked under button for car in brand
                Car c = tableViewCars.getSelectionModel().getSelectedItem(); // return object car from table when clicked
                NodeDoubleLinkedList s = ClientView.data.search(comboBoxBrand.getSelectionModel().getSelectedItem());
                NodeSingleList ns = s.getListCarInfo().getFirst();
                for (int i = 0; i < s.getListCarInfo().size(); i++) {
                    if (ns.getObjectCar() == c) {
                        s.getListCarInfo().removObj(ns);
                        new Warning("Deleted Successfully");
                        break;
                    }
                    ns = ns.getNext();
                }
                tableViewCars.getItems().remove(c);// remove selected from the table
            });
            paneAdd.getChildren().add(btn);
            paneAdd.getChildren().remove(btnDone);
        });


        btnUpdate.setOnAction(e -> { //Action left button update for car in brand
            fieldPane.setVisible(true);
            paneUpdate.setVisible(true);
            Button btnActionUpdate = new Button("Update");
            btnActionUpdate.setVisible(false);

            if (txtColor.isVisible() == false || txtPrice.isVisible() == false || txtYear.isVisible() == false || txtModel.isVisible() == false
                    || lblColor.isVisible() == false || lblyear.isVisible() == false || lblModel.isVisible() == false || lblPrice.isVisible() == false) {
                txtColor.setVisible(true);
                txtPrice.setVisible(true);
                txtYear.setVisible(true);
                txtModel.setVisible(true);
                lblColor.setVisible(true);
                lblyear.setVisible(true);
                lblModel.setVisible(true);
                lblPrice.setVisible(true);
            }
            btnSelectedCar = new Button("Selected Car");

            btnSelectedCar.setStyle("-fx-text-fill:  #f2bd12;-fx-background-color: black;-fx-border-color: white;-fx-background-radius: 10 50 10  50 ; -fx-border-radius: 10 50 10 50;-fx-font-size: 15 ");
            btnSelectedCar.setLayoutX(400);
            btnSelectedCar.setLayoutY(300);
            btnSelectedCar.setPrefWidth(150);

            btnActionUpdate.setStyle("-fx-text-fill:  #f2bd12;-fx-background-color: black;-fx-border-color: white;-fx-background-radius: 10 50 10  50 ; -fx-border-radius: 10 50 10 50;-fx-font-size: 15 ");
            btnActionUpdate.setLayoutX(400);
            btnActionUpdate.setLayoutY(300);
            btnActionUpdate.setPrefWidth(150);


            paneAdd.getChildren().addAll(btnSelectedCar, btnActionUpdate);
            //to fill text filed when clicked on table
            btnSelectedCar.setOnAction(u -> {
                txtColor.setText(tableViewCars.getSelectionModel().getSelectedItem().getColor());
                txtModel.setText(tableViewCars.getSelectionModel().getSelectedItem().getModel());
                txtPrice.setText(String.valueOf(tableViewCars.getSelectionModel().getSelectedItem().getPrice()));
                txtYear.setText(String.valueOf(tableViewCars.getSelectionModel().getSelectedItem().getYear()));

                tableViewCars.refresh();
                btnSelectedCar.setVisible(false);
                btnActionUpdate.setVisible(true);
                //to return object car when clicked on table
                Car c = tableViewCars.getSelectionModel().getSelectedItem();
                NodeDoubleLinkedList s = ClientView.data.search(comboBoxBrand.getSelectionModel().getSelectedItem());
                //array the content is first node in singel list
                final NodeSingleList[] ns = {s.getListCarInfo().getFirst()};
                for (int i = 0; i < s.getListCarInfo().size(); i++) {

                    //action update
                    btnActionUpdate.setOnAction(tr -> {
                        if (ns[0].getObjectCar().equals(c)) {
                            ns[0].getObjectCar().setColor(txtColor.getText().trim());
                            ns[0].getObjectCar().setModel(txtModel.getText().trim());
                            ns[0].getObjectCar().setYear(Integer.parseInt(txtYear.getText().trim()));
                            ns[0].getObjectCar().setPrice(Integer.parseInt(txtPrice.getText().trim()));
                            tableViewCars.refresh();
                            new Warning("Updated successfully");
                        }
                        ns[0] = ns[0].getNext();

                    });
                }
                tableViewCars.refresh();
            });
        });
    }


    //in this method to fill table for orders
    public void viewTable() {

        tabelOrder = new TableView<>();
        TableColumn<Orders, String> customerName = new TableColumn<>("Customer Name");
        TableColumn<Orders, String> mobile = new TableColumn<>("Customer Phone");
        TableColumn<Orders, String> model = new TableColumn<>("Model");
        TableColumn<Orders, String> year = new TableColumn<>("Year");
        TableColumn<Orders, String> color = new TableColumn<>("Color");
        TableColumn<Orders, String> price = new TableColumn<>("Price");
        TableColumn<Orders, Date> date = new TableColumn<>("Order Date");
        TableColumn<Orders, String> brand = new TableColumn<>("Brand");
        TableColumn<Orders, String> status = new TableColumn<>("Order Status");

        date.setCellFactory(col -> {
            TableCell<Orders, Date> cell = new TableCell<>() {
                private SimpleDateFormat format = new SimpleDateFormat("d/M/yyyy");
                @Override
                protected void updateItem(Date item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setText(null);
                    } else {
                        setText(format.format(item));
                    }
                }
            };
            return cell;
        });


        customerName.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().getClient().getName()));
        mobile.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().getClient().getPhone()));
        model.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().getCarInfo().getModel()));
        year.setCellValueFactory(data -> new ReadOnlyStringWrapper(String.valueOf(data.getValue().getCarInfo().getYear())));
        color.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().getCarInfo().getColor()));
        price.setCellValueFactory(data -> new ReadOnlyStringWrapper(String.valueOf(data.getValue().getCarInfo().getPrice())));
        date.setCellValueFactory(new PropertyValueFactory<>("orderDate"));
        status.setCellValueFactory(new PropertyValueFactory<>("orderStatus"));
        brand.setCellValueFactory(new PropertyValueFactory<>("brand"));

        tabelOrder.setPrefWidth(900);
        tabelOrder.setLayoutX(170);
        tabelOrder.setLayoutY(30);
        tabelOrder.getColumns().addAll(customerName, mobile, brand, model, year, color, price, date, status);

        QueueNode current = ClientView.queuelist.getFirst();//return first node in queue
        while (current != null) { // exit node in queue
            tabelOrder.getItems().add(current.getOrders());
            current = current.getNext();
        }

        btnFinish = new Button("Finish");
        btnFinish.setLayoutY(130);
        btnFinish.setLayoutX(30);
        btnFinish.setPrefWidth(120);
        btnFinish.setStyle("-fx-text-fill:  #f2bd12;-fx-background-color: black;-fx-border-color: white;-fx-background-radius: 10 50 10  50 ; -fx-border-radius: 10 50 10 50;-fx-font-size: 15 ");
        //action to add node to stack
        btnFinish.setOnAction(e -> {
            manegeOrders(1);
        });


        btnInProcess = new Button("In Proses");
        btnInProcess.setLayoutY(200);
        btnInProcess.setLayoutX(30);
        btnInProcess.setPrefWidth(120);
        btnInProcess.setStyle("-fx-text-fill:  #f2bd12;-fx-background-color: black;-fx-border-color: white;-fx-background-radius: 10 50 10  50 ; -fx-border-radius: 10 50 10 50;-fx-font-size: 15 ");
        btnInProcess.setOnAction(e -> {
            manegeOrders(2);
        });


        btnCancel = new Button("Cancel");
        btnCancel.setLayoutY(270);
        btnCancel.setLayoutX(30);
        btnCancel.setPrefWidth(120);
        btnCancel.setStyle("-fx-text-fill:  #f2bd12;-fx-background-color: black;-fx-border-color: white;-fx-background-radius: 10 50 10  50 ; -fx-border-radius: 10 50 10 50;-fx-font-size: 15 ");
        btnCancel.setOnAction(e -> {
            manegeOrders(3);
        });

        ActionButton();
        paneVeiwTable.getChildren().addAll(tabelOrder, btnFinish, btnInProcess, btnCancel);
    }
    
    
    
    

    public void manegeOrders(int action) {//  ***************************
        Orders orders = tabelOrder.getSelectionModel().getSelectedItem();//return order when clicked on table
        if (orders == null) {
            new Warning("Please Selected");
            return;
        }
        QueueNode current2 = ClientView.queuelist.getFirst();//first node in from queue
        QueueList queueList = new QueueList();//create new queue

        while (current2 != null && current2.getOrders() != orders) { // first node && selected
            queueList.enQueue(current2.getOrders());//add to queue
            ClientView.queuelist.deQueue();//remove from queue
            current2 = current2.getNext();
        }
        if (action == 1) {//finish
            current2.getOrders().setOrderStatus("Finished");
            ClientView.stackList.push(orders);//add order selected to stack
            NodeDoubleLinkedList brand = ClientView.data.search(current2.getOrders().getBrand());
            brand.getListCarInfo().removObj(new NodeSingleList(current2.getOrders().getCarInfo()));
        } else if (action == 2) {//in process
            ClientView.queuelist.enQueue(ClientView.queuelist.getFirst().getOrders());//add to last table
        }
        ClientView.queuelist.deQueue();
        current2 = current2.getNext();
        while (current2 != null) {//cancel
            queueList.enQueue(current2.getOrders());
            ClientView.queuelist.deQueue();
            current2 = current2.getNext();
        }
        ClientView.queuelist = queueList;
        viewTable();
    }

    
    
    

    //in this method view sold cars
    public void viewSoldCar() {
        TableView<Orders> tableView = new TableView<>();
        TableColumn<Orders, String> customerName = new TableColumn<>("Customer Name");
        TableColumn<Orders, String> mobile = new TableColumn<>("Customer Mobile");
        TableColumn<Orders, String> brand = new TableColumn<>("Brand");
        TableColumn<Orders, String> model = new TableColumn<>("Model");
        TableColumn<Orders, String> year = new TableColumn<>("Year");
        TableColumn<Orders, String> color = new TableColumn<>("Color");
        TableColumn<Orders, String> price = new TableColumn<>("Price");
        TableColumn<Orders, Date> date = new TableColumn<>("Order Date");
        TableColumn<Orders, String> status = new TableColumn<>("Order Status");
        date.setCellFactory(col -> {
            TableCell<Orders, Date> cell = new TableCell<Orders, Date>() {
                private SimpleDateFormat format = new SimpleDateFormat("d/M/yyyy");
                @Override
                protected void updateItem(Date item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setText(null);
                    } else {
                        setText(format.format(item));
                    }
                }
            };
            return cell;
        });

        customerName.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().getClient().getName()));
        mobile.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().getClient().getPhone()));
        model.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().getCarInfo().getModel()));
        year.setCellValueFactory(data -> new ReadOnlyStringWrapper(String.valueOf(data.getValue().getCarInfo().getYear())));
        color.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().getCarInfo().getColor()));
        price.setCellValueFactory(data -> new ReadOnlyStringWrapper(String.valueOf(data.getValue().getCarInfo().getPrice())));
        date.setCellValueFactory(new PropertyValueFactory<>("orderDate"));
        status.setCellValueFactory(new PropertyValueFactory<>("orderStatus"));
        brand.setCellValueFactory(new PropertyValueFactory<>("brand"));

        NodeStack nodeStack = ClientView.stackList.getTopItem();
        while (nodeStack != null) {
            tableView.getItems().add(nodeStack.getOrder());
            nodeStack = nodeStack.getNext();
        }

        tableView.setPrefWidth(900);
        tableView.setLayoutX(170);
        tableView.setLayoutY(30);
        tableView.getColumns().addAll(customerName, mobile, brand, model, year, color, price, date, status);
        btnTopten = new Button("Top 10");
        btnTopten.setLayoutY(140);
        btnTopten.setLayoutX(30);
        btnTopten.setPrefWidth(120);
        btnTopten.setStyle("-fx-text-fill:  #f2bd12;-fx-background-color: black;-fx-border-color: white;-fx-background-radius: 10 50 10  50 ; -fx-border-radius: 10 50 10 50;-fx-font-size: 15 ");
        tableView.refresh();
        
        
      //action button view top 10 in stack
        btnTopten.setOnAction(p -> {
            tableView.getItems().clear();
            int count = 0;
            StackList newStack  = new StackList();
            while(count<10){
            	if(ClientView.stackList.getTopItem()!=null) {
                tableView.getItems().add(ClientView.stackList.getTopItem().getOrder());
                newStack.push(ClientView.stackList.getTopItem().getOrder());
                ClientView.stackList.pop();
                }
                count++;
            }
            new Warning("Done");

            while (!newStack.isEmpty()) {
                ClientView.stackList.push(newStack.getTopItem().getOrder());
                newStack.pop();
            }
        });

        
        paneSold.getChildren().addAll(tableView, btnTopten);
    }


    //in this method add/delete/update brand
    public Pane MangeBrands(Pane p) {
        Button btnAddBrand = new Button("Add");
        btnAddBrand.setLayoutX(250);
        btnAddBrand.setLayoutY(340);
        btnAddBrand.setPrefWidth(194);
        btnAddBrand.setPrefHeight(24);
        btnAddBrand.setStyle("-fx-text-fill:  #f2bd12;-fx-background-color: black;-fx-border-color: white;-fx-background-radius: 10 50 10  50 ; -fx-border-radius: 10 50 10 50;-fx-font-size: 12");
        btnAddBrand.setFont(font);

        Button btnDeleteBrand = new Button("Delete");
        btnDeleteBrand.setLayoutX(530);
        btnDeleteBrand.setLayoutY(340);
        btnDeleteBrand.setPrefWidth(194);
        btnDeleteBrand.setPrefHeight(24);
        btnDeleteBrand.setStyle("-fx-text-fill:  #f2bd12;-fx-background-color: black;-fx-border-color: white;-fx-background-radius: 10 50 10  50 ; -fx-border-radius: 10 50 10 50;-fx-font-size: 12");
        btnDeleteBrand.setFont(font);

        Button btnUpdateBrand = new Button("Update");
        btnUpdateBrand.setLayoutX(800);
        btnUpdateBrand.setLayoutY(340);
        btnUpdateBrand.setPrefWidth(194);
        btnUpdateBrand.setPrefHeight(24);
        btnUpdateBrand.setStyle("-fx-text-fill:  #f2bd12;-fx-background-color: black;-fx-border-color: white;-fx-background-radius: 10 50 10  50 ; -fx-border-radius: 10 50 10 50;-fx-font-size: 12");
        btnUpdateBrand.setFont(font);

        Label lblNewBrand = new Label("New Brande");
        lblNewBrand.setLayoutX(260);
        lblNewBrand.setLayoutY(100);
        lblNewBrand.setStyle(lblStyle);
        TextField txtNewBrand = new TextField();
        txtNewBrand.setLayoutX(350);
        txtNewBrand.setLayoutY(100);
        txtNewBrand.setPrefHeight(25);
        txtNewBrand.setPrefWidth(330);

        Label lblNewBrandName = new Label("New Brande \n Name");
        lblNewBrandName.setLayoutX(260);
        lblNewBrandName.setLayoutY(150);
        lblNewBrandName.setStyle(lblStyle);
        TextField txtNewBrandName = new TextField();
        txtNewBrandName.setLayoutX(350);
        txtNewBrandName.setLayoutY(150);
        txtNewBrandName.setPrefHeight(25);
        txtNewBrandName.setPrefWidth(330);
        lblNewBrandName.setVisible(false);
        txtNewBrandName.setVisible(false);


        TableView tableBrands = new TableView();//to show brands
        tableBrands.setLayoutX(700);
        tableBrands.setLayoutY(75);
        tableBrands.setPrefHeight(250);
        tableBrands.setPrefWidth(350);
        TableColumn<NodeDoubleLinkedList, String> columnBrandName = new TableColumn<>("Brand Name");
        columnBrandName.setCellValueFactory(new PropertyValueFactory<>("brand"));
        tableBrands.getColumns().add(columnBrandName);

        p.getChildren().addAll(btnAddBrand, btnDeleteBrand, btnUpdateBrand, lblNewBrand, txtNewBrand, tableBrands, lblNewBrandName, txtNewBrandName);

        NodeDoubleLinkedList node = ClientView.data.getFirst();//to fill table name brand
        for (int i = 0; i < ClientView.data.size(); i++) {
            tableBrands.getItems().add(node);
            node = node.getNext();
        }

        btnAddBrand.setOnAction(a -> {
            if (lblNewBrandName.isVisible() || txtNewBrandName.isVisible()) {
                lblNewBrandName.setVisible(false);
                txtNewBrandName.setVisible(false);
            }

            if (txtNewBrand.getText().isEmpty()) {
                new Warning("Please Enter the Brand Name");
                return;
            } else {
                tableBrands.getItems().clear();
                if (ClientView.data.search(txtNewBrand.getText()) == null) {// not exit this brand
                    ClientView.data.addNodeSorted(new NodeDoubleLinkedList(txtNewBrand.getText()));
                    new Warning("Added Successfully");
                    txtNewBrand.clear();
                    NodeDoubleLinkedList node2 = ClientView.data.getFirst();
                    for (int i = 0; i < ClientView.data.size(); i++) {
                        tableBrands.getItems().add(node2);
                        node2 = node2.getNext();
                    }
                } else {
                    new Warning(txtNewBrand.getText() + " Brande Exit");//if input brand exit
                    txtNewBrand.clear();
                    NodeDoubleLinkedList node2 = ClientView.data.getFirst();
                    for (int i = 0; i < ClientView.data.size(); i++) {
                        tableBrands.getItems().add(node2);
                        node2 = node2.getNext();
                    }
                }
            }
        });


        btnDeleteBrand.setOnAction(d -> {
            if (lblNewBrandName.isVisible() || txtNewBrandName.isVisible()) {
                lblNewBrandName.setVisible(false);
                txtNewBrandName.setVisible(false);
            }
            if (txtNewBrand.getText().isEmpty()) {
                new Warning("Enter The Name Brande ");
                return;
            } else {
                tableBrands.getItems().clear();
                if (ClientView.data.search(txtNewBrand.getText()) != null) { //exit brand
                    ClientView.data.removeObj(ClientView.data.search(txtNewBrand.getText().trim()));
                    new Warning("Deleted Successfully");
                    tableBrands.getItems().clear();
                    NodeDoubleLinkedList node2 = ClientView.data.getFirst();
                    for (int i = 0; i < ClientView.data.size(); i++) {
                        tableBrands.getItems().add(node2);
                        node2 = node2.getNext();
                    }
                    txtNewBrand.clear();
                } else {
                    new Warning(txtNewBrand.getText() + " Not Found");// not exit brand
                    NodeDoubleLinkedList node2 = ClientView.data.getFirst();
                    for (int i = 0; i < ClientView.data.size(); i++) {
                        tableBrands.getItems().add(node2);
                        node2 = node2.getNext();
                    }
                    txtNewBrand.clear();
                }
            }
        });


        final NodeDoubleLinkedList[] selected = new NodeDoubleLinkedList[1];
        tableBrands.setOnMouseClicked(e -> {
            if (lblNewBrandName.isVisible() == false || txtNewBrandName.isVisible() == false) {
                lblNewBrandName.setVisible(true);
                txtNewBrandName.setVisible(true);
            }
            if (e.getClickCount() == 1) {
                selected[0] = (NodeDoubleLinkedList) tableBrands.getSelectionModel().getSelectedItem();
                if (selected[0] != null) {
                    txtNewBrand.setText(selected[0].getBrand());
                }
            }
        });
        //button update
        btnUpdateBrand.setOnAction(e -> {
            selected[0].setBrand(txtNewBrandName.getText());
            tableBrands.refresh();
        });
        return p;//return input pane
    }


    // to save to file order
    public void saveFile() {
        FileWriter write;
        try {
            write = new FileWriter(WelcomeView.orderFile);
            QueueNode currentQueue = ClientView.queuelist.getFirst();
            while (currentQueue != null) {
                write.append(currentQueue.getOrders().toString());
                currentQueue = currentQueue.getNext();
            }

            NodeStack nodeStack = ClientView.stackList.getTopItem();
            while (nodeStack != null) {
                write.append(nodeStack.getOrder().toString());
                nodeStack = nodeStack.getNext();
            }
            write.flush();
            write.close();
        } catch (IOException e2) {
            new Warning(e2.getMessage());
        }
    }


    //th this method add data report to table summary
    public void summary() {

        TableView<Summary> tableSummary = new TableView<>();
        TableColumn<Summary, String> brandName = new TableColumn<>("Brand Name");
        TableColumn<Summary, Integer> heightPrice = new TableColumn<>("Height Price");
        TableColumn<Summary, Integer> lowestPrice = new TableColumn<>("Lowest Price");
        TableColumn<Summary, Integer> nomInStack = new TableColumn<>("# car in Process");
        TableColumn<Summary, Integer> nomInQueue = new TableColumn<>("# car was solid");

        brandName.setCellValueFactory(new PropertyValueFactory<>("brand"));
        heightPrice.setCellValueFactory(new PropertyValueFactory<>("highPrice"));
        lowestPrice.setCellValueFactory(new PropertyValueFactory<>("lowPrice"));
        nomInStack.setCellValueFactory(new PropertyValueFactory<>("noInProcess"));
        nomInQueue.setCellValueFactory(new PropertyValueFactory<>("noSolid"));
        tableSummary.getColumns().addAll(brandName, heightPrice, lowestPrice, nomInStack, nomInQueue);

        tableSummary.setPrefWidth(900);
        tableSummary.setLayoutX(170);
        tableSummary.setLayoutY(30);
        paneSummary.getChildren().add(tableSummary);

        int totalHigh = 0, totalLow = 0, noProcess = 0, noSolid = 0;

        NodeDoubleLinkedList currentBrand = ClientView.data.getFirst();
        int size = 0;
        while (size < ClientView.data.size()) {
            Summary summary = new Summary();
            summary.setBrand(currentBrand.getBrand());
            int noP = noInProcess(currentBrand.getBrand());
            noProcess += noP;
            summary.setNoInProcess(noP);
            int noS = noSolid(currentBrand.getBrand());
            noSolid += noS;
            summary.setNoSolid(noS);
            int high = highPrice(currentBrand);
            totalHigh += high;
            summary.setHighPrice(high);
            int low = lowPrice(currentBrand);
            if (low == Integer.MAX_VALUE) low = 0;
            totalLow += low;
            summary.setLowPrice(low);
            tableSummary.getItems().add(summary);
            currentBrand = currentBrand.getNext();
            size++;
        }
        Summary summary = new Summary("Total", noProcess, noSolid, totalHigh, totalLow);
        tableSummary.getItems().add(summary);
    }


    //to get number cars in process (from queue)
    private int noInProcess(String brand) {
        QueueNode node = ClientView.queuelist.getFirst();
        int count = 0;
        while (node != null) {
            if (node.getOrders().getBrand().equals(brand)) count++;
            node = node.getNext();
        }
        return count;
    }


    //to get number cars finished (from stack)
    private int noSolid(String brand) {
        NodeStack nodeStack = ClientView.stackList.getTopItem();
        int count = 0;
        while (nodeStack != null) {
            if (nodeStack.getOrder().getBrand().equals(brand)) count++;
            nodeStack = nodeStack.getNext();
        }
        return count;
    }


    //to get heightPrice for every brand
    private int highPrice(NodeDoubleLinkedList currentBrand) {
        int max = 0;
        NodeSingleList currentCar = currentBrand.getListCarInfo().getFirst();
        while (currentCar != null) {
            if (currentCar.getObjectCar().getPrice() > max) {
                max = currentCar.getObjectCar().getPrice();
            }
            currentCar = currentCar.getNext();
        }
        return max;
    }


    //to get heightPrice for every brand
    private int lowPrice(NodeDoubleLinkedList currentBrand) {
        int min = Integer.MAX_VALUE;
        NodeSingleList currentCar = currentBrand.getListCarInfo().getFirst();
        while (currentCar != null) {
            if (currentCar.getObjectCar().getPrice() < min) {
                min = currentCar.getObjectCar().getPrice();
            }
            currentCar = currentCar.getNext();
        }
        return min;
    }


    //view button when clicked  change color
    public void ActionButton() {
        btnFinish.setOnMouseClicked(e -> {
            btnFinish.setStyle("-fx-text-fill:  white;-fx-background-color: green;-fx-border-color: white;-fx-background-radius: 10 50 10  50 ; -fx-border-radius: 10 50 10 50;-fx-font-size: 15 ");
        });
        btnFinish.setOnMouseExited(e -> {
            btnFinish.setStyle("-fx-text-fill:  #f2bd12;-fx-background-color: black;-fx-border-color: white;-fx-background-radius: 10 50 10  50 ; -fx-border-radius: 10 50 10 50;-fx-font-size: 15 ");
        });
        btnInProcess.setOnMouseClicked(p -> {
            btnInProcess.setStyle("-fx-text-fill:  white;-fx-background-color: blue;-fx-border-color: white;-fx-background-radius: 10 50 10  50 ; -fx-border-radius: 10 50 10 50;-fx-font-size: 15 ");
        });
        btnInProcess.setOnMouseExited(e -> {
            btnInProcess.setStyle("-fx-text-fill:  #f2bd12;-fx-background-color: black;-fx-border-color: white;-fx-background-radius: 10 50 10  50 ; -fx-border-radius: 10 50 10 50;-fx-font-size: 15 ");
        });
        btnCancel.setOnMouseClicked(c -> {
            btnCancel.setStyle("-fx-text-fill:  white;-fx-background-color: red;-fx-border-color: white;-fx-background-radius: 10 50 10  50 ; -fx-border-radius: 10 50 10 50;-fx-font-size: 15 ");
        });
        btnCancel.setOnMouseExited(e -> {
            btnCancel.setStyle("-fx-text-fill:  #f2bd12;-fx-background-color: black;-fx-border-color: white;-fx-background-radius: 10 50 10  50 ; -fx-border-radius: 10 50 10 50;-fx-font-size: 15 ");
        });

    }


    //  in this method to get every car for specific brand
    public void showCarFromBrand() {
        tableViewCars.getItems().clear();
        tableViewCars.setLayoutY(100);
        tableViewCars.setLayoutX(650);
        tableViewCars.setPrefHeight(160);
        tableViewCars.setPrefWidth(400);
        TableColumn<Car, String> modelName = new TableColumn<>("Model");
        TableColumn<Car, String> modelYear = new TableColumn<>("Year");
        TableColumn<Car, String> modelColor = new TableColumn<>("Color");
        TableColumn<Car, String> modelPrice = new TableColumn<>("Price");


        modelName.setCellValueFactory(new PropertyValueFactory<Car, String>("model"));
        modelYear.setCellValueFactory(new PropertyValueFactory<Car, String>("year"));
        modelColor.setCellValueFactory(new PropertyValueFactory<Car, String>("color"));
        modelPrice.setCellValueFactory(new PropertyValueFactory<Car, String>("price"));
        tableViewCars.getColumns().addAll(modelName, modelYear, modelColor, modelPrice);


        NodeDoubleLinkedList node = ClientView.data.getFirst();
        for (int i = 0; i < ClientView.data.size(); i++) {
            comboBoxBrand.getItems().add(node.getBrand());//to fill comboBox
            node = node.getNext();
        }

        comboBoxBrand.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            String selected = newValue;
            tableViewCars.getItems().clear();
            NodeDoubleLinkedList nodeDoubleLinkedList = ClientView.data.search(selected);//return node in comboBox
            NodeSingleList nodeSingleList = nodeDoubleLinkedList.getListCarInfo().getFirst();//return first list in this brand
            for (int i = 0; i < nodeDoubleLinkedList.getListCarInfo().size(); i++) {
                tableViewCars.getItems().add(nodeSingleList.getObjectCar());//fill table
                nodeSingleList = nodeSingleList.getNext();
            }
            btnDone.setOnAction(a -> {//action to add new brans
                if (txtModel.getText().isEmpty()) {
                    new Warning("Please Enter the Model");
                } else if (txtYear.getText().isEmpty()) {
                    new Warning("Please Enter the year");
                } else if (txtPrice.getText().isEmpty()) {
                    new Warning("Please Enter the Price");
                } else if (txtColor.getText().isEmpty()) {
                    new Warning("Please Enter the Color");
                } else {
                    int year = 0, price = 0;
                    try {
                        year = Integer.parseInt(txtYear.getText().trim());
                    } catch (Exception e) {
                        new Warning("Please Enter valid  year");
                        return;
                    }
                    try {
                        price = Integer.parseInt(txtPrice.getText().trim());
                    } catch (Exception e) {
                        new Warning("Please Enter valid  price");
                        return;
                    }
                    tableViewCars.refresh();
                    String selected2 = comboBoxBrand.getSelectionModel().getSelectedItem();
                    NodeDoubleLinkedList nodeDoubleLinkedList2 = ClientView.data.search(selected2);
                    nodeDoubleLinkedList2.getListCarInfo().addNodeSorted(new NodeSingleList(new Car(txtModel.getText(), year, txtColor.getText(), price)));
                    tableViewCars.refresh();
                    new Warning("Added Successfully");
                    NodeDoubleLinkedList nodeDoubleLinkedListt = ClientView.data.search(comboBoxBrand.getSelectionModel().getSelectedItem());
                    NodeSingleList nodeSingleListt = nodeDoubleLinkedListt.getListCarInfo().getFirst();
                    tableViewCars.getItems().clear();
                    for (int i = 0; i < nodeDoubleLinkedListt.getListCarInfo().size(); i++) {
                        tableViewCars.getItems().add(nodeSingleListt.getObjectCar());
                        nodeSingleListt = nodeSingleListt.getNext();
                    }
                    txtColor.clear();
                    txtModel.clear();
                    txtPrice.clear();
                    txtYear.clear();
                    tableViewCars.refresh();
                }
            });
        });

        tableViewCars.getItems().clear();
        paneAdd.getChildren().remove(tableViewCars);
        paneAdd.getChildren().add(tableViewCars);
    }
}
