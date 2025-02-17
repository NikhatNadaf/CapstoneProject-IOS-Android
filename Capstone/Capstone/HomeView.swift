//
//  HomeView.swift
//  Capstone
//
//  Created by admin on 15/02/25.
//

import SwiftUI
struct Doctors: Identifiable {
    let id = UUID()
    let name: String
    let specialty: String
    let availableTime: String
    let slot: String
    let isAvailable: Bool
}
struct HomeView: View {
    @State private var selectedDoctor: Doctors? = nil
    @State private var navigateToBooking = false

    let doctors: [Doctors] = [
        Doctors(name: "Dr. Smith", specialty: "Cardiologist", availableTime: "10:00 AM - 1:00 PM", slot: "Morning", isAvailable: true),
        Doctors(name: "Dr. Johnson", specialty: "Dermatologist", availableTime: "2:00 PM - 5:00 PM", slot: "Afternoon", isAvailable: false),
        Doctors(name: "Dr. Brown", specialty: "Pediatrician", availableTime: "6:00 PM - 9:00 PM", slot: "Evening", isAvailable: true),
        Doctors(name: "Dr. Wilson", specialty: "Neurologist", availableTime: "10:00 PM - 1:00 AM", slot: "Night", isAvailable: false),
        Doctors(name: "Dr. Smith", specialty: "Cardiologist", availableTime: "10:00 AM - 1:00 PM", slot: "Morning", isAvailable: true),
        Doctors(name: "Dr. Johnson", specialty: "Dermatologist", availableTime: "2:00 PM - 5:00 PM", slot: "Afternoon", isAvailable: false),
        Doctors(name: "Dr. Brown", specialty: "Pediatrician", availableTime: "6:00 PM - 9:00 PM", slot: "Evening", isAvailable: true),
        Doctors(name: "Dr. Wilson", specialty: "Neurologist", availableTime: "10:00 PM - 1:00 AM", slot: "Night", isAvailable: false)
    ]
    
    var body: some View {
        NavigationView {
            List(doctors) { doctor in
                HStack {
                    VStack(alignment: .leading, spacing: 5) {
                        Text(doctor.name)
                            .font(.headline)
                        
                        Text(doctor.specialty)
                            .font(.subheadline)
                            .foregroundColor(.gray)
                        
                        Text("Time: \(doctor.availableTime)")
                            .font(.footnote)
                        
                        Text("Slot: \(doctor.slot)")
                            .font(.footnote)
                            .foregroundColor(.blue)
                        
                        Text(doctor.isAvailable ? "Available" : "Not Available")
                            .font(.footnote)
                            .foregroundColor(doctor.isAvailable ? .green : .red)
                            .bold()
                    }
                    Spacer()
                    
                    Button(action: {
                        selectedDoctor = doctor
                        navigateToBooking = true
                    }) {
                        Text("Book")
                            .padding(.horizontal, 12)
                            .padding(.vertical, 6)
                            .background(doctor.isAvailable ? Color.green : Color.gray)
                            .foregroundColor(.white)
                            .cornerRadius(8)
                    }
                    .disabled(!doctor.isAvailable)
                }
                .padding(.vertical, 5)
            }
            .navigationTitle("Doctors List")
            .background(
                NavigationLink(destination: BookingView(doctor: selectedDoctor), isActive: $navigateToBooking) {
                    EmptyView()
                }
                .hidden()
            )
        }
    }
}

// MARK: - Preview
struct HomeView_Previews: PreviewProvider {
    static var previews: some View {
        HomeView()
    }
}
