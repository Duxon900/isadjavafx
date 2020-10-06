package ehu.isad;

import com.google.gson.Gson;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.StringConverter;

import javax.sound.sampled.LineUnavailableException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.List;

public class ComboBoxAdv extends Application {

    public class Liburuak{

        String ISBN;
        String info_url;
        String bib_key;
        String preview_url;
        String thumbnail_url;
        LiburuAtributu details;
        String preview;

        @Override
        public String toString() {
            return "Liburuak{" +
                    "info_url='" + info_url + '\'' +
                    ", bib_key='" + bib_key + '\'' +
                    ", preview_url='" + preview_url + '\'' +
                    ", thumbnail_url='" + thumbnail_url + '\'' +
                    ", details=" + details +
                    ", preview='" + preview + '\'' +
                    '}';
        }

    }


    @Override
    public void start(Stage primaryStage) throws Exception {

        primaryStage.setTitle("OpenLibrary APIa aztertzen");

        //Combobox
        ComboBox comboBox = new ComboBox();
        comboBox.getItems().addAll(
                "Blockchain: Blueprint for a New Economy",
                "R for Data Science",
                "Fluent Python",
                "Natural Language Processing with PyTorch",
                "Data Algorithms"
        );
        comboBox.setEditable(false);


        //HashMap liburuekin
        HashMap<String,String> liburuLista=new HashMap<>();  //Key , value

        liburuLista.put("Blockchain: Blueprint for a New Economy","9781491920497");
        liburuLista.put("R for Data Science","1491910399");
        liburuLista.put("Fluent Python","1491946008");
        liburuLista.put("Natural Language Processing with PyTorch","1491978236");
        liburuLista.put("Data Algorithms","9781491906187");

        //Testua
        Text text = new Text();
        text.wrappingWidthProperty().set(400);


        comboBox.valueProperty().addListener((obs, oldval, newval) -> {
            System.out.println(oldval);
            System.out.println(newval);

        });

        comboBox.setOnAction(e -> {
            System.out.println(comboBox.getValue());
            String titulua=(String)comboBox.getValue();


            Liburuak datuak=readFromUrl(liburuLista.get(titulua));

            text.setText("Idazlea(k): "+datuak.details.getPublishers()+
                    "\nOrri kopurua: "+datuak.details.getNumber_of_pages()+
                    "\nIzenburua: "+datuak.details.getTitle());
        });


        //HBox eratzen
        VBox vbox = new VBox();
        vbox.getChildren().addAll(comboBox,text);
        vbox.setAlignment(Pos.BASELINE_CENTER);
        vbox.setPadding(new Insets(50,0,50,0));


        Scene scene = new Scene(vbox, 500, 400);
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
            inputLine=bufferedReader.readLine();
            bufferedReader.close();

            String[] zatitu;//{"ISBN:9781491906187":
            zatitu = inputLine.split("\"ISBN:"+isbn+"\": "); //{ jartzen bada errorea ematen du

            inputLine=zatitu[1].substring(0,zatitu[1].length()-1);
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
