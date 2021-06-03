cmake_minimum_required(VERSION 3.0.0)
set(CMAKE_CXX_STANDARD 20)
project(lab3 VERSION 0.1.0)

option(BUILD_TEST "Build test" ON)

#add_executable(main main.cpp)

if(BUILD_TEST)
    add_executable(test test.cpp)
endif()
