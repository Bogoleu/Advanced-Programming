public class Main {
    public static void main(String[] args) {
        Repository repository = new Repository();


        repository.AddImage(new Image("Image1", "C:\\Users\\popu_\\Desktop\\images\\download.jpg"));
        repository.AddImage(new Image("Image2", "C:\\Users\\popu_\\Desktop\\images\\download (1).jpg"));
        repository.AddImage(new Image("Image3", "C:\\Users\\popu_\\Desktop\\images\\download (2).jpg"));
        repository.AddImage(new Image("Image4", "C:\\Users\\popu_\\Desktop\\images\\download (3).jpg"));
        repository.AddImage(new Image("Image5", "C:\\Users\\popu_\\Desktop\\images\\download (4).jpg"));



        Repository.DisplayImage("Image4");
    }
}