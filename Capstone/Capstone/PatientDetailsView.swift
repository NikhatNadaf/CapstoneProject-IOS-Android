//
//  PatientDetailsView.swift
//  Capstone
//
//  Created by admin on 15/02/25.
//

import SwiftUI

struct PatientDetailsView: View
{
    let selectedDoctor: Doctor?
    
    @State private var loggedInUser: UserEntity?

    var body: some View {
        VStack {
            if let user = loggedInUser {
                Text("Patient Details")
                    .font(.largeTitle)
                    .bold()
                    .padding()
                
                Text("Name: \(user.email)") // Assuming email is stored as username
                Text("Contact: \(user.contact ?? "N/A")")
                Text("Disease: \(user.disease ?? "N/A")")
                
                Divider()
                
                if let doctor = selectedDoctor {
                    Text("Doctor Details")
                        .font(.title2)
                        .bold()
                        .padding(.top, 10)
                    
                    Text("Name: \(doctor.name)")
                    Text("Specialty: \(doctor.specialty)")
                    Text("Time: \(doctor.availableTime)")
                    Text("Slot: \(doctor.slot)")
                }
            } else {
                Text("No user data available")
            }
        }
        .onAppear {
            loggedInUser = PersistenceController.shared.getLoggedInUser()
        }
    }
}

