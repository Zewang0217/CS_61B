package byog.Core;

import java.io.*;

/**
 * 坐标点
 */
public class Point implements Serializable {
    private static final long serialVersionUID = 1L;
    public int x;
    public int y;

    // 构造函数
    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }
    // 重写的equals函数
    @Override
    public boolean equals(Object p) {
        if (this == p) return true;

        if (p == null) return false;

        if (getClass() != p.getClass()) {
            return false;
        }
        Point p1 = (Point) p;
        return ((this.x == p1.x) && (this.y == p1.y));
    }

    // 重写的hashCode函数（Java 规定必须同时重写 equals() 和 hashCode()）
    @Override
    public int hashCode() {
        return (this.x * 31 + this.y);
    }

    // 重写一个打印坐标的方法（未使用）
    @Override
    public String toString() {
        return "Point (" + x + ", " + y + ")";
    }
}
