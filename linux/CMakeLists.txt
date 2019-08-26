#============================================================================
# File        : CMakeLists.txt
# Description : CMake-script to build and test the JNI Library.
#
# Copyright 2019 Harald Postner (Harald at free-creations.de)
#
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
#     http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.
#
#============================================================================
cmake_minimum_required(VERSION 3.1)

project(jackAudio4Java)

# the version shall be given on the command line, otherwise we'll use "x.x.x"
set(VERSION "x.x.x" CACHE STRING "The project version")

# The default library-name shall be the downcased project-name with version appended.
string( TOLOWER ${CMAKE_PROJECT_NAME}_${VERSION} DEFAULT_LIB_NAME )

# the name shall be given on the command line, otherwise we'll use the default name.
set(LIB_NAME ${DEFAULT_LIB_NAME} CACHE STRING "The name (stem) of the library")

# The directory where the final artifacts should be delivered is given on the command line, otherwise we'll use "build/out".
set(DELIVERIES_DIR "${PROJECT_BINARY_DIR}/out" CACHE STRING "The directory where the library should be delivered")


# Enable the custom-module "FindJACK.cmake" in the cmake/Modules subdirectory.
set(CMAKE_MODULE_PATH ${CMAKE_MODULE_PATH} "${CMAKE_SOURCE_DIR}/cmake/Modules/")

# We need the "JACK-development" package to be installed on the  build platform
# in order to have the "jack.h" headers and the jack library to link to.
find_package(JACK REQUIRED)

# Further, we need the header files of the Java Development Kit.
find_package(JNI REQUIRED)


# Let us use C++ 11 standard.
set(CMAKE_CXX_STANDARD 11)

# Write the generated library into the DELIVERIES_DIR selected above.
set(CMAKE_LIBRARY_OUTPUT_DIRECTORY ${DELIVERIES_DIR})

# All project sources reside here.
add_subdirectory(src)

# Build the unit-tests only in debug mode.
IF(CMAKE_BUILD_TYPE MATCHES DEBUG)
   add_subdirectory(tests)
ENDIF(CMAKE_BUILD_TYPE MATCHES DEBUG)
