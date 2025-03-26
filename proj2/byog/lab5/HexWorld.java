package byog.lab5;

import byog.TileEngine.TETile;
import byog.TileEngine.TERenderer;
import byog.TileEngine.Tileset;
import java.util.Random;

/**
 * Draws a world consisting of hexagonal regions.
 */
public class HexWorld {
    private static final int WIDTH = 30;
    private static final int HEIGHT = 30;

    private static final Random RANDOM = new Random(2873123);
    /**
     * 存放位置
     */
    public static class Position {
        int x;
        int y;
        public Position(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    /**
     *   ***
     *  *****
     * *******
     * ******* <----每一行最右边的位置
     *  *****
     *   ***
     * @param p 右边的起始位置
     * @param tile 用于加颜色
     * @param width 这一行的宽度
     * @param world 世界
     */
    private static void addOneRow(TETile[][] world, Position p, TETile tile, int width) {
        int x = p.x - width + 1;
        int y = p.y;

        for (int i = 0; i < width; i++) {
            if (x >= 0 && x < WIDTH && y >= 0 && y < HEIGHT) {
                world[x + i][y] = tile;
            }
        }
    }

    /**
     *
     *   *** <--topRightCorner
     *  *****
     * ******* <--- 上半部分结束再最右边位置
     * ******* <---这一行最左边，返回leftCorner
     *  *****
     *   ***
     * @param world 世界
     * @param p 上一个六边形的leftCorner
     * @param s 六边形大小
     * @param t TETile类
     * @return 返回生成的六边形的leftCorner
     */
    private static Position addHexagon(TETile[][] world, Position p, int s, TETile t) {
        // 从topRightCorner开始
        Position currentPosition = new Position(p.x - 1, p.y);
        // 上半部分
        for (int col = 0; col < s; col++) {
            addOneRow(world, currentPosition, t, s + col * 2);
            currentPosition.y--;
            if (col == s - 1) continue;
            currentPosition.x++;
        }
        // 返回leftCorner
        Position leftCorner = new Position(currentPosition.x - 3 * s + 3, currentPosition.y);
        // 下半部分
        for (int col = s; col > 0; col--) {
            addOneRow(world, currentPosition, t, s + (col - 1) * 2);
            if (col == 1) continue;
            currentPosition.x--;
            currentPosition.y--;
        }

        return leftCorner;
    }

    /**
     * 提供上一个六边形的topRightCorner，计算右下角的六边形的topRightCorner
     * @param p 上一个六边形的topRightCorner
     * @param s 六边形大小
     * @return 右下角的六边形的topRightCorner
     */
    private static Position getPosOfRightDown(Position p, int s) {
        Position returnPosition = new Position(p.x + 2 * s - 1, p.y - s);
        return returnPosition;
    }

    private static Position getPosOfDown(Position p, int s) {
        Position returnPosition = new Position(p.x, p.y - 2 * s);
        return returnPosition;
    }

    /**
     *
     * @param world 世界
     * @param px 右上角位置
     * @param py 右上角位置
     * @param s 六边形大小
     * @param t 用于绘制
     */
    public static void getTheHexWorld(TETile[][] world, int px, int py, int s, TETile t) {
        Position p = new Position(px, py); // 右上角
        Position currentPosition = new Position(p.x, p.y);
        Position roundHead = new Position(p.x, p.y);
        t = Tileset.FLOWER;
        // 每一轮之前找好下一轮初始位置
        roundHead = getPosOfRightDown(roundHead, s);
        // 一轮绘制
        for (int i = 0; i < 3; i++) {
            currentPosition = addHexagon(world, currentPosition, s, t);
        }
        // 回到下一轮初始位置
        currentPosition = roundHead;

        t = Tileset.TREE;
        roundHead = getPosOfRightDown(roundHead, s);
        for (int i = 0; i < 4; i++) {
            currentPosition = addHexagon(world, currentPosition, s, t);
        }
        currentPosition = roundHead;

        t = Tileset.MOUNTAIN;
        roundHead = getPosOfDown(roundHead, s);
        for (int i = 0; i < 5; i++) {
            currentPosition = addHexagon(world, currentPosition, s, t);
        }
        currentPosition = roundHead;

        t = Tileset.SAND;
        roundHead = getPosOfDown(roundHead, s);
        for (int i = 0; i < 4; i++) {
            currentPosition = addHexagon(world, currentPosition, s, t);
        }
        currentPosition = roundHead;

        t = Tileset.GRASS;
        for (int i = 0; i < 3; i++) {
            currentPosition = addHexagon(world, currentPosition, s, t);
        }

    }

    public static void main(String[] args) {
        TERenderer ter = new TERenderer();
        ter.initialize(WIDTH, HEIGHT);

        TETile[][] world = new TETile[WIDTH][HEIGHT];

        for (int x = 0; x < WIDTH; x += 1) {
            for (int y = 0; y < HEIGHT; y += 1) {
                world[x][y] = Tileset.NOTHING;
            }
        }

        TETile t = null;
        getTheHexWorld(world, 17, 30, 3, t);

        ter.renderFrame(world);
    }
}
