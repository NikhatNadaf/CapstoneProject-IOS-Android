//
//  RegisterView.swift
//  Capstone
//
//  Created by admin on 15/02/25.
//

import SwiftUI

struct RegisterView: View {
    @Environment(\.presentationMode) var presentationMode // For navigating back to LoginView
    @State private var email = ""
    @State private var contact = ""
    //@State private var diseaseName = ""
    @State private var password = ""
    @State private var confirmPassword = ""
    @State private var showAlert = false
    @State private var alertMessage = ""
    @State private var isRegistrationSuccessful = false

    var body: some View {
        VStack {
            Text("Register")
                .font(.largeTitle)
                .bold()
                .padding()
            //TextField("Disease",text:$diseaseName)
              //  .padding()
            
            TextField("Email", text: $email)
                .keyboardType(.emailAddress)
                .autocapitalization(.none)
                .disableAutocorrection(true)
                .textFieldStyle(RoundedBorderTextFieldStyle())
                .padding()
            
            TextField("Contact", text: $contact)
                .keyboardType(.numberPad)
                .textFieldStyle(RoundedBorderTextFieldStyle())
                .padding()
                .onChange(of: contact) { newValue in
                    contact = String(newValue.prefix(10)) // Limits contact to 10 digits
                }
            
            SecureField("Password", text: $password)
                .textFieldStyle(RoundedBorderTextFieldStyle())
                .padding()
            
            SecureField("Confirm Password", text: $confirmPassword)
                .textFieldStyle(RoundedBorderTextFieldStyle())
                .padding()
            
            Button("Register") {
                registerUser()
            }
            .padding()
            .frame(maxWidth: .infinity)
            .background(Color.blue)
            .foregroundColor(.white)
            .cornerRadius(8)
            .padding()
        }
        .alert(isPresented: $showAlert) {
            Alert(
                title: Text("Message"),
                message: Text(alertMessage),
                dismissButton: .default(Text("OK")) {
                    if isRegistrationSuccessful {
                        presentationMode.wrappedValue.dismiss() // Navigate back to LoginView
                    }
                }
            )
        }
    }

    func registerUser() {
        // Validate Email
        guard isValidEmail(email) else {
            alertMessage = "Please enter a valid email address"
            showAlert = true
            return
        }
        
        // Validate Contact
        guard contact.count == 10 else {
            alertMessage = "Contact number must be 10 digits"
            showAlert = true
            return
        }
        
        // Validate Password
        guard !password.isEmpty, password == confirmPassword else {
            alertMessage = "Passwords do not match"
            showAlert = true
            return
        }
        
        // Save User
        PersistenceController.shared.saveUser(email: email, password: password)
        alertMessage = "Registration Successful"
        isRegistrationSuccessful = true
        showAlert = true
    }

    // Email validation function
    func isValidEmail(_ email: String) -> Bool {
        let emailRegex = #"^[A-Z0-9a-z._%+-]+@[A-Za-z0-9.-]+\.[A-Za-z]{2,}$"#
        return NSPredicate(format: "SELF MATCHES %@", emailRegex).evaluate(with: email)
    }
}
