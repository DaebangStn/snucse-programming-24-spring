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
    Student(std::string name) {}
    virtual ~Student() {}

    void rest()
    {
        // 1. Resting increases energy by 50
        // 2. Display the status of the student
    }

    void meetFriends()
    {
        // 1. If energy is greater than or equal to 15, increase social by 20 and decrease energy by 15
        // 2. If energy is less than 15, display a message "${StudentName} is too tired to meet friends."
        // 3. Display the status of the student
        // *There is no case of overflow
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
    }

    void trainStudent()
    {
        // 1. Ask the user to enter the student name
        // 2. Display the training menu and ask user to choose an activity
        // 3. Process the chosen activity (refer to example text file)
        // 4. Repeat until the user chooses to exit(5)
        // *There is no case where the student with the given name does not exist
        // *There is no case where the user enters an invalid choice
    }

    void addStudent()
    {
        // 1. Ask the user to enter the student type and name
        // 2. Create a new student object according to the given type and add it to the students array
        // *There is no case where the user enters an invalid student type or the name of an existing student
        // *There is no case where the student count exceeds MAX_STUDENTS
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
    }

    void showStatus()
    {
        // Display the status of all students
    }

    void run()
    {
        // 1. Display the main menu and ask user to choose an activity
        // 2. Process the chosen activity (refer to example text file)
        // 3. Repeat until the user chooses to exit(5)
    }
};

int main()
{
    // Good Luck! :)
    Game game;
    game.run();
    return 0;
}