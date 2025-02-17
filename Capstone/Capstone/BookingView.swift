//
//  BookingView.swift
//  Capstone
//
//  Created by admin on 15/02/25.
//
import SwiftUI

struct BookingView: View {
    var doctor: Doctors?
    
    @State private var patientName: String = ""  // New field for patient name
    @State private var diseaseName: String = ""
    @State private var appointmentDate: String = ""
    @State private var selectedTimeSlot: String = ""
    @State private var isNavigating = false  // Navigation state

    var body: some View {
        VStack(spacing: 20) {
            if let doctor = doctor {
                VStack(alignment: .leading, spacing: 10) {
                    DetailRow(title: "Doctor Name", value: doctor.name)
                    DetailRow(title: "Specialty", value: doctor.specialty)
                    DetailRow(title: "Available Slot", value: doctor.slot)
                }
                .padding(.horizontal)
            }

            // Patient Name Input
            VStack(alignment: .leading, spacing: 5) {
                Text("Enter Patient Name")
                    .font(.subheadline)
                    .foregroundColor(.gray)
                TextField("e.g., John Doe", text: $patientName)
                    .textFieldStyle(RoundedBorderTextFieldStyle())
                    .padding(.horizontal)
            }

            // Disease Name Input
            VStack(alignment: .leading, spacing: 5) {
                Text("Enter Disease Name")
                    .font(.subheadline)
                    .foregroundColor(.gray)
                TextField("e.g., Fever, Cough, Headache", text: $diseaseName)
                    .textFieldStyle(RoundedBorderTextFieldStyle())
                    .padding(.horizontal)
            }

            // Manual Date Input
            VStack(alignment: .leading, spacing: 5) {
                Text("Enter Appointment Date (DD/MM/YYYY)")
                    .font(.subheadline)
                    .foregroundColor(.gray)
                TextField("e.g., 20/02/2025", text: $appointmentDate)
                    .textFieldStyle(RoundedBorderTextFieldStyle())
                    .padding(.horizontal)
            }

            // Time Slot Picker
            VStack(alignment: .leading, spacing: 5) {
                Text("Select Time Slot")
                    .font(.subheadline)
                    .foregroundColor(.gray)
                Picker("Choose a Time", selection: $selectedTimeSlot) {
                    ForEach(getTimeSlots(for: doctor?.slot ?? ""), id: \.self) { time in
                        Text(time)
                    }
                }
                .pickerStyle(MenuPickerStyle())
                .padding()
                .frame(maxWidth: .infinity)
                .background(Color.gray.opacity(0.2))
                .cornerRadius(8)
                .padding(.horizontal)
            }

            // Navigation Link to AppointmentDetailsView
            NavigationLink(destination: AppointmentDetailsView(
                userName: patientName, // Pass the patient name
                doctor: doctor,
                diseaseName: diseaseName,
                appointmentDate: appointmentDate,
                selectedTimeSlot: selectedTimeSlot
            ), isActive: $isNavigating) { EmptyView() }

            // Confirm Booking Button
            Button(action: {
                saveToDatabase()  // Save data to database
                isNavigating = true  // Navigate to AppointmentDetailsView
            }) {
                Text("Confirm Booking")
                    .padding()
                    .frame(maxWidth: .infinity)
                    .background(Color.blue)
                    .foregroundColor(.white)
                    .cornerRadius(8)
            }
            .padding()

            Spacer()
        }
        .padding()
        .navigationTitle("")
    }

    // Function to Get Available Time Slots
    func getTimeSlots(for slot: String) -> [String] {
        switch slot {
        case "Morning":
            return ["10:00 AM", "10:30 AM", "11:00 AM", "11:30 AM", "12:00 PM"]
        case "Afternoon":
            return ["2:00 PM", "2:30 PM", "3:00 PM", "3:30 PM", "4:00 PM"]
        case "Evening":
            return ["6:00 PM", "6:30 PM", "7:00 PM", "7:30 PM", "8:00 PM"]
        case "Night":
            return ["10:00 PM", "10:30 PM", "11:00 PM", "11:30 PM", "12:00 AM"]
        default:
            return ["Select a slot"]
        }
    }

    // Function to Save Booking Data to Database
    func saveToDatabase() {
        let bookingData = [
            "patientName": patientName,
            "diseaseName": diseaseName,
            "appointmentDate": appointmentDate,
            "selectedTimeSlot": selectedTimeSlot,
            "doctorName": doctor?.name ?? "N/A",
            "specialty": doctor?.specialty ?? "N/A"
        ]

        // Here, implement your database logic (Firebase, CoreData, etc.)
        print("Booking saved:", bookingData)
    }
}

// MARK: - Preview
struct BookingView_Previews: PreviewProvider {
    static var previews: some View {
        BookingView(doctor: Doctors(name: "Dr. Smith", specialty: "Cardiologist", availableTime: "10:00 AM - 1:00 PM", slot: "Morning", isAvailable: true))
    }
}
struct DetailRow: View {
    let title: String
    let value: String

    var body: some View {
        VStack(alignment: .leading, spacing: 5) {
            Text(title)
                .font(.subheadline)
                .foregroundColor(.gray)
            Text(value)
                .font(.headline)
                .padding()
                .frame(maxWidth: .infinity, alignment: .leading)
                .background(Color.gray.opacity(0.2))
                .cornerRadius(8)
        }
    }
}
