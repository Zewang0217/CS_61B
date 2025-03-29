package byog.Core;

import byog.TileEngine.TETile;
import byog.TileEngine.TERenderer;
import byog.TileEngine.Tileset;
import java.util.Random;

/**
 * 生成房间的类
 */
public class placeRooms {
    private static final int minRoomSize = 3;
    private static final int maxRoomSize = 6;
    private static final int MAP_WIDTH = 72;
    private static final int MAP_HEIGHT = 30;
    private static final int maxRooms = 18;
    private static final int minRooms = 14;
    private static int ptr = 0;
    private static long SEED;
    private static Random RANDOM;
    public Point player1;
    public Point door;

    // 构造函数
    public placeRooms (long seed) {
        SEED = seed;
        RANDOM = new Random(SEED);
    }

    // 增加迷宫（房间）
    public void addMaze (TETile[][] world, TETile t) {
        Room[] rooms = new Room[maxRooms];
        // 随机生成房间数量
        int roomNumber = minRooms + (int)RANDOM.nextInt(maxRooms - minRooms + 1);
        // 生成每一个房间
        for (int z = 0; z < roomNumber; z++) {
            // 随机生成房间大小
            int w = minRoomSize + (int)RANDOM.nextInt(maxRoomSize - minRoomSize + 1);
            int h = minRoomSize + (int)RANDOM.nextInt(maxRoomSize - minRoomSize + 1);
            // 确定房间的位置（起始点或左下角）
            int x = (int)RANDOM.nextInt(MAP_WIDTH - w - 2) + 1;
            int y = (int)RANDOM.nextInt(MAP_HEIGHT - h - 3) + 1;
            // 生成新房间
            Room newRoom = new Room(x, y, w, h);

            // 第ptr个房间
            rooms[ptr] = newRoom;
            // 渲染FLOOR
            for (int i = rooms[ptr].x1; i <= rooms[ptr].x2; i++) {
                for (int j = rooms[ptr].y1; j <= rooms[ptr].y2; j++) {
                    world[i][j] = t;
                }
            }

            // 记录中心位置
            Point newCenter = newRoom.center;

            // 联通上一个房间与这个房间 （创造迷宫或过道）
            if (ptr != 0) {
                // 前一个房间的中心位置
                Point prevCenter = rooms[ptr - 1].center;
                corridors corridor = new corridors();
                if (RANDOM.nextInt(2) == 1) {
                    corridors.hCorridor hc = corridor.new hCorridor(prevCenter.x, newCenter.x, prevCenter.y);
                    corridors.vCorridor vc = corridor.new vCorridor(prevCenter.y, newCenter.y, newCenter.x);
                } else {
                    corridors.vCorridor vc = corridor.new vCorridor(prevCenter.y, newCenter.y, prevCenter.x);
                    corridors.hCorridor hc = corridor.new hCorridor(prevCenter.x, newCenter.x, newCenter.y);
                }

                for (int q = 0; q < MAP_WIDTH; q++) {
                    for (int a = 0; a < MAP_HEIGHT; a++) {
                        if (corridor.arr[q][a] == true) {
                            world[q][a] = t;
                        }
                    }
                }
            }
            ptr++;
        }
        world[rooms[5].center.x][rooms[5].center.y] = Tileset.PLAYER;
        world[rooms[7].center.x][rooms[7].center.y] = Tileset.LOCKED_DOOR;
        player1 = new Point (rooms[5].center.x, rooms[5].center.y);
        door = new Point (rooms[7].center.x, rooms[7].center.y);
    }

    /**
     * 生成墙壁辅助方法
     * 用于判断周边是否有地板（周围八格）
     * @param world 世界
     * @param x 坐标x
     * @param y 坐标y
     * @return 周边是否有地板（周围八格）
     */
    public boolean isAdjacentToFloor(TETile[][] world, int x, int y) {
      // 检查八个相邻的方向
      for (int i = -1; i <= 1; i++) {
          for (int j = -1; j <= 1; j++) {
              int nx = x + i;
              int ny = y + j;
              // 只要周围有地板就生成墙壁
              if (nx >= 0 && nx < MAP_WIDTH && ny >= 0 && ny < MAP_HEIGHT && world[nx][ny] == Tileset.FLOOR) {
                  return true;
              }
          }
      }
      return false;
    }

    /**
     * 添加墙壁
     * @param world 世界
     * @param t 渲染器
     */
    public void addWall(TETile[][] world, TETile t) {
        for (int i = 0; i < MAP_WIDTH; i++) {
            for (int j = 0; j < MAP_HEIGHT; j++) {
                if (world[i][j] == Tileset.NOTHING && isAdjacentToFloor(world, i, j)) {
                    world[i][j] = t;
                }
            }
        }
    }
}
