public class City {
    private String name;
    private String state;
    private double highTemp;
    private double lowTemp;

    public City(String cityName, String stateName, double highTemp, double lowTemp) {
        this.name = cityName;
        this.state = stateName;
        this.highTemp = highTemp;
        this.lowTemp = lowTemp;
    }

    public String getName() {
        return name;
    }

    public String getState() {
        return state;
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
        System.out.printf("%s, %s (High = %.2f, Low = %.2f)\n", name, state, highTemp, lowTemp);
    }
}