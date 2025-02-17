//
//  LoginView.swift
//  Capstone
//
//  Created by admin on 15/02/25.
//

import SwiftUI

struct LoginView: View {
    @State private var email = ""
    @State private var password = ""
    @State private var showAlert = false
    @State private var alertMessage = ""
    @State private var isLoggedIn = false
    
    var body: some View {
        NavigationView {
            VStack {
                Text("Login")
                    .font(.largeTitle)
                    .bold()
                    .padding()
                
                TextField("Email", text: $email)
                    .keyboardType(.emailAddress) // Sets the keyboard for email input
                    .autocapitalization(.none) // Prevents auto-capitalization
                    .disableAutocorrection(true) // Disables autocorrect
                    .textFieldStyle(RoundedBorderTextFieldStyle())
                    .padding()
                
                SecureField("Password", text: $password)
                    .textFieldStyle(RoundedBorderTextFieldStyle())
                    .padding()
                
                Button("Login") {
                    loginUser()
                }
                .padding()
                .frame(maxWidth: .infinity)
                .background(Color.green)
                .foregroundColor(.white)
                .cornerRadius(8)
                .padding()
                
                NavigationLink("Register", destination: RegisterView())
                    .padding()
                
                NavigationLink(destination: HomeView(), isActive: $isLoggedIn) {
                    EmptyView()
                }
            }
            .alert(isPresented: $showAlert) {
                Alert(title: Text("Message"), message: Text(alertMessage), dismissButton: .default(Text("OK")))
            }
        }
    }
    
    func loginUser() {
        let user = PersistenceController.shared.fetchUser(email: email, password: password)
        if user != nil {
            isLoggedIn = true
            clearFields()
            
        } else {
            alertMessage = "Invalid email or password"
            showAlert = true
            clearFields()
        }
    }
    
    // Function to clear email and password fields
    func clearFields() {
        email = ""
        password = ""
    }
    
}
