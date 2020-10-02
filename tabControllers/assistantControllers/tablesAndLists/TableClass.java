package tabControllers.assistantControllers.tablesAndLists;

import coinClasses.SingleCoin;
import interfaces.TableInterface;
import java.lang.reflect.Field;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Spliterator;
import java.util.logging.Level;
import java.util.logging.Logger;
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

/**
 * General class for making & displaying tables.
 *
 * @author Kyle
 */
public class TableClass implements TableInterface{

    private final TableView TABLE_VIEW;
    private final String CURRENCY;
    private final long CURRENCY_RATE;
    private final LinkedList<String> COLUMN_NAMES;
    private final WebView WEB_VIEW;
    private LinkedList<SingleCoin> coinList;
    // List of columns to be added
    private LinkedList<TableColumn> cols;
    private ObservableList<SingleCoin> obvList;
    private TableColumn changeCol;
    private TableColumn col2;
    private TableColumn col3;
    private TableColumn col4;
    private TableColumn col5;
    private TableColumn col6;

    public TableClass(TableView _tableViewT1, LinkedList<SingleCoin> _coinList, WebView _webView, LinkedList<String> _columnNames, String _currency, long _currencyRate) {
        this.TABLE_VIEW = _tableViewT1;
        this.CURRENCY = _currency;
        this.COLUMN_NAMES = _columnNames;
        this.CURRENCY_RATE = _currencyRate;
        this.WEB_VIEW = _webView;
        this.coinList = _coinList;
        this.cols = new LinkedList<>();
        buildTable();
    }

    public TableClass(TableView _tableViewT1, LinkedList<SingleCoin> _coinList, LinkedList<String> _columnNames) {
        this.TABLE_VIEW = _tableViewT1;
        this.COLUMN_NAMES = _columnNames;
        this.CURRENCY = "USD";
        this.CURRENCY_RATE = 1;
        this.WEB_VIEW = null;
        this.coinList = _coinList;
        this.cols = new LinkedList<>();
        buildTable();
    }

    /**
     * This method created the columns for the table.
     *
     * The parameter names for the class of objects to be displayed are linked
     * to each column. i.e: column1.getName() == class.parameter.name
     *
     * The column names must be the same as the parameter names. They can,
     * however, have capital letters and spaces.
     *
     * @param colList
     */
    private void buildTable() {
        Class<SingleCoin> sclass = SingleCoin.class;
        this.COLUMN_NAMES.forEach((item) -> {
            TableColumn col1 = new TableColumn(item);
            this.cols.add(col1);
        });
        Field[] params = sclass.getDeclaredFields();
        this.cols.forEach((item) -> {
            for (Field fld : params) {
                if (item.getText().toLowerCase().replace(" ", "").equals(fld.getName())) {
                    item.setCellValueFactory(new PropertyValueFactory<>(item.getText().toLowerCase().replace(" ", "")));
                }
            }
        });
        setCurrency();
        // Add columns to tableView
        this.TABLE_VIEW.getColumns().addAll(this.cols);
        this.obvList = FXCollections.observableArrayList(this.coinList);
    }

    /**
     * Add red / green colored text to indicate price changes.
     */
    @Override
    public void colorChangeCol(String _colorUp, String _colorDown) {
        this.cols.forEach((item) -> {
            if (item.getText().equalsIgnoreCase("change")) {
                item.setCellFactory(new Callback<TableColumn, TableCell>() {
                    public TableCell call(TableColumn param) {
                        return new TableCell<SingleCoin, Number>() {
                            @Override
                            protected void updateItem(Number item, boolean empty) {
                                // calling super here is very important - don't skip this!
                                super.updateItem(item, empty);
                                // Change color based on data
                                if (!isEmpty()) {
                                    this.setStyle("-fx-text-fill: " + _colorUp + ";-fx-font-weight: bold;");
                                    if (item.toString().contains("-")) {
                                        this.setStyle("-fx-text-fill: " + _colorDown + ";-fx-font-weight: bold;");
                                    }
                                    setText(item.toString());
                                }
                            }
                        };
                    }
                });
            }
        });
    }

    /**
     * Set the currency rate and adjust all prices in the table.
     * @param currRate
     */
    private void setCurrency() {
        // Change text color of "change" column if positive or negative change.
        // Default: col5 and col3
        this.cols.forEach((item) -> {
            if (item.getText().equalsIgnoreCase("price")) {
                item.setCellFactory(new Callback<TableColumn, TableCell>() {
                    public TableCell call(TableColumn param) {
                        return new TableCell<SingleCoin, String>() {

                            @Override
                            public void updateItem(String item, boolean empty) {
                                super.updateItem(item, empty);
                                // Change color based on data
                                if (!isEmpty()) {
                                    // This is a SUPER "hacky" way to change prices LOL
                                    String newPrice = String.format("%.5f", Float.parseFloat(item) * Float.parseFloat("" + CURRENCY_RATE));
                                    setText(newPrice);
                                }
                            }
                        };
                        
                    }
                });
            }
        });
    }
    
    /**
     * Add a double click that displays the currencies logo.
     */
    @Override
    public void addDoubleClick() {
        this.TABLE_VIEW.setRowFactory(tv -> {
            TableRow<SingleCoin> row = new TableRow<>();
            row.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    if (event.getClickCount() == 2 && (!row.isEmpty())) {
                        SingleCoin rowData = row.getItem();
                        System.out.println(rowData);
                        String imgPath = rowData.getIconUrl();

                        // Attempting to resize the coin logo image.
                        WEB_VIEW.setPrefHeight(56);
                        WEB_VIEW.setPrefWidth(56);
                        WEB_VIEW.getEngine().load(imgPath);
                    }
                }
            });
            return row;
        });
    }

    @Override
    public void displayTable() {
        this.TABLE_VIEW.setItems(this.obvList);
        this.TABLE_VIEW.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    }

}
