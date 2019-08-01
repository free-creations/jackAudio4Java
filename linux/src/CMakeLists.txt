




set(CMAKE_MODULE_PATH ${CMAKE_MODULE_PATH} "${CMAKE_SOURCE_DIR}/cmake/Modules/")

find_package(JACK REQUIRED)


set(HEADER_FILES
        )
set(SOURCE_FILES
        inprocess.c
        )


# The library contains header and source files.
add_library(${LIB_NAME} SHARED ${SOURCE_FILES} ${HEADER_FILES})
target_include_directories(${LIB_NAME} PUBLIC ${JACK_INCLUDE_DIR})
