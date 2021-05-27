#include "FiniteAutomata.hpp"

#include <tuple>

namespace lexer {
NumberFiniteAutomata::NumberFiniteAutomata(){}
OperatorFiniteAutomata::OperatorFiniteAutomata(){}

int FiniteAutomata::last_non_comment_token(std::vector<Token>& tokens) {
    if (tokens.size() == 0) {
        return -1;
    }
    for (int i = tokens.size() - 1; i >= 0; i--) {
        if (!(tokens[i].get_type() == Token::Type::COMMENT_OL 
        || tokens[i].get_type() == Token::Type::COMMENT_ML 
        || (tokens[i].get_type() == Token::Type::PUNCTUATION && (tokens[i].get_value() == "/*" || tokens[i].get_value() == "*/" || tokens[i].get_value() == "//"))))
            return i;
    }
    return -1;
}

std::pair<bool, char> NumberFiniteAutomata::recognize(std::istream& is,
                                                      std::vector<Token>& tokens,
                                                      const char c0) {
    std::string value;
    value.push_back(c0);
    State state = NATURAL;
    while (is) {
        char c = is.get();

        switch (state) {
            case NATURAL:
                if (c == '.') {
                    value.push_back(c);
                    state = FLOAT;
                    continue;
                }
                if (is_digit(c)) {
                    value.push_back(c);
                    continue;
                }
                if (c == 'e') {
                    value.push_back(c);
                    state = WITH_E;
                    continue;
                }
                if (is_whitespace(c) || is_symbol(c) || is_punctuation(c) || c == -1) {
                    tokens.emplace_back(Token::Type::LITERAL, value);
                    return {true, c};
                }
                tokens.emplace_back(Token(Token::Type::ERROR, "Invalid number literal"));
                return {false, c};
            case FLOAT:
                if (is_digit(c)) {
                    value.push_back(c);
                    continue;
                }
                if (c == 'e') {
                    value.push_back(c);
                    state = WITH_E;
                    continue;
                }
                if (is_whitespace(c) || (is_symbol(c) && c != '.') || is_punctuation(c) || c == -1) {
                    tokens.emplace_back(Token::Type::LITERAL, value);
                    return {true, c};
                }
                tokens.emplace_back(Token(Token::Type::ERROR, "Invalid number literal"));
                return {false, c};
            case WITH_E:
                if (c == '-' || c == '+') {
                    value.push_back(c);
                    state = WITH_E_AND_SIGN;
                    continue;
                }
                if (is_digit(c)) {
                    value.push_back(c);
                    state = WITH_E_WITHOUT_SIGN;
                    continue;
                }
                tokens.emplace_back(Token(Token::Type::ERROR, "Invalid number literal"));
                return {false, c};
            case WITH_E_AND_SIGN:
            case WITH_E_WITHOUT_SIGN:
                if (is_digit(c)) {
                    value.push_back(c);
                    continue;
                }
                if (is_whitespace(c) || is_punctuation(c) || (is_symbol(c) && c != '.') || c == -1) {
                    tokens.emplace_back(Token::Type::LITERAL, value);
                    return {true, c};
                }
                tokens.emplace_back(Token(Token::Type::ERROR, "Invalid number literal"));
                return {false, c};
        }
    }

    if (state != WITH_E) {
        tokens.emplace_back(Token::Type::LITERAL, value);
        return {false, 0};
    } else {
        tokens.emplace_back(Token(Token::Type::ERROR, "Invalid number literal"));
        return {false, 0};
    }
}

std::pair<bool, char> OperatorFiniteAutomata::recognize(std::istream& is,
                                                        std::vector<Token>& tokens,
                                                        const char c0) {
    std::string value;
    state = 0;
    char c = c0;
    bool resume_reading;
    bool revise;
    std::tie(resume_reading, revise) = move(tokens, value, c);

    while(is && resume_reading) {
        c = is.get();

        std::tie(resume_reading, revise) = move(tokens, value, c);
    }

    return {revise,c};
}
std::pair<OperatorFiniteAutomata::resume, OperatorFiniteAutomata::revise_current_symbol> OperatorFiniteAutomata::move(std::vector<Token>& tokens,
                                                                                                                      std::string& value,
                                                                                                                      const char c) 
{
    int i;
    switch (state) {
        case 0:
            switch (c) {
                case '+':
                    value.push_back(c);
                    state = 1;
                    return {true, false};
                case '-':
                    value.push_back(c);
                    state = 5;
                    return {true, false};
                case '*':
                    value.push_back(c);
                    state = 10;
                    return {true, false};
                case '/':
                    value.push_back(c);
                    state = 13;
                    return {true, false};
                case '|':
                    value.push_back(c);
                    state = 21;
                    return {true, false};
                case '&':
                    value.push_back(c);
                    state = 24;
                    return {true, false};
                case '^':
                    value.push_back(c);
                    state = 27;
                    return {true, false};
                case '<':
                    value.push_back(c);
                    state = 30;
                    return {true, false};
                case '>':
                    value.push_back(c);
                    state = 34;
                    return {true, false};
                case '!':
                    value.push_back(c);
                    state = 38;
                    return {true, false};
                case '#':
                    value.push_back(c);
                    state = 41;
                    return {true, false};
                case '~':
                    value.push_back(c);
                    state = 42;
                    tokens.emplace_back(Token::Type::OPERATOR, value);
                    return {false, false};
                case '%':
                    value.push_back(c);
                    state = 43;
                    tokens.emplace_back(Token::Type::OPERATOR, value);
                    return {false, false};
                case '?':
                    value.push_back(c);
                    state = 44;
                    tokens.emplace_back(Token::Type::OPERATOR, value);
                    return {false, false};
                case '.':
                    value.push_back(c);
                    state = 45;
                    tokens.emplace_back(Token::Type::OPERATOR, value);
                    return {false, false};
                case ':':
                    value.push_back(c);
                    state = 46;
                    return {true, false};
                case '=':
                    value.push_back(c);
                    state = 49;
                    return {true, false};
                default:
                    tokens.emplace_back(Token::Type::ERROR, "unrecognized token");
                    return {false, false};
            }
        case 1:
            //case 2
            if (c == '+') {
                value.push_back(c);
                auto i = last_non_comment_token(tokens);
                if (i == -1) {
                    tokens.emplace_back(Token::Type::OPERATOR, value + "(infix)");
                    return {false, false};
                } else {
                    auto last_token = tokens[i];
                    if (last_token.get_type() == Token::Type::IDENTIFIER 
                    || last_token.get_type() == Token::Type::CONST 
                    || (last_token.get_type() == Token::Type::PUNCTUATION && (last_token.get_value() == "]" || last_token.get_value() == ")"))) 
                    {
                        tokens.emplace_back(Token::Type::OPERATOR, value + "(postfix)");
                        return {false, false};
                    } else {
                        tokens.emplace_back(Token::Type::OPERATOR, value + "(infix)");
                        return {false, false};
                    }
                }
            }
            //case 3
            if (c == '=') {
                value.push_back(c);
                tokens.emplace_back(Token::Type::OPERATOR, value);
                return {false, false};
            }

            //case 4
            tokens.emplace_back(Token::Type::OPERATOR, value);
            return {false, true};

        case 5:
            //case 6
            if (c == '-') {
                value.push_back(c);
                auto i = last_non_comment_token(tokens);
                if (i == -1) {
                    tokens.emplace_back(Token::Type::OPERATOR, value + "(infix)");
                    return {false, false};
                } else {
                    auto last_token = tokens[i];
                    if (last_token.get_type() == Token::Type::IDENTIFIER 
                    || last_token.get_type() == Token::Type::CONST 
                    || (last_token.get_type() == Token::Type::PUNCTUATION && (last_token.get_value() == "]" || last_token.get_value() == ")"))) 
                    {
                        tokens.emplace_back(Token::Type::OPERATOR, value + "(postfix)");
                        return {false, false};
                    } else {
                        tokens.emplace_back(Token::Type::OPERATOR, value + "(infix)");
                        return {false, false};
                    }
                }
            }
            //case 7 and case 8
            if (c == '=' || c == '>') {
                value.push_back(c);
                tokens.emplace_back(Token::Type::OPERATOR, value);
                return {false, false};
            }

            //case 9
            tokens.emplace_back(Token::Type::OPERATOR, value);
            return {false, true};

        case 10:
            //case 11
            if (c == '=') {
                value.push_back(c);
                tokens.emplace_back(Token::Type::OPERATOR, value);
                return {false, false};
            }

            //case 12
            i = last_non_comment_token(tokens);
            if (i == -1) {
                tokens.emplace_back(Token::Type::OPERATOR, value + "(adress)");
            } else {
                auto last_token = tokens[i];
                if (last_token.is_standart_type() 
                || last_token.get_type() == Token::Type::IDENTIFIER 
                || last_token.get_type() == Token::Type::CONST 
                || last_token.get_type() == Token::Type::LITERAL 
                || (last_token.get_type() == Token::Type::PUNCTUATION && (last_token.get_value() == "]" || last_token.get_value() == ")"))) {
                    tokens.emplace_back(Token::Type::OPERATOR, value);
                } else {
                    tokens.emplace_back(Token::Type::OPERATOR, value + "(pointer)");
                }
            }
            return {false, true};

        case 38:
            //case 39
            if (c == '=') {
                value.push_back(c);
                tokens.emplace_back(Token::Type::OPERATOR, value);
                return {false, false};
            }

            //case 40
            tokens.emplace_back(Token::Type::OPERATOR, value);
            return {false, true};

        case 46:
            //case 47
            if (c == ':') {
                value.push_back(c);
                tokens.emplace_back(Token::Type::OPERATOR, value);
                return {false, false};
            }

            //case 48
            tokens.emplace_back(Token::Type::PUNCTUATION, value);
            return {false, true};

        case 21:
        case 27:
        case 49:
            //case 22,28
            if (c == value[0]) {
                value.push_back(c);
                tokens.emplace_back(Token::Type::OPERATOR, value);
                return {false, false};
            }

            //case 23,29
            tokens.emplace_back(Token::Type::OPERATOR, value);
            return {false, true};

        case 24:
            //case 25
            if (c == value[0]) {
                value.push_back(c);
                tokens.emplace_back(Token::Type::OPERATOR, value);
                return {false, false};
            }

            //case 26
            i = last_non_comment_token(tokens);
            if (i == -1) {
                tokens.emplace_back(Token::Type::OPERATOR, value + "(adress)");                
            } else {
                auto last_token = tokens[i];
                if (last_token.is_standart_type()
                || last_token.get_type() == Token::Type::IDENTIFIER 
                || last_token.get_type() == Token::Type::CONST 
                || last_token.get_type() == Token::Type::LITERAL
                || (last_token.get_type() == Token::Type::PUNCTUATION && (last_token.get_value() == "]" || last_token.get_value() == ")"))) 
                {
                    tokens.emplace_back(Token::Type::OPERATOR, value + "(bitwise AND)");
                } else {
                    tokens.emplace_back(Token::Type::OPERATOR, value + "(adress)");
                }
            }
            return {false, true};

        case 30:
            //case 31, 32
            if (c == '<' || c == '=') {
                value.push_back(c);
                tokens.emplace_back(Token::Type::OPERATOR, value);
                return {false, false};
            }

            //case 33
            i = last_non_comment_token(tokens);
            if (i == -1) {
                tokens.emplace_back(Token::Type::OPERATOR, value);                
            } else {
                auto token = tokens[i];
                if (token.get_type() == Token::Type::IDENTIFIER_TYPE
                || (token.get_type() == Token::Type::KEYWORD && token.get_value() == "template"))
                {
                    tokens.emplace_back(Token::Type::PUNCTUATION, value);
                } else {
                    tokens.emplace_back(Token::Type::OPERATOR, value);
                }
            }
            return {false, true};
        
        case 34:
            //case 35, 36
            if (c == '>' || c == '=') {
                value.push_back(c);
                tokens.emplace_back(Token::Type::OPERATOR, value);
                return {false, false};
            }

            //case 37
            i = last_non_comment_token(tokens);
            if (i == -1) {
                tokens.emplace_back(Token::Type::OPERATOR, value);
            } else {
                auto token = tokens[i];
                if (token.get_type() == Token::Type::IDENTIFIER_TYPE 
                || check_template(tokens)) {
                    tokens.emplace_back(Token::Type::PUNCTUATION, value);
                } else {
                    tokens.emplace_back(Token::Type::OPERATOR, value);
                }
            }
            return {false, true};

        case 41:
            value.push_back(c);
            state = 52;
            return {true, false};
        case 52:
            if (c == '\n' || c == -1){
                tokens.emplace_back(Token::Type::PREPROC_DIRECTIVE, value);
                return {false, false};
            }
            value.push_back(c);
            return {true, false};

        case 13:
            if (c == '*') {
                tokens.emplace_back(Token::Type::PUNCTUATION, "/*");
                value.clear();
                state = 14;
                return {true, false};
            }
            if (c == '/') {
                tokens.emplace_back(Token::Type::PUNCTUATION, "//");
                value.clear();
                state = 17;
                return {true, false};
            }
            
            //case 19
            if (c == '=') {
                value.push_back(c);
                tokens.emplace_back(Token::Type::OPERATOR, value);
                return {false, false};
            }

            //case 20
            tokens.emplace_back(Token::Type::OPERATOR, value);
            return {false, true};
        case 14:
            if (c == -1) {
                tokens.emplace_back(Token::Type::COMMENT_ML, value);
            }
            value.push_back(c);
            if (c == '*') {
                state = 15;
            }           
            return {true, false};
        case 15:
            //case 16
            if (c == '/'){
                value.pop_back();
                if (!value.empty()) {
                    tokens.emplace_back(Token::Type::COMMENT_ML, value);
                }
                tokens.emplace_back(Token::Type::PUNCTUATION, "*/");
                return {false, false};
            }
            if (c == -1) {
                tokens.emplace_back(Token::Type::COMMENT_ML, value);
            }

            value.push_back(c);
            state = 14;
            return {true, false};
        case 17:
            //case 18
            if (c == '\n' || c == -1) {
                tokens.emplace_back(Token::Type::COMMENT_OL, value);
                return {false, false};
            }
            value.push_back(c);
            return {true, false};
    }
}

bool FiniteAutomata::check_template(std::vector<Token>& tokens) {
    if (tokens.size() == 0) {
        return false;
    }
    for (int i = tokens.size() - 1; i >= 0; i--) {
        if (tokens[i].get_type() == Token::Type::KEYWORD && tokens[i].get_value() == "template") {
            return true;
        }
        if (!((tokens[i].get_type() == Token::Type::PUNCTUATION 
        && (tokens[i].get_value() == "," || tokens[i].get_value() == "<" || tokens[i].get_value() == "/*" || tokens[i].get_value() == "*/" || tokens[i].get_value() == "//")) 
        || tokens[i].get_type() == Token::Type::IDENTIFIER_TYPE 
        || (tokens[i].get_type() == Token::Type::OPERATOR && tokens[i].get_value() == "::")
        || tokens[i].get_type() == Token::Type::COMMENT_OL || tokens[i].get_type() == Token::Type::COMMENT_ML)) {
            break;
        }
    }
    return false;
}
bool LetterFiniteAutomata::check_const(std::vector<Token>& tokens) {
    if (tokens.size() == 0) {
        return false;
    }
    for (int i = tokens.size() - 1; i >= 0; i--) {
        if (tokens[i].get_type() == Token::Type::KEYWORD && tokens[i].get_value() == "const") {
            return true;
        }
        if (tokens[i].get_type() == Token::Type::PUNCTUATION && (tokens[i].get_value() == "}" || tokens[i].get_value() == ";")){
            break;
        }
    }
    return false;
}

LetterFiniteAutomata::LetterFiniteAutomata(bool literal_mode, std::set<std::string>& custom_types, std::vector<std::string> keywords) : 
    literal_mode(literal_mode), 
    keywords(keywords), 
    custom_types(custom_types) {}

std::pair<bool, char> LetterFiniteAutomata::recognize(std::istream& is,
                                                      std::vector<Token>& tokens,
                                                      const char c0) {
    if (literal_mode) {
        if (c0 == '\"'){
            std::string value;
            int state = 1;

            while(is) {
                char c = is.get();
                //std::cout << c << '\n';

                switch(state){
                    case 1:
                        if (c == '\'' || c == '\n') {
                            tokens.emplace_back(Token::Type::ERROR, "illegal symbol literal");
                            return {false, c};
                        }
                        if (c == '\\') {
                            value.push_back(c);
                            state = 2;
                            continue;
                        }
                        if (c == '\"') {
                            tokens.emplace_back(Token::Type::LITERAL, value);
                            return {false, c};
                        }
                        value.push_back(c);
                        continue;
                    case 2:
                        switch (c) {
                            case '\'':
                            case '\"':
                            case '?':
                            case '\\':
                            case 'a':
                            case 'b':
                            case 'f':
                            case 'n':
                            case 'r':
                            case 't':
                            case 'v':
                                value.push_back(c);
                                state = 1;
                                continue;
                            default:
                                tokens.emplace_back(Token::Type::ERROR, "illegal symbol literal");
                                return {false, c};
                        }
                }
                
            }
            tokens.emplace_back(Token::Type::ERROR, "illegal symbol literal");
            return {false, 0};
        } else {
            std::string value;
            int state = 1;

            while(is) {
                char c =  is.get();

                switch (state){
                case 1:
                    if (c == '\\') {
                        value.push_back(c);
                        state = 2;
                        continue;
                    }
                    if (c == '\'' || c == '\"' || c == '\n') {
                        tokens.emplace_back(Token::Type::ERROR, "illegal symbol literal");
                        return {false, c};
                    }
                    value.push_back(c);
                    state = 3;
                    continue;
                case 2:
                    switch (c){
                        case '\'':
                        case '\"':
                        case '?':
                        case '\\':
                        case 'a':
                        case 'b':
                        case 'f':
                        case 'n':
                        case 'r':
                        case 't':
                        case 'v':
                            value.push_back(c);
                            state = 3;
                            continue;
                        default:
                            tokens.emplace_back(Token::Type::ERROR, "illegal symbol literal");
                            return {false, c};
                    }
                    
                case 3:
                    if (c == '\'') {
                        tokens.emplace_back(Token::Type::LITERAL, value);
                        return {false, c};
                    }
                    tokens.emplace_back(Token::Type::ERROR, "illegal symbol literal");
                    return {false, c};
                }
            }
            tokens.emplace_back(Token::Type::ERROR, "illegal symbol literal");
            return {false, 0};
        }

    } else {
        std::string value;
        value.push_back(c0);
        std::vector<int> paths(keywords.size());
        for (int i = 0; i < keywords.size(); ++i) {
            paths[i] = i;
        }

        int count = 0;
        while (is) {
            char c = is.get();
            
            if (is_letter(c) || is_digit(c)) {
                value.push_back(c);
                ++count;
                for (auto i = 0; i < paths.size(); ++i) {
                    while (i < paths.size() && (keywords[paths[i]].size() < count + 1 || keywords[paths[i]][count] != c)) {
                        paths.erase(paths.begin()+i);
                    }
                }
            } else if (is_symbol(c) || is_whitespace(c) || is_punctuation(c) || c == -1) {
                if (paths.size() != 0 && value == keywords[paths[0]]) {
                    auto i = paths[0];
                    tokens.emplace_back(Token::Type::KEYWORD, keywords[i]);
                    return {true, c};
                }

                if(custom_types.find(value) != custom_types.end()) {
                    tokens.emplace_back(Token::Type::IDENTIFIER_TYPE, value);
                    return {true, c};
                } else {
                    auto i = last_non_comment_token(tokens);
                    if (i != -1){
                        auto token = tokens[i];
                        if (token.get_type() == Token::Type::KEYWORD && (token.get_value() == "class" || token.get_value() == "struct" || token.get_value() == "enum")
                        || check_template(tokens)) {
                            // problem of type T in template and variable T in code later????
                            tokens.emplace_back(Token::Type::IDENTIFIER_TYPE, value);
                            custom_types.insert(value);
                            return {true, c};
                        }

                        if (check_const(tokens)) {
                            tokens.emplace_back(Token::Type::CONST, value);                       
                        } else {
                            tokens.emplace_back(Token::Type::IDENTIFIER, value);
                        }
                    } else {
                        tokens.emplace_back(Token::Type::IDENTIFIER, value);
                    }
                    return {true, c};
                }
            }
        }
    }
}

}  // namespace lexer