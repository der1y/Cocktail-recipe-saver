import java.util.ArrayList;
import java.io.*;
import java.util.Scanner;

public class ClimateZone {
    private ArrayList<City> cityList = null;

    public ClimateZone() {
        this.cityList = new ArrayList<City>();
    }

    public ClimateZone(String fileName) throws IOException {
        cityList = new ArrayList<City>();
        String currCity;
        String currState;
        double currHighTemp;
        double currLowTemp;

        FileInputStream cityList = new FileInputStream(fileName);
        Scanner inCL = new Scanner(cityList);

        while (inCL.hasNext()) {
            currCity = inCL.next();
            currState = inCL.next();
            currHighTemp = inCL.nextDouble();
            currLowTemp = inCL.nextDouble();

            City city = new City(currCity, currState, currHighTemp, currLowTemp);

            this.cityList.add(city);
        }

        inCL.close();
        cityList.close();
    }

    public void addCity(String newCityName, String newState, double newHightTemp, double newLowTemp) {
        City newCity = new City(newCityName, newState, newHightTemp, newLowTemp);
        cityList.add(newCity);
    }

    public int getCityCount() {
        return cityList.size();
    }

    public City getCityByName(String cityName, String stateName) {
        City targetCity = null;

        for (City city : cityList) {
            if (city.getName().equals(cityName) && city.getState().equals(stateName)) {
                targetCity = city;
            }
        }
        return targetCity;
    }

    public void printHottestCities() {
        City city1 = null;
        City city2 = null;
        double highestTemp1 = -100;
        double highestTemp2 = -100;

        for (City city : cityList) {
            if (city.getHighTemp() > highestTemp1) {
                city1 = city;
                highestTemp1 = city.getHighTemp();
            }
        }

        for (City city : cityList) {
            if (city.getHighTemp() > highestTemp2 && city.getHighTemp() != highestTemp1) {
                city2 = city;
                highestTemp2 = city.getHighTemp();
            }
        }
        city1.printInfo();
        city2.printInfo();
    }

    public void printColdestCities() {
        City city1 = null;
        City city2 = null;
        double coldestTemp1 = 100;
        double coldestTemp2 = 100;

        for (City city : cityList) {
            if (city.getLowTemp() < coldestTemp1) {
                city1 = city;
                coldestTemp1 = city.getLowTemp();
            }
        }

        for (City city : cityList) {
            if (city.getLowTemp() < coldestTemp2 && city.getLowTemp() != coldestTemp1) {
                city2 = city;
                coldestTemp2 = city.getLowTemp();
            }
        }
        city1.printInfo();
        city2.printInfo();
    }

    public void printAllCities() {
        for (City city : cityList) {
            city.printInfo();
        }
    }
}