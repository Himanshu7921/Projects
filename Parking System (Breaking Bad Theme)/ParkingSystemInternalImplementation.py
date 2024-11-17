class ParkingSlot:
    def __init__(self, slot_id):
        self.slot_id = slot_id
        self.vehicle_number = None
        self.is_booked = False

    def book(self, vehicle_number):
        if not self.is_booked:
            self.is_booked = True
            self.vehicle_number = vehicle_number
            return True
        return False

    def cancel_booking(self):
        if self.is_booked:
            self.is_booked = False
            self.vehicle_number = None
            return True
        return False

    def __str__(self):
        status = f"Booked {self.vehicle_number}" if self.is_booked else "Available"
        return f"Spot {self.slot_id}: {status}"

