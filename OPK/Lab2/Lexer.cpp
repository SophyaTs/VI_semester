#include "Lexer.hpp"

#include <fstream>
#include <functional>
#include <memory>
#include <set>
#include <tuple>

#include "FiniteAutomata.hpp"

namespace lexer {

std::vector<Token> Lexer::parse(const std::string filename) {
    std::filebuf fb;
    if (fb.open(filename, std::ios::in)) {
        std::istream is(&fb);
        auto tokens = std::vector<Token>();
        auto custom_types = std::set<std::string>();
        bool revise_c = false;
        char c;
        while (is) {
            if(!revise_c){
                c = is.get();                
            }
            if (c == -1 || (tokens.size()>0 && tokens[tokens.size()-1].get_type()== Token::Type::ERROR)) {
                return tokens;
            }

            if(!is_allowed(c)) {
                std::string message = "tokenization error: forbibben symbol ";
                message.push_back(c);
                tokens.emplace_back(Token(Token::Type::ERROR, message));
                std::cout << int(c) << std::endl;
                return tokens;
            }

            if (is_whitespace(c)) {
                revise_c = false;
                continue;
            }
            if (is_punctuation(c)) {
                revise_c = false;
                tokens.emplace_back(Token(Token::Type::PUNCTUATION, std::string(1,c) ));
                continue;
            }
            if (is_letter(c)) {
                auto keywords = std::vector<std::string>();
                switch (c) {
                    case 'a':
                        keywords = {
                            "auto",
                        };
                        break;
                    case 'b':
                        keywords = {
                            "break",
                            "bool"
                        };
                        break;
                    case 'c':
                        keywords = {
                            "char",
                            "case",
                            "const",
                            "catch",
                            "class",
                            "continue",
                            "const_cast",
                        };
                        break;
                    case 'd':
                        keywords = {
                            "do",
                            "double",
                            "delete",
                            "default",
                            "dynamic_cast",
                        };
                        break;
                    case 'e':
                        keywords = {
                            "enum",
                            "else"
                        };
                        break;
                    case 'f':
                        keywords = {
                            "for",
                            "friend",
                            "float",
                            "false"
                        };
                        break;
                    case 'g':
                        keywords = {
                            "goto"
                        };
                        break;
                    case 'i':
                        keywords = {
                            "if",
                            "int",
                            "inline"
                        };
                        break;
                    case 'l':
                        keywords = {
                            "long",
                        };
                        break;
                    case 'n':
                        keywords = {
                            "nullptr",
                            "new",
                            "namespace",
                            "noexcept",
                        };
                        break;
                    case 'o':
                        keywords = {
                            "operator"
                        };
                        break;
                    case 'p':
                        keywords = {
                            "public",
                            "protected",
                            "private"
                        };
                        break;                   
                    case 'r':
                        keywords = {
                            "return",
                            "reinterpret_cast"
                        };
                        break;
                    case 's':
                        keywords = {
                            "static",
                            "switch",
                            "short",
                            "struct",
                            "sizeof",
                            "static_cast"
                        };
                        break;
                    case 't':
                        keywords = {
                            "try",
                            "this",
                            "template",
                            "throw",
                            "true",
                        };
                        break;
                    case 'u':
                        keywords = {
                            "unsigned",
                            "union",
                            "using",
                        };
                        break;
                    case 'v':
                        keywords = {
                            "void",
                            "virtual",
                            "volatile",
                        };
                        break;
                    case 'w':
                        keywords = {
                            "while",
                            "wchar_t",
                        };
                        break;
                }
                auto fa = LetterFiniteAutomata(false, custom_types, keywords);
                std::tie(revise_c, c) = fa.recognize(is, tokens, c);
                continue;
            }
            if (is_digit(c)) {
                std::tie(revise_c, c) = NumberFiniteAutomata().recognize(is, tokens, c);
                continue;
            }
            if (is_symbol(c)) {
                if (c == '\'' || c == '\"') {
                    auto fa = LetterFiniteAutomata(true,custom_types);
                    std::tie(revise_c, c) = fa.recognize(is, tokens, c);
                } else {
                    std::tie(revise_c, c) = OperatorFiniteAutomata().recognize(is, tokens, c);
                }
                continue;
            }
            
        }
        fb.close();
        return tokens;
    }
}

bool is_letter(const char c) {
    return (c >= 'A' && c <= 'Z') || (c >= 'a' && c <= 'z') || c == '_';
}
bool is_digit(const char c) {
    return c >= '0' && c <= '9';
}
bool is_whitespace(const char c) {
    switch (c) {
        case ' ':
        case '\t':
        case '\r':
        case '\n':
            return true;
        default:
            return false;
    }
}
bool is_punctuation(const char c) {
    switch (c) {
        case '{':
        case '}':
        case '(':
        case ')':
        case '[':
        case ']':
        case ';':
        case ',':
            return true;
        default:
            return false;
    }
}
bool is_symbol(const char c) {
    switch (c) {       
        case '%':
        case '#':
        case '~':
        case '\'':
        case '\"':
        case '?':
        case '!':
        case '>':
        case '<':
        case '=':
        case '|':
        case '^':
        case '&':
        case '+':
        case '-':
        case '*':
        case '\\':
        case '/':
        case '.':
        case ':':
            return true;
        default:
            return false;
    }
}
bool is_allowed(const char c) {
    return is_letter(c) || is_digit(c) || is_punctuation(c) || is_symbol(c) || is_whitespace(c);
}
}  // namespace lexer