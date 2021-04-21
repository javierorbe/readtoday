package dev.team.readtoday.client.usecase.shared;

import java.lang.reflect.Array;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.Arrays;
import java.util.LinkedList;

/**
 * Customized copy of {@link jakarta.ws.rs.core.GenericType}
 */
public class GenericType<T> {

  /**
   * Type represented by the generic type instance.
   */
  private final Type type; 

  /**
   * The actual raw parameter type.
   */
  private final Class<?> rawType;

  /**
   * Constructs a new generic type, deriving the generic type and class from type parameter. Note
   * that this constructor is protected, users should create a (usually anonymous) subclass as shown
   * above.
   *
   * @throws IllegalArgumentException in case the generic type parameter value is not provided by
   * any of the subclasses.
   */
  protected GenericType() {
    // Get the type parameter of GenericType<T> (aka the T value)
    type = getTypeArgument(getClass());
    rawType = getClass(type);
  }

  /**
   * Retrieve the type represented by the generic type instance.
   *
   * @return the actual type represented by this generic type instance.
   */
  public final Type getType() {
    return type;
  }

  /**
   * Returns the object representing the class or interface that declared the supplied {@code
   * type}.
   *
   * @param type {@code Type} to inspect.
   * @return the class or interface that declared the supplied {@code type}.
   */
  private static Class<?> getClass(final Type type) {
    if (type instanceof Class) {
      return (Class<?>) type;
    }
    if (type instanceof ParameterizedType parameterizedType) {
      if (parameterizedType.getRawType() instanceof Class) {
        return (Class<?>) parameterizedType.getRawType();
      }
    } else if (type instanceof GenericArrayType array) {
      final Class<?> componentRawType = getClass(array.getGenericComponentType());
      return getArrayClass(componentRawType);
    }
    throw new IllegalArgumentException("Type parameter " + type.toString() + " not a class or " +
        "parameterized type whose raw type is a class");
  }

  /**
   * Get Array class of component class.
   *
   * @param c the component class of the array
   * @return the array class.
   */
  private static Class<?> getArrayClass(final Class<?> c) {
    try {
      Object o = Array.newInstance(c, 0);
      return o.getClass();
    } catch (NegativeArraySizeException e) {
      throw new IllegalArgumentException("Negative array size.", e);
    } catch (RuntimeException e) {
      throw new IllegalArgumentException(e);
    }
  }

  /**
   * Return the value of the type parameter of {@code GenericType<T>}.
   *
   * @param clazz subClass of {@code baseClass} to analyze.
   * @return the parameterized type of {@code GenericType<T>} (aka T)
   */
  private static Type getTypeArgument(final Class<?> clazz) {
    // collect superclasses
    LinkedList<Type> superclasses = new LinkedList<>();
    Type currentType;
    Class<?> currentClass = clazz;
    do {
      currentType = currentClass.getGenericSuperclass();
      superclasses.push(currentType);
      if (currentType instanceof Class) {
        currentClass = (Class<?>) currentType;
      } else if (currentType instanceof ParameterizedType) {
        currentClass = (Class<?>) ((ParameterizedType) currentType).getRawType();
      }
    } while (!currentClass.equals(GenericType.class));

    // find which one supplies type argument and return it
    TypeVariable<?> tv = ((Class<?>) GenericType.class).getTypeParameters()[0];
    while (!superclasses.isEmpty()) {
      currentType = superclasses.pop();

      if (currentType instanceof ParameterizedType pt) {
        Class<?> rawType = (Class<?>) pt.getRawType();
        int argIndex = Arrays.asList(rawType.getTypeParameters()).indexOf(tv);
        if (argIndex > -1) {
          Type typeArg = pt.getActualTypeArguments()[argIndex];
          if (typeArg instanceof TypeVariable) {
            // type argument is another type variable - look for the value of that
            // variable in subclasses
            tv = (TypeVariable<?>) typeArg;
            continue;
          }
          // found the value - return it
          return typeArg;
        }
      }

      // needed type argument not supplied - break and throw exception
      break;
    }
    throw new IllegalArgumentException(currentType + " does not specify the type parameter T of GenericType<T>");
  }

  @Override
  public final boolean equals(final Object obj) {
    if (obj == this) {
      return true;
    }

    if (obj instanceof GenericType<?> other) {
      return type.equals(other.type);
    }

    return false;
  }

  @Override
  public final int hashCode() {
    return type.hashCode();
  }
}
