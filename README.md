# TinySpring
- Inspired by [@Jeka1978](https://github.com/Jeka1978) and his [Spring-построитель](https://www.youtube.com/watch?v=rd6wxPzXQvo)

## About
- Repository contains two modules (*Framework* and *FrameworkUsage*)
- *Framework* module contains *TinySpring* (a small framework which imitates *Spring* behaviour)
- *FrameworkUsage* module imports *Framework* as a dependency and uses imported *IoC container* to build an example app

## Features
- *DI*
- *ObjectConfigurators* (aka *BeanPostProcessors*)
- Dynamic proxying
- Supported annotations:
    - `@InjectByType` (aka `@Autowired`)
    - `@InjectProperty` (aka `@Value`)
    - `@Singleton`
    - `@PostConstruct`
    - `@Deprecated`

## Improvements after conference
- Add support of abstract classes as types
- Add singleton initialization on context start
- Separate scanner to framework and client (to scan internal and external packages)
- Change `@Deprecated` annotation from classes to methods

## Limitations
- Only five annotations are supported now
- Properties can only be read from `application.properties` file
- Only Java configuration is supported now
