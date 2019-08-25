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
set(VERSION "x.x.x" CACHE STRING "The project version")

# enable the custom module "FindJACK.cmake"
set(CMAKE_MODULE_PATH ${CMAKE_MODULE_PATH} "${CMAKE_SOURCE_DIR}/cmake/Modules/")
# we need the "JACK-developement" package to be installed on the  build platform
# in order to have the "jack.h" headers and the jack library to link to.
find_package(JACK REQUIRED)

# further we need the header files of the Java Development Kit.
find_package(JNI REQUIRED)

# The library name shall be be the downcased project-name with version appended.
string( TOLOWER ${CMAKE_PROJECT_NAME}_${VERSION} LIB_NAME )

set(CMAKE_CXX_STANDARD 11)

# write the generated library into "build/out"
set(CMAKE_LIBRARY_OUTPUT_DIRECTORY ${PROJECT_BINARY_DIR}/out)

add_subdirectory(src)
IF(CMAKE_BUILD_TYPE MATCHES DEBUG)
   add_subdirectory(tests)
ENDIF(CMAKE_BUILD_TYPE MATCHES DEBUG)
