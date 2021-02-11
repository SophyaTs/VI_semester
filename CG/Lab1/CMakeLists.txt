cmake_minimum_required(VERSION 3.0)

project(lab1)

set (CMAKE_RUNTIME_OUTPUT_DIRECTORY ${CMAKE_SOURCE_DIR}/build)

add_executable(lab1 main.cpp)
find_package(OpenGL REQUIRED)
find_package(GLUT REQUIRED)
include_directories( ${OPENGL_INCLUDE_DIRS}  ${GLUT_INCLUDE_DIRS} ${CMAKE_SOURCE_DIR})

#add_library(Polygon STATIC ${CMAKE_SOURCE_DIR})

target_link_libraries(lab1 ${OPENGL_LIBRARIES} ${GLUT_LIBRARY})
