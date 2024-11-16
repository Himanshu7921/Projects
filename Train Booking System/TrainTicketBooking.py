class Train:
    def __init__(self, train_id, seats, name):

        # Initialize the Parameters
        self.train_id = train_id
        self.seats = seats
        self.name = name
        self.booked_seats = []

    def book_seat(self, seat_number):

        if seat_number in self.booked_seats:
            print(f"Seat {seat_number} is already Booked, try Booking Different Seat")
        elif seat_number < 1 or seat_number > self.seats:
            print(f"Invalid Seat No Selected, Please Select Seat between 1 and {self.seats}")
        else:
            self.booked_seats.append(seat_number) # Seat Booked Successfully, add it to the Booked Seat List
            print(f"Seat {seat_number} Booked Successfully ✅ ")

    def display_seats(self):
        available_seats = [seat for seat in range(1, self.seats + 1) if seat not in self.booked_seats]

        print(f"Available Seats {available_seats} 💺")

    def booking_status(self):
        print(f"Booked Seat for {self.name}: {self.booked_seats if self.booked_seats else 'No seats Booked yet'} ")


class TrainBookingSystem:
    def __init__(self) -> None:
        self.trains = {
            101: Train(101,10,"Express A"),
            102: Train(102,8,"Express B"),
            103: Train(103,4,"Express C")
        }

    def display_trains(self) -> None:
        print("Available Trains")
        for train_no, trains in self.trains.items():
            print(f"Train ID: {train_no}, Train Name: {trains.name}, Seats: {trains.seats}")

    def book_seat(self, train_id, seat_number) -> None:
        if train_id in self.trains:
            self.trains[train_id].book_seat(seat_number)

        else:
            print(f"Invalid Train ID Selected, Please Try Again ❌")

    def display_seats(self, train_id) -> None:
        if train_id in self.trains:
            self.trains[train_id].display_seats()

        else:
            print(f"Invalid Train ID Selected, Please Try Again ❌")

    def booking_status(self, train_id) -> None:
        if train_id in self.trains:
            self.trains[train_id].booking_status()

        else:
            print(f"Invalid Train ID Selected, Please Try Again ❌")


def main():
    systems = TrainBookingSystem()

    while True:
        print("\n Welcome To Our Train Booking Application 🚉")

        print("1. View Trains 🚊")
        print("2. Book a Seat 🪑")
        print("3. Check Available Seats 😊")
        print("4. View Booking Status 💫")
        print("5. Exit 🚪")

        choice = int(input("Enter your Choice: "))

        if choice == 1:
            systems.display_trains()

        elif choice == 2:
            train_id = int(input("Enter Train Id: "))
            seat_number = int(input("Enter Seat Number: "))
            systems.book_seat(train_id, seat_number)

        elif choice == 3:
            train_id = int(input("Enter Train Id: "))
            systems.display_seats(train_id)

        elif choice == 4:
            train_id = int(input("Enter Train Id: "))
            systems.booking_status(train_id)

        elif choice == 5:
            print("\nThank you for using Our Train Booking Application")
            break
        else:
            print("Invalid Choice, Enter again")

if __name__ == "__main__":
    main()