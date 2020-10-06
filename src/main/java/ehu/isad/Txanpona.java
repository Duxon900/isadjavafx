package ehu.isad;


import com.google.gson.Gson;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class Txanpona extends Application {
    int trade_id;
    float price;
    float size;
    String time;
    float bid;
    float ask;
    float volume;


    @Override
    public String toString() {
        return "Txanpona{" +
                "trade_id=" + trade_id +
                ", price=" + price +
                ", size=" + size +
                ", time='" + time + '\'' +
                ", bid=" + bid +
                ", ask=" + ask +
                ", volume=" + volume +
                '}';
    }



    public static Txanpona readFromUrl(String txanpona) {
        txanpona = txanpona.toLowerCase();
        String inputLine = "";

        try {
            URL coinMarket = new URL("https://api.gdax.com/products/" + txanpona + "-eur/ticker");
            URLConnection yc = coinMarket.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(yc.getInputStream()));
            new InputStreamReader(coinMarket.openStream());

            inputLine = in.readLine();
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Gson gson = new Gson();
        return gson.fromJson(inputLine, Txanpona.class);
    }


    @Override
    public void start(Stage primaryStage) throws Exception {

        primaryStage.setTitle("Txanpona");


        Group root = new Group();
        ObservableList list = root.getChildren();

        ComboBox comboBox = new ComboBox();

        comboBox.getItems().add("BTC");
        comboBox.getItems().add("ETC");
        comboBox.getItems().add("LTC");

        comboBox.setEditable(false);

        //Emaitza
        Label label = new Label("Hautatu txanpona");

        //vBox
        VBox vBox=new VBox();
        vBox.getChildren().addAll(label,comboBox);


        //Arrow operator gutxiago idazteko
        comboBox.setOnAction(e -> {
            float price = Txanpona.readFromUrl((String) comboBox.getValue()).price;
            label.setText(price + " EUR=1 " + comboBox.getValue());
        });

        Scene scene = new Scene(vBox,500,100);
        primaryStage.setScene(scene);
        primaryStage.show();

    }
}
