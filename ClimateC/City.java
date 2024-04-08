public class City {
    private String name;
    private String State;
    private double highTemp;
    private double lowTemp;

    public City(String cityName, String stateName, double highTemp, double lowTemp) {
        this.name = cityName;
        this.State = stateName;
        this.highTemp = highTemp;
        this.lowTemp = lowTemp;
    }

    public String getName() {
        return name;
    }

    public String getState() {
        return State;
    }

    public double getHighTemp() {
        return highTemp;
    }

    public void setHighTemp(double highTemp) {
        this.highTemp = highTemp;
    }

    public double getLowTemp() {
        return lowTemp;
    }

    public void setLowTemp(double lowTemp) {
        this.lowTemp = lowTemp;
    }

    public void printInfo() {
        System.out.printf("%s, %s (High = %.2f, Low = %.2f)\n", name, State, highTemp, lowTemp);
    }
}