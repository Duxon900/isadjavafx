package ehu.isad;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Argazki extends Application{
    String animaliaIzena;
    String argazkiIzena;

    public Argazki(String animaliaIzenaS, String argazkiIzenaS) {
        this.animaliaIzena=animaliaIzenaS;
        this.argazkiIzena=argazkiIzenaS;
    }

    public String getFitx() {
        return this.argazkiIzena;
    }

    public String getAnimaliaIzena(){
        return this.animaliaIzena;
    }

    @Override
    public String toString() {
        return animaliaIzena;
    }



    @Override
    public void start(Stage primaryStage) throws Exception {

        primaryStage.setTitle("Lista Argazkiak");

        //ImageView berria
        ImageView imageView = new ImageView();

        //abereen comboBox
        ComboBox comboBoxAbere=new ComboBox();
        List<String> listaEzIkusi=List.of("abereak","landareak","frutak");
        ObservableList listaIkusi= FXCollections.observableArrayList(listaEzIkusi);

        comboBoxAbere.setItems(listaIkusi);
        comboBoxAbere.getSelectionModel().selectFirst();

        //VBox ikusi
        VBox vBox=new VBox(comboBoxAbere);


        //HashMap prestatu
        Map<String, List<Argazki>> bildumaMap = new HashMap<>();



        //Abereak
        bildumaMap.put("abereak", List.of(
                new Argazki("Elefantea", "elefantea.jpeg"),
                new Argazki("Txakurra", "txakurra.jpeg"),
                new Argazki("Untxia", "untxia.png")
        ));

        //Landareak
        bildumaMap.put("landareak",List.of(
                new Argazki("Cactus","cactus.png"),
                new Argazki("Landare Berdea","landareberdea.jpeg"),
                new Argazki("Landare Horia","landarehoria.jpeg")
        ));

        //Frutak
        bildumaMap.put("frutak",List.of(
                new Argazki("Marrubi","fresa.jpeg"),
                new Argazki("Sagarra","sagarra.jpeg"),
                new Argazki("Sandia","sandia.png")
        ));

        ObservableList<Argazki> argazkiList = FXCollections.observableArrayList();
        argazkiList.addAll(bildumaMap.get("abereak"));

        ListView listViewOfArgazki = new ListView<>(argazkiList);


        comboBoxAbere.setOnAction(event -> {
            argazkiList.clear();
            argazkiList.addAll(bildumaMap.get(comboBoxAbere.getValue()));
            listViewOfArgazki.refresh();

        });

        listViewLandu(imageView, listViewOfArgazki);

        vBox.getChildren().addAll(listViewOfArgazki,imageView);

        Scene scene = new Scene(vBox,500,500);
        primaryStage.setScene(scene);
        primaryStage.show();

    }


    private void listViewLandu(ImageView imageView, ListView listViewOfArgazki) {
        listViewOfArgazki.getSelectionModel().selectedItemProperty().addListener(  (observable, oldValue, newValue) -> {
            if (observable.getValue() == null) return;


            Argazki argazkiLag=(Argazki)observable.getValue();
            String fitx=argazkiLag.getFitx();

            try {//create new imageView lortu irudia irudia lortzeko metodoa
                imageView.setImage(lortuIrudia(fitx));//48x48
            } catch (IOException e) {
                e.printStackTrace();
            }

        });
    }

    //unean hautatutako elementuaren irudia lortzeko
    private Image lortuIrudia(String location) throws IOException {
        InputStream is = getClass().getResourceAsStream("/" + location);
        BufferedImage reader = ImageIO.read(is);
        return SwingFXUtils.toFXImage(reader, null);
    }
}
