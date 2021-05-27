#pragma once

#include <set>
#include <memory>

#include "Lexer.hpp"

namespace lexer {
class Lexer;

class FiniteAutomata {
   public:
    virtual std::pair<bool,char> recognize(std::istream& is,
                           std::vector<Token>& tokens,
                           const char c0) = 0;
    int last_non_comment_token(std::vector<Token>& tokens);
    bool check_template(std::vector<Token>& tokens);
};

class LetterFiniteAutomata : public FiniteAutomata {
   public:
    LetterFiniteAutomata(bool literal_mode, std::set<std::string>& custom_types, std::vector<std::string> keywords = std::vector<std::string>());

    std::pair<bool, char> recognize(std::istream& is,
                                    std::vector<Token>& tokens,
                                    const char c0) override;

   private:
    bool literal_mode = false;
    std::vector<std::string> keywords ;
    std::set<std::string>& custom_types;

    bool check_const(std::vector<Token>& tokens);    
};

class NumberFiniteAutomata : public FiniteAutomata {
   public:
    enum State {
        NATURAL,
        FLOAT,
        WITH_E,
        WITH_E_AND_SIGN,
        WITH_E_WITHOUT_SIGN,
    };

    NumberFiniteAutomata();

    std::pair<bool, char> recognize(std::istream& is,
                                    std::vector<Token>& tokens,
                                    const char c0) override;
};

class OperatorFiniteAutomata : public FiniteAutomata {
   public:
    OperatorFiniteAutomata();
    std::pair<bool, char> recognize(std::istream& is,
                                    std::vector<Token>& tokens,
                                    const char c0) override;
    
    
   private:
    typedef bool resume;
    typedef bool revise_current_symbol;
    std::pair<resume, revise_current_symbol> move(std::vector<Token>& tokens,
                                                  std::string& value,
                                                  const char c);
    int state = 0;
};

}  // namespace lexer