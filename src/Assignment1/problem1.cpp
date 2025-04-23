#include <iostream>
#include <string>

const int MAX_STUDENTS = 3;
enum ContestResult
{
    WIN,
    LOSE,
    TIE
};

// The Student class has the following common attributes:
// - name: string
// - social: int (default: 0)
// - energy: int (default: 100)
// However, each student has different attributes according to their type. This is called 'special attribute'
//   Scientist - knowledge: int (default: 0, can be negative)
//   Athlete - health: int (default: 0, can be negative)
//   Artist - creativity: int (default: 0, can be negative)
// These attributes are only accessible by the derived classes and itself.
// They are not accessible by any other function outside the class.
// Implement the getters if needed!
class Student
{
public:
    Student(std::string name) {
        this->name = name;
        this->social = 0;
        this->energy = 100;
    }
    virtual ~Student() {}

    void rest()
    {
        // 1. Resting increases energy by 50
        // 2. Display the status of the student
        this->energy += 50;
        showStatus();
    }

    void meetFriends()
    {
        // 1. If energy is greater than or equal to 15, increase social by 20 and decrease energy by 15
        // 2. If energy is less than 15, display a message "${StudentName} is too tired to meet friends."
        // 3. Display the status of the student
        // *There is no case of overflow
        if (this->energy >= 15) {
            this->social += 20;
            this->energy -= 15;
            showStatus();
        } else {
            std::cout << this->name << " is too tired to meet friends." << std::endl;
        }
    }

    // getters
    std::string getName() {
        return this->name;
    }

    int getSocial() {
        return this->social;
    }

    int getEnergy() {
        return this->energy;
    }

    // There are three types of students: Scientist, Athlete, and Artist
    // Each student has different activities to do and different effects on their stats
    // Override this method in the derived classes
    // Scientist: Study
    //  - If energy is greater than or equal to 15, incerase knowledge by 20 and decrease energy by 15
    //  - If energy is less than 15, display a message "${StudentName} is too tired to study."
    //  - Display the status of the student
    // Athlete: Exercise
    //  - If energy is greater than or equal to 15, increase health by 20 and decrease energy by 15
    //  - If energy is less than 15, display a message "${StudentName} is too tired to exercise."
    //  - Display the status of the student
    // Artist: Work on art
    //  - If energy is greater than or equal to 15, increase creativity by 20 and decrease energy by 15
    //  - If energy is less than 15, display a message "${StudentName} is too tired to work on art."
    //  - Display the status of the student
    // *There is no case of overflow
    virtual void doActivity() = 0;

    // Return the sum of the special attribute and social and energy
    virtual int getStats() = 0;

    // 1. If the result is WIN, increase special attribute by 10 and decrease energy by 10
    // 2. If the result is LOSE, decrease special attribute and energy by 10
    // 3. If the result is TIE, decrease energy by 10
    // 4. Display the status of the student
    // *There is no case of overflow/underflow
    virtual void updateAfterContest(ContestResult result) = 0;

    // Display the status of the student
    // For example, if the name is "John" and its type is Scientist, with knowledge: 0, social: 0, energy: 0,
    // print "Status of John: Knowledge: 0, Social: 0, Energy: 0"
    virtual void showStatus() = 0;

protected: 
    std::string name;
    int social;
    int energy;
};

class Scientist : public Student
{
public:
    Scientist(std::string name) : Student(name) {
        this->knowledge = 0;
    }
    ~Scientist() {}

    void doActivity() {
        if (this->energy >= 15) {
            this->knowledge += 20;
            this->energy -= 15;
            showStatus();
        } else {
            std::cout << this->name << " is too tired to study" << std::endl;
        }
    }

    int getStats() {
        return this->knowledge + this->social + this->energy;
    }

    void updateAfterContest(ContestResult result) {
        if (result == WIN) {
            this->knowledge += 10;
        } else if (result == LOSE) {
            this->energy -= 15;
        } else {
            this->energy -= 10; // TIE
        }
        showStatus();
    }

    void showStatus() {
        std::cout << "Status of " << this->name << ": Knowledge: " 
        << this->knowledge << ", Social: " << this->social << 
        ", Energy: " << this->energy << std::endl;
    }

    // getters
    int getKnowledge() {
        return this->knowledge;
    }

protected:
    int knowledge;
};

class Athlete : public Student
{
public:
    Athlete(std::string name) : Student(name) {
        this->health = 0;
    }
    ~Athlete() {}

    void doActivity() {
        if (this->energy >= 15) {
            this->health += 20;
            this->energy -= 15;
            showStatus();
        } else {
            std::cout << this->name << " is too tired to exercise." << std::endl;
            showStatus();
        }
    }

    int getStats() {
        return this->health + this->social + this->energy;
    }

    void updateAfterContest(ContestResult result) {
        if (result == WIN) {
            this->health += 10;
        } else if (result == LOSE) {
            this->energy -= 15;
        } else {
            this->energy -= 10; // TIE
        }
        showStatus();
    }

    void showStatus() {
        std::cout << "Status of " << this->name << ": Health: " 
        << this->health << ", Social: " << this->social << 
        ", Energy: " << this->energy << std::endl;
    }

    // getters
    int getHealth() {
        return this->health;
    }

protected:
    int health;
};

class Artist : public Student
{
public:
    Artist(std::string name) : Student(name) {
        this->creativity = 0;
    }
    ~Artist() {}

    void doActivity() {
        if (this->energy >= 15) {
            this->creativity += 20;
            this->energy -= 15;
            showStatus();
        } else {
            std::cout << this->name << " is too tired to work on art" << std::endl;
        }
    }

    int getStats() {
        return this->creativity + this->social + this->energy;
    }

    void updateAfterContest(ContestResult result) {
        if (result == WIN) {
            this->creativity += 10;
        } else if (result == LOSE) {
            this->energy -= 15;
        } else {
            this->energy -= 10; // TIE
        }
        showStatus();
    }

    void showStatus() {
        std::cout << "Status of " << this->name << ": Creativity: " 
        << this->creativity << ", Social: " << this->social << 
        ", Energy: " << this->energy << std::endl;
    }

    // getters
    int getCreativity() {
        return this->creativity;
    }

protected:
    int creativity;
};

class Game
{
private:
    Student *students[MAX_STUDENTS];
    int studentCount;

public:
    Game()
    {
        studentCount = 0;
    }
    ~Game()
    {
    }

    Student *getStudent(std::string name)
    {
        // Return the student whose name is {name}
        // *There is no case where two students have the same name
        // *There is no case where the student with the given name does not exist
        for (int i = 0; i < studentCount; i++) {
            if (students[i]->getName() == name) {
                return students[i];
            }
        }
        return nullptr;
    }

    void trainStudent()
    {
        // 1. Ask the user to enter the student name
        // 2. Display the training menu and ask user to choose an activity
        // 3. Process the chosen activity (refer to example text file)
        // 4. Repeat until the user chooses to exit(5)
        // *There is no case where the student with the given name does not exist
        // *There is no case where the user enters an invalid choice
        std::string name;
        std::cout << "Enter student name: ";
        std::cin >> name;
        Student* student = getStudent(name);

        while (true)
        {
            int choice;
            std::cout << "--- Training Menu ---" << std::endl;
            std::cout << "1. Do Activity" << std::endl;
            std::cout << "2. Meet Friends" << std::endl;
            std::cout << "3. Rest" << std::endl;
            std::cout << "4. Show Status" << std::endl;
            std::cout << "5. Exit" << std::endl;
            std::cout << "Enter your choice: ";
            std::cin >> choice;
            std::cout << "----------------------" << std::endl;

            switch (choice)
            {
            case 1:
                student->doActivity();
                break;
            case 2:
                student->meetFriends();
                break;
            case 3:
                student->rest();
                break;
            case 4:
                student->showStatus();
                break;
            case 5:
                return;
            }
        }        
    }

    void addStudent()
    {
        // 1. Ask the user to enter the student type and name
        // 2. Create a new student object according to the given type and add it to the students array
        // *There is no case where the user enters an invalid student type or the name of an existing student
        // *There is no case where the student count exceeds MAX_STUDENTS
        std::string type, name;
        std::cout << "Enter student type (Scientist, Athlete, Artist): ";
        std::cin >> type;
        std::cout << "Enter student name: ";
        std::cin >> name;

        if (type == "Scientist") {
            students[studentCount] = new Scientist(name);
        } else if (type == "Athlete") {
            students[studentCount] = new Athlete(name);
        } else if (type == "Artist") {
            students[studentCount] = new Artist(name);
        }
        studentCount++;
    }

    void contestStudents()
    {
        // 1. Ask the user to enter the names of two students
        // 2. If the energy of any student is less than 15, display a message "${StudentName} is too tired to contest."
        // 3. Contest the two students
        //  - The student with higher stats wins
        //  - If the stats are equal, it's a tie
        //  - Display the result
        //    - If it is not a tie, display "${StudentName} wins!"
        //    - If it is a tie, display "It's a tie!"
        // 4. Update the stats of the students according to the result
        // *There is no case where the user enters the name of a non-existing student or the same name for both student
        std::string name1, name2;
        std::cout << "Enter first student name for the contest: ";
        std::cin >> name1;
        std::cout << "Enter second student name for the contest: ";
        std::cin >> name2;
        Student* student1 = getStudent(name1);
        Student* student2 = getStudent(name2);

        if (student1->getEnergy() < 15) {
            std::cout << name1 << " is too tired to contest." << std::endl;
            return;
        }
        if (student2->getEnergy() < 15) {
            std::cout << name2 << " is too tired to contest." << std::endl;
            return;
        }

        std::cout << "Contesting " << student1->getName() << " vs. " 
                  << student2->getName() << std::endl;

        int stats1 = student1->getStats();
        int stats2 = student2->getStats();
        ContestResult result;
        if (stats1 > stats2) {
            std::cout << name1 << " wins!" << std::endl;
            student1->updateAfterContest(WIN);
            student2->updateAfterContest(LOSE);
        } else if (stats1 < stats2) {
            std::cout << name2 << " wins!" << std::endl;
            student1->updateAfterContest(LOSE);
            student2->updateAfterContest(WIN);
        } else {
            std::cout << "It's a tie!" << std::endl;
            student1->updateAfterContest(TIE);
            student2->updateAfterContest(TIE);
        }
    }

    void showStatus()
    {
        // Display the status of all students
        for (int i = 0; i < studentCount; i++) {
            students[i]->showStatus();
        }
    }

    void run()
    {
        // 1. Display the main menu and ask user to choose an activity
        // 2. Process the chosen activity (refer to example text file)
        // 3. Repeat until the user chooses to exit(5)
        while (true)
        {
            int choice;
            std::cout << "--- Main Menu ---" << std::endl;
            std::cout << "1. Add Student" << std::endl;
            std::cout << "2. Train Student" << std::endl;
            std::cout << "3. Contest Students" << std::endl;
            std::cout << "4. Students Status" << std::endl;
            std::cout << "5. Exit" << std::endl;
            std::cout << "Enter your choice: ";
            std::cin >> choice;
            std::cout << "----------------------" << std::endl;

            switch (choice)
            {
            case 1:
                addStudent();
                break;
            case 2:
                trainStudent();
                break;
            case 3:
                contestStudents();
                break;
            case 4:
                showStatus();
                break;
            case 5:
                return;
            }
        }     
    }
};


int main()
{
    // Good Luck! :)
    Game game;
    game.run();
    return 0;
}