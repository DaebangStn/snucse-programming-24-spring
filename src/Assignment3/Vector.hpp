#pragma once

#include <cstddef>

namespace CP {
template <typename T> class Vector {
public:
  class Iterator {
  public:
    Iterator(Vector *vec);
    Iterator(Vector *vec, size_t idx);
    Iterator(const Iterator &iter);
    Iterator &operator=(const Iterator &iter);
    T &operator*();
    bool operator==(const Iterator &iter) const;
    bool operator!=(const Iterator &iter) const;
    Iterator &operator++();
    Iterator operator++(int);
    Iterator &operator--();
    Iterator operator--(int);

  private:
    Vector *vec_;
    size_t idx_;
  };

  class ReverseIterator {
  public:
    ReverseIterator(Vector *vec);
    ReverseIterator(Vector *vec, size_t idx);
    ReverseIterator(const ReverseIterator &iter);
    ReverseIterator &operator=(const ReverseIterator &iter);
    T &operator*();
    bool operator==(const ReverseIterator &iter) const;
    bool operator!=(const ReverseIterator &iter) const;
    ReverseIterator &operator++();
    ReverseIterator operator++(int);
    ReverseIterator &operator--();
    ReverseIterator operator--(int);

  private:
    Vector *vec_;
    size_t idx_;
  };

  Vector();
  ~Vector();
  Vector(const Vector &vec);
  Vector &operator=(const Vector &vec);

  T &operator[](size_t idx);

  Iterator begin();
  Iterator end();
  ReverseIterator rbegin();
  ReverseIterator rend();

  size_t size() const;
  size_t capacity() const;

  void reserve(size_t capacity);

  void push_back(T &data);
  void pop_back();

private:
  T *data_;
  size_t size_;
  size_t capacity_;
};
} // namespace CP


namespace CP {
  template<typename T>
  Vector<T>::Vector() {
    size_ = 0;
    capacity_ = 0;
    data_ = nullptr;
  }

  template<typename T>
  Vector<T>::~Vector() {
    delete[] data_;
  }

  template<typename T>
  Vector<T>::Vector(const Vector<T>& v) {
    size_ = v.size_;
    capacity_ = v.capacity_;
    data_ = new T[capacity_];
    for (size_t i = 0; i < size_; i++) {
      data_[i] = v.data_[i];
    }
  }

  template<typename T>
  T& Vector<T>::operator[](size_t idx) {
    if (idx < size_){
      return data_[idx];
    } else {
      // throw std::out_of_range("Index out of range"); // Since the exception is not allowed, we will return 0th element instead
      return data_[0];
    }
  }

  template<typename T>
  Vector<T>& Vector<T>::operator=(const Vector<T>& v) {
    if (this == &v) {
      return *this;
    }
    delete[] data_;
    size_ = v.size_;
    capacity_ = v.capacity_;
    data_ = new T[capacity_];
    for (size_t i = 0; i < size_; i++) {
      data_[i] = v.data_[i];
    }
    return *this;
  }

  template<typename T>
  void Vector<T>::push_back(T& data) {
    if (size_ >= capacity_) {
      reserve(capacity_ == 0 ? 1 : capacity_ * 2);
    }
    data_[size_++] = data;
  }

  template<typename T>
  void Vector<T>::pop_back() {
    if (size_ != 0){
      size_--;
    } else {
      // throw std::out_of_range("Index out of range");  // Since the exception is not allowed, we will do nothing instead
    }
  }

  template<typename T>
  size_t Vector<T>::size() const {
    return size_;
  }

  template<typename T>
  size_t Vector<T>::capacity() const {
    return capacity_;
  }

  template<typename T>
  void Vector<T>::reserve(size_t capacity) {
    T* new_data = new T[capacity];
    for (size_t i = 0; i < size_; i++) {
      new_data[i] = data_[i];
    }
    delete[] data_;
    data_ = new_data;
    capacity_ = capacity;
  }

  template<typename T>
  Vector<T>::Iterator::Iterator(Vector* vec) : vec_(vec), idx_(0) {}

  template<typename T>
  Vector<T>::Iterator::Iterator(Vector* vec, size_t idx) : vec_(vec), idx_(idx) {}

  template<typename T>
  Vector<T>::Iterator::Iterator(const Iterator& iter) : vec_(iter.vec_), idx_(iter.idx_) {}

  template<typename T>
  typename Vector<T>::Iterator& Vector<T>::Iterator::operator=(const Iterator& iter) {
    if (this == &iter) {
      return *this;
    }
    vec_ = iter.vec_;
    idx_ = iter.idx_;
    return *this;
  }
  
  template<typename T>
  T& Vector<T>::Iterator::operator*() {
    return vec_->data_[idx_];
  }

  template<typename T>
  bool Vector<T>::Iterator::operator==(const Iterator& iter) const {
    return vec_ == iter.vec_ && idx_ == iter.idx_;
  }

  template<typename T>
  bool Vector<T>::Iterator::operator!=(const Iterator& iter) const {
    return !(*this == iter);
  }

  template<typename T>
  typename Vector<T>::Iterator& Vector<T>::Iterator::operator++() {
    idx_++;
    return *this;
  }

  template<typename T>
  typename Vector<T>::Iterator Vector<T>::Iterator::operator++(int) {
    Iterator temp = *this;
    idx_++;
    return temp;
  }

  template<typename T>
  typename Vector<T>::Iterator& Vector<T>::Iterator::operator--() {
    idx_--;
    return *this;
  }

  template<typename T>
  typename Vector<T>::Iterator Vector<T>::Iterator::operator--(int) {
    Iterator temp = *this;
    idx_--;
    return temp;
  }

  template<typename T>
  typename Vector<T>::Iterator Vector<T>::begin() {
    return Iterator(this);
  }

  template<typename T>
  typename Vector<T>::Iterator Vector<T>::end() {
    return Iterator(this, size_);
  }

  template<typename T>
  Vector<T>::ReverseIterator::ReverseIterator(Vector* vec) : vec_(vec), idx_(vec->size_ - 1) {}

  template<typename T>
  Vector<T>::ReverseIterator::ReverseIterator(Vector* vec, size_t idx) : vec_(vec), idx_(idx) {}

  template<typename T>
  Vector<T>::ReverseIterator::ReverseIterator(const ReverseIterator& iter) : vec_(iter.vec_), idx_(iter.idx_) {}

  template<typename T>
  typename Vector<T>::ReverseIterator& Vector<T>::ReverseIterator::operator=(const ReverseIterator& iter) {
    if (this == &iter) {
      return *this;
    }
    vec_ = iter.vec_;
    idx_ = iter.idx_;
    return *this;
  }

  template<typename T>
  T& Vector<T>::ReverseIterator::operator*() {
    return vec_->data_[idx_];
  }

  template<typename T>
  bool Vector<T>::ReverseIterator::operator==(const ReverseIterator& iter) const {
    return vec_ == iter.vec_ && idx_ == iter.idx_;
  }

  template<typename T>
  bool Vector<T>::ReverseIterator::operator!=(const ReverseIterator& iter) const {
    return !(*this == iter);
  }

  template<typename T>
  typename Vector<T>::ReverseIterator& Vector<T>::ReverseIterator::operator++() {
    idx_--;
    return *this;
  }

  template<typename T>
  typename Vector<T>::ReverseIterator Vector<T>::ReverseIterator::operator++(int) {
    ReverseIterator temp = *this;
    idx_--;
    return temp;
  }

  template<typename T>
  typename Vector<T>::ReverseIterator& Vector<T>::ReverseIterator::operator--() {
    idx_++;
    return *this;
  }

  template<typename T>
  typename Vector<T>::ReverseIterator Vector<T>::ReverseIterator::operator--(int) {
    ReverseIterator temp = *this;
    idx_++;
    return temp;
  }

  template<typename T>
  typename Vector<T>::ReverseIterator Vector<T>::rbegin() {
    return ReverseIterator(this);
  }

  template<typename T>
  typename Vector<T>::ReverseIterator Vector<T>::rend() {
    return ReverseIterator(this, -1);
  }
}