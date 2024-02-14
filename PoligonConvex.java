import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.annotations.XYTextAnnotation;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.DefaultXYDataset;
import org.jfree.ui.ApplicationFrame;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.text.DecimalFormat;
import java.util.Arrays;

public class PoligonConvex extends ApplicationFrame {


    private Point M;

    private Point[] listaPuncte;


    public PoligonConvex() {
        super("Poligon convex");
        listaPuncte = new Point[6];
        listaPuncte[0] = new Point(85, 36);//A
        listaPuncte[1] = new Point(47, 52);//B
        listaPuncte[2] = new Point(-15, 46);//C
        listaPuncte[3] = new Point(-48, 25);//D
        listaPuncte[4] = new Point(-37, -10);//E
        listaPuncte[5] = new Point(45, -15);//F

        M = new Point(26, 50);

        Point s = centrulDeGreutate(listaPuncte);
        DecimalFormat df = new DecimalFormat();
        df.setMaximumFractionDigits(2);
        System.out.println("Punctul S x:"+df.format(s.getX()) + " y:" + df.format(s.getY()));

        translatiePuncte(listaPuncte, s);

        for (int i = 0; i < listaPuncte.length; i++) {
            System.out.println("Punctele dupa translatie x: " + listaPuncte[i].getX() + "y " + listaPuncte[i].getY());
        }

        Arrays.sort(listaPuncte, new CadranComparator());
        for (int i = 0; i < listaPuncte.length; i++)
            System.out.println("Punctele dupa ordonare: x: " + listaPuncte[i].getX() + " y:" + listaPuncte[i].getY());


        translatiePunct(M, s);
        System.out.println("Punctul M dupa translatie x: " + df.format(M.getX()) + " y:" + df.format(M.getY()));


        Point ss = centrulDeGreutate(listaPuncte);


        DefaultXYDataset punctS = new DefaultXYDataset();
        double coordonateS[][] = {{ss.getX()}, {ss.getY()}};
        punctS.addSeries("S", coordonateS);

        DefaultXYDataset punctM = new DefaultXYDataset();
        double coordonateM[][] = {{M.getX()}, {M.getY()}};
        punctM.addSeries("M", coordonateM);


        double[][] polygonData = new double[2][7];
        for (int i = 0; i < listaPuncte.length; i++) {
            polygonData[0][i] = listaPuncte[i].getX();
            polygonData[1][i] = listaPuncte[i].getY();
        }
        polygonData[0][6] = listaPuncte[0].getX();
        polygonData[1][6] = listaPuncte[0].getY();

        DefaultXYDataset polygonDataset = new DefaultXYDataset();


        polygonDataset.addSeries("Punctele translatate", polygonData);
        polygonDataset.addSeries("S (Centrul Poligonului)", coordonateS);
        polygonDataset.addSeries("M", coordonateM);


        JFreeChart chart = ChartFactory.createXYLineChart(
                " ",
                "Axa-X",
                "Axa-Y",
                polygonDataset,   // Dataset
                PlotOrientation.VERTICAL,
                true, true, false
        );

        XYPlot plot = (XYPlot) chart.getPlot();
        chart.getXYPlot().setDataset(0, polygonDataset);
        plot.setBackgroundPaint(Color.WHITE);

        // Adaugare axa X
        plot.setDomainGridlinesVisible(true);
        plot.setDomainGridlinePaint(Color.BLACK);
        plot.setDomainZeroBaselineVisible(true);
        plot.setDomainZeroBaselinePaint(Color.BLUE);

        // Adaugarea axa y
        plot.setRangeGridlinesVisible(true);
        plot.setRangeGridlinePaint(Color.BLACK);
        plot.setRangeZeroBaselineVisible(true);
        plot.setRangeZeroBaselinePaint(Color.BLUE);

        //design puncte poligon
        XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
        renderer.setSeriesShape(0, new Ellipse2D.Double(-3, -3, 5, 5));
        renderer.setSeriesPaint(0, Color.RED);
        //design punct M
        XYLineAndShapeRenderer mRenderer = new XYLineAndShapeRenderer();
        mRenderer.setSeriesShape(0, new Ellipse2D.Double(-5, -5, 7, 7));
        mRenderer.setSeriesPaint(0, Color.green);
        //design punct S
        XYLineAndShapeRenderer sRenderer = new XYLineAndShapeRenderer();
        sRenderer.setSeriesShape(0, new Ellipse2D.Double(-5, -5, 7, 7));
        sRenderer.setSeriesPaint(0, Color.ORANGE);

        plot.setRenderer(0, renderer);
        plot.setRenderer(1, mRenderer);
        plot.setRenderer(2, sRenderer);

        XYTextAnnotation labelM = new XYTextAnnotation("M", M.getX() + 3, M.getY() + 2);
        labelM.setFont(new Font("SansSerif", Font.PLAIN, 12));
        plot.addAnnotation(labelM);


        XYTextAnnotation labelS = new XYTextAnnotation("S ", ss.getX() - 4, ss.getY() + 3);
        labelS.setFont(new Font("SansSerif", Font.PLAIN, 12));
        plot.addAnnotation(labelS);

        XYTextAnnotation labelA = new XYTextAnnotation("A", polygonData[0][0], polygonData[1][0] + 4);
        labelA.setFont(new Font("SansSerif", Font.PLAIN, 14));
        plot.addAnnotation(labelA);

        XYTextAnnotation labelB = new XYTextAnnotation("B", polygonData[0][1] + 3, polygonData[1][1] + 2);
        labelB.setFont(new Font("SansSerif", Font.PLAIN, 14));
        plot.addAnnotation(labelB);

        XYTextAnnotation labelC = new XYTextAnnotation("C", polygonData[0][2] - 3, polygonData[1][2] + 1);
        labelC.setFont(new Font("SansSerif", Font.PLAIN, 14));
        plot.addAnnotation(labelC);

        XYTextAnnotation labelD = new XYTextAnnotation("D", polygonData[0][3] - 3, polygonData[1][3]);
        labelD.setFont(new Font("SansSerif", Font.PLAIN, 14));
        plot.addAnnotation(labelD);

        XYTextAnnotation labelE = new XYTextAnnotation("E", polygonData[0][4] + 2, polygonData[1][4] - 2);
        labelE.setFont(new Font("SansSerif", Font.PLAIN, 14));
        plot.addAnnotation(labelE);

        XYTextAnnotation labelF = new XYTextAnnotation("F", polygonData[0][5] + 2, polygonData[1][5] - 2);
        labelF.setFont(new Font("SansSerif", Font.PLAIN, 14));
        plot.addAnnotation(labelF);

        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new java.awt.Dimension(560, 370));
        setContentPane(chartPanel);

        int rezultat = cautareBinara(listaPuncte, M);
        if (rezultat > 0) {
            chart.setTitle("M se afla in interiorul poligonului");
        } else if (rezultat < 0) {
            chart.setTitle("M se afla in exteriorul poligonului");
        } else {
            chart.setTitle("M se afla pe frontiera poligonului");
        }

    }


    public int calculDeterminant(Point a, Point b, Point c) {
        return (int) (((a.getX() * b.getY()) + (b.getX() * c.getY()) + (a.getY() * c.getX())) - (b.getY() * c.getX()) - (a.getY() * b.getX()) - (a.getX() * c.getY()));
    }

  public Point centrulDeGreutate(Point[] listaPuncte) {
        int nrPuncte = listaPuncte.length;
        double sumX = 0;
        double sumY = 0;
        for (Point point : listaPuncte) {
            sumX += point.getX();
            sumY += point.getY();
        }
        return new Point(sumX / nrPuncte, sumY / nrPuncte);
    }






    public void translatiePuncte(Point[] listaPuncte, Point S) {
        for (Point point : listaPuncte) {
            point.setX(point.getX() - S.getX());
            point.setY(point.getY() - S.getY());
        }
    }



    public void translatiePunct(Point A, Point S) {
        A.setX(A.getX() - S.getX());
        A.setY(A.getY() - S.getY());
    }

    public int cautareBinara(Point[] listaPuncte, Point M) {
        int stg = 0;
        int dr = listaPuncte.length - 1;
        int determinant=0;
        while (stg <= dr) {
            int mij = (stg + dr) / 2;
            Point A = listaPuncte[mij];
            Point B = listaPuncte[(mij + 1) % listaPuncte.length];
            int det = calculDeterminant(M, B, A);
            determinant=det;
            if (det == 0) {
                return 0;
            } else if (det > 0) {
                stg = mij + 1;
            } else {
                dr = mij - 1;
            }
        }
       determinant=0;
        if (determinant > 0) {
            return 1;
        } else if (determinant < 0) {
            return -1;
        } else {
            return 0;
        }

    }


    public static void main(String[] args) {


        PoligonConvex chart = new PoligonConvex();
        chart.pack();
        chart.setVisible(true);
    }


}
