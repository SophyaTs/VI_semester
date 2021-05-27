#pragma once

#include <iostream>
#include <string>
#include <map>

namespace lexer {
class Token {
   public:
    enum Type {
        PUNCTUATION,
        OPERATOR,
        PREPROC_DIRECTIVE,
        KEYWORD,
        IDENTIFIER,
        IDENTIFIER_TYPE,
        LITERAL,
        CONST,
        COMMENT_OL,
        COMMENT_ML,
        ERROR
    };

    static const inline std::map<Type, std::string> string_view = {
        {Token::Type::PUNCTUATION, "punctuation"},
        {Token::Type::OPERATOR, "operator"},
        {Token::Type::PREPROC_DIRECTIVE, "preprocessor directive"},
        {Token::Type::KEYWORD, "keyword"},
        {Token::Type::IDENTIFIER, "identifier"},
        {Token::Type::IDENTIFIER_TYPE, "identifier (type)"},
        {Token::Type::LITERAL, "literal"},
        {Token::Type::CONST, "const"},
        {Token::Type::COMMENT_OL, "comment (one line)"},
        {Token::Type::COMMENT_ML, "comment (multiline)"},
        {Token::Type::ERROR, "error"}};

    Token(const Token& t) = default;
    Token(Type type, std::string value):
        type{type},
        value{value}
    {}

    void set_type(Type type) {
        this->type = type;
    }
    void set_value(std::string value) {
        this->value = value;
    }
    Type get_type() const {
        return this->type;
    }
    std::string get_value() const {
        return this->value;
    }

    bool is_standart_type() const {
        if (this->type != KEYWORD) {
            return false;
        }
        if (this->value == "int" 
        || this->value == "long" 
        || this->value == "short" 
        || this->value == "float" 
        || this->value == "double" 
        || this->value == "unsigned" 
        || this->value == "char" 
        || this->value == "void" 
        || this->value == "bool"){
            return true;
        }
        return false;
    }

    friend std::ostream& operator<<(std::ostream& os, const Token& t) {
        os << string_view.at(t.get_type()) << ": " << t.get_value();
        return os;
    }

    private : 
    Type type;
    std::string value;
};

}  //namespace lexer