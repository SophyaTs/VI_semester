#pragma once

#include <iostream>
#include <vector>

#include "Token.hpp"

namespace lexer {
class Lexer {
   public:
    Lexer() = default;
    Lexer(const Lexer&) = default;

    std::vector<Token> parse(const std::string filename);

   //private:
};

bool is_letter(const char c);
bool is_digit(const char c);
bool is_whitespace(const char c);
bool is_punctuation(const char c);
bool is_symbol(const char c);
bool is_allowed(const char c);

}  //namespace lexer