public class Profits {
    private double concertPercentage = 0;
    private double galaPercentage = 0;
    private double screeningPercentage = 0;
    private double workshopPercentage = 0;

    private static final Profits INSTANCE = new Profits();

    private Profits(){
    }

    public static Profits instance(){
        return INSTANCE;
    }

    public double getConcertPercentage(){
        return this.concertPercentage;
    }

    public double getGalaPercentage(){
        return this.galaPercentage;
    }

    public double getScreeningPercentage(){
        return this.screeningPercentage;
    }

    public double getWorkshopPercentage(){
        return this.workshopPercentage;
    }

    public void setPercentages(double cp, double gp, double sp, double wp){
        this.concertPercentage = cp;
        this.galaPercentage = gp;
        this.screeningPercentage = sp;
        this.workshopPercentage = wp;
    }
}
