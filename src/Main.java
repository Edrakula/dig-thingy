import java.util.Random;
import java.util.Scanner;
class Player{
    int x = 50;
    int y = 10;
    int score = 0;
}
class Tile{
    String sprite = " ";
    int scoreToGive = 0;

}

class Stone extends Tile{
    Stone(){
        sprite = ".";
    }
}

class Coal extends Tile{
    Coal(){
        sprite = "c";
    }
}

public class Main {
    static Random rng = new Random();
    public static void main(String[] args) {
        // Press Alt+Enter with your caret at the highlighted text to see how
        // IntelliJ IDEA suggests fixing it.
        Scanner scanner = new Scanner(System.in);
        System.out.println("Hello and welcome!");

        Tile[][] map = GenerateMap(100, 100);

        char input = 'L';
        Player player = new Player();

        while (input != 'q'){
            String screen = "";
            for (int y = player.y - 5; y < player.y + 5; y++){
                for (int x = player.x - 10; x < player.x + 10; x++){
                    screen = screen.concat(map[x][y].sprite);
                }
                screen = screen.concat("\n");
            }
            System.out.println(screen);
            input = scanner.next().charAt(0);
            switch (input){
                case 'a':
                    player.x --;
                    break;
                case 'd':
                    player.x ++;
                    break;
            }
        }

        scanner.close();
    }

    static Tile[][] GenerateMap(int width, int height){
        Tile[][] tempMap = new Tile[width][height];
        for (int x = 0; x < width; x++){
            for (int y = 0; y < height; y++){
                tempMap[x][y] = new Tile();
                if (y>10){
                    if (rng.nextInt(100) < 20){
                        tempMap[x][y] = new Coal();
                    }
                    else {
                        tempMap[x][y] = new Stone();
                    }

                }
            }
        }
        return tempMap;
    }
}