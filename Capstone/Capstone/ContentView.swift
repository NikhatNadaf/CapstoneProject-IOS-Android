//
//  ContentView.swift
//  Capstone
//
//  Created by admin on 15/02/25.
//

import SwiftUI

struct ContentView: View {
    var body: some View {
        LoginView()
    }
}


struct LoginApp: App {
    let persistenceController = PersistenceController.shared

    var body: some Scene {
        WindowGroup {
            ContentView()
                .environment(\.managedObjectContext, persistenceController.container.viewContext)
        }
    }
}
