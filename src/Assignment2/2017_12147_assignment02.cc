#include <iostream>
#include <string>
#include <queue>
#include <stack>

using namespace std;

/**
 * Linter class
 */
#ifndef LINTER_H_
#define LINTER_H_

class Linter {
private:
    void setQ(const string& str_in);
    string getQ();
protected:
    const char TAB;
    const char NEWLINE;
    queue<char> q;
    void setError(string str_in);
public:
    explicit Linter(const string& str_in);
    virtual ~Linter() {}
    void print(const int& lint_status);
    virtual int lint() = 0;
};

#endif // LINTER_H_

Linter::Linter(const string& str_in): NEWLINE('\n'), TAB('\t') {
    setQ(str_in);
}

void Linter::setQ(const string &str_in) {
    queue<char> tmp_q;
    for (char c : str_in) {
        tmp_q.push(c);
    }
    this->q = tmp_q;
}

string Linter::getQ() {
    string res = "";
    int q_size = q.size();
    for (int i = 0; i < q_size; i++) {
        res += q.front();
        q.push(q.front());
        q.pop();
    }
    return res;
}

void Linter::print(const int& lint_status) {
    if (lint_status == 0) {
        cout << getQ() << endl;
    } else {
        cerr << getQ() << endl;
    }
}

void Linter::setError(string str_in = "error: invalid parentheses") {
    setQ(str_in);
}

/**
 * StackLinter class
 */
#ifndef LV1_STACK_LINTER_H_
#define LV1_STACK_LINTER_H_

class StackLinter : public Linter {
private:
    stack<char> stk;
public:
    explicit StackLinter(const string& str_in) : Linter(str_in) {}
    int lint() override;
};

#endif // LV1_STACK_LINTER_H_

int StackLinter::lint() {
    // Complete `lint` function for `StackLinter` class
    // below the code (setError(...) and return 1;) are just examples
    // delete these two lines and start implementing
    int length = q.size();
    int level = 0;
    for (int i = 0; i < length; i++) {
        char c = q.front();
        q.pop();
        if (c == '{' || c == '[' || c == '(') {
            stk.push(c);

            for (int j = 0; j < level; j++) {
                q.push(TAB);
            }

            q.push(c);

            level++;
            q.push(NEWLINE);
        } else if (c == '}' || c == ']' || c == ')') {
            if (stk.empty()) {
                setError("error: invalid parentheses");
                return 1;
            }
            char top = stk.top();
            if ((c == '}' && top == '{') || (c == ']' && top == '[') || 
                (c == ')' && top == '(')) {
                stk.pop();
                level--;
                for (int j = 0; j < level; j++) {
                    q.push(TAB);
                }

                q.push(c);

                if (i != length - 1) {
                    q.push(NEWLINE);
                }            } else {
                setError("error: invalid parentheses");
                return 1;
            }
        }
    }
    if (!stk.empty()) {
        setError("error: invalid parentheses");
        return 1;
    }
    return 0;
}

/**
 * QueueLinter class
 */
#ifndef LV2_QUEUE_LINTER_H_
#define LV2_QUEUE_LINTER_H_

class QueueLinter : public Linter {
private:
    queue<char> q_stk;
public:
    explicit QueueLinter(const string& str_in) : Linter(str_in) {}
    int lint() override;
};

#endif // LV2_QUEUE_LINTER_H_

int QueueLinter::lint() {
    // Complete `lint` function for `QueueLinter` class
    // below the code (setError(...) and return 1;) are just examples
    // delete these two lines and start implementing
    int length = q.size();
    int level = 0;
    for (int i = 0; i < length; i++) {
        char c = q.front();
        q.pop();
        if (c == '{' || c == '[' || c == '(') {
            q_stk.push(c);

            for (int j = 0; j < level; j++) {
                q.push(TAB);
            }

            q.push(c);

            level++;
            q.push(NEWLINE);
        } else if (c == '}' || c == ']' || c == ')') {
            if (q_stk.empty()) {
                setError("error: invalid parentheses");
                return 1;
            }
            char top = q_stk.back();
            if ((c == '}' && top == '{') || (c == ']' && top == '[') || 
                (c == ')' && top == '(')) {
                int len = q_stk.size();
                for (int j = 0; j < len - 1; j++) {
                    q_stk.push(q_stk.front());
                    q_stk.pop();
                }
                q_stk.pop();

                level--;
                for (int j = 0; j < level; j++) {
                    q.push(TAB);
                }

                q.push(c);

                if (i != length - 1) {
                    q.push(NEWLINE);
                }
            } else {
                setError("error: invalid parentheses");
                return 1;
            }
        }
    }
    if (!q_stk.empty()) {
        setError("error: invalid parentheses");
        return 1;
    }
    return 0;
}

/**
 * main func
 */
int main() {
    cout << "Input: ";
    string in;
    getline(cin, in);

    cout << "----- StackLinter -----" << endl;
    Linter* stk_linter = new StackLinter(in);
    int stk_lint_result = stk_linter->lint();
    stk_linter->print(stk_lint_result);
    delete stk_linter;

    cout << "----- QueueLinter -----" << endl;
    Linter* q_linter = new QueueLinter(in);
    int q_lint_result = q_linter->lint();
    q_linter->print(q_lint_result);
    delete q_linter;

    return stk_lint_result + q_lint_result;
}
