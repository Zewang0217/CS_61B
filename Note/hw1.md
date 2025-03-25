### 作业1：包、接口、泛型、异常、迭代
共3509字，阅读需4.5分钟

计算机科学61B：数据结构
#### 获取框架文件
还是老样子，使用`git pull skeleton master`命令。
#### 引言
在本次作业中，你将学习如何编写和使用包，同时获得一些关于接口和抽象类的实践经验。我们还将有机会实现一个简单的数据结构，以及一个借助该数据结构易于实现的算法。最后，我们会为数据结构添加迭代和异常处理的支持（我们将在周五讲解这部分内容）。

正如课堂上所提到的，包是一个命名空间，用于组织一组相关的类和接口。从概念上讲，你可以把包想象成计算机上不同的文件夹。在构建大型系统时，将其组织成不同的包是个不错的主意。
#### 作业详情
本次作业，我们将创建一个`synthesizer`包，供那些想要模拟乐器声音的程序使用。

这个`synthesizer`包包含四个组件：
- `BoundedQueue`：一个接口，声明了所有实现`BoundedQueue`的类必须实现的方法。
- `AbstractBoundedQueue`：一个抽象类，实现了`BoundedQueue`接口，提取了`BoundedQueue`中方法间的共性。
- `ArrayRingBuffer`：一个类，继承自`AbstractBoundedQueue`，并使用数组作为`BoundedQueue`的实际实现。
- `GuitarString`：它使用`ArrayRingBuffer<Double>`来实现Karplus-Strong算法，以合成吉他弦的声音。

我们已经为你提供了`ArrayRingBuffer`和`GuitarString`的框架代码，但你需要从头开始实现另外两个类。在本次作业中，我们将从最抽象的部分逐步深入到最具体的部分。

注意：虽然从设计角度来看，可能只将`GuitarString`类设为公共类会更好（因为使用合成器的人并不关心`AbstractBoundedQueues`），但为了测试方便，在本次作业中我们将所有类都设为公共类。
#### 任务1：BoundedQueue
回顾：什么是接口？为什么需要接口？正如课堂上所讨论的，接口是类与外界之间的正式契约。如果你的类声称实现了某个接口，那么在该类成功编译之前，该接口定义的所有方法都必须出现在你的类（或其超类的某个位置）中。这是一种确保承诺行为得以执行的方式。你声明或定义的所有方法自动是公共的和抽象的（即使你省略了`public`关键字）。

你的任务：我们将从定义`BoundedQueue`接口开始。`BoundedQueue`与项目1中的`Deque`类似，但API更有限。具体来说，元素只能在队列尾部入队，只能从队列头部出队。与`Deque`不同的是，`BoundedDeque`有固定的容量，队列满时不允许再入队。

在`synthesizer`文件夹中创建一个`BoundedQueue.java`文件。在IntelliJ中，你可以通过在项目结构侧边栏中右键点击`synthesizer`文件夹，然后选择“新建” -> “Java类”轻松完成。确保将“类型”设置为“接口”。

你的`BoundedQueue`接口应包含以下方法：
```java
int capacity(); // 返回缓冲区的大小
int fillCount(); // 返回当前缓冲区中的元素数量
void enqueue(T x); // 将元素x添加到队列尾部
T dequeue(); // 删除并返回队列头部的元素
T peek(); // 返回（但不删除）队列头部的元素
```
你还应该创建默认方法`isEmpty()`和`isFull()`，当`BoundedQueue`为空或满时返回相应的结果。
```java
default boolean isEmpty() // 缓冲区是否为空（fillCount等于零）？
default boolean isFull // 缓冲区是否已满（fillCount与capacity相同）？
```
例如，给定一个容量为4的空`BoundedQueue<Double>`，每次操作后队列的状态如下所示：
```java
isEmpty() // (返回true)
enqueue(9.3) // 9.3
enqueue(15.1) // 9.3 15.1
enqueue(31.2) // 9.3 15.1 31.2
isFull() // 9.3 15.1 31.2 (返回false)
enqueue(-3.1) // 9.3 15.1 31.2 -3.1
isFull() // 9.3 15.1 31.2 -3.1 (返回true)
dequeue() // 15.1 31.2 -3.1 (返回9.3) 
peek() // 15.1 31.2 -3.1 (返回15.1)
```
当然，你的`BoundedQueue.java`文件实际上不会执行任何操作（因为它是一个接口），但它定义了任何`BoundedQueue`都必须遵循的契约。

确保将这个接口声明为`synthesizer`包的一部分。声明属于某个包的语法是`package <包名>;`。例如，如果你属于`animal`包，文件顶部应该有一行`package animal;`。你的包名应该只是`synthesizer`，仅此而已。（如果你使用IntelliJ创建文件，这一步应该已经自动完成了。）

在继续之前，确保`BoundedQueue`没有编译错误。

如果你遇到困难，可以参考`List61B`接口，它是一个带有泛型的接口声明示例。
#### 任务2：AbstractBoundedQueue
回顾：什么是抽象类？为什么需要抽象类？方法和类可以使用`abstract`关键字声明为抽象的。抽象类不能被实例化，但可以使用`extends`关键字被继承。与接口不同，抽象类可以为除公共方法之外的其他特性提供实现继承，包括实例变量。

实现接口的类将继承该接口的所有方法和变量。如果一个实现类没有实现从接口继承的任何抽象方法，那么该类必须声明为抽象类，如下所示：
```java
public abstract class AbstractBoundedQueue
```
顺便说一句，也可以声明额外的抽象方法。要做到这一点，方法必须用`abstract`关键字定义，并且不能有实现（没有花括号，后面跟分号），如下所示：
```java
abstract void moveTo(double deltaX, double deltaY);
```
在作业1中，我们不会显式地将任何非继承方法定义为抽象方法，但将来你可能会发现它很有用。

你的任务：在一个名为`AbstractBoundedQueue.java`的文件中创建一个新的抽象类，该类实现`BoundedQueue`接口。你的`AbstractBoundedQueue`类应该包含以下方法和字段（字段只是实例变量的另一种说法）：
```java
protected int fillCount; 
protected int capacity; 
public int capacity() 
public int fillCount() 
public boolean isEmpty() 
public boolean isFull() 
public abstract T peek(); 
public abstract T dequeue(); 
public abstract void enqueue(T x);
```
注意，`isEmpty`、`isFull`、`peek`、`dequeue`、`enqueue`是从`BoundedQueue`继承的，所以你不应该在`AbstractBoundedQueue.java`文件中显式声明这些方法。上面神秘的`protected`关键字将在2月21日的课上讲解。它的意思是只有`AbstractBoundedQueue`的子类和与`AbstractBoundedQueue`在同一个包中的类才能访问这个变量。

`AbstractBoundedQueue`的目的是简单地提供一个`protected`的`fillCount`和`capacity`变量，所有子类都将继承这些变量，以及所谓的“获取器”方法`capacity()`和`fillCount()`，分别返回`capacity`和`fillCount`。这为未来像`ArrayRingBuffer.java`（见下一节）这样的实现节省了少量工作。
#### 任务3：ArrayRingBuffer
`ArrayRingBuffer`类将通过继承`AbstractBoundedQueue`来完成所有实际工作。这意味着我们可以愉快地继承`capacity()`、`fillCount()`、`isEmpty()`和`isFull()`方法，而无需重写它们，但我们需要重写所有抽象方法。在这一部分，你将填写`ArrayRingBuffer.java`文件。你需要将文件名从`ArrayRingBuffer.java.skeleton`重命名为`ArrayRingBuffer.java`。

一种简单的基于数组的`BoundedQueue`实现方式可能是将最新的元素存储在位置0，次新的元素存储在位置1，依此类推。这是一种低效的方法，如下例所示，注释显示了数组的0、1、2和3位置的元素。我们假设数组最初全为`null`。
```java
BoundedQueue x = new NaiveArrayBoundedQueue(4);
x.enqueue(33.1) // 33.1 null null null
x.enqueue(44.8) // 33.1 44.8 null null
x.enqueue(62.3) // 33.1 44.8 62.3 null
x.enqueue(-3.4) // 33.1 44.8 62.3 -3.4
x.dequeue() // 44.8 62.3 -3.4 null (返回33.1)
```
注意，在这种设置下，调用`dequeue`非常慢，因为它需要将每个元素向左移动。对于更大的数组，这将导致不可接受的性能问题。

`ArrayRingBuffer`将通过使用“环形缓冲区”数据结构显著提高运行时性能，类似于项目1A中的循环数组。环形缓冲区最初为空，有预定义的长度。例如，这是一个7元素的缓冲区：
（此处应有环形缓冲区初始状态的图示）

假设将一个1写入缓冲区中间位置（在环形缓冲区中，确切的起始位置并不重要）：
（此处应有写入1之后的图示）

然后假设再添加两个元素2和3，它们会追加在1之后。这里，2和3必须按所示的顺序和位置放置：
（此处应有写入2和3之后的图示）

如果从缓冲区中移除两个元素，则会移除缓冲区中最旧的两个值。在这种情况下，移除的两个元素是1和2，缓冲区中只剩下3：
（此处应有移除1和2之后的图示）

如果我们接着入队4、5、6、7、8和9，现在环形缓冲区如下所示：
（此处应有入队4 - 9之后的图示）

注意，6被入队到数组的最左边位置（即缓冲区像环一样环绕）。此时，环形缓冲区已满，如果再执行一次`enqueue()`操作，则会抛出异常。你需要手动抛出这个异常。有关更多信息，请参阅本次作业末尾名为“迭代和异常”的部分。

我们建议你维护一个整数实例变量`first`，用于存储最近插入元素的索引；再维护一个整数实例变量`last`，用于存储比最近插入元素的索引大1的位置。插入元素时，将其放在`last`索引处并递增`last`。移除元素时，从`first`索引处取出元素并递增`first`。当任一索引等于`capacity`时，通过将索引更改为0使其环绕。我们的框架文件提供了大致这样的起始代码。如果你愿意，也可以采用其他方式，因为这些变量是私有的，我们的测试器无法看到它们。

在本次作业的最后一部分，我们将实现`ArrayRingBuffer`，以便在客户端尝试向已满的缓冲区入队或从空缓冲区调用`dequeue()`或`peek()`时抛出运行时异常。我们将在周一讲解异常，所以请等到那时（或者提前阅读《Head First Java》或在线资料）。

在你完成所有待办事项后，确保`ArrayRingBuffer`能够编译通过再继续。你也可以选择在`TestArrayRingBuffer`类中添加测试（可以在编写`ArrayRingBuffer`之前或之后进行）。

`TestArrayRingBuffer.java`不会被评分。

对于作业和实验（但项目除外），你可以分享测试代码。欢迎在Piazza上分享本次作业的测试代码。
#### 任务4：GuitarString
最后，我们要完善`GuitarString`类，它使用`ArrayRingBuffer`来模拟拨弦的声音。

我们将使用Karplus-Strong算法，该算法使用`BoundedQueue`很容易实现。

Karplus-Strong算法简单来说就是以下三个步骤：
1. 用随机噪声（介于 -0.5和0.5之间的双精度值）替换`BoundedQueue`中的每个元素。
2. 移除`BoundedQueue`头部的双精度值，并将其与队列中下一个双精度值求平均（提示：使用`dequeue()`和`peek()`方法），再乘以0.996的能量衰减因子。
3. 播放你在步骤2中出队的双精度值。回到步骤2（永远重复）。

或者从视觉上看，如果`BoundedQueue`如下所示，我们将出队0.2，将其与0.4组合形成0.2988，入队0.2988，并播放0.2。
（此处应有描述算法步骤的图示）

你可以使用`StdAudio.play`方法播放一个双精度值。例如，`StdAudio.play(0.333)`会让扬声器的振膜伸展到其总伸展范围的三分之一，`StdAudio.play(-0.9)`会让它尽可能向后伸展。扬声器振膜的运动使空气发生位移，如果你以合适的模式使空气位移，经过数十亿年的进化，这些干扰会被你的大脑解读为悦耳的声音。更多信息请查看此页面。如果你只是执行`StdAudio.play(0.9)`且之后不再播放任何声音，那么图中所示的振膜就会一直保持在向前伸展十分之九的位置静止不动。

将`GuitarString.java.skeleton`重命名为`GuitarString.java`。完善`GuitarString.java`，使其实现Karplus-Strong算法的步骤1和步骤2。步骤3将由`GuitarString`类的客户端完成。

确保像往常一样将`javalib`导入到你的项目中，否则`StdAudio`将无法工作。

例如，提供的`TestGuitarString`类中有一个示例测试`testPluckTheAString`，它尝试在吉他弦上弹奏A音。运行这个测试时，你应该能听到A音。如果你听不到，可以尝试`testTic`方法并从那里开始调试。考虑在`GuitarString.java`中添加一个`print`或`toString`方法，这将有助于你查看每次迭代之间发生了什么。
#### 趣味尝试：TTFAF
当你觉得`GuitarString`应该能正常工作后，将其余的`.java.skeleton`文件重命名为`.java`，然后尝试运行`TTFAF`。确保打开声音！

你可以阅读`GuitarPlayer`和`TTFAF`类，了解它们的工作原理。特别是`TTFAF`类中（以注释掉的代码形式）包含了另一种使用方式的示例。
#### GuitarHeroLite
现在你应该也能够使用`GuitarHero`类了。运行它将提供一个界面，允许用户（也就是你！）使用`synthesizer`包中的`GuitarString`类以交互方式播放声音。

考虑编写一个`GuitarHero`程序，它类似于`GuitarHeroLite`，但支持从110Hz到880Hz的全音阶中的37个音符。使用以下37个键来表示键盘，从最低音到最高音：
```java
String keyboard = "q2we4r5ty7u8i9op-[=zxdcfvgbnjmk,.;/";
```
这种键盘布局模仿了钢琴键盘：“白键”在`qwerty`和`zxcv`行，“黑键”在`12345`和`asdf`行。

字符串`keyboard`的第`i`个字符对应频率为`440 * 2^((i - 24) / 12)`Hz的音符，因此字符`q`的频率是110Hz，`i`是220Hz，`v`是440Hz，空格是880Hz。不要考虑使用37个单独的`GuitarString`变量或37路`if`语句！相反，创建一个包含37个`GuitarString`对象的数组，并使用`keyboard.indexOf(key)`来确定按下的是哪个键。确保当按下的键与你的37个音符中的任何一个都不对应时，程序不会崩溃。

这部分作业不评分。
#### 更多趣味尝试
- **竖琴音效**：在`tic()`方法中，在入队新值之前将其符号取反，这样可以将声音从类似吉他的声音变为类似竖琴的声音。你可能需要调整衰减因子以提高逼真度，并将缓冲区大小调整为原来的两倍，因为`tic()`方法的改变会使自然共振频率减半。
- **鼓声音效**：在`tic()`方法中，以0.5的概率在入队新值之前将其符号取反，这样会产生鼓的声音。衰减因子设为1.0（无衰减）会产生更好的音效，并且你需要调整所使用的频率集。
- **模拟吉他多弦**：吉他通过6根物理弦弹奏每个音符。为了模拟这一点，你可以将`GuitarString`实例分成6组，当拨动一根弦时，将该组中的所有其他弦归零。
- **钢琴延音踏板效果**：钢琴配有延音踏板，可以使琴弦保持振动。你可以通过在按住某个特定键（如Shift键）的迭代过程中改变衰减因子来实现这个效果。
- **纯律音阶**：虽然我们使用的是十二平均律，但人耳会觉得按照纯律音阶的小分数划分的音程听起来更悦耳。例如，当音乐家使用铜管乐器演奏纯五度和声