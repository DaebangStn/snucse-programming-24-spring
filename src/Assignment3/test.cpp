#include "Vector.hpp"
#include <iostream>
#include <stdexcept>
#include <cassert>

using namespace CP;

template class Vector<int>;

void printVector(Vector<int>& v) {
  std::cout << "Vector: ";
  for (Vector<int>::Iterator it = v.begin(); it != v.end(); ++it) {
    std::cout << *it << " ";
  }
  std::cout << std::endl;
}

void testBasicFunctionality() {
  Vector<int> v;
  std::cout << "Testing basic functionality" << std::endl;

  // Test push_back and initial capacity
  for (int i = 1; i <= 5; ++i) {
    v.push_back(i);
  }
  assert(v.size() == 5);
  assert(v.capacity() >= 5);
  printVector(v);

  // Test operator[]
  assert(v[2] == 3);
  v[2] = 10;
  assert(v[2] == 10);

  // Test pop_back
  v.pop_back();
  assert(v.size() == 4);
  printVector(v);

  // Test reverse iteration
  std::cout << "Testing reverse iteration" << std::endl;
  std::cout << "Reverse Vector: ";
  for (Vector<int>::ReverseIterator it = v.rbegin(); it != v.rend(); ++it) {
    std::cout << *it << " ";
  }
  std::cout << std::endl;

  // Test reserve and capacity expansion
  v.reserve(10);
  assert(v.capacity() >= 10);

  // Clear vector
  while (v.size() > 0) {
    v.pop_back();
  }
  assert(v.size() == 0);
  assert(v.capacity() >= 10);  // Capacity should not decrease
  printVector(v);
}

void testCopyAndAssignment() {
  std::cout << "Testing copy constructor and assignment operator" << std::endl;

  Vector<int> v1;
  for (int i = 1; i <= 5; ++i) {
    v1.push_back(i);
  }
  printVector(v1);

  // Test copy constructor
  Vector<int> v2(v1);
  printVector(v2);
  assert(v2.size() == v1.size());
  for (size_t i = 0; i < v1.size(); ++i) {
    assert(v1[i] == v2[i]);
  }

  // Test assignment operator
  Vector<int> v3;
  v3 = v1;
  printVector(v3);
  assert(v3.size() == v1.size());
  for (size_t i = 0; i < v1.size(); ++i) {
    assert(v1[i] == v3[i]);
  }

  // Modify v1 and check v2, v3
  v1[0] = 100;
  assert(v1[0] == 100);
  assert(v2[0] == 1);
  assert(v3[0] == 1);
}

void testLargeData() {
  std::cout << "Testing large data set" << std::endl;

  Vector<int> v;
  const int largeSize = 1000;
  for (int i = 0; i < largeSize; ++i) {
    v.push_back(i);
  }
  assert(v.size() == largeSize);
  assert(v.capacity() >= largeSize);

  // Check contents
  for (int i = 0; i < largeSize; ++i) {
    assert(v[i] == i);
  }

  // Performance check
  v.reserve(2000);
  assert(v.capacity() >= 2000);

  std::cout << "Large data set test passed" << std::endl;
}

void testBoundaryConditions() {
  std::cout << "Testing boundary conditions" << std::endl;

  Vector<int> v;
  try {
    v.pop_back();  // Should handle gracefully
    std::cout << "Handled pop_back on empty vector" << std::endl;
  } catch (const std::out_of_range& e) {
    std::cerr << "Exception caught: " << e.what() << std::endl;
  }

  // Add and remove elements
  int val = 1;
  v.push_back(val);
  v.pop_back();
  assert(v.size() == 0);
  std::cout << "Added and removed element successfully" << std::endl;

  // Test iterator on empty vector
  assert(v.begin() == v.end());
  std::cout << "Boundary conditions test passed" << std::endl;
}

void testIteratorValidity() {
  std::cout << "Testing iterator validity" << std::endl;

  Vector<int> v;
  for (int i = 0; i < 10; ++i) {
    v.push_back(i);
  }
  printVector(v);

  Vector<int>::Iterator it = v.begin();
  assert(*it == 0);
  ++it;
  assert(*it == 1);

  // Modify vector and check iterator validity
  v.pop_back();
  int val = 100;
  v.push_back(val);
  assert(*it == 1);  // Iterator should still be valid and point to correct element

  printVector(v);
  std::cout << "Iterator validity test passed" << std::endl;
}

int main() {
  testBasicFunctionality();
  testCopyAndAssignment();
  testLargeData();
  testBoundaryConditions();
  testIteratorValidity();

  std::cout << "All tests passed successfully!" << std::endl;
  return 0;
}
