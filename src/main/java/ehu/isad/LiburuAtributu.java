package ehu.isad;

import java.util.Arrays;

public class LiburuAtributu {
    String[] publishers;
    String title;
    int number_of_pages;

    public LiburuAtributu(String titleA,int number_of_pagesA){
        this.number_of_pages=number_of_pagesA;
        this.title=titleA;
    }

    @Override
    public String toString() {
        return "LiburuAtributu{" +
                "publishers=" + Arrays.toString(publishers) +
                ", title='" + title + '\'' +
                ", number_of_pages=" + number_of_pages +
                '}';
    }

    public int getNumber_of_pages(){
        return number_of_pages;
    }

    public String getTitle(){
        return title;
    }

    public String getPublishers(){
        return Arrays.toString(publishers);
    }

}
