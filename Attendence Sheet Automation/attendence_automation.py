import streamlit as st
import pandas as pd
import datetime
from io import BytesIO

# Streamlit UI
st.title("📋 Attendance Generator")
st.write("Enter attendance details in the format: `Person_1: Present, Person_2: Absent`")

# User Input
user_input = st.text_area("Enter Attendance Data:", "")

if st.button("Generate Attendance File"):
    if not user_input.strip():
        st.warning("Please enter attendance data!")
    else:
        # Process input
        attendance = [entry.strip().split(": ") for entry in user_input.split(",")]
        present = [name for name, status in attendance if status.lower() == "present"]
        absent = [name for name, status in attendance if status.lower() == "absent"]

        # Create DataFrame
        max_len = max(len(present), len(absent))
        data = {
            "Present": present + [""] * (max_len - len(present)),
            "Absent": absent + [""] * (max_len - len(absent))
        }
        df = pd.DataFrame(data)

        # Generate Excel File
        file_name = f"Attendance_{datetime.date.today()}.xlsx"
        output = BytesIO()
        with pd.ExcelWriter(output, engine="openpyxl") as writer:
            df.to_excel(writer, sheet_name="Attendance", index=False)
        output.seek(0)

        # Provide Download Link
        st.success(f"✅ Attendance sheet `{file_name}` generated successfully!")
        st.download_button(
            label="📥 Download Excel File",
            data=output,
            file_name=file_name,
            mime="application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"
        )
