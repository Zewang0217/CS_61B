package byog.Core;

/**
 * 过道
 */
public class corridors {
    private static final int MAP_WIDTH = 72;
    private static final int MAP_HEIGHT = 30;
    // 用于存放是否是过道，防止重复渲染
    public static boolean[][] arr = new boolean[MAP_WIDTH][MAP_HEIGHT];

    public corridors() {
        for (int x = 0; x < MAP_WIDTH; x++) {
            for (int y = 0; y < MAP_HEIGHT; y++) {
                arr[x][y] = false;
            }
        }
    }

    // 横向过道
    public class hCorridor{
        public hCorridor(int x1, int x2, int y) {
            for (int x = Math.min(x1, x2); x <= Math.max(x1, x2); x++) {
                arr[x][y] = true;
            }
        }
    }

    // 纵向过道
    public class vCorridor{
        public vCorridor(int y1, int y2, int x) {
            for (int y = Math.min(y1, y2); y <= Math.max(y1, y2); y++) {
                arr[x][y] = true;
            }
        }
    }
}
