package com.yee.study.designpattern.bridge;

/**
 * @author Roger.Yi
 */
public class Client
{
    public static void main(String[] args)
    {
        // Gasoline Bus
        Bus gasolineBus = new Bus();
        gasolineBus.setEngine(new GasolineEngine());
        gasolineBus.start();
        gasolineBus.loadPeople();
        gasolineBus.stop();

        // Diesel Bus
        Bus dieselBus = new Bus();
        dieselBus.setEngine(new DieselEngine());
        dieselBus.start();
        dieselBus.loadPeople();
        dieselBus.stop();

        // Gasoline Truck
        Truck gasolineTruck = new Truck();
        gasolineTruck.setEngine(new GasolineEngine());
        gasolineTruck.start();
        gasolineTruck.loadGoods();
        gasolineTruck.stop();

        // Diesel Truck
        Truck dieselTruck = new Truck();
        dieselTruck.setEngine(new DieselEngine());
        dieselTruck.start();
        dieselTruck.loadGoods();
        dieselTruck.stop();
    }
}
