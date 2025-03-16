import streamlit as st
import pandas as pd
import datetime

# Set page title
st.title("Attendance Tracker")

# Input field for attendance
user_input = st.text_area("Enter attendance (Format: Person_1: Present, Person_2: Absent, ...)", "")

# Button to process input
if st.button("Generate Excel Sheet"):
    if user_input.strip():
        # Process input
        attendance = [entry.strip().split(": ") for entry in user_input.split(",")]
        
        # Separate present and absent persons
        present = [name for name, status in attendance if status.lower() == "present"]
        absent = [name for name, status in attendance if status.lower() == "absent"]

        # Create DataFrame
        data = {
            "Present": present + [""] * (max(len(present), len(absent)) - len(present)),
            "Absent": absent + [""] * (max(len(present), len(absent)) - len(absent))
        }
        df = pd.DataFrame(data)

        # Generate file name based on the current date
        file_name = f"Attendance_{datetime.date.today()}.xlsx"

        # Save to Excel
        df.to_excel(file_name, index=False)

        # Provide download link
        with open(file_name, "rb") as file:
            st.download_button(label="Download Attendance Sheet", data=file, file_name=file_name, mime="application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")

        st.success(f"Attendance sheet generated: {file_name}")
    else:
        st.error("Please enter attendance details.")