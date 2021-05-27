cmake_minimum_required(VERSION 3.16)
set(CMAKE_CXX_STANDARD 20)
project(cplusplus_lexer)

set (CMAKE_RUNTIME_OUTPUT_DIRECTORY ${CMAKE_SOURCE_DIR}/build)

configure_file(${CMAKE_SOURCE_DIR}/example.cpp ${CMAKE_CURRENT_BINARY_DIR}/example.cpp COPYONLY)


set(SOURCE_EXE main.cpp)

set(SOURCE_LIB 
                Lexer.cpp
                FiniteAutomata.cpp
                Token.hpp
                )

add_library(Lexer STATIC ${SOURCE_LIB})
add_library(Token STATIC ${SOURCE_LIB})
add_library(FiniteAutomata STATIC ${SOURCE_LIB})


add_executable(main ${SOURCE_EXE})	

target_link_libraries(main 
                            Token
                            Lexer
                            FiniteAutomata
                            )