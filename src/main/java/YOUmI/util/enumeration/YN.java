package YOUmI.util.enumeration;

public enum YN {
    Y('Y'),
    N('N');
    private final char YN;
    YN(char c){
        this.YN = c;
    }

    public char getYN(){
        return this.YN;
    }

}
