[TOC]



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

+ ```java
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

+ ```java
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

  

#### 注意：

+ 在进行自动装箱和取消装箱时，需要记住以下几点：
  + 数组从来都不是自动装箱或自动拆箱的。
    例如，如果你有一个整数数组 `int[] x`，并尝试将其地址放入 `Integer[]` 类型的变量中，编译器将不允许你的程序编译。
  + 自动装箱和拆箱也会对性能产生可衡量的影响。
    也就是说，依赖于自动装箱和拆箱的代码将比避免此类自动转换的代码慢。
  + 包装类型使用的内存比基元类型多得多。在大多数现代计算机上，不仅您的代码必须保存对对象的 64 位引用，而且每个对象还需要 64 位开销，用于存储对象的动态类型等内容。

#### 扩大 Widening 

+ 自动加宽基元类型

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

       

## 5.2 immutability 不变性

### 不可变数据类型

+ 其实例在实例化后**无法**以任何可观察的方式更改。
+ 例如，Java 中的 `String` 对象是不可变的。无论如何，如果你有一个 `String` 实例，你可以在该 `String` 上调用任何方法，但它将完全保持不变。这意味着当 `String` 对象被连接时，原始的 String 都不会被修改 -- **而是返回一个全新的 `String` 对象**。

+ 可变数据类型包括 `ArrayDeque` 和 `Planet` 等对象。
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

  `deque` 变量是 final，永远不能重新分配，但它指向的数组 deque 对象可以改变！ArrayDeques 始终是可变的！

## 5.3 创建另一个泛型类

### map

+ 将值与对相关联

+ `ArrayMap `见 `myCode`中

+ excercies

  + **Excercise 5.2.2** What would we need to do in order to call `assertEquals(long, long)`? A.) Widen `expected` to a `long` B.) Autobox `expected` to a `Long` C.) Unbox `am.get(2)` D.) Widen the unboxed `am.get(2)` to long

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

    

+ **Excercise 5.2.3** How would we make it work with `assertEquals(Object, Object)`?

+ **题目：如何让 `assertEquals(Object, Object)` 工作？**

  ```java
  assertEquals(expected, am.get(2));
  ```

  **选项分析：**

  - `assertEquals(Object, Object)` 需要 **两个对象** 作为参数。
  - `am.get(2)` 可能返回 `Integer`，但 `expected` 可能是 `int`（基本类型）。
  - **int 不是对象**，所以 `expected` 需要**自动装箱（autoboxing）**为 `Integer`，这样 `assertEquals(Object, Object)` 才能匹配。

  ✅ **最终答案**：**Autobox expected to an Integer**。

  

+ **Excercise 5.2.4** How do we make the code compile with casting?

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

+ 新语法

```java
public static <K extends Comparable<K>, V> K maxKey(Map61B<K, V> map) {...}
```

`K 扩展了 Comparable<K>` 意味着键必须实现类似的接口，并且可以与其他 K 进行比较。我们需要在 `Comparable` 之后包含 `<K>`，因为 `Comparable` 本身就是一个通用接口！因此，我们必须指定我们想要什么样的可比物。在本例中，我们希望将 K 与 K 进行比较。

+ 为什么`extends`而不是`implement`
+ 在泛型上下文中，`extends` 用于指定类型上界（type upper bound）。它表示泛型类型参数必须是指定类型或其子类型。这里的 “类型” 既可以是类，也可以是接口。当使用 `extends` 与接口搭配时，它意味着泛型类型参数必须实现该接口。
+ extends 只是陈述了一个事实：你必须是你要扩展的任何内容的子类。
+ 当与泛型一起使用时（如在泛型方法标头中），extends 会施加约束，而不是授予新功能。

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

