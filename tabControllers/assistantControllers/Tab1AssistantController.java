

package tabControllers.assistantControllers;

import coinClasses.ConnectToDatabase;
import coinClasses.SingleCoin;
import java.util.LinkedList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.web.WebView;
import javafx.util.Callback;
import tabControllers.assistantControllers.tablesAndLists.TableClass;

/**
 * This Class contains additional methods used in Tab1 to display data to the
 * screen.
 *
 * The main purpose of this class is to keep the main tab controllers as clean
 * as possible.
 *
 * @author Kyle
 */
public class Tab1AssistantController {
    
    private final boolean DEBUG = tabControllers.Tab1Controller.DEBUG;

    public void coinTable(TableView tableViewT1, LinkedList<SingleCoin> coinList, WebView webViewT1, String currency, long currencyRate) {
//        LinkedList<String> colNames = new LinkedList<>();
//        colNames.add("Symbol");
//        colNames.add("Name");
//        colNames.add("Price");
//        colNames.add("Rank");
//        colNames.add("Change");
//        colNames.add("Volume");
//        TableClass tbl = new TableClass(tableViewT1, coinList, webViewT1, colNames, currency, currencyRate);
//        tbl.displayTable();
        
        
        
        // Create columns
        TableColumn col1 = new TableColumn("Symbol");
        TableColumn col2 = new TableColumn("Name");
        TableColumn col3 = new TableColumn("Price (" + currency + ")");
        TableColumn col4 = new TableColumn("Rank");
        TableColumn col5 = new TableColumn("Change");
        TableColumn col6 = new TableColumn("Volume");
        // Link columns to properties in SingleCoin class
        col1.setCellValueFactory(new PropertyValueFactory<>("symbol"));
        col2.setCellValueFactory(new PropertyValueFactory<>("name"));
        col3.setCellValueFactory(new PropertyValueFactory<>("price"));
        col4.setCellValueFactory(new PropertyValueFactory<>("rank"));
        col5.setCellValueFactory(new PropertyValueFactory<>("stringChange"));
        col6.setCellValueFactory(new PropertyValueFactory<>("volume"));
        // Add columns to tableView
        tableViewT1.getColumns().addAll(col1, col2, col3, col4, col5, col6);
        ObservableList<SingleCoin> obvList = FXCollections.observableArrayList(coinList);
        // Change text color of "change" column if positive or negative change.
        col5.setCellFactory(new Callback<TableColumn, TableCell>() {
            public TableCell call(TableColumn param) {
                return new TableCell<SingleCoin, String>() {

                    @Override
                    public void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        // Change color based on data
                        if (!isEmpty()) {
                            this.setStyle("-fx-text-fill: #09de57;-fx-font-weight: bold;");
                            if (item.contains("-")) {
                                this.setStyle("-fx-text-fill: #ff0000;-fx-font-weight: bold;");
                            }
                            setText(item);
                        }
                    }
                };
            }
        });
        col3.setCellFactory(new Callback<TableColumn, TableCell>() {
            public TableCell call(TableColumn param) {
                return new TableCell<SingleCoin, String>() {

                    @Override
                    public void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        // Change color based on data
                        if (!isEmpty()) {
                            // This is a SUPER "hacky" way to change prices LOL
                            String newPrice = String.format("%.5f", Float.parseFloat(item)*Float.parseFloat(""+currencyRate));
                            setText(newPrice);
                        }
                    }
                };
            }
        });
        tableViewT1.setItems(obvList);
        tableViewT1.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        // Allows user to double click a table row and display info in textArea
        tableViewT1.setRowFactory(tv -> {
            TableRow<SingleCoin> row = new TableRow<>();
            row.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    if (event.getClickCount() == 2 && (!row.isEmpty())) {
                        SingleCoin rowData = row.getItem();
                        System.out.println(rowData);
                        String imgPath = rowData.getIconUrl();

                        // Attempting to resize the coin logo image.
                        webViewT1.setPrefHeight(56);
                        webViewT1.setPrefWidth(56);
                        webViewT1.getEngine().load(imgPath);
                    }
                }
            });
            return row;
        });
    }

    /**
     * Display coin table on the Dsahboard
     * @param tableView
     * @param coinList
     */
    public void coinTableDash(TableView tableView, LinkedList<SingleCoin> coinList) {
        // Create columns
        SingleCoin sc = new SingleCoin();
        LinkedList<String> colNames = new LinkedList<>();
        // Add single coin param names for column names.
        colNames.add("Name");
        colNames.add("Symbol");
        colNames.add("Price");
        colNames.add("Change");
        TableClass tbl = new TableClass(tableView, coinList, colNames);
        tbl.displayTable();
//        tbl.setColor("#09de57", "#ff0000");
    }

    /**
     * Change a users online status. i.e. when they log on/off .
     * @param _uname
     * @param _status
     */
    public void setOnlineStatus(String _uname, int _status) {
        if(DEBUG){System.out.println("Update " + _uname + "'s online status");}
        ConnectToDatabase conn = new ConnectToDatabase();
        conn.setUserOnlineStatus(_uname, _status);
        conn.close();
    }
}
