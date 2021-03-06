#============================================================================
# File        : CMakeLists.txt
# Description : CMake-script to build and test the JNI Library.
#
# Copyright 2019 Harald Postner (www.free-creations.de)
#
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
#     http:www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.
#
#============================================================================
include_directories(${gtest_SOURCE_DIR}/include ${gtest_SOURCE_DIR})

set(UNIT_TEST_EXE_NAME unit_Tests_run)



set(target_sources
        test_Jack.cpp
        test_types_Int.cpp
        test_JackClient.cpp
        test_level2_JackPorts.cpp
        ${CMAKE_SOURCE_DIR}/tests/lib/jnimock/jnimock.cpp)

add_executable(${UNIT_TEST_EXE_NAME} ${target_sources})

target_link_libraries(${UNIT_TEST_EXE_NAME} gtest gtest_main gmock gmock_main)
target_link_libraries(${UNIT_TEST_EXE_NAME} ${LIB_NAME})
target_include_directories(${UNIT_TEST_EXE_NAME} PUBLIC
        ${CMAKE_SOURCE_DIR}/src
        ${CMAKE_SOURCE_DIR}/src/3rdparty
        ${CMAKE_SOURCE_DIR}/tests/lib
        ${JACK_INCLUDE_DIR}
        ${JNI_INCLUDE_DIRS})
