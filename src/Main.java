void main() {
    System.out.println("Hello World!");
    IO.println("Project Idea 1: Submitting and managing homework.");

    DatabaseManager db = new DatabaseManager();

    //db.createTestData();
    System.out.println("\n--- Teacher creates tasks ---");

    db.saveTask("Math P. 42", "Please complete Nr. 1-5.", "2025-12-20", 1);
    db.saveTask("German Essay", "Topic: My best vacation.", "2025-12-22", 1);

    System.out.println("Success: Database has been initialized and tasks saved.");
    System.out.println("You can now start the 'Server' and 'Client'.");
}