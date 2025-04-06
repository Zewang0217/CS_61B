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
  + 补充：初始化和实例化区别
    + 实例化：
      创建一个类的对象（实例）的过程，使用`new`关键字完成
    + 初始化：
      为变量或对象赋初始值的过程（构造函数等）

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

+ 为什么静态类型是`List`而不是`ArrayList`？

+  **面向接口编程（Programming to Interface）**

  - `List` 是一个接口，`ArrayList` 是它的一个具体实现类。
  - 声明变量时使用接口类型 (`List`)，而不是具体实现类 (`ArrayList`)，可以使代码更灵活、更易于维护。

  **好处：**

  - **松耦合**：如果以后需要更换 `List` 的实现（比如换成 `LinkedList`），只需修改 `new ArrayList<>()` 部分，而不需要修改所有使用 `L` 的代码。
  - **符合设计原则**：依赖抽象（`List`）而不是具体实现（`ArrayList`），符合 **"依赖倒置原则"（Dependency Inversion Principle, DIP）**。

+ **什么时候应该用具体类声明变量？**

  少数情况下，如果你需要调用 `ArrayList` 特有的方法（如 `trimToSize()`），才需要用 `ArrayList` 声明变量：

  

  ```java
  ArrayList<Integer> L = new ArrayList<>();
  L.trimToSize();  // 只有 ArrayList 有这个方法
  ```

  但这种情况较少见，通常仍然优先使用 `List` 接口。

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

+ 用于实现个性化的`println`格式

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
> ### `finally` 块**
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

## 8.2 渐进学 I：渐近分析导论

#### 算法成本示例
- **目标**：判断一个有序数组中是否包含重复元素。
- **低效算法**：考虑每一对元素，如果有匹配则返回`true`！
- **更好的算法**：利用数组的有序特性。我们知道，如果存在重复元素，它们必然相邻。比较相邻元素，第一次发现匹配就返回`true`！如果遍历完所有元素都没有匹配，则返回`false`。

可以看出，低效算法似乎比更好的算法做了更多不必要的、冗余的工作。但是多做了多少工作呢？我们究竟如何量化或确定一个程序的效率呢？本章将为你提供正式的技术和工具，用于比较各种算法的效率！
#### 运行时特征描述
为了研究这些技术，我们将描述以下两个函数`dup1`和`dup2`的运行时情况。这是我们上面讨论的两种查找重复元素的不同方法。在描述过程中需要牢记：
- 描述应该简单且在数学上严谨。
- 描述还应该清楚地展示`dup2`相对于`dup1`的优越性。

```java
// 低效的重复元素查找：比较所有元素
public static boolean dup1(int[] A) { 
    for (int i = 0; i < A.length; i += 1) {
        for (int j = i + 1; j < A.length; j += 1) {
            if (A[i] == A[j]) {
                return true;
            }
        }
    }
    return false;
}
// 更好的重复元素查找：仅比较相邻元素
public static boolean dup2(int[] A) {
    for (int i = 0; i < A.length - 1; i += 1) {
        if (A[i] == A[i + 1]) { 
            return true; 
        }
    }
    return false;
}
```
#### 衡量计算成本的技术
1. **技术1**：使用客户端程序以秒为单位测量执行时间（即实际观察程序运行的物理时间）
    - **步骤**：可以使用物理秒表；Unix系统有内置的`time`命令来测量执行时间；普林斯顿标准库中有`stopwatch`类。
    - **观察结果**：随着输入规模的增加，可以看到`dup1`完成所需的时间更长，而`dup2`完成的速度相对保持不变。
    - **优缺点**
        - **优点**：测量非常简单（只需使用秒表）。意义明确（查看实际完成所需的时间长度）。
        - **缺点**：测试可能需要花费大量时间。结果也可能因运行程序的机器、编译器、输入数据等因素而有所不同。
        - **与目标的匹配度**：这种方法很简单，这是优点，但在数学上不严谨。此外，由于机器、编译器、输入等因素导致的结果差异意味着，可能无法清楚地展示`dup1`和`dup2`之间的关系。还有其他方法吗？
2. **技术2A**：计算大小为`N = 10000`的数组的可能操作数
    - **步骤**：查看代码及其使用的各种操作（即赋值、增量等）；计算每个操作执行的次数。
    - **观察结果**：有些操作的计数计算起来比较棘手。计算这些数字的方法可能很复杂且繁琐。
    - **优缺点**
        - **优点**：在很大程度上与机器无关。模型中考虑了输入的依赖性。
        - **缺点**：计算繁琐。数组大小是任意选择的（我们计算了`N = 10000`时的情况，但对于更大或更小的`N`呢？那些情况下的操作计数是多少？）。操作次数并不能告诉你某个操作实际执行所需的时间（有些操作可能执行得比其他操作快）。这种方法虽然解决了上述时间模拟中的一些缺点，但它自身也存在问题。
3. **技术2B**：根据输入数组大小`N`计算可能的操作数（符号计数）
    - **优缺点**
        - **优点**：仍然与机器无关（只是计算操作次数）。模型中仍然考虑了输入的依赖性。而且，它告诉我们算法如何随着输入大小的变化而扩展。
        - **缺点**：计算更加繁琐。仍然无法告诉我们实际执行时间！
4. **检查点**：对`dup2`应用技术2A和2B
计算以下代码中每个操作相对于`N`的计数，并预测每个计数的大致数量级！
```java
for (int i = 0; i < A.length - 1; i += 1){
    if (A[i] == A[i + 1]) { 
        return true; 
    }
}
return false;
```
| 操作      | 符号计数     | 当`N = 10000`时的计数 |
| --------- | ------------ | --------------------- |
| `i = 0`   | 1            | 1                     |
| 小于`<`   | 0 到`N`      | 0 到 10000            |
| 增量`+=1` | 0 到`N - 1`  | 0 到 9999             |
| 等于`==`  | 1 到`N - 1`  | 1 到 9999             |
| 数组访问  | 2 到`2N - 2` | 2 到 19998            |
5. **检查点**：现在，考虑以下两个填写好的表格，你认为哪个算法更好，为什么？
| 操作        | 符号计数                | 当`N = 10000`时的计数 |
| ----------- | ----------------------- | --------------------- |
| `i = 0`     | 1                       | 1                     |
| `j = i + 1` | 1 到`N`                 | 1 到 10000            |
| 小于`<`     | 2 到`(N² + 3N + 2) / 2` | 2 到 50015001         |
| 增量`+=1`   | 0 到`(N² + N) / 2`      | 0 到 50005000         |
| 等于`==`    | 1 到`(N² - N) / 2`      | 1 到 49995000         |
| 数组访问    | 2 到`N² - N`            | 2 到 99990000         |
| 操作        | 符号计数                | 当`N = 10000`时的计数 |
| ---         | ---                     | ---                   |
| `i = 0`     | 1                       | 1                     |
| 小于`<`     | 0 到`N`                 | 0 到 10000            |
| 增量`+=1`   | 0 到`N - 1`             | 0 到 9999             |
| 等于`==`    | 1 到`N - 1`             | 1 到 9999             |
| 数组访问    | 2 到`2N - 2`            | 2 到 19998            |
6. **答案（以及算法扩展为何重要）**：`dup2`更好！原因如下：
    - **一种回答**：它完成相同目标所需的操作更少。
    - **更好的回答**：在最坏情况下，该算法的扩展性更好，比如`(N² + 3N + 2) / 2`与`N`的对比。
    - **更优的回答**：抛物线函数（如`N²`）的增长速度比线性函数（如`N`）快。这与“更好的回答”思路一致，但提供了更通用的几何直观理解。

#### 渐近行为

在大多数情况下，我们只关心当`N`非常大时的情况（渐近行为）。我们希望考虑哪些算法能够最好地处理大量数据，例如以下示例：
- 模拟数十亿相互作用的粒子
- 拥有数十亿用户的社交网络
- 编码数十亿字节的视频数据

扩展性好的算法（即表现类似线性函数）比扩展性相对较差的算法（即表现类似抛物线函数）具有更好的渐近运行时行为。

如果有两个函数，一个执行需要`2N²`次操作，另一个需要`500N`次操作，在某些情况下，比如当`N = 4`时（`32`次操作与`20000`次操作对比），难道不是执行`2N²`次操作的函数更快吗？
- 是的！对于一些较小的`N`，`2N²`可能小于`500N`。
- 然而，随着`N`的增长，`2N²`将占据主导。例如，当`N = 10000`时，`2 * 100000000`与`5 * 100000`对比。

重要的是图表的“形状”（即抛物线形与线性），目前我们可以非正式地将图表的形状称为“增长阶数”。
#### 回到查找重复元素问题
回到最初描述`dup1`和`dup2`运行时情况的目标：
- 描述应该简单且在数学上严谨。
- 描述还应该清楚地展示`dup2`相对于`dup1`的优越性。

我们已经完成了第二个任务！能够清楚地看到`dup2`的性能优于`dup1`。然而，我们的方法既不简单，在数学上也不够严谨。不过我们确实讨论了`dup1`的性能“类似”抛物线，而`dup2`的性能“类似”直线。现在，通过应用四种简化方法，我们将更正式地阐述这些表述的含义。
1. **直观简化1：仅考虑最坏情况**：在比较算法时，我们通常只关心最坏情况（尽管在本课程后面会看到一些例外情况）。
2. **检查点：增长阶数识别**：考虑以下算法的操作计数。你认为该算法运行时的增长阶数是什么？
| 操作计数 |              |
| -------- | ------------ |
| 小于`<`  | `100N² + 3N` |
| 大于`>`  | `N³ + 1`     |
| 与`&&`   | `5000`       |
3. **答案**：是立方阶（`N³`）！原因如下：假设`<`操作需要`α`纳秒，`>`操作需要`β`纳秒，`&&`操作需要`γ`纳秒。总时间是`α(100N² + 3N) + β(2N³ + 1) + 5000γ`纳秒。对于非常大的`N`，`2βN³`这一项比其他项大得多。如果有助于理解，你可以从微积分的角度思考。当`N`趋近于无穷大时会发生什么？当它变得非常大时，哪个项最终会占据主导地位？理解为什么这一项大得多是非常重要的一点！
4. **直观简化2：关注一种操作**：选择一些有代表性的操作作为整体运行时的代理。好的选择包括：增量操作、小于等于操作或数组访问操作；不好的选择包括：`j = i + 1`或`i = 0`这样的赋值操作。我们选择的操作可以称为“成本模型”。
5. **直观简化3：消除低阶项**：忽略低阶项！合理性检验：为什么这样做有意义呢？（与上面的检查点相关！）
6. **直观简化4：消除乘法常数**：忽略乘法常数。原因是它们没有实际意义！请记住，通过选择单个代表性操作，我们已经“舍弃”了一些信息。有些操作的计数为`3N²`、`N² / 2`等。一般来说，它们都属于`N²`这一“家族”/形状。这一步也与前面`500N`与`2N²`的例子相关。
7. **简化总结**：仅考虑最坏情况；选择一个代表性操作（也称为成本模型）；忽略低阶项；忽略乘法常数。
8. **检查点**：根据以下表格，对`dup2`应用这四个步骤。
| 操作计数  |                             |
| --------- | --------------------------- |
| `i = 0`   | 1                           |
| 小于`<`   | 0 到`N`                     |
| 增量`+=1` | 0 到`N - 1`                 |
| 等于`==`  | 1 到`N - 1`                 |
| 数组访问  | 2 到`2N - 2`                |
| 操作      | 最坏情况下的增长阶数        |
| ---       | ---                         |
| 数组访问  | `N`，或者小于/增量/等于操作 |
|           | `N`                         |
9. **总结我们（繁琐的）分析过程**：构建一个包含所有可能操作精确计数的表格（这需要花费大量精力！）；使用四种简化方法将表格转换为最坏情况下的增长阶数。但是，如果我们从一开始就使用简化方法，避免构建表格呢？
10. **简化分析过程**：不必构建整个表格，我们可以：选择成本模型（即我们想要计数的代表性操作）；通过以下两种方式确定代表性操作计数的增长阶数：进行精确计数并舍弃不必要的部分；或者通过直觉/观察来确定增长阶数（这需要练习！）。现在我们将使用这个过程重新分析`dup1`。
11. **嵌套循环分析：精确计数**：找到`dup1`最坏情况下运行时的增长阶数。
```java
int N = A.length;
for (int i = 0; i < N; i += 1)
    for (int j = i + 1; j < N; j += 1)
        if (A[i] == A[j])
            return true;
return false;
```
12. **成本模型**：`==`操作的次数。根据以下图表，如何确定`==`操作出现的次数呢？`y`轴表示`i`的每次增量，`x`轴表示`j`的每次增量。
13. **最坏情况下`==`操作的次数**：`Cost = 1 + 2 + 3 +... + (N - 2) + (N - 1)`。如何对这个成本进行求和呢？我们知道它也可以写成：`Cost = (N - 1) + (N - 2) +... + 3 + 2 + 1`。将这两个成本方程相加：`2 * Cost = N + N + N +... + N`。这里有多少个`N`项呢？是`N - 1`个！（通过将两个成本方程相加得到的和为`N`的项数）。因此：`2 * Cost = N(N - 1)`，所以：`Cost = N(N - 1) / 2`。如果进行简化（舍弃低阶项，去除乘法常数），我们得到最坏情况下的增长阶数为`N²`。
14. **嵌套循环分析：几何论证**：可以看到，相等操作的次数可以由一个直角三角形的面积给出，该直角三角形的边长为`N - 1`。因此，面积的增长阶数为`N²`。能够做到这一点需要时间和练习！
15. **形式化增长阶数**：给定某个函数`Q(N)`，我们可以应用最后两个简化方法来得到`Q(N)`的增长阶数。提醒：最后两个简化方法是舍弃低阶项和乘法常数。例如：`Q(N) = 3N³ + N²`，应用增长阶数的简化方法后，得到：`N³`。现在，我们将使用“大Θ”的形式化符号来表示我们对代码的分析方式。
16. **检查点**：以下5个函数的形状/增长阶数是什么？
| 函数             | 增长阶数 |
| ---------------- | -------- |
| `N³ + 3N⁴`       |          |
| `1/N + N³`       |          |
|                  |          |
| `1/N + 5`        |          |
| `Neⁿ + N`        |          |
| `40sin(N) + 4N²` |          |
17. **答案**：
| 增长阶数 |
| -------- |
| `N⁴`     |
| `N³`     |
| `1`      |
| `Neⁿ`    |
| `N²`     |
18. **大Θ**：假设我们有一个函数`R(N)`，其增长阶数为`f(N)`。用“大Θ”符号表示为`R(N) ∈ Θ(f(N))`。这个符号是正式表示我们上面所找到的“家族”的方式。例如（来自上面的检查点）：
```
N³ + 3N⁴ ∈ Θ(N⁴)
1/N + N³ ∈ Θ(N³)
1/N + 5 ∈ Θ(1)
Neⁿ + N ∈ Θ(Neⁿ)
40sin(N) + 4N² ∈ Θ(N²)
```
### 大 Θ 正式定义
$R(N) \in \Theta(f(N))$意味着存在正的常数$k_1$、$k_2$ ，使得对于所有大于某个$N_0$（一个非常大的$N$值）的$N$，都有$k_1 \cdot f(N) \leq R(N) \leq k_2 \cdot f(N)$ 。

+ 关注最好和最坏情况

### 大 Θ 与运行时分析
使用这个符号并不会改变我们分析运行时的方式（无需找到常数$k_1$、$k_2$ ）！唯一的区别是我们用 Θ 符号来代替“增长阶数”（例如，最坏情况下的运行时：$\Theta(N^{2})$ ）。
### 大 O 表示法
之前，我们使用大 Θ 来描述函数以及代码片段的增长阶数。回想一下，如果$R(N) \in \Theta(f(N))$，那么$R(N)$ 同时被$\Theta(f(N))$上界和下界约束。用上下界来描述运行时可以非正式地被认为是运行时“相等”。

例如：
| 函数$R(N)$         | 增长阶数       |
| ------------------ | -------------- |
| $N^3 + 3N^4$       | $\Theta(N^4)$  |
| $N^3 + 1/N$        | $\Theta(N^3)$  |
| $5 + 1/N$          | $\Theta(1)$    |
| $Ne^N + N$         | $\Theta(Ne^N)$ |
| $40\sin(N) + 4N^2$ | $\Theta(N^2)$  |

比如，$N^3 + 3N^4 \in \Theta(N^4)$，它同时被$N^4$上界和下界约束。

另一方面，大 O 可以被认为是一种运行时的不等式，即“小于等于”。例如，以下所有表述都是正确的：
$N^3 + 3N^4 \in O(N^4)$
$N^3 + 3N^4 \in O(N^6)$
$N^3 + 3N^4 \in O(N!)$
$N^3 + 3N^4 \in O(N^{N!})$

换句话说，如果一个函数，比如上面这个，被$N^4$上界约束，那么它也被所有上界大于等于$N^4$的函数上界约束。在渐近意义上，$N^3 + 3N^4$ “小于等于”所有这些函数。

回想大 Θ 的正式定义：$R(N) \in \Theta(f(N))$意味着存在正的常数$k_1$ 、$k_2$，使得对于所有大于某个$N_0$（一个非常大的$N$ ）的$N$，都有$k_1 \cdot f(N) \leq R(N) \leq k_2 \cdot f(N)$ 。
### 大 O 正式定义
类似地，大 O 的正式定义如下：$R(N) \in O(f(N))$意味着存在正的常数$k_2$ ，使得对于所有大于某个$N_0$（一个非常大的$N$ ）的$N$，都有$R(N) \leq k_2 \cdot f(N)$ 。可以观察到，这是一个比大 Θ 更宽松的条件，因为大 O 不关心下界。

+ 关注最坏情况

### 总结
给定一段代码，我们可以将其运行时表示为一个函数$R(N)$ ，$N$是函数输入的某个属性，通常情况下，$N$代表输入的大小。我们通常并不关心$R(N)$的精确值，而是只关心$R(N)$的增长阶数。一种常用（但并非通用）的方法是：
1. 选择一个代表性操作。
2. 令$C(N)$表示该操作出现的次数，它是关于$N$的函数。
3. 确定$C(N)$的增长阶数$f(N)$，即$C(N) \in \Theta(f(N))$ 。
4. 通常（但不总是）我们考虑最坏情况下的计数。
5. 如果该操作花费的时间是常数，那么$R(N) \in \Theta(f(N))$ 。 

## 8.3 渐近分析I

#### 循环的第一个示例

既然我们已经了解了一些运行时分析的知识，那就来研究一些更具挑战性的例子。我们的目标是通过练习，掌握运行时分析中涉及的模式和方法。理解这个概念可能有点棘手，所以练习得越多越好。

上次，我们看到了函数`dup1`，它用于检查列表中是否有元素首次重复：
```java
int N = A.length;
for (int i = 0; i < N; i += 1)
    for (int j = i + 1; j < N; j += 1)
        if (A[i] == A[j])
            return true;
return false;
```
我们有两种方法来进行运行时分析：第一种是计算操作的数量；第二种是通过几何方法。

**第一种方法**：由于主要的重复操作是比较操作，所以我们来计算必须执行的`==`操作的数量。在第一次遍历外层循环时，内层循环将运行`N - 1`次。第二次时，内层循环将运行`N - 2`次。然后是`N - 3`次…… 在最坏的情况下，我们必须遍历每个元素（外层循环运行`N`次）。

最后，我们可以看到比较的次数为：
\[C=1+2+3+...+(N-3)+(N-2)+(N-1)=\frac{N(N - 1)}{2}\]

\(\frac{N(N - 1)}{2}\)属于\(N^2\)这一类别。由于`==`操作是常数时间操作，所以在最坏情况下，总体运行时间为\(\theta(N^{2})\)。

![dup1_square](D:\Zewang\CS_61B\Note\image\dup1_square.png)

**第二种方法**：我们也可以从几何角度来分析这个问题。让我们画出在\(i\)、\(j\)组合的网格中使用`==`操作的情况：
```
N=6
0 = = = == =
1 = = == ==
2 == HI ==
i 3 == =
4
=
5
0 1 2 3 4 5
j
```
我们可以看到，`==`操作的数量与边长为`N - 1`的直角三角形的面积相同。因为面积属于\(N^{2}\)这一类别，所以我们再次得出总体运行时间为\(\theta(N^{2})\)。
#### 循环示例2
接下来，让我们看一个更复杂的例子。考虑以下函数，它有类似的嵌套`for`循环：
```java
public static void printParty(int N) {
    for (int i = 1; i <= N; i = i * 2) {
        for (int j = 0; j < i; j += 1) {
            System.out.println("hello"); 
            int ZUG = 1 + 1;
        }
    }
}
```
第一个循环每次将`i`乘以2来推进。内层循环从0运行到当前`i`的值。循环内的两个操作都是常数时间操作，所以我们来思考一下 “对于给定的\(N\)值，这个函数会打印多少次`hello`？”



上面的可视化工具帮助我们理解了`d![loops2_1](D:\Zewang\CS_61B\Note\image\loops2_1.png)up1`的运行时，所以我们在这里也采用类似的方法。我们将为嵌套的`for`循环绘制网格，然后跟踪给定\(N\)值下所需的打印语句总数。

如果\(N\)为1，那么`i`只能达到1，而`j`只能为0，因为\(0 < 1\)。所以只有一条打印语句：

![loops2_1](D:\Zewang\CS_61B\Note\image\loops2_1.png)

如果\(N\)为2，下一次循环时`i`将变为\(1 * 2 = 2\)，`j`可以达到1。打印语句的总数将是3条：第一次循环1条，第二次循环2条。

![loops2_2](D:\Zewang\CS_61B\Note\image\loops2_2.png)

当\(N\)为3时会发生什么呢？`i`会再进行一次循环吗？

嗯，在第二次循环后，`i = 2 * 2 = 4`，大于\(N\)，所以外层循环不会继续，在`i = 2`时就结束了，和\(N = 2\)的情况一样。\(N = 3\)的打印语句数量将与\(N = 2\)时相同。

下一个变化发生在\(N = 4\)时，当`i = 4`时有4次打印，`i = 2`时有3次打印，`i = 1`时有1次打印（记住`i`永远不会等于3）。所以总共是7次。

![loops2_3](D:\Zewang\CS_61B\Note\image\loops2_3.png)

我们可以继续完善这个图表，以获得更全面的情况。以下是到\(N = 18\)的情况：

![loops2_4](D:\Zewang\CS_61B\Note\image\loops2_4.png)

如果我们把循环每个阶段的计数加起来，会发现打印语句的数量是：\(C(N)=1 + 2 + 4 +... + N\)（如果\(N\)是2的幂次方）。

但这对运行时意味着什么呢？

同样，我们可以从几个角度来思考这个问题。既然我们已经开始用图形的方法，那就从这里入手。如果我们绘制\(0.5N\)（下面的虚线）、\(4N\)（上面的虚线）以及\(C(N)\)本身（红色阶梯线）的轨迹，会发现\(C(N)\)完全在这两条虚线之间。

![loops2_graph](D:\Zewang\CS_61B\Note\image\loops2_graph.png)

因此，根据定义，运行时也一定是线性的！

我们也可以用另一种方式来看待这个问题：

我们可以运用数学方法求解上面的等式，得到：\(C(N)=1 + 2 + 4 +... + N = 2N - 1\)（同样，如果\(N\)是2的幂次方）。例如，如果\(N = 8\)，\(1 + 2 + 4 + 8 = 15 = 2 * 8 - 1\)。

通过去除较小的项和乘法常数，我们知道\(2N - 1\)属于线性类别。

我们也可以在图表上绘制\(2N\)来观察：

![loops2_graph2](D:\Zewang\CS_61B\Note\image\loops2_graph2.png)

#### 没有神奇的捷径
如果有一种神奇的方法，只需看一眼算法就能知道其运行时，那就太好了。如果所有嵌套的`for`循环的运行时都是\(N^{2}\)，那就超级方便了。但事实并非如此。我们之所以知道这一点，是因为上面我们刚做了两个嵌套`for`循环的例子，它们的运行时各不相同。

归根结底，进行运行时分析没有捷径可走，需要仔细思考。不过，有一些有用的技巧和知识。
#### 技巧
- **求精确和**
- **写出示例**
- **画图**

我们在上面的例子中都用到了这些方法。
#### 需知的求和公式
这里有两个你会经常见到且应该记住的重要求和公式：

1+2+3+...+Q=Q(Q+1)/2=Θ(Q^2) (Sum of First Natural Numbers)

1+2+4+8+...+Q=2Q−1=Θ(Q) (Sum of First Powers of 2)

你在上面已经看到了这两个公式，它们在运行时分析中会反复出现。

#### 递归

既然我们已经研究了几个嵌套`for`循环的例子，现在来看看一个递归的例子。考虑以下函数：
```java
public static int f3(int n) {
    if (n <= 1) 
        return 1;
    return f3(n-1) + f3(n-1);
}
```
这个函数是做什么的呢？

让我们举个例子。如果调用`f3(4)`，它将返回`f3(4 - 1) + f3(4 - 1)`，也就是每个`f3(3 - 1) + f3(3 - 1)`，再进一步是每个`f3(2 - 1) + f3(2 - 1)`，而每个`f3(2 - 1)`都返回1。所以最后我们有8个`f3(2 - 1)`相加，结果为8。我们可以将其可视化为一棵树，其中每一层是函数的参数：

![asymptotics2_tree](D:\Zewang\CS_61B\Note\image\asymptotics2_tree.png)

你可以再举几个例子，会发现这个函数返回\(2^{N - 1}\)。这有助于理解这个函数的功能。
#### 直观方法
现在，让我们思考一下运行时。我们可以注意到，每次\(N\)增加1，所需的工作量就会翻倍：

![asymptotics2_tree2](D:\Zewang\CS_61B\Note\image\asymptotics2_tree2.png)

这个直观的分析表明，运行时是\(2^{N}\)。

这是一个相当不错的分析，但我们再用几种方法来研究这个例子。
#### 代数方法
解决这个问题的第二种方法是计算对`f3`的调用次数。例如：
*C*(1)=1 C(2)=1+2 *C*(2)=1+2 C(3)=1+2+4*C*(3)=1+2+4 C(N)=1+2+4+...+*C*(*N*)=1+2+4+...+ ???
我们如何推广最后一种情况呢？一个有用的方法是再举几个例子。\(C(4)\)是多少呢？

嗯，我们可以看上面`f3(4)`的图表，发现最后一行有8个方块，所以：
*C*(4)=1+2+4+8 and C(5)=1+2+4+8+16*C*(5)=1+2+4+8+16
这些式子中的最后一项都等于\(2^{N - 1}\)，例如：
\[16 = 2^{5 - 1} \quad 8 = 2^{4 - 1}\]
那么我们的通用形式就是：
\[C(N)=1 + 2 + 4 +... + 2^{N - 1}\]
这应该看起来有点眼熟了。上面我们看到过前\(Q\)个2的幂次方的和：\(1 + 2 + 4 + 8 +... + Q = 2Q - 1\)。

在这种情况下，\(Q = 2^{N - 1}\)，所以：
C(N)=2Q−1=2(2^N−1)−1=2^N−1
每次调用期间的工作（不包括递归工作）是常数时间，所以运行时是 θ*(2*N*)。

#### 递归关系
这种方法不是必读内容，超出了课程范围，但出于兴趣值得一提。

我们可以使用 “递归关系” 来计算调用次数，而不是用代数方法。它看起来是这样的：
\[C(1)=1 \quad C(N)=2C(N - 1)+1\]
用一种我们这里不详细介绍（但你可以在幻灯片或网上查阅）的方法展开这个式子，我们会得到与上面类似的和，并且同样可以化简为\(2^{N} - 1\)，得出相同的结果\(\theta(2^{N})\)。
#### 二分查找
二分查找是在列表中查找特定元素的一种很好的方法。它要求列表是有序的，并利用这一事实快速找到元素。

进行二分查找时，我们从列表的中间开始，检查该元素是否是我们想要的。如果不是，我们就判断：这个元素比我们要找的元素大还是小？

如果它更大，那么我们知道只需要查看列表中较小元素的那一半。如果它太小，那么我们只查看较大元素的那一半。通过这种方式，我们可以在每一步将剩余的选项数量减半，直到找到目标元素。

最坏的情况是什么呢？当我们要找的元素根本不在列表中时。那么我们会一直进行比较，直到排除了列表中的所有区域，没有更大或更小的部分可供查找。

关于二分查找的动画演示，可以查看这些幻灯片。

二分查找的直观运行时是多少呢？花点时间，运用你所知道的知识来思考一下。

我们从\(n\)个选项开始，然后是\(n/2\)，接着是\(n/4\)…… 直到只剩下1个。每次我们将数组减半，所以最后我们总共必须执行\(\log _{2}(n)\)次操作。每次\(\log _{2}(n)\)操作，例如找到中间元素并与之比较，都需要常数时间。所以总体运行时是\(\log _{2}(n)\)阶的。

不过需要注意的是，每一步并不是精确地将数组减半。如果数组长度是偶数，没有真正的 “中间” 元素，我们必须选择较小或较大的部分。但这是一个不错的直观分析方法。

接下来我们用精确的方法计算。

为了精确计算二分查找的运行时，我们像之前一样计算操作次数。

首先，我们定义成本模型：我们使用递归二分查找调用的次数。由于每次调用内部的操作次数是常数，调用次数会根据输入大小而变化，所以这是一个很好的成本模型。

就像我们之前看到的，让我们为特定的\(N\)计算一些示例次数。作为练习，在继续之前试着填写这个表格：
| N         | 1    | 2    | 3    | 4    | 5    | 6    | 7    | 8    | 9    | 10   | 11   | 12   | 13   |
| :-------- | :--- | :--- | :--- | :--- | :--- | :--- | :--- | :--- | :--- | :--- | :--- | :--- | :--- |
| **Count** |      |      |      |      |      |      |      |      |      |      |      |      |      |

好的，结果如下：

| N         | 1    | 2    | 3    | 4    | 5    | 6    | 7    | 8    | 9    | 10   | 11   | 12   | 13   |
| :-------- | :--- | :--- | :--- | :--- | :--- | :--- | :--- | :--- | :--- | :--- | :--- | :--- | :--- |
| **Count** | 1    | 2    | 2    | 3    | 3    | 3    | 3    | 4    | 4    | 4    | 4    | 4    | 4    |

这些结果似乎支持了我们上面关于\(\log _{2}(n)\)的直观分析。我们可以看到，只有当\(N\)是2的幂次方时，计数才会增加1。

但我们可以更精确：C(N)=⌊log2(N)⌋+1（这些类似`L`的符号是 “向下取整” 函数，它将表达式的结果向下舍入到最接近的整数）。

有几个值得知道的性质（证明见下文）：
⌊*f*(*N*)⌋=Θ(*f*(*N*)) ⌈f(N)⌉=Θ(f(N))

 logp(N)=Θ(logq(N))

最后一个性质本质上表明，对于对数运行时，对数的**底数根本不重要**，因为在大O表示法中它们都是等价的（这可以通过应用对数换底公式看出）。应用这些简化，我们可以看到：
Θ(⌊log2(*N*)⌋)=Θ(log*N*)
正如我们从直观分析中预期的那样。

**示例证明**：证明 ⌊f(N)⌋=Θ(f(N))⌊*f*(*N*)⌋=Θ(*f*(*N*))。
**解决方案**：
*f*(*N*)−1/2<*f*(*N*)≤⌊*f*(*N*)+1/2⌋≤*f*(*N*)+1/2
根据我们的大Θ规则，去掉常数项来简化*f*(*N*)+1/2 and f(N)−1/2*f*(*N*)−1/2，可以发现它们的阶都是\(f(N)\)。因此，⌊*f*(*N*)+1/2⌋被两个阶为\(f(N)\)的表达式所界定，所以它也是 Θ(f(N))。

**练习**：Prove ⌈f(N)⌉=Θ(f(N))⌈*f*(*N*)⌉=Θ(*f*(*N*)) **Exercise:** Prove logp(N)=Θ(logq(N))log*p*(*N*)=Θ(log*q*(*N*))

最后有个很酷的事实：对数时间超级快！它几乎和常数时间一样快，比线性时间要好得多。这就是为什么我们喜欢二分查找，而不是逐个遍历列表来寻找目标元素。

具体来看：
| \(N\)               | \(\log_2{N}\) | 典型运行时间（纳秒） |
| ------------------- | ------------- | -------------------- |
| 100                 | 6.6           | 1                    |
| 100,000             | 16.6          | 2.5                  |
| 100,000,000         | 26.5          | 4                    |
| 100,000,000,000     | 36.5          | 5.5                  |
| 100,000,000,000,000 | 46.5          | 7                    |
#### 归并排序
在最后一个示例中，我们将分析归并排序，这是另一种很棒的排序算法。

首先，让我们回顾一下选择排序，它最初将作为归并排序的一个构建块。

选择排序基于两个基本步骤：
1. 在未排序的元素中找到最小的元素，将其移动到前面，并 “固定” 到位。
2. 使用选择排序对剩余未排序/未固定的元素进行排序。如果分析选择排序，我们会发现它的时间复杂度是Θ(*N*2).。

**练习**：为了让自己确信选择排序的运行时间是\(\Theta(N^{2})\)，可以尝试用几何方法（试着画出每次排序调用时列表的状态）或计算操作次数。

这里我们引入另一个概念：任意时间单位。虽然某操作实际所需的时间取决于机器、具体操作等因素，但我们可以通过任意时间单位（AU）来大致了解时间。

如果对\(N = 6\)的列表进行选择排序，且运行时间为\(N^{2}\)阶，那么大约需要36 AU。如果\(N = 64\)，则大约需要2048 AU。我们不知道这是2048纳秒、秒还是年，但我们可以了解不同\(N\)值所需时间的相对大小。记住这个概念，以便后续分析。

既然了解了选择排序，现在来谈谈合并。

假设我们有两个已排序的数组，想要将它们合并成一个大的已排序数组。我们可以将一个数组追加到另一个数组后面，然后重新排序，但这样没有利用到每个数组已经排序的特性。我们如何利用这一特性呢？

事实证明，利用数组已排序的特性，我们可以更快地合并它们。最小的元素肯定在两个列表的开头之一。所以我们比较这两个开头元素，将较小的元素放到新列表的开头。

现在，下一个最小的元素一定在这两个列表新的开头位置。我们可以继续比较前两个元素，并将较小的元素移动到合适的位置，直到其中一个列表为空，然后将另一个列表的剩余部分复制到新列表的末尾。

想要查看这个过程的动画演示，可以点击[这里](https://docs.google.com/presentation/d/1mdCppuWQfKG5JUBHAMHPgbSv326JtCi5mvjH1-6XcMw/edit#slide=id.g463de7561_042)。

合并的运行时间是多少呢？我们可以使用向新列表 “写入” 操作的次数作为成本模型并计算操作次数。由于我们只需要对每个列表中的每个元素写入一次，所以运行时间是\(\Theta(N)\)。

选择排序速度较慢，而合并速度较快。我们如何将它们结合起来，使排序更快呢？

我们之前注意到，对\(N = 64\)的列表进行选择排序大约需要2048 AU。但如果对大小为其一半（\(N = 32\)）的列表进行排序，只需要大约512 AU，速度快了两倍多！所以缩小要排序的数组大小可以显著节省时间。

有两个已排序的数组是很好的一步，但我们还需要将它们合并起来。幸运的是，我们有合并操作。合并操作的运行时间是线性的，对\(N = 64\)的数组合并大约只需要64 AU。所以，将数组分成两半、分别排序然后合并，总共只需要\(512 + 512 + 64 = 1088\) AU，比直接对整个数组进行选择排序要快。但快多少呢？

虽然AU不是实际的时间单位，但有时它比直接看运行时间更简单、更直观。我们这种先拆分再合并的排序方法的运行时间是*N*+2(*N*/2)^2，大约是选择排序\(N^{2}\)的一半。然而，它们的时间复杂度仍然都是Θ(*N*2)。

如果我们再将数组减半会怎样呢？会变得更快吗？是的！如果进行两层合并，从大小为\(N/4\)的列表开始，总时间大约是640 AU。

**练习**：通过计算对每个子列表排序并将它们合并成一个数组所需的时间，来说明为什么时间大约是640 AU。

如果我们继续不断地将数组减半呢？

最终，我们会得到大小为1的列表。在这一点上，我们甚至不需要使用选择排序，因为只有一个元素的列表已经是有序的。

这就是归并排序的核心：
1. 如果列表大小为1，直接返回。否则：
2. 对左半部分进行归并排序。
3. 对右半部分进行归并排序。
4. 合并结果。

那么归并排序的运行时间是多少呢？

我们知道合并操作本身的时间复杂度是\(N\)阶的，所以我们可以从观察每一层的合并操作开始：
 - 顶层：合并大约64个元素 = 64 AU。
 - 第二层：合并大约32个元素，两次 = 64 AU。
 - 第三层：大约\(16×4 = 64\) AU。

总体运行时间（以AU为单位）大约是\(64×k\)，其中\(k\)是层数。这里\(k=\log _{2}(64)=6\)，所以归并排序的总成本大约是384 AU。

我们之前看到拆分更多层会更快，但仍然是\(N^{2}\)阶的。归并排序比\(N^{2}\)更快吗？

是的！归并排序的最坏情况运行时间是\(\Theta(N \log N)\)。

顶层大约需要\(N\) AU。
 - 下一层需要大约\(\frac{N}{2}+\frac{N}{2}=N\) 。
 - 再下一层：大约\(\frac{N}{4}+\frac{N}{4}+\frac{N}{4}+\frac{N}{4}=N\) 。

因此，总运行时间大约是\(N×k\)，其中\(k\)是层数。

有多少层呢？我们不断拆分数组，直到其长度为1，所以\(k=\log _{2}(N)\)。因此，总体运行时间是\(\Theta(N \log N)\)。

**练习**：通过精确计算来论证归并排序的时间复杂度是\(\Theta(N \log N)\)，并考虑列表不能完美二分的情况。

那么\(\Theta(N \log N)\)真的比\(\Theta(N^{2})\)好吗？是的！事实证明，\(\Theta(N \log N)\)比线性时间慢不了多少。
|                   | \(n\) | \(n \log_2{n}\) | \(n^{2}\) | \(n^{3}\) | \(2^n\)      | \(n!\)        |
| ----------------- | ----- | --------------- | --------- | --------- | ------------ | ------------- |
| \(n = 10\)        | <1秒  | <1秒            | <1秒      | <1秒      | <1秒         | 4秒           |
| \(n = 30\)        | <1秒  | <1秒            | <1秒      | <1秒      | 18分钟       | \(10^{25}\)年 |
| \(n = 50\)        | <1秒  | <1秒            | <1秒      | <1秒      | 11分钟       | 36年          |
| \(n = 100\)       | <1秒  | <1秒            | <1秒      | 1秒       | \(12,892\)年 | \(10^{17}\)年 |
| \(n = 1,000\)     | <1秒  | <1秒            | 1秒       | 18分钟    | 极长         | 极长          |
| \(n = 10,000\)    | <1秒  | <1秒            | 2分钟     | 12天      | 极长         | 极长          |
| \(n = 100,000\)   | <1秒  | 2秒             | 3小时     | 32年      | 极长         | 极长          |
| \(n = 1,000,000\) | 1秒   | 20秒            | 12天      | 31,710年  | 极长         | 极长          |
#### 总结
#### 要点
1. 分析代码运行时没有神奇的捷径。
2. 在我们的课程中，进行精确计算或直观分析都是可以的。
3. 要知道如何计算\(1+2+3+...+N\)和\(1+2+4+...+N\)的和。
4. 本课程中我们不会书写数学证明。
5. 本课程中你遇到的许多运行时问题都类似于今天讲的五个问题之一。可以查看教科书、学习指南以及讨论区来获取更多练习。

这个主题是本课程所有主题中技术上限最高的主题之一。虽然所有工具都在这里了，但练习才是你真正的朋友！

对于同一个问题，例如排序，不同的解决方案可能有不同的运行时（差异大到足以使运行时从不实用变为实用！）。\(N^{2}\)和\(N \log N\)之间有着巨大的差异。从\(N \log N\)提升到\(N\)固然不错，但并不是根本性的改变。

希望这一系列示例能让你在运行时分析的技术和模式方面得到很好的练习。记住，虽然没有神奇的捷径，但你已经掌握了处理这些问题的工具。勇往直前，去分析吧！



## Ω（Omega）和均摊分析

在本节中，我们将结束对渐近表示法的讨论。其中大部分内容要到课程后期才会进一步展开。本节会拓展大O（Big O）的概念并介绍Ω（Big Omega）。我们还会探讨均摊运行时的概念及其分析方法。最后，我们将以运行时的实证分析和复杂性理论的简要介绍作为结尾。
#### 运行时分析的微妙之处
为了说明使用大O表示法的用处，让我们回到查找重复元素的函数，并思考以下练习。
**练习**：设 $R(N)$ 是函数 `dup3` 的运行时，它是数组长度 $N$ 的函数。$R(N)$ 的增长阶是多少？
```java
public boolean dup3(int[] a) {
    int N = a.length;
    for (int i = 0; i < N; i += 1) {
        for (int j = 0; j < N; j += 1) {
            if (a[i] == a[j]) {
                return true;
            }
        }
    }
    return false;
}
```
**答案**：$R(N) \in \Theta(1)$，它是常数时间！这是因为 `dup3` 存在一个漏洞：它总是将第一个元素与自身进行比较。在第一次迭代时，`i` 和 `j` 都为0，所以函数总是立即返回。真糟糕！

让我们修复 `dup4` 中的漏洞并再次尝试。
**练习**：设 $R(N)$ 是函数 `dup4` 的运行时，它是数组长度 $N$ 的函数。$R(N)$ 的增长阶是多少？
```java
public boolean dup4(int[] a) {
    int N = a.length;
    for (int i = 0; i < N; i += 1) {
        for (int j = i + 1; j < N; j += 1) {
            if (a[i] == a[j]) {
                return true;
            }
        }
    }
    return false;
}
```
**答案**：这次，运行时不仅取决于输入的长度，还取决于数组的内容。在最好的情况下，$R(N) \in \Theta(1)$。如果输入数组包含的所有元素都相同，那么无论数组有多长，`dup4` 都会在第一次迭代时返回。

另一方面，在最坏的情况下，$R(N) \in \Theta(N^2)$。如果数组中没有重复元素，那么 `dup4` 永远不会提前返回，嵌套的 `for` 循环将导致运行时为二次方阶。

这个练习凸显了大Θ（Big Theta）的一个局限性。大Θ表示法精确地描述了运行时作为输入大小的函数的阶数。然而，如果运行时不仅取决于输入的大小，那么在使用大Θ表示法之前，我们必须将情况进行分类说明。大O表示法则避免了这种麻烦。对于上述示例，我们无需同时描述最好和最坏情况，而可以简单地说 `dup4` 的运行时是 $O(N^2)$。有时 `dup4` 会更快，但最坏情况下是二次方阶的运行时。
#### 大O表示法的滥用
考虑以下两个陈述：
1. 酒店里最贵的房间每晚639美元。
2. 酒店里每个房间的价格都小于或等于639美元。

哪个陈述能让你对酒店有更多了解呢？

第一个。第二个陈述只提供了房价的上限，即一个不等式。而第一个陈述不仅告诉了你房价的上限，还表明这个上限是实际存在的。例如，假设有一家便宜的酒店，其最贵的房间每晚89美元，还有一家昂贵的酒店，其最贵的房间每晚639美元。这两家酒店都符合第二个陈述，但第一个陈述能让你确定是后面这家酒店。

**练习**：哪个陈述能让你对一段代码的运行时有更多了解？
1. 最坏情况下的运行时是 $\Theta(N^2)$。
2. 运行时是 $O(N^2)$。

**答案**：与酒店问题类似，第一个陈述提供了更多信息。考虑以下方法：
```java
public static void printLength(int[] a) {
    System.out.println(a.length);
}
```
这个简单的方法和 `dup4` 的运行时都是 $O(N^2)$，所以仅知道陈述2无法区分它们。但陈述1更精确，并且只适用于 `dup4`。

在现实世界以及日常讨论中，大O表示法常常被用于本应使用大Θ表示法才能更有意义的地方。我们已经看到了这样做的一个合理原因 —— 它让我们无需使用限定性语句。然而，虽然大O表示法的表述更宽松且正确，但它不如大Θ界限有用。

**注意**：大O并不等同于 “最坏情况”，但它经常被这样使用。

总结大O表示法的用处：
1. 当运行时因输入不同而不同时，它让我们能够做出简单的陈述，而无需对不同情况进行说明。
2. 有时，对于特别复杂的问题，我们（计算机科学界）不知道确切的运行时，所以我们可能只能给出一个上限。
3. 证明大O比证明大Θ要容易得多，就像我们在上一章中求归并排序的运行时那样。这超出了本课程的范围。
#### 大Ω（Big Omega）
为了完善我们对运行时的理解，让我们定义大O的补充概念大Ω，用于描述下限。

大Θ可以通俗地理解为运行时的 “相等”，大O表示 “小于或等于”，而大Ω可以理解为 “大于或等于”。例如，除了知道 $N^3 + 3N^4 \in \Theta(N^4)$，以下所有陈述也都是正确的：
 - $N^3 + 3N^4 \in \Omega(N^4)$
 - $N^3 + 3N^4 \in \Omega(N^3)$
 - $N^3 + 3N^4 \in \Omega(\log N)$
 - $N^3 + 3N^4 \in \Omega(1)$

如果 $N^3 + 3N^4 \in \Theta(N^4)$，那么函数 $N^3 + 3N^4$ 也 “大于或等于” $N^4$。该函数的增长速度也必然快于任何渐近增长速度比 $N^4$ 慢的函数，例如 $1$ 和 $N^3$。

大Ω有两种常见用途：
1. 用于证明大Θ运行时。如果 $R(N) = O(f(N))$ 且 $R(N) = \Omega(f(N))$，那么 $R(N) = \Theta(f(N))$。有时，分别证明大O和大Ω会更容易。这超出了本课程的范围。
2. 用于证明问题的难度。例如，任何查找重复元素的算法必然是 $\Omega(N)$，因为算法至少必须查看每个元素。

以下表格总结了这三个大符号：
| 非正式含义                                  | 示例家族      | 示例家族成员                   |
| ------------------------------------------- | ------------- | ------------------------------ |
| 大Θ $\Theta(f(N))$：增长阶是 $f(N)$         | $\Theta(N^2)$ | $N^2/2, 2N^2, N^2 + 38N + 1/N$ |
| 大O $O(f(N))$：增长阶小于或等于 $f(N)$      | $O(N^2)$      | $N^2/2, 2N^2, \log N$          |
| 大Ω $\Omega(f(N))$：增长阶大于或等于 $f(N)$ | $\Omega(N^2)$ | $N^2/2, 2N^2, 5^N$             |
#### 均摊分析（直观解释）
#### 格里戈梅斯的瓮
格里戈梅斯是一只看起来有点阴森的恶魔犬。它能让你像它有时出现在你的梦境和考试中一样，出现在马的梦里。不过，作为获得这种能力的回报，你必须定期向它献上满瓮的干草作为贡品。它给了你两种支付方式：
 - **选择1**：每天，格里戈梅斯从你的瓮里吃掉3蒲式耳的干草。
 - **选择2**：格里戈梅斯吃的干草量随时间呈指数增长，但来的频率呈指数降低。具体如下：
    - 第1天，它吃1蒲式耳干草（总计1蒲式耳）。
    - 第2天，它多吃2蒲式耳干草（总计3蒲式耳） 。
    - 第4天，它多吃4蒲式耳干草（总计7蒲式耳） 。
    - 第8天，它多吃8蒲式耳干草（总计15蒲式耳）。

你想制定一个计划，每天在瓮里放入固定数量的干草。对于每种支付方案，你每天需要在瓮里放多少干草呢？哪种方案更便宜呢？

对于第一种选择，你每天必须在瓮里放入恰好3蒲式耳的干草。然而，第二种选择实际上更便宜一些！你每天只需在瓮里放入2蒲式耳干草就可以了。（试着说服自己为什么这是正确的 —— 一种方法是写出每天之后你贡献的干草总量。你会注意到，每当格里戈梅斯来吃干草时，在它吃饱后，你的瓮里总会多1蒲式耳干草。很巧妙吧。）

抛开干草数量不谈，注意这里格里戈梅斯每天的干草消耗量实际上是恒定的，在选择2中，我们可以将这种情况描述为均摊的恒定干草消耗。
#### AList的扩容与均摊
事实证明，格里戈梅斯的干草难题与课程前面提到的AList扩容非常相似。回想一下，在我们基于数组的列表实现中，当我们对底层数组已满的AList调用 `add` 方法时，我们需要对数组进行扩容。换句话说，当数组满时，`add` 方法必须创建一个更大的新数组，复制旧元素，最后添加新元素。我们的新数组应该增大多少呢？回想以下两种实现方式：
```java
// 实现1
public void addLast(int x) {
  if (size == items.length) {
    resize(size + RFACTOR);
  }
  items[size] = x;
  size += 1;
}

// 实现2
public void addLast(int x) {
  if (size == items.length) {
    resize(size * RFACTOR);
  }
  items[size] = x;
  size += 1;
}
```
第一种实现方式非常糟糕，几乎无法使用。当数组满了之后，每次添加新元素时，都必须将整个数组复制到新数组中。另一方面，我们称之为几何扩容的第二种实现方式效果很好。实际上，Python列表就是这样实现的。

让我们更详细地研究一下实现2的运行时。设 `RFACTOR` 为2。当数组满时，`resize` 会将其大小翻倍。大多数 `add` 操作的时间复杂度是 $\Theta(1)$，但有些操作非常耗时，与当前数组大小呈线性关系。然而，如果我们将扩容时昂贵的 `add` 操作的成本，与所有廉价的 `add` 操作的成本进行平均计算，并且考虑到昂贵的 `add` 操作每次出现的频率会减半，结果发现平均而言，`add` 操作的运行时是 $\Theta(1)$。我们将在下一节证明这一点。同时，这里有一张图来说明这一点：
[此处应有表示均摊add操作的图]

![amortized_adds](D:\Zewang\CS_61B\Note\image\amortized_adds.png)

#### 均摊分析（严格解释）
这里通过三个步骤对均摊分析进行更严格的研究：
1. 选择一个成本模型（类似于常规运行时分析）。
2. 计算第 $i$ 次操作的平均成本。
3. 证明这个平均（均摊）成本是有常数上界的。

假设最初我们的 `ArrayList` 包含一个长度为1的数组。让我们将这三个步骤应用到 `ArrayList` 的扩容上：
1. 对于成本模型，我们只考虑数组的读取和写入操作。（你也可以将其他操作纳入成本模型，例如数组创建和填充默认数组值的成本。但事实证明，这些都会产生相同的结果。）
2. 让我们计算一系列数组添加操作的成本。假设我们有以下代码及相应的示意图：
TODO:
 - `x.add(0)` 执行1次写入操作，无需扩容。总成本：1次操作。
 - `x.add(1)` 进行扩容并复制现有数组（1次读取，1次写入），然后写入新元素。总成本：3次操作。
 - `x.add(2)` 进行扩容并复制现有数组（2次读取，2次写入），然后写入新元素。总成本：5次操作。
 - `x.add(3)` 无需扩容，仅写入新元素。总成本：1次操作。
 - `x.add(4)` 进行扩容并复制现有数组（4次读取，4次写入），然后写入新元素。总成本：9次操作。

用表格记录会更方便：
| 插入序号        | 0    | 1    | 2    | 3    | 4    | 5    | 6    | 7    | 8    | 9    | 10   | 11   | 12   | 13   |
| --------------- | ---- | ---- | ---- | ---- | ---- | ---- | ---- | ---- | ---- | ---- | ---- | ---- | ---- | ---- |
| `a[i]` 写入成本 | 1    | 1    | 1    | 1    | 1    |      |      |      |      |      |      |      |      |      |
| 扩容/复制成本   | 0    | 2    | 4    | 0    | 8    |      |      |      |      |      |      |      |      |      |
| 单次插入总成本  | 1    | 3    | 5    | 1    | 9    |      |      |      |      |      |      |      |      |      |
| 累计成本        | 1    | 4    | 9    | 10   | 19   |      |      |      |      |      |      |      |      |      |

**练习**：填写此表格的其余部分。单次插入总成本是一次特定插入操作的总成本。累计成本是到目前为止所有插入操作的成本。
**答案**：
| 插入序号        | 0    | 1    | 2    | 3    | 4    | 5    | 6    | 7    | 8    | 9    | 10   | 11   | 12   | 13   |
| --------------- | ---- | ---- | ---- | ---- | ---- | ---- | ---- | ---- | ---- | ---- | ---- | ---- | ---- | ---- |
| `a[i]` 写入成本 | 1    | 1    | 1    | 1    | 1    | 1    | 1    | 1    | 1    | 1    | 1    | 1    | 1    | 1    |
| 扩容/复制成本   | 0    | 2    | 4    | 0    | 8    | 0    | 0    | 0    | 16   | 0    | 0    | 0    | 0    | 0    |
| 单次插入总成本  | 1    | 3    | 5    | 1    | 9    | 1    | 1    | 1    | 17   | 1    | 1    | 1    | 1    | 1    |
| 累计成本        | 1    | 4    | 9    | 10   | 19   | 20   | 21   | 22   | 39   | 40   | 41   | 42   | 43   | 44   |

那么一系列 `add` 操作的平均成本是多少呢？对于13次插入操作，平均成本是 $44/13 = 3.14$ 次操作/每次插入。但对于前8次插入操作，平均成本是 $39/8 = 4.875$ 次操作/每次插入。

平均（均摊）成本是否有常数上界呢？看起来它可能有一个值为5的上界。但仅通过查看前13次插入操作，我们不能完全确定。

现在我们引入 “**势能**” 的概念来帮助我们解决这个均摊分析的难题。对于每次操作 $i$，例如每次 `add` 操作或格里戈梅斯来吃干草，设 $c_i$ 是操作的**实际成本**，而 $a_i$ 是操作的某个任意**均摊成本**。$a_i$ 是一个常数，对于所有的 $i$ 都必须相同。

设 $\Phi_i$ 是操作 $i$ 时的势能，它是**均摊成本与实际成本的累积差值**：$\Phi_i = \Phi_{i - 1} + a_i - c_i$。

$a_i$ 是一个任意常数，这意味着我们可以选择它。如果我们**选择 $a_i$ 使得 $\Phi_i$ 永远不会为负，并且 $a_i$ 对于所有的 $i$ 都是常数，那么均摊成本就是实际成本的上界**。如果实际成本有常数上界，那么我们就证明了它平均而言是**常数时间**！

让我们用格里戈梅斯的干草贡品问题来试试这个方法。对于第 $i$ 天，实际成本是格里戈梅斯当天吃的干草量。均摊成本是我们当天放入瓮中的干草量 —— 假设是3蒲式耳。设初始势能 $\Phi_0 = 0$：

| 天数 ($i$)     | 1    | 2    | 3    | 4    | 5    | 6    | 7    | 8    | 9    | 10   | 11   | 12   | 13   | 14   |
| -------------- | ---- | ---- | ---- | ---- | ---- | ---- | ---- | ---- | ---- | ---- | ---- | ---- | ---- | ---- |
| 实际成本 $c_i$ | 1    | 2    | 0    | 4    | 0    | 0    | 0    | 0    | 8    | 0    | 0    | 0    | 0    | 0    |
| 均摊成本 $a_i$ | 3    | 3    | 3    | 3    | 3    | 3    | 3    | 3    | 3    | 3    | 3    | 3    | 3    | 3    |
| 势能变化       | 2    | 1    | 3    | -1   | 3    | 3    | 3    | 3    | -5   | 3    | 3    | 3    | 3    | 3    |
| 势能 $\Phi_i$  | 2    | 3    | 6    | 5    | 8    | 11   | 14   | 17   | 12   | 15   | 18   | 21   | 24   | 27   |

如果我们令 $a_i = 3$，势能永远不会变为负数。实际上，从趋势来看，如果我们每天添加3蒲式耳干草，过了许多天后，我们给格里戈梅斯的干草会一直有剩余。我们将这个严格证明留到另一门课程，但希望这个趋势看起来足够令人信服。所以，格里戈梅斯的干草贡品平均成本是常数。

**练习**：我们希望尽可能节省干草。证明即使我们设 $a_i = 2$，也能满足格里戈梅斯的食量且势能永远不会为负。

现在回到 `ArrayList` 扩容的问题。
**练习**：`ArrayList` 的 `add` 操作中 $c_i$ 的值是多少？如果我们令均摊成本 $a_i = 5$，势能会变为负数吗？是否存在更小的均摊成本也能满足条件？填写类似格里戈梅斯问题的表格来辅助分析。
**答案**：$c_i$ 是数组扩容和添加新元素的总成本。当 $i$ 是2的幂次方时，$c_i = 2i + 1$；否则，$c_i = 1$。
| 插入序号       | 0    | 1    | 2    | 3    | 4    | 5    | 6    | 7    | 8    | 9    | 10   | 11   | 12   | 13   |
| -------------- | ---- | ---- | ---- | ---- | ---- | ---- | ---- | ---- | ---- | ---- | ---- | ---- | ---- | ---- |
| 实际成本 $c_i$ | 1    | 3    | 5    | 1    | 9    | 1    | 1    | 1    | 17   | 1    | 1    | 1    | 1    | 1    |
| 均摊成本 $a_i$ | 5    | 5    | 5    | 5    | 5    | 5    | 5    | 5    | 5    | 5    | 5    | 5    | 5    | 5    |
| 势能变化       | 4    | 2    | 0    | 4    | -4   | 4    | 4    | 4    | -12  | 4    | 4    | 4    | 4    | 4    |
| 势能 $\Phi_i$  | 4    | 6    | 6    | 10   | 6    | 10   | 14   | 18   | 6    | 10   | 14   | 18   | 22   | 26   |

从趋势上看，势能应该永远不会为负（证明过程省略）。直观地说，**对于高成本操作，我们利用之前低成本操作积累的势能来平衡**。最后，我们证明了 `ArrayList` 的 `add` 操作**平均时间复杂度确实是常数时间**。**几何扩容（乘以 `RFACTOR`）能够带来良好的列表性能**。
#### 总结
- 大O是一个上界（“小于或等于”）。
- 大Ω是一个下界（“大于或等于”）。
- 大Θ既是上界也是下界（“等于”）。
- 大O并不意味着 “最坏情况”。我们仍然可以使用大Θ来描述最坏情况。
- 大Ω并不意味着 “最好情况”。我们仍然可以使用大Θ来描述最好情况。
- 大O有时会在本应使用大Θ来更精确表述的情况下被通俗使用。
- 均摊分析提供了一种证明操作平均成本的方法。
- 如果我们选择 $a_i$ 使得 $\Phi_i$ 永远不会为负，并且 $a_i$ 对所有 $i$ 都是常数，那么均摊成本就是实际成本的上界。 

------



## 9.1 不相交集

不相交集（Disjoint Sets）简介  
若两个集合无共同元素，则称它们为不相交集。不相交集数据结构（又称并查集）用于维护由固定数量元素组成的多个互不相交的子集，主要支持两种操作：  

`connect(x, y)`: 连接x和y（又称合并操作）  
`isConnected(x, y)`: 若x和y处于同一集合则返回true  

该数据结构初始化时，每个元素自成一个独立子集。通过调用`connect(x, y)`可合并元素所在的子集。  

示例：假设有四个元素A、B、C、D  
初始状态：  

（图示：四个独立集合{A}{B}{C}{D}）  

调用connect(A, B)后：  

（图示：合并为{AB}{C}{D}）  

此时验证连接状态：  
`isConnected(A, B) -> true`  
`isConnected(A, C) -> false ` 

再调用connect(A, D)后：  

（图示：合并为{ABD}{C}）  

验证连接状态：  
`isConnected(A, D) -> true  `
`isConnected(A, C) -> false  `

基于上述理解，我们正式定义并查集接口（暂时仅处理非负整数集合，实际应用可通过整数映射扩展）：  

```java
public interface DisjointSets {
    /** 连接元素p和q */
    void connect(int p, int q);

    /** 检测元素p和q是否连通 */
    boolean isConnected(int p, int q); 
}
```

本章不仅将学习这一精妙数据结构的实现，还将见证其设计方案的演进过程。我们将逐步探讨四种实现方案：  
快速查找 → 快速合并 → 带权快速合并 → 带路径压缩的带权快速合并  
通过分析不同设计方案对时间复杂度和代码复杂度的影响，深入理解算法优化的核心思想。  

（注：图示部分保留原文描述方式，建议配套原课程图示理解。关键术语如"connect/isConnected"采用"连接/连通"的译法保持操作语义清晰，接口代码保留英文形式符合编程惯例）

------



## 9.2快速查找法（Quick Find）

为了实现 `DisjointSets` 接口的功能，我们需要有效管理元素的集合归属。  

#### **ListOfSets（初步想法）**

最直观的方式可能是用 `List<Set<Integer>>` 存储各个子集。例如，初始时有 6 个元素，数据结构为 `[{0}, {1}, {2}, {3}, {4}, {5}, {6}]`。  

但执行 `connect(5, 6)` 时，必须遍历最多 `N` 个子集才能找到 `5` 和 `6`，时间复杂度为 `O(N)`。此外，代码实现会较为复杂。  

**关键启示**：初始设计的选择直接影响代码复杂度和运行效率。  

---

#### **快速查找法（Quick Find）**

改用**单数组（`int[]`）**表示集合关系：  
- **数组索引**代表元素  
- **数组值**代表该元素所属的集合编号  

例如，集合 `{0, 1, 2, 4}`、`{3, 5}`、`{6}` 可表示为：  

```
索引: 0 1 2 3 4 5 6  
值:  [4,4,4,5,4,5,6]  
```
（集合编号的具体数值不重要，只需保证同集合元素的值相同）  

##### **操作实现**  
1. **`connect(x, y)`**  
   合并 `x` 和 `y` 所在的集合。例如，当前 `id[2] = 4` 和 `id[3] = 5`，执行 `connect(2, 3)` 后，所有值为 `4` 或 `5` 的元素被统一赋值为 `5`：  

   ```
   更新后数组: [5,5,5,5,5,5,6]  
   ```
   **时间复杂度**：`Θ(N)`（需遍历整个数组）  

2. **`isConnected(x, y)`**  
   直接检查 `id[x] == id[y]`。  
   **时间复杂度**：`Θ(1)`  

**为什么叫“Quick Find”？**  
因为查询操作（`isConnected`）是常数时间！  

---

### **性能对比与代码实现**  

| 实现方式     | 构造器 | `connect` | `isConnected` |
| ------------ | ------ | --------- | ------------- |
| `ListOfSets` | `Θ(N)` | `O(N)`    | `O(N)`        |
| `QuickFind`  | `Θ(N)` | `Θ(N)`    | `Θ(1)`        |

```java
public class QuickFindDS implements DisjointSets {
    private int[] id;

    /** 初始化：每个元素自成一个集合 Θ(N) */
    public QuickFindDS(int N) {
        id = new int[N];
        for (int i = 0; i < N; i++) {
            id[i] = i; // 初始时集合编号=元素自身
        }
    }

    /** 合并：将p所在集合的所有元素改为q的集合编号 Θ(N) */
    public void connect(int p, int q) {
        int pid = id[p];
        int qid = id[q];
        for (int i = 0; i < id.length; i++) {
            if (id[i] == pid) {
                id[i] = qid; // 统一集合编号
            }
        }
    }

    /** 查询：检查是否同属一个集合 Θ(1) */
    public boolean isConnected(int p, int q) {
        return id[p] == id[q];
    }
}
```

**缺点**：虽然查询快，但每次合并需遍历整个数组，对大规模数据效率低。后续将介绍更优的**快速合并（Quick Union）**优化这一问题。  

（注：代码保留英文变量名如 `id` 符合编程惯例，注释中使用中文解释关键逻辑。复杂度符号 `Θ` 表示严格上下界，区别于大O符号的渐进上界）

------

## 9.3 Quick Union

### 快速合并法（Quick Union）  

为了优化 `connect` 操作的效率，我们引入**树形结构**表示集合，仍使用单数组 `parent[]` 存储：  
- **数组索引**代表元素  
- **数组值**代表其父节点索引（若为根节点，则值为负数或自身，具体实现可能不同）  

例如，集合 `{0, 1, 2, 4}`、`{3, 5}`、`{6}` 可表示为以下树结构及对应数组：  
```
树形结构：
0      3      6
| \    |
1  2   5
|
4

数组表示（假设根节点值为-1）：
索引: 0 1 2 3 4 5 6  
值:  [-1,0,0,-1,1,3,-1] 
```

#### **核心操作**  
1. **`find(x)`（辅助函数）**  
   递归或迭代查找 `x` 的根节点。例如：  
   - `find(4)` → 1 → 0 → 返回 `0`  
   - `find(5)` → 3 → 返回 `3`  
   **时间复杂度**：`O(树的高度)`  

2. **`connect(x, y)`**  
   合并两棵树：将 `x` 的根节点指向 `y` 的根节点。例如 `connect(5, 2)`：  
   - `find(5)` → 3  
   - `find(2)` → 0  
   - 更新 `parent[3] = 0`  
   **时间复杂度**：`O(树的高度)`（主要开销在 `find`）  

3. **`isConnected(x, y)`**  
   检查两元素是否同根：`find(x) == find(y)`  
   **时间复杂度**：`O(树的高度)`  

#### **性能分析**  
| 实现方式     | 构造器 | `connect` | `isConnected` |
| ------------ | ------ | --------- | ------------- |
| `QuickFind`  | `Θ(N)` | `Θ(N)`    | `Θ(1)`        |
| `QuickUnion` | `Θ(N)` | `O(N)`    | `O(N)`        |

**问题**：树可能退化为链（如 `0→1→2→3→4`），导致 `find` 的最坏时间复杂度为 `O(N)`。  

#### **代码实现**  
```java
public class QuickUnionDS implements DisjointSets {
    private int[] parent;

    /** 初始化：每个元素自成一棵树（根节点值为-1）Θ(N) */
    public QuickUnionDS(int num) {
        parent = new int[num];
        for (int i = 0; i < num; i++) {
            parent[i] = -1; // 初始时所有元素为根节点
        }
    }

    /** 查找根节点：O(树高度) */
    private int find(int p) {
        while (parent[p] >= 0) { // 非根节点时继续向上
            p = parent[p];
        }
        return p;
    }

    /** 合并：将p的根节点指向q的根节点 O(树高度) */
    @Override
    public void connect(int p, int q) {
        int rootP = find(p);
        int rootQ = find(q);
        parent[rootP] = rootQ; // 简单合并，可能增加树高度
    }

    /** 查询：检查是否同根 O(树高度) */
    @Override
    public boolean isConnected(int p, int q) {
        return find(p) == find(q);
    }
}
```

**改进方向**：  
1. **按秩合并（Weighted Quick Union）**：合并时让小树挂在大树下，控制树高度。  
2. **路径压缩（Path Compression）**：`find` 时扁平化树结构，进一步降低高度。  

（注：代码中根节点判断逻辑可根据需求调整，如初始值设为 `-1` 或自身索引。注释中明确标注时间复杂度，区分最好/最坏情况。）

------

## 9.4带权快速合并（Weighted Quick Union, WQU）

#### **核心思想**
在快速合并（Quick Union）的基础上，通过**按树大小合并**（总是将小树的根节点挂到大树下），确保树的最大高度为 **O(log N)**，从而优化 `find`、`connect` 和 `isConnected` 的时间复杂度。

---

#### **关键改进**
1. **记录树的大小**  
   - 用数组 `parent[]` 存储父节点索引，**根节点的值设为 `-size`**（表示树的大小）。  
   - 例如：`parent[3] = -5` 表示节点 `3` 是根节点，其树下共有 `5` 个元素。

2. **合并策略**  
   - 比较两棵树的大小，**将小树的根指向大树的根**。  
   - 合并后更新大树的大小。

---

#### **操作实现**
1. **`find(x)`**  
   与 Quick Union 相同，沿父链找到根节点。  
   **时间复杂度**：`O(log N)`（因树高度被控制）。

2. **`connect(x, y)`**  
   - 找到 `x` 和 `y` 的根节点 `rootX` 和 `rootY`。  
   - 比较树大小：  
     - 若 `size(rootX) < size(rootY)`，将 `rootX` 挂到 `rootY` 下。  
     - 否则，将 `rootY` 挂到 `rootX` 下。  
   - 更新合并后的树大小。  
   **时间复杂度**：`O(log N)`（主要开销在 `find`）。

3. **`isConnected(x, y)`**  
   检查 `find(x) == find(y)`。  
   **时间复杂度**：`O(log N)`。

---

#### **性能对比**
| 实现方式                 | 构造器 | `connect`  | `isConnected` |
| ------------------------ | ------ | ---------- | ------------- |
| QuickFind                | `Θ(N)` | `Θ(N)`     | `Θ(1)`        |
| QuickUnion               | `Θ(N)` | `O(N)`     | `O(N)`        |
| **Weighted Quick Union** | `Θ(N)` | `O(log N)` | `O(log N)`    |

---

#### **为什么树高是 O(log N)？**
- 每次合并时，小树挂到大树下，**树的规模至少翻倍**。  
- 最坏情况下，树需要合并 `log₂N` 次才能包含所有 `N` 个节点，因此树高不超过 `log₂N`。  

**示例**：  
- 初始：`{0}, {1}, {2}, ..., {N-1}`（每个节点独立）。  
- 合并 `0` 和 `1` → 树 `{0,1}`（大小=2）。  
- 合并 `{0,1}` 和 `2` → 树 `{0,1,2}`（大小=3）。  
- ...  
- 最终树的最大高度为 `log₂N`。

---

#### **代码框架（Lab 6 任务）**
```java
public class WeightedQuickUnionDS implements DisjointSets {
    private int[] parent;

    public WeightedQuickUnionDS(int N) {
        parent = new int[N];
        for (int i = 0; i < N; i++) {
            parent[i] = -1; // 初始时每个节点是根，大小为1（用-1表示）
        }
    }

    private int find(int p) {
        while (parent[p] >= 0) { // 非根节点时继续向上
            p = parent[p];
        }
        return p;
    }

    @Override
    public void connect(int p, int q) {
        int rootP = find(p);
        int rootQ = find(q);
        if (rootP == rootQ) return; // 已在同一集合

        // 比较树大小（注意：根节点的值为 -size）
        if (parent[rootP] < parent[rootQ]) { // rootP的树更大
            parent[rootP] += parent[rootQ]; // 更新大小
            parent[rootQ] = rootP;         // 小树rootQ挂到大树rootP下
        } else {
            parent[rootQ] += parent[rootP];
            parent[rootP] = rootQ;
        }
    }

    @Override
    public boolean isConnected(int p, int q) {
        return find(p) == find(q);
    }
}
```

---

#### **为何不用树高度合并？**
虽然按高度合并也能达到 `O(log N)`，但：
1. **实现复杂度更高**：需额外维护高度数组。  
2. **性能相同**：按大小合并已足够优化。  

---

#### **下一步优化：路径压缩**
在 `find` 操作时扁平化树结构，可进一步降低树高至**近似常数**（见 WQU with Path Compression）。



------

## 9.5带路径压缩的按权合并（WQUPC）

#### **核心思想**
在按权合并（WQU）的基础上，**在 `find` 操作时扁平化树结构**，使得后续查询几乎只需常数时间。这是通过**路径压缩（Path Compression）**实现的。

---

#### **路径压缩的原理**
1. **操作时机**：  
   每次调用 `find(x)` 时，在从 `x` 向上查找根节点的过程中，**将途经的所有节点直接挂到根节点下**。  
   - **效果**：树的高度被动态压缩，后续操作更快。

2. **图示例子**：  
   假设原树结构为 `5 → 4 → 3 → 2 → 1 → 0`（高度=5），调用 `find(5)` 后：  
   - **压缩前**：需遍历 5 层到达根 `0`。  
   - **压缩后**：`5` 和 `4`、`3`、`2`、`1` 全部直接指向 `0`，树高变为 1。  

---

#### **时间复杂度分析**
- **平均时间复杂度**：  
  经过多次操作后，`find`、`connect` 和 `isConnected` 的**均摊时间复杂度**趋近于 **O(α(N))**。  
  - **α(N)** 是反阿克曼函数，增长极其缓慢（对于所有实际应用的 `N`，α(N) ≤ 5）。  
  - 因此，可以认为这些操作是**近似常数时间**的。

- **数学背景**：  
  严格证明需复杂分析（如势能法），但直观理解是路径压缩大幅减少了未来操作的路径长度。

---

#### **代码实现**
```java
public class WQUWithPathCompressionDS implements DisjointSets {
    private int[] parent;

    public WQUWithPathCompressionDS(int N) {
        parent = new int[N];
        for (int i = 0; i < N; i++) {
            parent[i] = -1; // 初始化：每个元素是根，大小为1
        }
    }

    /** find + 路径压缩 */
    private int find(int p) {
        if (parent[p] < 0) {
            return p; // 找到根节点
        }
        // 递归压缩路径：将当前节点的父节点设为根
        parent[p] = find(parent[p]); 
        return parent[p];
    }

    @Override
    public void connect(int p, int q) {
        int rootP = find(p);
        int rootQ = find(q);
        if (rootP == rootQ) return;

        // 按权合并（小树挂大树）
        if (parent[rootP] < parent[rootQ]) { // rootP的树更大
            parent[rootP] += parent[rootQ];
            parent[rootQ] = rootP;
        } else {
            parent[rootQ] += parent[rootP];
            parent[rootP] = rootQ;
        }
    }

    @Override
    public boolean isConnected(int p, int q) {
        return find(p) == find(q);
    }
}
```

---

#### **关键改进点**
1. **递归路径压缩**：  
   `find` 方法通过递归将路径上的节点直接指向根，代码简洁高效。  
   - 例如：`find(5)` 会将 `5 → 4 → 0` 压缩为 `5 → 0` 和 `4 → 0`。

2. **保留按权合并**：  
   合并时仍优先挂接小树，保证树高平衡。

---

#### **性能对比**
| 实现方式                   | `isConnected`       | `connect`           |
| -------------------------- | ------------------- | ------------------- |
| Quick Find                 | Θ(1)                | Θ(N)                |
| Quick Union                | O(N)                | O(N)                |
| Weighted Quick Union (WQU) | O(log N)            | O(log N)            |
| **WQU + Path Compression** | **O(α(N))** (≈常数) | **O(α(N))** (≈常数) |

---

#### **为什么路径压缩有效？**
- **平摊分析**：虽然单次 `find` 可能较慢（需递归压缩路径），但后续操作受益于扁平化结构。  
- **实际效果**：在动态操作中，树高几乎不会超过常数级别。

---

#### **应用场景**
- **大规模数据**：如社交网络中的好友关系、图像处理中的像素连通性。  
- **高频查询**：需要快速响应的场景（如游戏中的实时连通性判断）。  

> **注**：Lab 6 的任务是结合按权合并和路径压缩，实现高效的并查集。
