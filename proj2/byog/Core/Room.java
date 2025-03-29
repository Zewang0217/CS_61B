package byog.Core;

/**
 * 房间
 */
public class Room {
    public int x1;
    public int y1;
    public int x2;
    public int y2;

    public int w;
    public int h;

    // 用于计算房间的中心位置，以便存放玩家以及门
    public Point center;

    /**
     * 房间生成
     *
     * @param x 起始点x
     * @param y 起始点y
     * @param w 房间宽度
     * @param h 房间高度
     */
    public Room(int x, int y, int w, int h) {
        this.x1 = x;
        this.x2 = x + w;
        this.y1 = y;
        this.y2 = y + h;

        this.center = new Point((int)Math.floor((x1 + x2) / 2), (int)Math.floor((y1 + y2) / 2));
    }
}
