#============================================================================
# File        : CMakeLists.txt
# Description : CMake-script to build and test the JNI Library.
#
# Copyright 2019 Harald Postner (www.h-postner.de)
#
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
#     http:#www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.
#
#============================================================================
cmake_minimum_required(VERSION 3.14)
project(jackAudio4Java)

# The library name shall be be the project name with "_lib" appended and downcased
string( TOLOWER ${CMAKE_PROJECT_NAME}_lib LIB_NAME )

set(CMAKE_CXX_STANDARD 17)


add_subdirectory(src)
add_subdirectory(tests)

