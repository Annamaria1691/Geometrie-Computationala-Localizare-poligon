import java.util.Comparator;

public class CadranComparator implements Comparator<Point> {
    public Point S;

    @Override
    public int compare(Point A, Point B) {
        if (A == null && B == null) {
            return 0; // Both are null, consider them equal
        }
        if (A == null) {
            return -1; // A is null, so B comes after A
        }
        if (B == null) {
            return 1; // B is null, so A comes after B
        }
        Cadran cadran = new Cadran();
        int cadranA = cadran.Cadran(A);
        int cadranB = cadran.Cadran(B);
        if (cadranA == cadranB) {
            int det = calculDeterminant(B, S, A);
            if (det == 0) return 0;
            return det > 0 ? -1 : 1;
        }
        return cadranA - cadranB;
    }

    public int calculDeterminant(Point a, Point b, Point c) {
        if (a == null || b == null || c == null) {
            return 0;
        }
        return (int) (((a.getX() * b.getY()) + (b.getX() * c.getY()) + (a.getY() * c.getX())) - (b.getY() * c.getX()) - (a.getY() * b.getX()) - (a.getX() * c.getY()));

    }
}
