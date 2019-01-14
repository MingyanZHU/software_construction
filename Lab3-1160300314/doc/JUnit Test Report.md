# Junit Test Report

* 对于Vertex测试不能达到百分之百的原因 是`toString`方法无法测试。而Vertex本身为抽象类且没有抽象的子类，所以在测试中不能调用到`toString`方法。
* 另外在所有顶点类测试中均不再次测试有关于构造方法的测试，是由于所有的顶点的构造方法都仅有`label`一个参数，关于其`Vertex`类中的方法已经测试完毕，故不再单独进行测试。
* PersonTest不能到达百分之百的原因是， 在`restore()`中，由于`Person`类中在构造函数里添加了第1条备忘录，故`在Caretaker里面的mementos必不为空`。
