---
​---
title: java与数据结构算法的学习（伯克利大学CS61B课程学习笔记）
author: Zewang
date: 2025/3
tags:
  - 数据结构算法
  - java
---

[TOC]

# 还需加强理解的地方

[泛型方法 Generic Methods](#泛型方法 Generic Methods)



提示：2025版新增了软件工程相关知识，记得补上——————

# 泛型和自动装箱

## 5.1 自动装箱

### 自动转换

#### 自动装箱和拆箱 Autoboxing and Unboxing

+ `<>` 语法定义具有泛型类型变量的类

  例如 `LinkedListDeque<Item>` 和 `ArrayDeque<Item>`

+ 回忆：java有八个基元类型 (byte, int, short, long, float, double, boolean, char), 其他都是引用类型 

+ Java 的一个特殊功能是，我们不能提供原始类型作为泛型的实际类型参数
  例如 `ArrayDeque<int>` 是一个语法错误。相反，我们使用 `ArrayDeque<Integer>`

+ 对于每一个基元类型，都有一个引用类型

![下载](D:\Zewang\CS_61B\Note\下载.png)

+ 隐式转换

+ 代码原样式
  
  ```java
  public class BasicArrayList {
      public static void main(String[] args) {
        ArrayList<Integer> L = new ArrayList<Integer>();
        L.add(new Integer(5));
        L.add(new Integer(6)); //自动装箱
  
        /* Use the Integer.valueOf method to convert to int */
        int first = L.get(0).valueOf(); //自动拆箱
      }
  }
  ```
  
+ 自动装箱、拆箱之后
  
  ```java
  public class BasicArrayList {
      public static void main(String[] args) {
        ArrayList<Integer> L = new ArrayList<Integer>();
        L.add(5);
        L.add(6);
        int first = L.get(0);
      }
  }
  ```
  
+ Java 隐式创建一个值为 20 的新 `Integer`，从而产生对等效调用 `blah（new Integer（20））` 的调用。此过程称为自动装箱。

+ 自动拆箱 

+ 同样，如果 Java 需要一个基元类型：

  ```java
  public static void blahPrimitive(int x) {
      System.out.println(x);
  }
  ```

  但你给它一个相应的包装器类型的值：

  ```java
  Integer x = new Integer(20);
  blahPrimitive(x);
  ```

  它将自动拆箱整数，相当于调用 `Integer` 类的 `valueOf` 方法。

  ------
  
  

#### 注意：

+ 在进行自动装箱和取消装箱时，需要记住以下几点：
  + **数组**从来都不是自动装箱或自动拆箱的。
    例如，如果你有一个整数数组 `int[] x`，并尝试将其地址放入 `Integer[]` 类型的变量中，编译器将不允许你的程序编译。
  + 自动装箱和拆箱也会对**性能**产生可衡量的影响。
    也就是说，依赖于自动装箱和拆箱的代码将比避免此类自动转换的代码慢。
  + 包装类型使用的**内存**比基元类型多得多。在大多数现代计算机上，不仅您的代码必须保存对对象的 64 位引用，而且每个对象还需要 64 位开销，用于存储对象的动态类型等内容。
  
    ------
  
    

#### 扩大  `Widening`

+ **自动加宽基元类型**

+ 例如：
  Java 中的 doubles 比 int 宽。如果我们有如下所示的功能：

  ```java
  public static void blahDouble(double x) {
      System.out.println(“double: “ + x);
  }
  ```

  我们可以用int参数调用：

  ```java
  int x = 20;
  blahDouble(x);
  ```

  效果等同与 `blahDouble（（double） x）`

  + 宽变窄需要手动

  + 例如：

    ```java
    public static void blahInt(int x) {
        System.out.println(“int: “ + x);
    }
    
    double x = 20;
    blahInt((int) x);
    ```
  
    ------
    
    

## 5.2 immutability 不变性

### 不可变数据类型

+ 其实例在实例化后**无法**以任何可观察的方式更改。
+ 例如，Java 中的 **`String` 对象**是不可变的。无论如何，如果你有一个 `String` 实例，你可以在该 `String` 上调用任何方法，但它将完全保持不变。这意味着当 `String` 对象被连接时，**原始的 String 都不会被修改** -- **而是返回一个全新的 `String` 对象**。

+ 可变数据类型包括 `ArrayDeque` 和 `Planet` 等对象。(ADT)
  我们可以在 `ArrayDeque` 中添加或删除项目，这些项目是可观察的更改。同样，`planet`的速度和位置可能会随时间而变化

### final

+ 任何具有**非私有**变量的数据类型都是可变的，除非这些变量被声明为 `final`（这不是可变性的唯一条件 -- 还有许多其他方法可以定义数据类型，使其是可变的）

+ `final` 关键字是变量的关键字，用于防止变量在第一次赋值后被更改

### 不可变数据类型优缺点

+ 优点
  + 防止错误并使调试更容易，因为**属性永远不会更改**
  + 您可以指望对象具有特定的行为 / 特征
+ 缺点
  + 您需要创建一个新对象才能更改属性

### 注意

+ 将引用声明为 **final** 并不会使引用指向的对象不可变！

+ 举例：

  ```java
   public final ArrayDeque<String>() deque = new ArrayDeque<String>();
  ```

  `deque` 变量是 final，永远不能重新分配，但它指向的数组 deque 对象可以改变！`ArrayDeques` 始终是可变的！
  
  ------
  
  

## 5.3 创建另一个泛型类

### map

+ 将值与对相关联

+ `ArrayMap `见 `myCode`中

+ `excercise`

  + **`Excercise 5.2.2`** What would we need to do in order to call `assertEquals(long, long)`? A.) Widen `expected` to a `long` B.) Autobox `expected` to a `Long` C.) Unbox `am.get(2)` D.) Widen the unboxed `am.get(2)` to long

  + **题目：如何调用 `assertEquals(long, long)`？**

    ```java
    assertEquals(expected, am.get(2));
    ```

    **选项分析：**

    - ```
      A.) Widen expected to a long
      ```

       ✅

      - 如果 `expected` 是 `int`，它可以 **自动拓宽（widening）** 为 `long`，所以有效。

    - ```
      B.) Autobox expected to a Long
      ```

       ❌

      - `assertEquals(long, long)` 需要 **基本类型** `long`，但 `Long` 是一个 **对象**，不匹配。

    - ```
      C.) Unbox am.get(2)
      ```

       ✅

      - 如果 `am.get(2)` 返回的是 `Integer`，那么它 **会被拆箱（unboxing）** 为 `int`。如果 `assertEquals(long, long)` 需要 `long`，那么 `int` **会被拓宽** 为 `long`，所以有效。

    - ```
      D.) Widen the unboxed am.get(2) to long
      ```

       ✅

      - 这里 `am.get(2)` 拆箱后是 `int`，它可以自动拓宽为 `long`，符合 `assertEquals(long, long)`，所以有效。

    ✅ **最终答案**：**A, C, D**。

    

+ **`Excercise 5.2.3`** How would we make it work with `assertEquals(Object, Object)`?

+ **题目：如何让 `assertEquals(Object, Object)` 工作？**

  ```java
  assertEquals(expected, am.get(2));
  ```

  **选项分析：**

  - `assertEquals(Object, Object)` 需要 **两个对象** 作为参数。
  - `am.get(2)` 可能返回 `Integer`，但 `expected` 可能是 `int`（基本类型）。
  - **int 不是对象**，所以 `expected` 需要**自动装箱（autoboxing）**为 `Integer`，这样 `assertEquals(Object, Object)` 才能匹配。

  ✅ **最终答案**：**Autobox expected to an Integer**。

  

+ **`Excercise 5.2.4`** How do we make the code compile with casting?

+ **题目：如何使用 \**显式类型转换（casting）\** 使代码编译？**

  ```java
  assertEquals(expected, am.get(2));
  ```

  如果 `expected` 是 `int`，但 `am.get(2)` 可能返回 `Integer`，**如何强制转换使它匹配？**

  - `assertEquals(Object, Object)` 需要 **两个对象**。

  - `expected` 是 `int`，需要转换为 `Integer`。

  - 解决方法是 显式类型转换：

    ```java
    assertEquals((Integer) expected, am.get(2));
    ```

    这样，`expected`先转换为 `Integer`

    ，然后匹配 `assertEquals(Object, Object)`

    。

  ✅ **最终答案**：**Cast expected to Integer**。

### 泛型方法 Generic Methods

[^这里还需要再理解]:



+ 新语法

```java
public static <K extends Comparable<K>, V> K maxKey(Map61B<K, V> map) {...}
```

`K 扩展了 Comparable<K>` 意味着键必须实现类似的接口，并且可以与其他 K 进行比较。我们需要在 `Comparable` 之后包含 `<K>`，因为 `Comparable` 本身就是一个通用接口！因此，我们必须指定我们想要什么样的可比物。在本例中，我们希望将 K 与 K 进行比较。

+ 为什么`extends`而不是`implement`?

  在泛型上下文中，`extends` 用于指定类型上界（type upper bound）。它表示泛型类型参数必须是指定类型或其子类型。这里的 “类型” 既可以是类，也可以是接口。当使用 `extends` 与接口搭配时，它意味着泛型类型参数必须实现该接口。

+ `extends` 只是陈述了一个事实：你必须是你要扩展的任何内容的子类。

+ 当与泛型一起使用时（如在泛型方法标头中），`extends` 会施加约束，而不是授予新功能。

+ `extends` 和 `implements` 的使用场景差异

  + **`implements`**：在类定义时使用，用于表明一个类实现了某个或多个接口。例如：

  ```java
  public class MyClass implements Comparable<MyClass> {
      @Override
      public int compareTo(MyClass other) {
          // 实现比较逻辑
          return 0;
      }
  }
  ```

  在这个例子中，`MyClass` 类使用 `implements` 关键字表明它实现了 `Comparable<MyClass>` 接口。

  - **`extends`**：在泛型类型参数定义中使用，用于约束泛型类型参数的类型范围。例如你给出的代码：

  ```java
  public static <K extends Comparable<K>, V> K maxKey(Map61B<K, V> map) {...}
  ```

  这里的 `<K extends Comparable<K>>` 表示泛型类型参数 `K` 必须是实现了 `Comparable<K>` 接口的类型。也就是说，`K` 所代表的具体类型必须能够进行自我比较。

## 总结

We’ve seen four new features of Java that make generics more powerful:
我们已经看到了 Java 的四个新功能，它们使泛型更加强大：

- Autoboxing and auto-unboxing of primitive wrapper types.
  原始包装器类型的自动装箱和自动取消装箱。
- Promotion/widening between primitive types.
  基元类型之间的提升/扩大。
- Specification of generic types for methods (before return type).
  方法的泛型类型规范（返回类型之前）。
- Type upper bounds in generic methods (e.g. `K extends Comparable<K>`).
  在泛型方法中键入上限（例如 `K extends Comparable<K>`）。



> 这四个概念都是 Java 语言中的重要特性，主要涉及 **基本类型、包装类型、泛型** 等方面。下面详细介绍每一个概念。
>
> ------
>
> ## **1. Autoboxing 和 Auto-unboxing（自动装箱和自动拆箱）**
>
> Java 中有 **8 种基本数据类型**（`int`, `double`, `char`, `boolean` 等），以及它们对应的 **包装类**（`Integer`, `Double`, `Character`, `Boolean` 等）。
>
> ### **自动装箱（Autoboxing）**
>
> **将基本数据类型转换为对应的包装类对象**，Java 编译器会自动完成此操作。
>
> ```java
> int num = 10;
> Integer obj = num;  // 自动装箱，相当于 Integer obj = Integer.valueOf(num);
> ```
>
> ### **自动拆箱（Auto-unboxing）**
>
> **将包装类对象转换回基本数据类型**，Java 编译器同样会自动完成此操作。
>
> ```java
> Integer obj = 20;
> int num = obj;  // 自动拆箱，相当于 int num = obj.intValue();
> ```
>
> ### **示例**
>
> ```java
> public class AutoboxingExample {
>     public static void main(String[] args) {
>         Integer a = 10;  // 自动装箱
>         int b = a;       // 自动拆箱
> 
>         Integer c = a + b;  // a 和 b 参与运算时，自动拆箱，然后结果自动装箱
>         System.out.println(c); // 输出 20
>     }
> }
> ```
>
> ### **注意事项**
>
> 1. **自动装箱不会创建新对象，而是使用缓存池**（`-128` 到 `127` 之间的 `Integer` 对象被缓存）。
>
> 2. 包装类的比较要用 `equals`，不能用 `==`（除非是小范围的缓存值）
>
>    。
>
>    ```java
>    Integer x = 100, y = 100; // 这个值在缓存范围内，x == y 为 true
>    Integer m = 200, n = 200; // 超出缓存范围，m == n 为 false
>    System.out.println(m == n);       // false
>    System.out.println(m.equals(n));  // true
>    ```
>
> ------
>
> ## **2. Promotion/Widening（基元类型之间的提升/扩大）**
>
> Java 允许基本数据类型之间的转换，通常从 **小范围类型转换为大范围类型（隐式转换）**，称为 **widening conversion（扩大转换）**。
>
> ### **数据类型的大小**
>
> | 类型   | 字节数 | 取值范围         |
> | ------ | ------ | ---------------- |
> | byte   | 1      | -128 ~ 127       |
> | short  | 2      | -32,768 ~ 32,767 |
> | int    | 4      | -2³¹ ~ 2³¹-1     |
> | long   | 8      | -2⁶³ ~ 2⁶³-1     |
> | float  | 4      | ±3.4 × 10³⁸      |
> | double | 8      | ±1.8 × 10³⁰⁸     |
>
> ### **自动提升规则**
>
> - **从小到大自动提升**（`byte → short → int → long → float → double`）
> - `char` 也是可以转换为 `int` 或更大范围的类型
>
> **示例：**
>
> ```java
> public class WideningExample {
>     public static void main(String[] args) {
>         int num = 10;
>         double d = num; // int 自动转换为 double
>         System.out.println(d); // 输出 10.0
> 
>         char ch = 'A';
>         int ascii = ch; // char 自动转换为 int（ASCII 值）
>         System.out.println(ascii); // 输出 65
>     }
> }
> ```
>
> ### **注意**
>
> - 不允许从大范围类型隐式转换为小范围类型
>
>   （需要强制类型转换）。
>
>   ```java
>   double d = 9.8;
>   int x = d; // 错误，需要强制转换
>   int y = (int) d; // 正确，但小数部分丢失，y = 9
>   ```
>
> ------
>
> ## **3. Specification of Generic Types for Methods（方法的泛型类型规范）**
>
> Java **泛型方法**允许在**方法级别**定义泛型类型，通常**在返回类型之前指定泛型**。
>
> ### **语法**
>
> ```java
> public <T> T methodName(T param) {
>     return param;
> }
> ```
>
> - `<T>` 表示这是一个泛型方法，`T` 是 **方法的类型参数**。
> - `T methodName(T param)` 表示 **方法的参数和返回值类型都是 `T`**。
>
> ### **示例**
>
> ```java
> public class GenericMethodExample {
>     // 泛型方法，返回相同类型的参数
>     public static <T> T printAndReturn(T value) {
>         System.out.println("Value: " + value);
>         return value;
>     }
> 
>     public static void main(String[] args) {
>         String str = printAndReturn("Hello");
>         Integer num = printAndReturn(100);
>     }
> }
> ```
>
> **输出**
>
> ```
> Value: Hello
> Value: 100
> ```
>
> ### **注意**
>
> - 泛型方法可以用于 **任何类型**（`String`、`Integer`、`Double` 等）。
> - `<T>` 只在该方法内部适用，不影响类的其他部分。
>
> ------
>
> ## **4. Type Upper Bounds in Generic Methods（泛型方法中的类型上限）**
>
> Java 泛型支持**上界（upper bound）**，用于限制类型参数必须是某个类型的子类或实现某个接口。
>
> ### **语法**
>
> ```java
> public <T extends 上界类型> void 方法名(T 参数) { }
> ```
>
> - `<T extends Comparable<T>>` 表示 `T` 必须是 `Comparable<T>` 的子类（或实现类）。
> - `extends` 既适用于 **类**，也适用于 **接口**。
>
> ### **示例：限制类型必须是 `Number` 的子类**
>
> ```java
> public class UpperBoundExample {
>     // 计算两个数的平均值，类型必须是 Number 或其子类
>     public static <T extends Number> double average(T num1, T num2) {
>         return (num1.doubleValue() + num2.doubleValue()) / 2;
>     }
> 
>     public static void main(String[] args) {
>         System.out.println(average(10, 20));   // int 自动装箱为 Integer
>         System.out.println(average(5.5, 2.2)); // double
>     }
> }
> ```
>
> ### **示例：类型上界为 `Comparable<T>`**
>
> 如果要比较两个元素，就需要确保 `T` 具有 **可比较性**（即实现 `Comparable<T>` 接口）。
>
> ```java
> public class CompareExample {
>     public static <T extends Comparable<T>> T max(T a, T b) {
>         return a.compareTo(b) > 0 ? a : b;
>     }
> 
>     public static void main(String[] args) {
>         System.out.println(max(5, 10));  // 输出 10
>         System.out.println(max("apple", "banana")); // 输出 banana（字典序）
>     }
> }
> ```
>
> ### **总结**
>
> - `T extends Number`：T 必须是 `Number` 或其子类。
> - `T extends Comparable<T>`：T 必须实现 `Comparable<T>` 接口，才能进行比较操作。
>
> ------
>
> ## **总结**
>
> | 主题              | 说明                                                |
> | ----------------- | --------------------------------------------------- |
> | **自动装箱/拆箱** | 基本类型 ↔ 包装类的自动转换 (`int ↔ Integer` 等)    |
> | **类型提升**      | 小范围基本类型自动转换为大范围类型 (`int → double`) |
> | **方法泛型**      | 在方法返回类型前定义 `<T>`，让方法适用于不同类型    |
> | **类型上界**      | `T extends 类/接口` 限制泛型类型的取值范围          |
>
> 这些概念都是 Java 类型系统的核心特性，能够提高代码的灵活性和安全性！

*以上总结由AI提供*

------

+ `ArrayMap`的实现

```java
package Map61B;

import org.junit.Assert.*;
import java.util.List;
import java.util.ArrayList;
import org.junit.Test;


import static org.junit.Assert.*;

public class ArrayMap<K, V> implements Map61B<K, V> {
    private K[] keys;
    private V [] values;
    int size;

    public ArrayMap() {
        keys = (K[]) new Object[100];
        values = (V[]) new Object[100];
        size = 0;
    }

    /**返回键键对应的索引
     * 如果没有就返回-1*/
    private int KeyIndex(K key) {
        for (int i = 0; i < size; i++) {
            if (keys[i].equals(key)) {
                return i;
            }
        }
        return -1;
    }

    public boolean containsKey(K key) {
        int index = KeyIndex(key);
        return index > -1;
    }

    public void put(K key, V value) {
        int index = KeyIndex(key);
        if (index == -1) {
            keys[size] = key;
            values[size] = value;
            size++;
        } else {
            values[index] = value;
        }
    }

    public V get(K key) {
        int index = KeyIndex(key);
        return values[index];
    }

    public int size() {
        return size;
    }

    public List<K> keys() {
        List<K> KeyList = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            KeyList.add(keys[i]);
        }
        return KeyList;
    }

}
```



# 六、`exceptions, iterators, iterables`...

## 6.1 列表、集合、数组集

+ 我们从头开始构建了一个列表，但 Java 提供了一个内置的 `List` 接口和几个实现，例如 `ArrayList`。请记住，由于 `List` 是一个接口，因此我们不能初始化它！我们必须实施它的一个实现。

+ 要访问它，我们可以使用 classes、interfaces 的全名（'canonical name'）：

```java
java.util.List<Integer> L = new java.util.ArrayList<>();
```

+ 但是，这有点冗长。与我们导入 `JUnit` 的方式类似，我们可以导入 java 库：

```java
import java.util.List;
import java.util.ArrayList;

public class Example {
    public static void main(String[] args) {
        List<Integer> L = new ArrayList<>();
        L.add(5);
        L.add(10);
        System.out.println(L);
    }
}
```

### sets 集

+ 集是唯一元素的集合 - 每个元素只能有一个副本。也没有顺序。

+ Java 具有 `Set` 接口以及 `HashSet` 等实现。如果您不想使用全名，请记得导入它们！

  ```java
  import java.util.Set;
  import java.util.HashSet;
  ```

+ 示例使用：

  ```java
  Set<String> s = new HashSet<>();
  s.add("Tokyo");
  s.add("Lagos");
  System.out.println(S.contains("Tokyo")); // true
  ```

### 数组集 `ArraySet`

+ 目的：制作自己的`ArraySet`：
  + `add(value)`: add the value to the set if not already present
  + `contains(value)`: check to see if `ArraySet` contains the key
  + `size()`: return number of values

```java
public class ArraySet<T> {

    private T[] items;
    private int size;

    public ArraySet() {
        items = (T[]) new Object[100];
        size = 0;
    }

    /* Returns true if this map contains a mapping for the specified key.
     */
    public boolean contains(T x) {
        for (int i = 0; i < size; i++) {
            if (items[i].equals(x)) {
                return true;
            }
        }
        return false;
    }

    /* Associates the specified value with the specified key in this map.
       Throws an IllegalArgumentException if the key is null. */
    public void add(T x) {
        if (x == null) return;
        if (contains(x)) return;
        items[size] = x;
        size++;
    }

    /* Returns the number of key-value mappings in this map. */
    public int size() {
        return size;
    }

    public static void main(String[] args) {
        ArraySet<String> s = new ArraySet<>();
        s.add(null);
        s.add("horse");
        s.add("fish");
        s.add("house");
        s.add("fish");
        System.out.println(s.contains("horse"));
        System.out.println(s.size());
    }

    /* Also to do:
    1. Make ArraySet implement the Iterable<T> interface.
    2. Implement a toString method.
    3. Implement an equals() method.
    */
}
```

## 6.2 Throwing Exceptions 引发异常

```java
throw new ExceptionObject(parameter1, ...)
```

```java
throw new IllegalArgumentException("can't add null");
```

## 6.3 iteration 迭代

### 增强的for循环

+ 关键：一个叫`iterator`的对象

+ 对于我们的示例，在 List.java 中，我们可以定义一个返回迭代器对象的 `iterator（）` 方法。
  ```java
  public Iterator<E> iterator();
  ```

  我们的迭代器方法有三种关键方法：

  + 首先，我们得到一个新的迭代器对象，其中包含 `Iterator<Integer> seer = friends.iterator();`
  + 接下来，我们使用 while 循环遍历列表。我们使用 `seer.hasNext（）` 检查是否仍有剩余项目，如果剩余未看见的项目，则返回 true，如果所有项目都已处理完，则返回 false。
  + 最后，`seer.next（）` 同时执行两项作。它返回列表的下一个元素，在这里我们将其打印出来。它还将迭代器前进一项。这样，迭代器将只检查每个项目一次。

### 实现迭代器

```java
List<Integer> friends = new ArrayList<Integer>();
Iterator<Integer> seer = friends.iterator();

while(seer.hasNext()) {
    System.out.println(seer.next());
}
```

考虑两点：

+ List 接口有 iterator（） 方法吗？
+ Iterator 接口有 `next` / `hasNext`（） 方法吗？

+ `Iteratabl`迭代接口

+ 支持迭代的 `ArraySet` 代码如下：

```java
import java.util.Iterator;

public class ArraySet<T> implements Iterable<T> {
    private T[] items;
    private int size; // the next item to be added will be at position size

    public ArraySet() {
        items = (T[]) new Object[100];
        size = 0;
    }

    /* Returns true if this map contains a mapping for the specified key.
     */
    public boolean contains(T x) {
        for (int i = 0; i < size; i += 1) {
            if (items[i].equals(x)) {
                return true;
            }
        }
        return false;
    }

    /* Associates the specified value with the specified key in this map.
       Throws an IllegalArgumentException if the key is null. */
    public void add(T x) {
        if (x == null) {
            throw new IllegalArgumentException("can't add null");
        }
        if (contains(x)) {
            return;
        }
        items[size] = x;
        size += 1;
    }

    /* Returns the number of key-value mappings in this map. */
    public int size() {
        return size;
    }

    /** returns an iterator (a.k.a. seer) into ME */
    public Iterator<T> iterator() {
        return new ArraySetIterator();
    }

    private class ArraySetIterator implements Iterator<T> {
        private int wizPos;

        public ArraySetIterator() {
            wizPos = 0;
        }

        public boolean hasNext() {
            return wizPos < size;
        }

        public T next() {
            T returnItem = items[wizPos];
            wizPos += 1;
            return returnItem;
        }
    }

    public static void main(String[] args) {
        ArraySet<Integer> aset = new ArraySet<>();
        aset.add(5);
        aset.add(23);
        aset.add(42);

        //iteration
        for (int i : aset) {
            System.out.println(i);
        }
    }

}
```

### 6.3总结

#### 为什么要使用 Iterator 才可以使用增强 for 循环（实现原理）？

增强型 for 循环的底层实现依赖于 `Iterable` 和 `Iterator` 接口。当你使用增强型 for 循环时，编译器会自动将其转换为使用 `Iterator` 的代码。例如：

```java
for (String city : s) {
    System.out.println(city);
}
```

会被转换为：

```java
Iterator<String> seer = s.iterator();
while (seer.hasNext()) {
    String city = seer.next();
    System.out.println(city);
}
```

因此，只有实现了 `Iterable` 接口的类才能使用增强型 for 循环。

#### `Iterator` 和 `Iterable `是什么？

- **`Iterable`**: 这是一个接口，表示一个类可以被迭代。它只有一个方法 `iterator()`，返回一个 `Iterator` 对象。实现了 `Iterable` 接口的类可以使用增强型 for 循环。
- **`Iterator`**: 这也是一个接口，表示一个迭代器。它有两个主要方法：
  - `hasNext()`: 检查是否还有更多的元素可以遍历。
  - `next()`: 返回当前元素并将迭代器移动到下一个元素。



+ 要想使用迭代器，需要实现 `Iterator` 和 `Iterable`接口（共三个主要函数）
+ 关注增强for循环底层逻辑（迭代器）

## 6.4 对象方法 Object Methods 

+ 所有类都继承自总体Object类，继承方法如下：

> - `String toString()`
> - `boolean equals(Object obj)`
> - `Class <?> getClass()`
> - `int hashCode()`
> - `protected Objectclone()`
> - `protected void finalize()`
> - `void notify()`
> - `void notifyAll()`
> - `void wait()`
> - `void wait(long timeout)`
> - `void wait(long timeout, int nanos)`

### `toString()`

+ `toString（）` 方法提供对象的字符串表示形式。`System.out.println（）` 函数在传递给它的任何对象上隐式调用此方法，并打印返回的字符串。当您运行 `System.out.println（dog）` 时，它实际上是在执行以下作：

  ```java
  String s = dog.toString()
  System.out.println(s)
  ```

+ 对于我们自己编写的类，如 `ArrayDeque`、`LinkedListDeque` 等，如果我们想看到以可读格式打印的对象，我们需要提供自己的 `toString（）` 方法。
+ 编写之前，输出样式：
  ![image-20250319203613128](C:\Users\zewan\AppData\Roaming\Typora\typora-user-images\image-20250319203613128.png)

+ 编写过程

  + 初步想法：
    ```java
    @Override
        public String toString() {
            String returnString = "{";
            for (int i = 0; i < size; i++) {
                returnString += items[i];
                returnString += ", ";
            }
            returnString += "}";
            return returnString;
        }
    ```

    + 问题：效率低下（重新创建了一个string并逐个线性添加）

  + 解决方法：Java 有一个名为 `StringBuilder` 的特殊类。它会创建一个可变的字符串对象，因此您可以继续附加到同一个字符串对象，而不是每次都创建一个新对象。

    ```java
    @Override
        public String toString() {
            StringBuilder returnSB = new StringBuilder();
            for (int i = 0; i < size - 1; i++) {
                returnSB.append(items[i].toString());
                returnSB.append(", ");
            }
            returnSB.append(items[size - 1].toString());
            returnSB.append("}");
            return returnSB.toString();
        }
    ```

    注： `StringBuilder`是java的核心包的一部分，不用显式导入

  + 编写之后输出：

  + ```
    {1, 2, 3}
    ```

### `equals()`

+ `equals（）` 和 `==` 在 Java 中具有不同的行为。`==`检查两个对象是否实际上是内存中的同一对象。请记住，按值传递！`==` 检查两个盒子是否包含相同的内容。对于基元，这意味着检查值是否相等。对于对象，这意味着检查地址/指针是否相等。

+ `equals（Object o）` 是 Object 中的一个方法，默认情况下，它的作用类似于 ==，因为它检查 this 的内存地址是否与 o 相同。但是，我们可以重写它以我们想要的任何方式定义相等！例如，要使两个 `Arraylist` 被视为相等，它们只需要具有相同顺序的相同元素。

+ 实现：
  ```java
  @Override
      public boolean equals(Object other) {
          if (this == other) {
              return true;
          }
          if (other == null) {
              return false;
          }
          if (other.getClass() != this.getClass()) {
              return false;
          }
  
          ArraySet<T> o = (ArraySet<T>) other;
          if (o.size() != this.size()) {
              return false;
          }
  
          for (T item : items) {
              if (!o.contains(item)) {
                  return false;
              }
          }
          return true;
      }
  ```

+ 事实上，在java中要重写 `.equals()`方法时，要遵守几条规则：

  + equals必须时等价关系
    + **自反**：  `x.equals（x）` 为 true
    + **对称**：当且仅当 `y.equals（x）` 时 `x.equals（y）`
    + **传递**：`x.equals（y）` 和 `y.equals（z）` 表示 `x.equals（z）`
  + 它必须接受Object参数，以便重写方法
  +  如果 `x.equals（y）` 则它必须是一致的，那么只要 `x` 和 `y` 保持不变： `x` 必须继续等于 `y`
  + 对于 null **从来都不是真的** `x.equals（null）` 必须为 false

### extra

+ `tostring()`另外实现

+ ```java
  @Override
  public String toString() {
      List<String> listOfItems = new ArrayList<>();
      for (T x : this) {
          listOfItems.add(x.toString());
      }
      return "{" + String.join(", ", listOfItems) + "}";
  } 
  ```

+ 静态工厂方法of

  + 静态工厂方法 `of` 是一种常见的设计模式，用于创建对象实例。它通常用于提供一种更简洁、更灵活的方式来创建对象，而不是直接使用构造函数。

  + 静态工厂方法 `of` 的主要作用是：

    - **简化对象创建**：
      - 提供一种更直观的方式来创建对象，尤其是当构造函数参数较多或复杂时。
    - **隐藏实现细节**：
      - 封装对象的创建逻辑，调用者无需关心具体的实现细节。
    - **支持不可变对象**：
      - 常用于创建不可变对象（如 `List.of()`、`Set.of()` 等）。
    - **提供命名**：
      - 静态工厂方法可以有描述性的名称（如 `of`、`create`、`newInstance` 等），使代码更易读。

  + ### **静态工厂方法 `of` 的实现**

    ```java
    public static <Glerp> ArraySet<Glerp> of(Glerp... stuff) {
        ArraySet<Glerp> returnSet = new ArraySet<Glerp>();
        for (Glerp x : stuff) {
            returnSet.add(x);
        }
        return returnSet;
    }
    ```

    #### 代码解析：

    1. **方法签名**：

       - `public static <Glerp> ArraySet<Glerp> of(Glerp... stuff)`：
         - `public static`：静态方法，可以通过类名直接调用。
         - `<Glerp>`：泛型类型参数，表示方法可以处理任意类型的元素。
         - `ArraySet<Glerp>`：返回一个 `ArraySet` 实例，其元素类型为 `Glerp`。
         - `Glerp... stuff`：可变参数，**允许传入任意数量**的 `Glerp` 类型参数。

    2. **方法逻辑**：

       - 创建一个新的 `ArraySet<Glerp>` 实例。
       - 遍历可变参数 `stuff`，将每个元素添加到 `ArraySet` 中。
       - 返回填充好的 `ArraySet`。

    3. **示例用法**：

       java

       ```java
       ArraySet<String> asetOfStrings = ArraySet.of("hi", "I'm", "here");
       System.out.println(asetOfStrings);
       ```

       输出：

       ```
       {hi, I'm, here}
       ```

+ **静态工厂方法的适用场景**

  1. **创建不可变对象**：
     - 静态工厂方法常用于创建不可变对象（如 `List.of()`、`Set.of()`）。
  2. **简化对象创建**：
     - 当构造函数参数较多或复杂时，静态工厂方法可以提供更简洁的调用方式。
  3. **隐藏实现细节**：
     - 当对象的创建逻辑较复杂时，可以使用静态工厂方法封装细节。
  4. **支持缓存**：
     - 静态工厂方法可以实现对象缓存（如 `Integer.valueOf()`）。

## 捕获异常

 **`try` 和 `catch` 的基本语法**

```java
try {
    // 可能抛出异常的代码
} catch (ExceptionType e) {
    // 处理异常的代码
}
```

- **`try` 块**：
  - 包含可能抛出异常的代码。
  - 如果 `try` 块中的代码抛出了异常，程序会立即跳转到对应的 `catch` 块。
- **`catch` 块**：
  - 捕获并处理特定类型的异常。
  - `ExceptionType` 是异常的类型（如 `NullPointerException`、`ArithmeticException` 等）。
  - `e` 是异常对象，可以用于获取异常的详细信息。

+ 补充

> ###  **`catch` 块的多个异常类型**
>
> 可以为一个 `try` 块添加多个 `catch` 块，以捕获不同类型的异常：
>
> ```java
> try {
>     int[] arr = {1, 2, 3};
>     System.out.println(arr[5]); // 可能抛出 ArrayIndexOutOfBoundsException
>     int result = divide(10, 0); // 可能抛出 ArithmeticException
> } catch (ArrayIndexOutOfBoundsException e) {
>     System.out.println("Array index out of bounds: " + e.getMessage());
> } catch (ArithmeticException e) {
>     System.out.println("Arithmetic error: " + e.getMessage());
> }
> ```
>
> #### 输出：
>
> ```java
> Array index out of bounds: Index 5 out of bounds for length 3
> ```
>
> ------
>
> ### finally` 块**
>
> `finally` 块用于定义无论是否发生异常都必须执行的代码：
>
> ```java
> try {
>     int result = divide(10, 0);
> } catch (ArithmeticException e) {
>     System.out.println("Error: " + e.getMessage());
> } finally {
>     System.out.println("This will always run.");
> }
> ```
>
> #### 输出：
>
> ```java
> Error: / by zero
> This will always run.
> ```
>
> - **`finally` 的作用**：
>   - 通常用于释放资源（如关闭文件、数据库连接等）。
>   - 无论是否发生异常，`finally` 块中的代码都会执行。
>
> ------
>
> ### **捕获所有异常**
>
> 可以使用 `Exception` 类捕获所有类型的异常：
>
> ```java
> try {
>     int[] arr = {1, 2, 3};
>     System.out.println(arr[5]); // 可能抛出 ArrayIndexOutOfBoundsException
>     int result = divide(10, 0); // 可能抛出 ArithmeticException
> } catch (Exception e) {
>     System.out.println("An error occurred: " + e.getMessage());
> }
> ```
>
> #### 输出：
>
> ```java
> An error occurred: Index 5 out of bounds for length 3
> ```
>
> - **注意**：
>   - 捕获所有异常可能会导致代码难以调试，因此应尽量避免滥用。
>
> ------
>
> ### **自定义异常**
>
> 除了捕获 Java 内置的异常，还可以自定义异常类：
>
> ```java
> class CustomException extends Exception {
>     public CustomException(String message) {
>         super(message);
>     }
> }
> 
> public class Main {
>     public static void main(String[] args) {
>         try {
>             throw new CustomException("This is a custom exception!");
>         } catch (CustomException e) {
>             System.out.println("Caught custom exception: " + e.getMessage());
>         }
>     }
> }
> ```
>
> #### 输出：
>
> ```java
> Caught custom exception: This is a custom exception!
> ```



## Checked vs. Unchecked Exceptions

在 Java 中，异常分为两类：**Checked Exceptions（已检查异常）** 和 **Unchecked Exceptions（未检查异常）**。以下是它们的核心区别以及如何处理它们：

------

1. **Checked Exceptions（已检查异常）**

- **定义**：

  - 编译器强制要求处理的异常。
  - 必须在代码中显式捕获（`catch`）或声明抛出（`throws`），否则代码无法编译。

- **特点**：

  - 通常是**外部因素**导致的错误，例如文件不存在（`FileNotFoundException`）、网络连接失败（`IOException`）等。
  - 编译器认为这些异常是可以预见的，因此要求程序员必须处理。

- **示例**：

  ```java
  public void readFile() throws IOException {
      FileReader file = new FileReader("example.txt"); // 可能抛出 IOException
  }
  ```

- **处理方式**：

  1. **捕获异常（`catch`）**：

     ```java
     try {
         FileReader file = new FileReader("example.txt");
     } catch (IOException e) {
         System.out.println("File not found!");
     }
     ```

  2. **声明抛出（`throws`）**：

     ```java
     public void readFile() throws IOException {
         FileReader file = new FileReader("example.txt");
     }
     ```

------

2. **Unchecked Exceptions（未检查异常）**

- **定义**：

  - 编译器不强制要求处理的异常。
  - 通常是程序逻辑错误导致的，例如空指针（`NullPointerException`）、数组越界（`ArrayIndexOutOfBoundsException`）等。

- **特点**：

  - 这些异常在运行时才会被发现，编译器无法提前检查。
  - 通常是程序员错误导致的，无法通过外部手段修复。

- **示例**：

  ```java
  public void divide(int a, int b) {
      if (b == 0) {
          throw new ArithmeticException("Division by zero!"); // 运行时抛出异常
      }
      System.out.println(a / b);
  }
  ```

- **处理方式**：

  - 可以选择捕获，但通常建议修复代码逻辑，而不是依赖异常处理。

------

3. **Checked vs. Unchecked 的区别**

| **特性**               | **Checked Exceptions**             | **Unchecked Exceptions**                      |
| :--------------------- | :--------------------------------- | :-------------------------------------------- |
| **编译器是否强制处理** | 是                                 | 否                                            |
| **常见类型**           | `IOException`, `SQLException`      | `NullPointerException`, `ArithmeticException` |
| **原因**               | 外部因素（如文件不存在、网络问题） | 程序逻辑错误（如空指针、除零）                |
| **处理方式**           | 必须捕获或声明抛出                 | 可选捕获，通常修复代码逻辑                    |

------

4. **如何选择使用 Checked 还是 Unchecked 异常？**

- **使用 Checked Exceptions**：
  - 当异常是可以预见的，并且调用者有可能恢复或处理时。
  - 例如：文件操作、网络请求等。
- **使用 Unchecked Exceptions**：
  - 当异常是由于程序逻辑错误导致的，调用者无法恢复时。
  - 例如：空指针、数组越界等。

------

5. **最佳实践**

1. **捕获异常时**：
   - 尽量捕获具体的异常类型，而不是直接捕获 `Exception`。
   - 在 `catch` 块中处理异常，而不是简单地打印堆栈信息。
2. **声明抛出异常时**：
   - 如果方法内部无法处理异常，可以通过 `throws` 将异常抛给调用者处理。
   - 确保调用者知道方法可能抛出哪些异常。
3. **避免滥用 Checked Exceptions**：
   - 如果异常是程序逻辑错误导致的，使用 `RuntimeException` 或其子类。
   - 过多的 Checked Exceptions 会导致代码冗长且难以维护。

------

6. **示例代码**

Checked Exception 示例：

```java
public void readFile(String path) throws IOException {
    FileReader file = new FileReader(path); // 可能抛出 IOException
    // 其他操作
}

public static void main(String[] args) {
    try {
        readFile("example.txt");
    } catch (IOException e) {
        System.out.println("Error reading file: " + e.getMessage());
    }
}
```

Unchecked Exception 示例：

```java
public void divide(int a, int b) {
    if (b == 0) {
        throw new ArithmeticException("Division by zero!"); // 运行时抛出异常
    }
    System.out.println(a / b);
}

public static void main(String[] args) {
    try {
        divide(10, 0);
    } catch (ArithmeticException e) {
        System.out.println("Error: " + e.getMessage());
    }
}
```

------

7. **总结**

- **Checked Exceptions**：
  - 编译器强制要求处理。
  - 通常是外部因素导致的错误。
  - 必须通过 `catch` 或 `throws` 处理。
- **Unchecked Exceptions**：
  - 编译器不强制要求处理。
  - 通常是程序逻辑错误导致的。
  - 可以选择捕获，但通常建议修复代码逻辑。

通过合理使用 Checked 和 Unchecked Exceptions，可以编写出更健壮、更易维护的代码。

# 七、软件包和访问控制 Packages and Access Control

## 7.1 包和JAR文件 packages and JAR files

### package

+ 一个组织类和接口的命名空间。

+ 通常，在创建包时，您应该遵循以下命名约定：包名为网站地址倒过来。

```
ug.joshh.animal; // note: his website is joshh.ug
com.baidu
```

### 使用包

+ 从同一个包中访问类：
  ```java
  Dog d = new Dog(...)
  ```

+ 从包外部访问类：
  ```java
  ug.joshh.animal.Dog d = new ug.joshh.animal.Dog(...)
  ```

+ 简化操作——提前导入包：
  ```java
  import ug.joshh.animal.Dog(...)
      ...
  Dog d = new Dog(...)
  ```

### 创建包

+ 两个步骤：

  1、将包的名字放在此包每个文件的顶部：

  ```java
  package ug.joshh.animal;
  
  public class Dog{
      private String name;
      private String breed;
      ...
  }
  ```

  2、将文件存储再2具有相应文件夹名称的文件夹内

### 在 `inteliJ` 中创建包

1、右键单击 `Package name`

2、选择 `New -> Java Class`



### 默认软件包Default packages

+ 任何在文件顶部没有明确包名的Java类都会自动被视为“default”的一部分。
+ 尽量避免

### Jar文件

- 通常，程序包含多个`.class`文件。如果要共享这个程序，而不是共享所以的class文件，可以创建JAR文件将所有文件压缩到一起。
- 创建方法
  +  Go to File → Project Structure → Artifacts → JAR → “From modules with dependencies”
  +  Click OK a couple of times
  + Click Build → Build Artifacts (this will create a JAR file in a folder called “Artifacts”)
  +  Distribute this JAR file to other Java programmers, who can now import it into IntelliJ (or otherwise)

+ 构建系统

## 7.2 访问控制 Access Control

### private

+ 只有给定类中的代码才可以访问私有成员。它对其他所有内容都是真正私有的，因为**子类、包和其他外部类**无法访问私有成员。
+ 总结：只有类本身需要这段代码

### Package Private

+ 如果没有显示修饰符，这是Java成员**默认**访问权限。包私有意味着属于**同一个包的类**可以访问这些成员，但子类不能。
+ 总结：只有位于同一个包中的类才能访问

### Protected

+ 受保护的对象对外部世界是受保护的，因此**同一个包中的类**和子类可以访问这些成员，但外部世界（例如包外部的类或非子类）不行。
+ 总结：子类可能需要它，但子类的客户端不需要

### public

+ 所有人都可以访问

### 注意事项

+ Default package 默认包
  + 没有包声明的代码会自动成为默认包的一部分。如果这些类的成员没有访问修饰符（即**包私有**），那么由于所有内容都属于同一个（未命名的）默认包，这些成员**仍然可以在这些“默认”包类之间访问**。
  + 尽量避免
+ 访问仅基于**静态类型**
  + 访问权限是基于静态类型（编译时类型）而不是动态类型（运行时类型）的
+ **接口**中的方法
  - 接口中的**方法****默认是 `public`** 的，即使不写修饰符。
  - 接口中的**变量默认是 `public static fina**l` 的。

#### **访问控制的总结表格**

| 修饰符           | 本类 | 同一包 | 子类 | 其他包 |
| :--------------- | :--- | :----- | :--- | :----- |
| `public`         | ✔️    | ✔️      | ✔️    | ✔️      |
| `protected`      | ✔️    | ✔️      | ✔️    | ❌      |
| `默认（包私有）` | ✔️    | ✔️      | ❌    | ❌      |
| `private`        | ✔️    | ❌      | ❌    | ❌      |

# 八、高效性编程

## 8.1 封装、API和抽象数据类型ADT

+ 高效编程体现在两个方面：
  1. **编程成本**
     - 开发程序需要多长时间？
     - 代码是否易于阅读、修改和维护？
  2. **执行成本**（从下周开始讨论）
     - 程序执行需要多少时间？
     - 程序需要多少内存？

------

### 一些有用的 Java 特性（在 61B 中讨论过）

1. **包（Packages）**
   - **优点**：组织代码，使某些内容包私有。
   - **缺点**：过于具体。
2. **静态类型检查（Static Type Checking）**
   - **优点**：早期检查错误，代码更易读。
   - **缺点**：不够灵活（例如需要类型转换）。
3. **继承（Inheritance）**
   - **优点**：代码复用。
   - **缺点**：“是一个”关系，调试路径复杂，无法实例化，必须实现接口的所有方法。

------

### 封装

+ 模块`module`：
  一组协同工作以完成某项任务或相关任务的方法
+ 封装`Encapsulated`：
  如果一个模块的实现完全隐藏，只能通过文档化的接口访问，则称该模块是封装的

------

### API（应用程序编程接口）

+ API 是抽象数据类型（ADT）的构造函数和方法列表，以及每个方法的简短描述。
+ API 包括语法规范和语义规范：
  - **语法规范**：编译器验证语法是否正确（即 API 中指定的所有内容是否存在）。
  - **语义规范**：测试帮助验证语义是否正确（即一切是否按预期工作）。语义规范通常用英语描述（可能包括使用示例），数学上精确的形式规范虽然可能，但并不普遍。

------

### ADT（抽象数据类型）

+ ADT 是高级类型，由其行为定义，而不是其实现。

+ 例如，Proj1 中的 `Deque` 是一个 ADT，它具有某些行为（如 `addFirst`、`addLast` 等），但我们实际用于实现它的数据结构是 `ArrayDeque` 和 `LinkedListDeque`。

+ 一些 ADT 实际上是其他 ADT 的特殊情况。例如，栈（Stack）和队列（Queue）只是具有更特定行为的列表。

------

### 练习 8.1.1

编写一个使用链表作为底层数据结构的 `Stack` 类。你只需要实现一个方法：`push(Item x)`。确保使类具有泛型，`Item` 是泛型类型！

以下是三种常见的实现方式：

#### 1. 扩展（Extension）

```java
public class ExtensionStack<Item> extends LinkedList<Item> {
    public void push(Item x) {
        add(x);
    }
}
```

- **特点**：直接继承 `LinkedList<Item>` 的方法并使用它们。

#### 2. 委托（Delegation）

```java
public class DelegationStack<Item> {
    private LinkedList<Item> L = new LinkedList<Item>();
    public void push(Item x) {
        L.add(x);
    }
}
```

- **特点**：创建一个链表对象并调用其方法来实现目标。

#### 3. 适配器（Adapter）

```java
public class StackAdapter<Item> {
    private List L;
    public StackAdapter(List<Item> worker) {
        L = worker;
    }

    public void push(Item x) {
        L.add(x);
    }
}
```

- **特点**：可以使用任何实现 `List` 接口的类（如 `LinkedList`、`ArrayList` 等）。

+ 注意：在委托和扩展之间，委托是通过传入一个类来实现的，而扩展是通过继承实现的（尽管乍一看可能很难注意到）

### 委托 vs 扩展

目前来看，委托和扩展似乎可以互换，但在使用时必须记住一些重要区别：

- **扩展**：当你了解父类的实现细节时使用。换句话说，你知道方法的实现方式。此外，扩展意味着你正在扩展的类与扩展它的类行为相似。
- **委托**：当你不希望当前类被视为你从中提取方法的类的版本时使用。

### 视图（Views）

视图是现有对象的替代表示。视图本质上限制了用户对底层对象的访问。然而，通过视图所做的更改会影响实际对象。

例如：

```java
List<String> L = new ArrayList<>();
L.add("at"); L.add("ax"); ...
List<String> SL = L.subList(1, 4);
SL.set(0, "jug");
```

- **用途**：例如，如果我们只想反转列表的一部分，可以使用子列表视图来实现。

### 补充：委托、适配器、视图的再介绍

> ## 1. 委托（Delegation）
>
> ### 定义
>
> 委托是一种设计模式，通过将一个类的功能委托给另一个类的对象来实现。换句话说，一个类不直接实现某些功能，而是将这些功能交给另一个类的对象来完成。
>
> ### 核心思想
>
> - **“有一个”关系**：一个类持有另一个类的对象，并通过调用该对象的方法来完成工作。
> - **代码复用**：通过委托，可以复用其他类的功能，而不需要继承。
>
> ### 理解：
>
> + 创建一个委托类，存放操作类，在委托类中实现功能
>
> ### 示例
>
> 假设我们有一个 `Printer` 类，负责打印内容。我们可以通过委托的方式，将打印功能交给 `Printer` 类的对象来完成。
>
> ```java
> // 打印机类
> class Printer {
>     public void print(String document) {
>         System.out.println("打印内容: " + document);
>     }
> }
> 
> // 办公室类，委托 Printer 类来完成打印
> class Office {
>     private Printer printer; // 委托对象
> 
>     public Office(Printer printer) {
>         this.printer = printer;
>     }
> 
>     public void printDocument(String document) {
>         // 委托 Printer 对象完成打印
>         printer.print(document);
>     }
> }
> 
> // 测试
> public class Main {
>     public static void main(String[] args) {
>         Printer printer = new Printer();
>         Office office = new Office(printer);
>         office.printDocument("年度报告"); // 输出: 打印内容: 年度报告
>     }
> }
> ```
>
> ### 优点
>
> - **灵活性**：可以动态更换委托对象。
> - **解耦**：将功能分离到不同的类中，降低耦合度。
>
> ### 缺点
>
> - **代码量增加**：需要显式创建委托对象并调用其方法。
>
> ------
>
> ## 2. 适配器（Adapter）
>
> ### 定义
>
> 适配器是一种结构型设计模式，用于将一个类的接口转换成客户端期望的另一个接口。适配器通常用于解决接口不兼容的问题。
>
> ### 核心思想
>
> - **接口转换**：通过适配器类，将不兼容的接口转换为兼容的接口。
> - **复用现有代码**：适配器模式可以复用现有的类，而不需要修改其代码。
>
> ### 理解：
>
> + 用适配器让旧的类实现新的接口
>
> ### 示例
>
> 假设我们有一个 `LegacyPrinter` 类，它的打印方法与新系统的接口不兼容。我们可以通过适配器模式来解决这个问题。
>
> ```java
> // 旧打印机类
> class LegacyPrinter {
>     public void printDocument(String text) {
>         System.out.println("旧打印机打印: " + text);
>     }
> }
> 
> // 新系统的打印接口
> interface Printer {
>     void print(String document);
> }
> 
> // 适配器类，将 LegacyPrinter 适配到 Printer 接口
> class PrinterAdapter implements Printer {
>     private LegacyPrinter legacyPrinter;
> 
>     public PrinterAdapter(LegacyPrinter legacyPrinter) {
>         this.legacyPrinter = legacyPrinter;
>     }
> 
>     @Override
>     public void print(String document) {
>         // 调用 LegacyPrinter 的方法
>         legacyPrinter.printDocument(document);
>     }
> }
> 
> // 测试
> public class Main {
>     public static void main(String[] args) {
>         LegacyPrinter legacyPrinter = new LegacyPrinter();
>         Printer printer = new PrinterAdapter(legacyPrinter);
>         printer.print("新报告"); // 输出: 旧打印机打印: 新报告
>     }
> }
> ```
>
> ### 优点
>
> - **兼容性**：可以让不兼容的接口一起工作。
> - **复用性**：可以复用现有的类，而不需要修改其代码。
>
> ### 缺点
>
> - **复杂性增加**：引入了额外的适配器类，增加了代码的复杂性。
>
> ------
>
> ## 3. 视图（Views）
>
> ### 定义
>
> 视图是一种设计模式，用于提供对底层数据的特定表示或访问方式。视图通常用于限制或转换对数据的访问。
>
> ### 核心思想
>
> - **数据封装**：视图隐藏了底层数据的实现细节，只暴露特定的接口。
> - **动态更新**：视图可以动态反映底层数据的变化。
>
> ### 详细说明
>
> ### 1. **什么是视图？**
>
> 视图是底层数据的一种特定表示或访问方式。它并不存储实际的数据，而是提供对现有数据的某种“窗口”或“视角”。视图可以限制或转换对数据的访问，同时动态反映底层数据的变化。
>
> - **类比**：视图就像是一面镜子，镜子本身并不存储图像，而是反射出实际物体的影像。如果你移动物体，镜子中的影像也会随之改变。
> - **在编程中**：视图通常是对现有数据结构（如列表、集合、映射等）的一种封装或包装。
>
> ------
>
> ### 2. **视图的特点**
>
> 1. **动态性**
>    视图是动态的，它会实时反映底层数据的变化。如果底层数据被修改，视图也会自动更新。
> 2. **轻量级**
>    视图本身不存储数据，只是提供对现有数据的访问方式，因此它的内存开销很小。
> 3. **限制访问**
>    视图可以限制对底层数据的访问，例如只允许读取部分数据，或者只允许修改部分数据。
> 4. **接口一致性**
>    视图通常与底层数据结构实现相同的接口，因此可以无缝替换底层数据。
>
> ------
>
> ### 3. **视图的常见用途**
>
> 1. **子列表视图**
>    例如，Java 中的 `List.subList()` 方法返回一个子列表视图，该视图是原列表的一部分。
> 2. **只读视图**
>    例如，Java 中的 `Collections.unmodifiableList()` 方法返回一个只读视图，禁止对列表的修改。
> 3. **映射视图**
>    例如，Java 中的 `Map.keySet()` 方法返回一个键的视图，`Map.values()` 方法返回一个值的视图。
> 4. **数据转换视图**
>    例如，可以将一个列表转换为另一种数据类型的视图（如将字符串列表转换为整数列表）。
>
> ------
>
> ### 4. **视图的实现方式**
>
> 视图通常通过以下方式实现：
>
> 1. **封装底层数据**
>    视图类内部持有一个对底层数据的引用，并通过该引用访问数据。
> 2. **实现相同的接口**
>    视图类通常与底层数据结构实现相同的接口，以提供一致的访问方式。
> 3. **动态更新**
>    视图类的方法会实时调用底层数据的方法，确保视图与底层数据保持一致。
>
> ### 示例
>
> 在 Java 中，`List.subList()` 方法返回一个视图，该视图是原列表的一部分。
>
> ```java
> import java.util.ArrayList;
> import java.util.List;
> 
> public class Main {
>     public static void main(String[] args) {
>         // 创建一个 ArrayList
>         List<String> list = new ArrayList<>();
>         list.add("A");
>         list.add("B");
>         list.add("C");
>         list.add("D");
> 
>         // 创建一个子列表视图
>         List<String> subList = list.subList(1, 3); // 包含索引 1 和 2
> 
>         // 修改子列表
>         subList.set(0, "X"); // 将 "B" 改为 "X"
> 
>         // 查看原列表
>         System.out.println("原列表: " + list); // 输出: [A, X, C, D]
>     }
> }
> ```
>
> #### 示例 2：只读视图
>
> Java 中的 `Collections.unmodifiableList()` 方法返回一个只读视图。
>
> ```java
> import java.util.ArrayList;
> import java.util.Collections;
> import java.util.List;
> 
> public class Main {
>     public static void main(String[] args) {
>         // 创建一个 ArrayList
>         List<String> list = new ArrayList<>();
>         list.add("A");
>         list.add("B");
> 
>         // 创建一个只读视图
>         List<String> readOnlyList = Collections.unmodifiableList(list);
> 
>         // 尝试修改只读视图（会抛出异常）
>         try {
>             readOnlyList.add("C"); // 抛出 UnsupportedOperationException
>         } catch (UnsupportedOperationException e) {
>             System.out.println("只读视图不允许修改！");
>         }
> 
>         // 修改原列表
>         list.add("C");
> 
>         // 查看只读视图
>         System.out.println("只读视图: " + readOnlyList); // 输出: [A, B, C]
>     }
> }
> ```
>
> **输出：**
>
> ```
> 只读视图不允许修改！
> 只读视图: [A, B, C]
> ```
>
> **解释：**
>
> - `readOnlyList` 是 `list` 的一个只读视图，禁止对列表的修改。
> - 修改 `list` 会动态反映在 `readOnlyList` 中。
>
> ------
>
> #### 示例 3：映射视图
>
> Java 中的 `Map.keySet()` 和 `Map.values()` 方法返回键和值的视图。
>
> ```java
> import java.util.HashMap;
> import java.util.Map;
> import java.util.Set;
> 
> public class Main {
>     public static void main(String[] args) {
>         // 创建一个 HashMap
>         Map<String, Integer> map = new HashMap<>();
>         map.put("A", 1);
>         map.put("B", 2);
> 
>         // 获取键的视图
>         Set<String> keys = map.keySet();
> 
>         // 获取值的视图
>         java.util.Collection<Integer> values = map.values();
> 
>         // 修改映射
>         map.put("C", 3);
> 
>         // 查看键和值的视图
>         System.out.println("键视图: " + keys); // 输出: [A, B, C]
>         System.out.println("值视图: " + values); // 输出: [1, 2, 3]
>     }
> }
> ```
>
> **输出：**
>
> ```java
> 键视图: [A, B, C]
> 值视图: [1, 2, 3]
> ```
>
> **解释：**
>
> - `keys` 是 `map` 的键视图，`values` 是 `map` 的值视图。
> - 修改 `map` 会动态反映在 `keys` 和 `values` 中。
>
> ### 优点
>
> - **灵活性**：可以动态创建数据的特定视图。
> - **一致性**：视图会动态反映底层数据的变化。
>
> ### 缺点
>
> - **潜在的性能问题**：如果视图的实现依赖于底层数据，可能会导致性能问题。
>
> ------
>
> ## 总结
>
> | 模式       | 核心思想                           | 适用场景                   | 优点               | 缺点           |
> | :--------- | :--------------------------------- | :------------------------- | :----------------- | :------------- |
> | **委托**   | 将功能交给另一个类的对象来完成     | 需要复用其他类的功能       | 灵活性高，解耦     | 代码量增加     |
> | **适配器** | 将不兼容的接口转换为兼容的接口     | 解决接口不兼容问题         | 兼容性强，复用性好 | 复杂性增加     |
> | **视图**   | 提供对底层数据的特定表示或访问方式 | 需要动态创建数据的特定视图 | 灵活性高，动态更新 | 潜在的性能问题 |

