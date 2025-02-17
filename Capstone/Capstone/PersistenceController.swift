//
//  PersistenceController.swift
//  Capstone
//
//  Created by admin on 15/02/25.
//

import CoreData

struct PersistenceController {
    static let shared = PersistenceController()
    let container: NSPersistentContainer
    
    init() {
        container = NSPersistentContainer(name: "PatientModel")
        container.loadPersistentStores { _, error in
            if let error = error {
                fatalError("Unresolved error \(error)")
            }
        }
    }
    
    var context: NSManagedObjectContext {
        container.viewContext
    }

    // Save user to Core Data
    func saveUser(email: String, password: String) {
        let patient = Patient(context: context)
        patient.email = email
        patient.password = password
        do {
            try context.save()
        } catch {
            print("Failed to save user: \(error.localizedDescription)")
        }
    }

    // Fetch user by email and password
    func fetchUser(email: String, password: String) -> Patient? {
        let request: NSFetchRequest<Patient> = Patient.fetchRequest()
        request.predicate = NSPredicate(format: "email == %@ AND password == %@", email, password)
        
        do {
            let users = try context.fetch(request)
            return users.first
        } catch {
            print("Error fetching user: \(error.localizedDescription)")
            return nil
        }
    }
}


