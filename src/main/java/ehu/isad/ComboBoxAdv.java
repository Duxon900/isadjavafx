package ehu.isad;

import com.google.gson.Gson;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.StringConverter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class ComboBoxAdv extends Application {

    public class Liburuak{

        String title;
        int number_of_pages;
        String details;
        String isbn;


        public Liburuak(String izena, String isbn){
            this.title=izena;
            this.isbn=isbn;
        }


        @Override
        public String toString() {
            return "Liburuak{" +
                    ", title='" + title + '\'' +
                    ", number_of_pages=" + number_of_pages +
                    ", details='" + details + '\'' +
                    '}';
        }

        public String getTitle(){
            return this.title;
        }

        public String getIsbn(){
            return this.isbn;
        }

    }


    @Override
    public void start(Stage primaryStage) throws Exception {

        primaryStage.setTitle("OpenLibrary APIa aztertzen");

        ComboBox comboBox = new ComboBox();



        Label label=new Label("izenak");

        ObservableList<Liburuak> liburuList = FXCollections.observableArrayList();
        liburuList.addAll(
                new Liburuak("Blockchain: Blueprint for a New Economy","9781491920497"),
                new Liburuak("R for Data Science","1491910399"),
                new Liburuak("Fluent Python","1491946008"),
                new Liburuak("Natural Language Processing with PyTorch","1491978236"),
                new Liburuak("Data Algorithms","9781491906187")
        );

        comboBox.setItems(liburuList);
        comboBox.setEditable(false);

        comboBox.setConverter(new StringConverter<Liburuak>() {


            @Override
            public String toString(Liburuak object) {
                if(object==null){
                    return "";
                }
                else return object.getIsbn();
            }

            @Override
            public Liburuak fromString(String izena) {
                return null;
                // return (Argazki) comboBox.getItems().stream().filter(o -> ((Argazki)o).getIzena().equals(izena)).findFirst().orElse(null);
            }

        });

        comboBox.valueProperty().addListener((obs, oldval, newval) -> {
            System.out.println(oldval);
            System.out.println(newval);

        });

        comboBox.setOnAction(e -> {
            Liburuak datuak=readFromUrl((String)comboBox.getValue());

            label.setText(datuak.toString());
        });


        HBox hbox = new HBox();
        hbox.getChildren().addAll(comboBox,label);
        hbox.setAlignment(Pos.BASELINE_CENTER);


        Scene scene = new Scene(hbox, 200, 120);
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    public static Liburuak readFromUrl(String isbn){
        isbn=isbn.toLowerCase();
        String inputLine="";

        try{
            URL isbnWeb= new URL("https://openlibrary.org/api/books?bibkeys=ISBN:"+isbn+"&jscmd=details&format=json");
            URLConnection iw= isbnWeb.openConnection();
            BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(iw.getInputStream()));
            new InputStreamReader(isbnWeb.openStream());

            inputLine=bufferedReader.readLine();
            bufferedReader.close();

        }
        catch(IOException e){
            e.printStackTrace();
        }
        Gson gson=new Gson();
        return gson.fromJson(inputLine,Liburuak.class);
    }

    public static void main(String[] args) {
        Application.launch(args);
    }
}
