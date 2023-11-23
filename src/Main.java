import java.util.Random;
import java.util.Scanner;
class Player{
    int upgradeCost = 50;
    int x = 50;
    int y = 10;
    int score = 0;
    String sprite = "&";
    int movesLeft = 100;
    int maxMoves = 100;
}
class Tile{
    String sprite = "-";
    int scoreToGive = 0;
}

class Stone extends Tile{
    Stone(){
        sprite = "■";
    }
}

class Coal extends Tile{
    Coal(){
        sprite = "c";
        scoreToGive = 1;
    }
}

class Iron extends Tile{
    Iron(){
        sprite = "i";
        scoreToGive = 4;
    }
}

class Ladder extends Tile{
    Ladder(){
        sprite = "=";
    }
}

public class Main {
    static Random rng = new Random();
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int height = 100;
        int width = 100;
        Player player = new Player();
        Tile[][] map = GenerateMap(width, height);

        boolean[][] revealedMap = new boolean[width][height];
        char input = 'L';

        while (input != 'q'){
            revealAroundPlayer(revealedMap, player);
            String screen = getMap(player, height, map, revealedMap);
            System.out.println(screen);
            System.out.println("score: " + player.score + ". moves left: " + player.movesLeft);
            if (player.y <= 10){
                System.out.println("Press b to upgrade by paying " + player.upgradeCost + " score");
            }
            // ------------------------------------------------ input ------------------------------------
            input = scanner.next().charAt(0);
            switch (input){
                case 'a':
                    if (player.x > 0){
                        player.x --;
                    }
                    break;
                case 'd':
                    if (player.x < width - 1){
                        player.x ++;
                    }
                    break;
                case 's':
                    if (player.y < height - 1){
                        player.y ++;
                    }
                    break;
                case 'w':
                    if (player.y > 0){
                        player.y --;
                    }
                    break;
                case 'e':
                    map[player.x][player.y] = new Ladder();
                    break;
            }
            player.score += map[player.x][player.y].scoreToGive;
            if (!map[player.x][player.y].getClass().getName().equals("Ladder")){
                map[player.x][player.y] = new Tile();
            }
            player.movesLeft --;
            if (player.y <= 10){
                player.movesLeft = player.maxMoves;
            }
            if (player.movesLeft <= 0){
                break;
            }
            // gravity
            if (player.y < height - 2 && map[player.x][player.y + 1].getClass().getName().equals("Tile") && map[player.x][player.y + 2].getClass().getName().equals("Tile")){
                player.y ++;
            }
        }
        System.out.println("dead :(");
        scanner.close();
    }


    private static String getMap(Player player, int height, Tile[][] map, boolean[][] revealedMap) {
        String screen = "";
        // ----------------------------------- setup screen ----------------------
        if (player.y > 5 && player.y < height - 5){
            for (int y = player.y - 5; y < player.y + 5; y++){
                for (int x = player.x - 10; x < player.x + 10; x++){
                    if (x == player.x && y == player.y){
                        screen = screen.concat(player.sprite);
                    }
                    else if (revealedMap[x][y]){
                        screen = screen.concat(map[x][y].sprite);
                    }
                    else {
                        screen = screen.concat("?");
                    }
                }
                screen = screen.concat("\n");
            }
        } else if (player.y > height - 11) {
            for (int y = height - 11; y < height - 1; y++){
                for (int x = player.x - 10; x < player.x + 10; x++){
                    if (x == player.x && y == player.y){
                        screen = screen.concat(player.sprite);
                    }
                    else if (revealedMap[x][y]){
                        screen = screen.concat(map[x][y].sprite);
                    }
                    else {
                        screen = screen.concat("?");
                    }
                }
                screen = screen.concat("\n");
            }
        } else{
            for (int y = 0; y < 10; y++){
                for (int x = player.x - 10; x < player.x + 10; x++){
                    if (x == player.x && y == player.y){
                        screen = screen.concat(player.sprite);
                    }
                    else if (revealedMap[x][y]){
                        screen = screen.concat(map[x][y].sprite);
                    }
                    else {
                        screen = screen.concat("?");
                    }
                }
                screen = screen.concat("\n");
            }
        }
        return screen;
    }

    private static void revealAroundPlayer(boolean[][] revealedMap, Player player){
        for (int x = player.x - 3; x <= player.x + 3; x++){
            for (int y = player.y - 2; y <= player.y + 2; y++){
                revealedMap[x][y] = true;
            }
        }
    }
    private static Tile[][] GenerateMap(int width, int height){
        Tile[][] tempMap = new Tile[width][height];
        for (int x = 0; x < width; x++){
            for (int y = 0; y < height; y++){
                tempMap[x][y] = new Tile();
                if (y>10){
                    if (rng.nextInt(100) < 20) {
                        tempMap[x][y] = new Coal();
                    }
                    else if (y > 30 && rng.nextInt(100) < 20){
                        tempMap[x][y] = new Iron();
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