package byog.Core;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;
import edu.princeton.cs.introcs.StdDraw;
import java.awt.Color;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Random;

public class Game {
    /* Feel free to change the width and height. */
    public static final int WIDTH = 72;
    public static final int HEIGHT = 30;

    private static int MouseX;
    private static int MouseY;

    public TERenderer ter = new TERenderer();
    public Point player;
    public Point door;
    public boolean win = false;
    public life LIFE;
    public static TETile[][] finalWorldFrame;

    /**
     * Method used for playing a fresh game. The game should start from the main menu.
     */
    public void playWithKeyboard() {
    }

    /**
     * Method used for autograding and testing the game code. The input string will be a series
     * of characters (for example, "n123sswwdasdassadwas", "n123sss:q", "lwww". The game should
     * behave exactly as if the user typed these characters into the game after playing
     * playWithKeyboard. If the string ends in ":q", the same world should be returned as if the
     * string did not end with q. For example "n123sss" and "n123sss:q" should return the same
     * world. However, the behavior is slightly different. After playing with "n123sss:q", the game
     * should save, and thus if we then called playWithInputString with the string "l", we'd expect
     * to get the exact same world back again, since this corresponds to loading the saved game.
     * @param input the input string to feed to your program
     * @return the 2D TETile[][] representing the state of the world
     */
    public TETile[][] playWithInputString(String input) {
        // TODO: Fill out this method to run the game using the input passed in,
        // and return a 2D tile representation of the world that would have been
        // drawn if the same inputs had been given to playWithKeyboard().

        finalWorldFrame = new TETile[WIDTH][HEIGHT];
        ter.initialize(WIDTH, HEIGHT);

        long seed = toDigit(input);

        // 初始化世界
        for (int x = 0; x < WIDTH; x++) {
            for (int y = 0; y < HEIGHT; y++) {
                finalWorldFrame[x][y] = Tileset.NOTHING;
            }
        }

        // 开始渲染地图
        ter.renderFrame(finalWorldFrame);
        // 渲染房间
        placeRooms PLACEROOMS = new placeRooms(seed);
        TETile t = Tileset.FLOOR;
        // 渲染过道（迷宫）
        PLACEROOMS.addMaze(finalWorldFrame, t);
        t = Tileset.WALL;
        PLACEROOMS.addWall(finalWorldFrame, t);
        player = PLACEROOMS.player1;
        door = PLACEROOMS.door;
        LIFE = new life(5);
        finalWorldFrame = drawLife(finalWorldFrame, LIFE.x);
        ter.renderFrame(finalWorldFrame);
        operation();
        return finalWorldFrame;

    }

    public void operation() {
        while (true) {
            if (StdDraw.hasNextKeyTyped()) {d
                // 保存进度
                if (c == 'Q') {
                    String filename = "file.ser";
                    String filename1 = "file1.ser";
                    String filename2 = "file2.ser";
                    String filename3 = "file3.ser";

                    // Serialization
                    try {
                        FileOutputStream file = new FileOutputStream(filename);
                        ObjectOutputStream out = new ObjectOutputStream(file);
                        FileOutputStream file1 = new FileOutputStream(filename1);
                        ObjectOutputStream out1 = new ObjectOutputStream(file1);
                        FileOutputStream file2 = new FileOutputStream(filename2);
                        ObjectOutputStream out2 = new ObjectOutputStream(file2);
                        FileOutputStream file3 = new FileOutputStream(filename3);
                        ObjectOutputStream out3 = new ObjectOutputStream(file3);

                        out.writeObject(finalWorldFrame);
                        out1.writeObject(player);
                        out2.writeObject(door);
                        out3.writeObject(LIFE);

                        out.close();
                        file.close();
                        out1.close();
                        file1.close();
                        out2.close();
                        file2.close();
                        out3.close();
                        file3.close();

                        System.out.println("Object had been serialized");
                    } catch (IOException ex) {
                        System.out.println("IOException in caught");
                    }
                    break;
                }
                finalWorldFrame = playerMove(finalWorldFrame, c);
                finalWorldFrame = drawLife(finalWorldFrame, LIFE.x);
                ter.renderFrame(finalWorldFrame);

                if (win) {
                    System.exit(0);
                } else if (LIFE.x == 0) {
                    System.exit(0);
                }
                try
                {
                    Thread.sleep(10);
                }
                catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                }
            }

            if (StdDraw.isMousePressed()) {
                MouseX = (int) StdDraw.mouseX();
                MouseY = (int) StdDraw.mouseY();
                String des = finalWorldFrame[MouseX][MouseY].description();
                finalWorldFrame = drawHUD(finalWorldFrame, des);
                ter.renderFrame(finalWorldFrame);

                try
                {
                    Thread.sleep(1000);
                }
                catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }

    /**
     * Convert the string input into a `long` output (seed).
     *
     * @param input 输入
     * @return seed long
     */
    public long toDigit(String input) {
        char[] c = input.toCharArray();
        int count = 0, length = input.length();

        // 获得数组长度
        for (int i = 0; i < length; i++) {
            if (Character.isDigit(c[i])) {
                count++;
            }
        }

        // 创建arr用于存放数字构成的seed
        int[] arr = new int[count];
        count = 0;
        for (int i = 0; i < length; i++) {
            if (Character.isDigit(c[i])) {
                arr[count] = c[i] - '0';
                count++;
            }
        }

        // 将int[] arr 中的元素拼接成一个字符串，然后转换为long类型的数值
        // StringBuilder用于高效拼接字符串
        StringBuilder sb = new StringBuilder();
        for (int i : arr) {
            sb.append(i);
        }

        long requestLong = Long.parseLong(sb.toString());
        return requestLong;
    }

    /**
     *
     * @param finalWorldFrame 世界
     * @param des 目标
     * @return 世界
     */
    public TETile[][] drawHUD(TETile[][] finalWorldFrame, String des) {
        for (int i = 0; i < 15; i++) {
            finalWorldFrame[i][HEIGHT - 1] = Tileset.NOTHING;
        }
        for (int i = 0; i < des.length(); i++) {
            finalWorldFrame[i][HEIGHT - 1] = new TETile (des.toCharArray()[i], Color.white, Color.black, "nothing");
        }
        return finalWorldFrame;
    }

    /**
     * 渲染生物
     *
     * @param finalWorldFrame 世界
     * @param life 生物数量
     * @return 渲染后世界
     */
    public TETile[][] drawLife(TETile[][] finalWorldFrame, int life) {
        for (int i = 60; i < 70; i++) {
            finalWorldFrame[i][HEIGHT - 1] = Tileset.NOTHING;
        }
        for (int i = 60; i < 60 + life; i++) {
            finalWorldFrame[i][HEIGHT - 1] = Tileset.FLOWER;
        }
        return finalWorldFrame;
    }

    /**
     * 用于玩家的移动
     *
     * @param finalWorldFrame 世界
     * @param c 操作
     * @return 玩家移动后的世界
     */
    public TETile[][] playerMove(TETile[][] finalWorldFrame, char c) {
        TETile t = Tileset.WALL;
        if (c == 'w') {
            if (!finalWorldFrame[player.x][player.y + 1].equals(t)) {
                finalWorldFrame[player.x][player.y] = Tileset.FLOOR;
                finalWorldFrame[player.x][player.y + 1] = Tileset.PLAYER;
                player = new Point(player.x, player.y + 1);
                if (player.equals(door)) {
                    win = true;
                }
            } else {
                LIFE.x--;
            }
            return finalWorldFrame;
        }else if (c == 'a'){
            if (!finalWorldFrame[player.x-1][player.y].equals(t)){
                finalWorldFrame[player.x][player.y] = Tileset.FLOOR;
                finalWorldFrame[player.x-1][player.y] = Tileset.PLAYER;
                player = new Point(player.x-1, player.y);
                if (player.equals(door)){
                    win = true;
                }
            } else{
                LIFE.x--;
            }
            return finalWorldFrame;
        }else if (c == 's'){
            if (!finalWorldFrame[player.x][player.y-1].equals(t)){
                finalWorldFrame[player.x][player.y] = Tileset.FLOOR;
                finalWorldFrame[player.x][player.y-1] = Tileset.PLAYER;
                player = new Point(player.x, player.y-1);
                if (player.equals(door)){
                    win = true;
                }
            }else{
                LIFE.x--;
            }
            return finalWorldFrame;
        }else if (c == 'd'){
            if (!finalWorldFrame[player.x+1][player.y].equals(t)){
                finalWorldFrame[player.x][player.y] = Tileset.FLOOR;
                finalWorldFrame[player.x+1][player.y] = Tileset.PLAYER;
                player = new Point(player.x+1, player.y);
                if (player.equals(door)){
                    win = true;
                }
            }else{
                LIFE.x--;
            }
            return finalWorldFrame;
        }else {
            return finalWorldFrame;
        }
    }
}
