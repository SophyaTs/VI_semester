#include "Lexer.hpp"
#include <iostream>
#include <vector>

template <typename T>
std::ostream& operator<<(std::ostream& os, const std::vector<T>& v) {
    os << "[";
    for (int i = 0; i < v.size(); ++i) {
        os << v[i];
        if (i != v.size() - 1)
            os << "\n";
    }
    os << "]\n";
    return os;
}

int main() {
    auto l = lexer::Lexer();
    auto tokens = l.parse("example.cpp");
    std::cout << tokens << std::endl;
    return 0;
}