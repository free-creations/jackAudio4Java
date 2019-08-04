# About Data Conversions
For exhaustive discussion see these documents:
- [Fundamental types in C++](https://en.cppreference.com/w/cpp/language/types)
- [Primitive Data Types in Java](https://docs.oracle.com/javase/tutorial/java/nutsandbolts/datatypes.html)

In short:
```objectivec
typedef int jint;

#ifdef _LP64 /* 64-bit Linux */
typedef long jlong;
#else
typedef long long jlong;
#endif

typedef signed char jbyte;
```