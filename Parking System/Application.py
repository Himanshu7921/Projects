from ParkingSystemInternalImplementation import ParkingSlot


class ParkingArea:
    def __init__(self, total_spots):
        self.spots = [ParkingSlot(i + 1) for i in range(total_spots)]

    def view_spots(self):
        print("\n* Breaking Bad Parking - View Parking Spots *")
        for spot in self.spots:
            print(spot)

    def book_spot(self, spot_id, vehicle_number):
        if 1 <= spot_id <= len(self.spots):
            if self.spots[spot_id - 1].book(vehicle_number):
                print(f"Spot {spot_id} is Now Your's! Welcome to the world of Walter White.")
            else:
                print(f"Spot {spot_id} has already been taken. Remember, no one can take what's mine.")
        else:
            print("Invalid Spot Id - You Can't Go Beyond This.")

    def view_booked_spot(self):
        booked_spots = [spot for spot in self.spots if spot.is_booked]
        if booked_spots:
            print("\n* Breaking Bad Parking - Booked Spots *")
            for spot in booked_spots:
                print(f"{spot}, Vehicle Number {spot.vehicle_number}")
        else:
            print("No spots are booked yet. The meth lab is empty.")

    def cancel_booking(self, spot_id):
        if 1 <= spot_id <= len(self.spots):
            if self.spots[spot_id - 1].cancel_booking():
                print(f"Booking for Spot {spot_id} has been canceled. You know, Skyler doesn't like this.")
            else:
                print(f"Spot {spot_id} is not currently booked. Like Walt said, 'You are the one who knocks'.")
        else:
            print(f"Invalid Spot ID. No one can help you now.")

def display_menu():
    print("\n* Breaking Bad Parking System *")
    print("1. View Available Parking Spots")
    print("2. Book a Parking Spot")
    print("3. View Booked Spots")
    print("4. Cancel a Booking")
    print("5. Exit (I am the danger)")

def main():
    parkinglot = ParkingArea(10)

    while True:
        display_menu()

        try:
            choice = int(input("Enter your Choice (1-5): "))
        except ValueError:
            print("Invalid input, please choose a number between 1 and 5!")
            continue

        if choice == 1:
            parkinglot.view_spots()

        elif choice == 2:
            try:
                spot_id = int(input("Enter Spot ID: "))
                vehicle_number = input("Enter Vehicle Number: ")
                parkinglot.book_spot(spot_id, vehicle_number)
            except ValueError as e:
                print("Invalid input, please enter valid details.")

        elif choice == 3:
            parkinglot.view_booked_spot()

        elif choice == 4:
            try:
                spot_id = int(input("Enter Spot ID: "))
                parkinglot.cancel_booking(spot_id)
            except ValueError as e:
                print("Invalid input, please enter a valid spot ID.")

        elif choice == 5:
            print("Thank you for using the Breaking Bad Parking System. Remember, I am the one who parks.")
            break

        else:
            print("Please choose a valid option between 1 and 5.")

if __name__ == "__main__":
    main()
