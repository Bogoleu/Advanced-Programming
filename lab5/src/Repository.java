import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Repository {
    private static final List<Image> images = new ArrayList<>();

    public void AddImage(Image image){
        images.add(image);
    }
    public static void DisplayImage(String name){
        for(Image image:images)
        {
            if(image.name().equals(name))
            {
                openImage(image.path());
                return;
            }
        }
        System.out.println("Imaginea nu a fost gasita");
    }
    public static void openImage(String path){
        File imagefile = new File(path);
        if(!imagefile.exists()){
            System.out.println("Imaginea nu exista");
            return;
        }
        if(Desktop.isDesktopSupported()){
            Desktop desktop = Desktop.getDesktop();
            try{
                desktop.open(imagefile);

            } catch (IOException e) {
                System.out.println("Eroare la deschiderea imaginii");
            }
        }
        else
        System.out.println("Eroare Desktop");

    }
}
