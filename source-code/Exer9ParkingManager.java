/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package com.fit.mp2;
import java.util.Scanner;
/**
 *
 * @author dastarosa
 */

enum VehicleType {
    MOTORCYCLE, CAR
}

// Requirement 1: Vehicle class with private plate number, owner name, and type
class Vehicle {
    private String plateNumber;
    private String ownerName;
    private VehicleType type;

    public Vehicle(String plateNumber, String ownerName, VehicleType type) {
        this.plateNumber = plateNumber;
        this.ownerName = ownerName;
        this.type = type;
    }

    public String getPlateNumber() {
        return plateNumber;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public VehicleType getType() {
        return type;
    }
}

// Requirement 2: ParkingSlot class with private slot number, slot type, occupied state, and parkedVehicle
class ParkingSlot {
    private int slotNumber;
    private char slotType; // 'M' for motorcycle-only, 'C' for car-compatible
    private boolean isOccupied;
    private Vehicle parkedVehicle;

    public ParkingSlot(int slotNumber, char slotType) {
        this.slotNumber = slotNumber;
        this.slotType = slotType;
        this.isOccupied = false;
        this.parkedVehicle = null;
    }

    public int getSlotNumber() {
        return slotNumber;
    }

    public char getSlotType() {
        return slotType;
    }

    public boolean isOccupied() {
        return isOccupied;
    }

    public Vehicle getParkedVehicle() {
        return parkedVehicle;
    }

    public void parkVehicle(Vehicle vehicle) {
        this.parkedVehicle = vehicle;
        this.isOccupied = true;
    }

    public void removeVehicle() {
        this.parkedVehicle = null;
        this.isOccupied = false;
    }
}

// Requirement 3: Garage class with private ParkingSlot[] slots
class Garage {
    private ParkingSlot[] slots;
    
    // Requirement 8: Static field to track the number of vehicles currently parked
    private static int parkedCount = 0;

    // Updated constructor to take specific counts of 'M' and 'C' slots
    public Garage(int numMSlots, int numCSlots) {
        int totalSlots = numMSlots + numCSlots;
        slots = new ParkingSlot[totalSlots];
        int slotNumber = 1;

        // Populate Motorcycle-only slots ('M')
        for (int i = 0; i < numMSlots; i++) {
            slots[slotNumber - 1] = new ParkingSlot(slotNumber, 'M');
            slotNumber++;
        }

        // Populate Car-compatible slots ('C')
        for (int i = 0; i < numCSlots; i++) {
            slots[slotNumber - 1] = new ParkingSlot(slotNumber, 'C');
            slotNumber++;
        }
    }

    public static int getParkedCount() {
        return parkedCount;
    }

    // Requirement 5 & 4: park(Vehicle vehicle) to find first compatible free slot and reject duplicate plates
    public boolean park(Vehicle vehicle) {
        if (vehicle == null) {
            return false;
        }

        // Check if plate number is already parked
        for (int i = 0; i < slots.length; i++) {
            if (slots[i].isOccupied() && slots[i].getParkedVehicle().getPlateNumber().equalsIgnoreCase(vehicle.getPlateNumber())) {
                System.out.println("Parking failed: Vehicle with plate '" + vehicle.getPlateNumber() + "' is already parked.");
                return false;
            }
        }

        // Find the first compatible free slot
        for (int i = 0; i < slots.length; i++) {
            ParkingSlot slot = slots[i];
            if (!slot.isOccupied()) {
                boolean isCompatible = false;
                
                // Requirement 4: Motorcycle may use M or C; car may use C only
                if (vehicle.getType() == VehicleType.MOTORCYCLE) {
                    if (slot.getSlotType() == 'M' || slot.getSlotType() == 'C') {
                        isCompatible = true;
                    }
                } else if (vehicle.getType() == VehicleType.CAR) {
                    if (slot.getSlotType() == 'C') {
                        isCompatible = true;
                    }
                }

                if (isCompatible) {
                    slot.parkVehicle(vehicle);
                    parkedCount++;
                    System.out.println("Success: " + vehicle.getType() + " [" + vehicle.getPlateNumber() + 
                                       "] parked in Slot " + slot.getSlotNumber() + " (" + slot.getSlotType() + ")");
                    return true;
                }
            }
        }

        System.out.println("Parking failed: No compatible free slots available for " + vehicle.getType() + ".");
        return false;
    }

    // Requirement 6 & 7: exit(String plate, int hours) to locate vehicle, compute fee, and free slot
    public double exit(String plate, int hours) {
        // Requirement 7: Hours must be at least 1
        if (hours < 1) {
            System.out.println("Exit failed: Hours parked must be at least 1.");
            return -1;
        }

        ParkingSlot targetSlot = null;
        for (int i = 0; i < slots.length; i++) {
            if (slots[i].isOccupied() && slots[i].getParkedVehicle().getPlateNumber().equalsIgnoreCase(plate)) {
                targetSlot = slots[i];
                break;
            }
        }

        if (targetSlot == null) {
            System.out.println("Exit failed: Vehicle with plate '" + plate + "' not found in the garage.");
            return -1;
        }

        Vehicle vehicle = targetSlot.getParkedVehicle();
        double fee = 0;

        // Requirement 7 Fee Rules:
        // MOTORCYCLE = PHP 20 first hour + PHP 10 per additional hour
        // CAR = PHP 40 first hour + PHP 20 per additional hour
        if (vehicle.getType() == VehicleType.MOTORCYCLE) {
            fee = 20 + (hours - 1) * 10;
        } else if (vehicle.getType() == VehicleType.CAR) {
            fee = 40 + (hours - 1) * 20;
        }

        targetSlot.removeVehicle();
        parkedCount--;
        System.out.println("Exit successful for " + vehicle.getType() + " [" + plate + "]. Parking Fee: PHP " + fee);
        return fee;
    }

    // Requirement 10: Print final occupancy and all occupied slots
    public void printFinalReport() {
        System.out.println("\n========================================");
        System.out.println("          FINAL GARAGE REPORT           ");
        System.out.println("========================================");
        System.out.println("Total Garage Capacity: " + slots.length);
        System.out.println("Total Parked Vehicles: " + parkedCount);
        System.out.println("Available Slots      : " + (slots.length - parkedCount));
        System.out.println("----------------------------------------");
        System.out.println("List of Occupied Slots:");

        boolean anyOccupied = false;
        for (int i = 0; i < slots.length; i++) {
            if (slots[i].isOccupied()) {
                anyOccupied = true;
                Vehicle v = slots[i].getParkedVehicle();
                System.out.println(" - Slot " + slots[i].getSlotNumber() + " [" + slots[i].getSlotType() + "]: " + 
                                   v.getType() + " | Plate: " + v.getPlateNumber() + " | Owner: " + v.getOwnerName());
            }
        }

        if (!anyOccupied) {
            System.out.println(" (None - All slots are currently empty)");
        }
        System.out.println("========================================");
    }
}

public class Exer9ParkingManager {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("=== Garage Setup ===");
        System.out.print("Enter number of Motorcycle slots: ");
        int mSlots = scanner.nextInt();
        System.out.print("Enter number of Car slots: ");
        int cSlots = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        Garage garage = new Garage(mSlots, cSlots);

        // Requirement 9: Process a user-defined number of P (park) and E (exit) operations
        System.out.print("Enter the number of operations to process: ");
        int numOperations = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        for (int op = 1; op <= numOperations; op++) {
            System.out.println("\n--- Operation " + op + " of " + numOperations + " ---");
            System.out.print("Enter operation type ([P]ark / [E]xit): ");
            String choice = scanner.nextLine().trim().toUpperCase();

            if (choice.equals("P")) {
                System.out.print("Enter Plate Number: ");
                String plate = scanner.nextLine();
                System.out.print("Enter Owner Name: ");
                String owner = scanner.nextLine();
                System.out.print("Enter Vehicle Type (MOTORCYCLE / CAR): ");
                String typeStr = scanner.nextLine().trim().toUpperCase();

                VehicleType type;
                try {
                    type = VehicleType.valueOf(typeStr);
                } catch (IllegalArgumentException e) {
                    System.out.println("Invalid vehicle type entered. Defaulting to CAR.");
                    type = VehicleType.CAR;
                }

                Vehicle vehicle = new Vehicle(plate, owner, type);
                garage.park(vehicle);

            } else if (choice.equals("E")) {
                System.out.print("Enter Plate Number to exit: ");
                String plate = scanner.nextLine();
                System.out.print("Enter hours parked (minimum 1): ");
                int hours = scanner.nextInt();
                scanner.nextLine(); // Consume newline

                garage.exit(plate, hours);
            } else {
                System.out.println("Invalid choice. Please enter 'P' or 'E'.");
                op--; // Retry current operation loop step
            }
        }

        // Requirement 10: Print final occupancy report
        garage.printFinalReport();

        scanner.close();
    }
}