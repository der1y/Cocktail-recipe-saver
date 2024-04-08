class city {
    private String name;
    private String State;
    private double highTemp;
    private double lowTemp;

    public city(String cityName, String stateName, double highTemp, double lowTemp) {
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
        return hightTemp;
    }

    public void setHighTemp(double highTemp) {
        this.name = highTemp;
    }

    public double getLowTemp() {
        return lowTemp;
    }

    public double setLowTemp(double lowTemp) {
        this.lowTemp = lowTemp;
    }

    public void printInfo() {
        System.out.printf("%s, %s, (High = %.2f, Low = %.2f)\n", name, State, hightTemp, lowTemp);
    }
}