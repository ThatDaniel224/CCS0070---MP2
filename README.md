# CCS0070---MP2

## Machine Problem 3:
1. Which fields should be private, and what class methods are responsible for changing them?
- The product values should be private and the class methods that are responsible for changing them are the getters, and methods that compute and account for said values.
2. Why should sell() return a success/failure result?
- The function sell() needs to account for the amount of stock, which fails when there is insufficient amount in the transaction. 
3. What is the purpose of a static product counter?
- It searches for the products in the array.
4. How could this program be extended to support a reorder level unique to each product?
- Add a private field that tracks it based on the user's input.

## Machine Problem 6:
1. Why does Course have a Student[] instead of storing only student IDs?
- So that it can directly reference the student data without referencing another database.
2. How is the has-a relationship represented in the code?
- The has-a relationship is represented by the Course class containing a Student[]
3. What loop is needed to detect a duplicate enrollment? 
- A for loop that checks the elements of the entire array.
4. How would you model one student enrolling in many courses without duplicating Student objects?
- Bidirectional reference to Students and Course
## Machine Problem 9:
1. Why does Garage need to own an array of ParkingSlot objects?
- To reference it for checking duplicate plate numbers.
2. What condition checks slot compatibility for a car?
```if (slot.getSlotType() == 'C') {
    isCompatible = true;
}
```
3. Why should duplicate plate numbers be rejected?
- Plate numbers are unique identifiers of the vehicle. The vehicle is the only object that needs to be tracked in this case to prevent logical errors.
4. How could inheritance be introduced later without changing the core requirement of this problem?
- By adding and abstract class Vehicle, which has the fields of the license plate and owner name, which then can later be inherited by the car class and motorcycle class.
