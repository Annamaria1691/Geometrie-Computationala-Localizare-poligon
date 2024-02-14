public class Cadran {
    private int Ca;

    public int Cadran(Point A){
        if(A.getX()>0 && A.getY()>=0){
            Ca=1;
        }else if(A.getX()<=0 && A.getY()>0){
            Ca=2;
        }else if(A.getX()<0 && A.getY()<=0){
            Ca=3;
        }else if(A.getX()>=0 && A.getY()<0){
            Ca=4;
        }
        return Ca;
    }
}
