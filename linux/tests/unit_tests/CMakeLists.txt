include_directories(${gtest_SOURCE_DIR}/include ${gtest_SOURCE_DIR})

set(UNIT_TEST_EXE_NAME unit_Tests_run)

add_executable(${UNIT_TEST_EXE_NAME} test_server.cpp)

target_sources(${UNIT_TEST_EXE_NAME} PRIVATE ${CMAKE_SOURCE_DIR}/tests/lib/jnimock/jnimock.cpp)

target_link_libraries(${UNIT_TEST_EXE_NAME} gtest gtest_main gmock gmock_main)
target_link_libraries(${UNIT_TEST_EXE_NAME} ${LIB_NAME})
target_include_directories(${UNIT_TEST_EXE_NAME} PUBLIC
        ${CMAKE_SOURCE_DIR}/src
        ${CMAKE_SOURCE_DIR}/tests/lib
        ${JACK_INCLUDE_DIR}
        ${JNI_INCLUDE_DIRS})