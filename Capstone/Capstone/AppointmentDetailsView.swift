//
//  AppointmentDetailsView.swift
//  Capstone
//
//  Created by admin on 17/02/25.
//

import SwiftUI

struct AppointmentDetailsView: View {
    var userName: String // Add this
    var doctor: Doctors?
    var diseaseName: String
    var appointmentDate: String
    var selectedTimeSlot: String
    @Environment(\.presentationMode) var presentationMode // For navigation back

    var body: some View {
        VStack(spacing: 20) {
            

            // Display Patient & Appointment Details
            VStack(alignment: .leading, spacing: 10) {
                DetailRow(title: "Patient Name", value: userName) // Show patient name
                DetailRow(title: "Doctor Name", value: doctor?.name ?? "N/A")
                DetailRow(title: "Specialty", value: doctor?.specialty ?? "N/A")
                DetailRow(title: "Available Slot", value: doctor?.slot ?? "N/A")
                DetailRow(title: "Appointment Date", value: appointmentDate)
                DetailRow(title: "Time Slot", value: selectedTimeSlot)
                DetailRow(title: "Disease Name", value: diseaseName)
            }
            .padding(.horizontal)

            // Buttons
            HStack(spacing: 20) {
                Button(action: {
                    presentationMode.wrappedValue.dismiss() // Go back to edit
                }) {
                    Text("Edit")
                        .padding()
                        .frame(maxWidth: .infinity)
                        .background(Color.orange)
                        .foregroundColor(.white)
                        .cornerRadius(8)
                }

                Button(action: {
                    cancelBooking()
                }) {
                    Text("Delete")
                        .padding()
                        .frame(maxWidth: .infinity)
                        .background(Color.red)
                        .foregroundColor(.white)
                        .cornerRadius(8)
                }
            }
            .padding(.horizontal)

            Spacer()
        }
        .padding()
        .navigationTitle("Appointment Details")
    }

    // Function to Cancel Booking
    func cancelBooking() {
        print("Appointment for \(diseaseName) with \(doctor?.name ?? "N/A") has been deleted.")
        presentationMode.wrappedValue.dismiss()
    }
}

// MARK: - Preview
struct AppointmentDetailsView_Previews: PreviewProvider {
    static var previews: some View {
        AppointmentDetailsView(
            userName: "John Doe", // Sample Patient Name
            doctor: Doctors(name: "Dr. Smith", specialty: "Cardiologist", availableTime: "10:00 AM - 1:00 PM", slot: "Morning", isAvailable: true),
            diseaseName: "Flu",
            appointmentDate: "20/02/2025",
            selectedTimeSlot: "10:30 AM"
        )
    }
}
