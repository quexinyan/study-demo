package demo.collection.set;

import java.util.HashSet;

/**
 * HashSet是Set接口的典型实现，HashSet使用HASH算法来存储集合中的元素，因此具有良好的存取和查找性能。当向HashSet集合中存入一个元素时，HashSet会调用该对象的
 　　　　 hashCode()方法来得到该对象的hashCode值，然后根据该HashCode值决定该对象在HashSet中的存储位置。
 值得主要的是，HashSet集合判断两个元素相等的标准是两个对象通过equals()方法比较相等，并且两个对象的hashCode()方法的返回值相等
 */
public class HashSetTest {

    // 类A的equals方法总是返回true,但没有重写其hashCode()方法。不能保证当前对象是HashSet中的唯一对象
    static class A{
        public boolean equals(Object obj){
            return true;
        }
    }

    // 类B的hashCode()方法总是返回1,但没有重写其equals()方法。不能保证当前对象是HashSet中的唯一对象
    static class B{
        public int hashCode(){
            return 1;
        }
    }

    static class C{
        public int hashCode(){
            return 2;
        }

        public boolean equals(Object obj){
            return true;
        }
    }

    /**
     * result : [demo.collection.set.HashSetTest$B@1, demo.collection.set.HashSetTest$B@1,
     *          demo.collection.set.HashSetTest$C@2,
     *          demo.collection.set.HashSetTest$A@3d646c37, demo.collection.set.HashSetTest$A@26a1ab54]
     *
     * 可以看到，如果两个对象通过equals()方法比较返回true，但这两个对象的hashCode()方法返回不同的hashCode值时，这将导致HashSet会把这两个对象保存在Hash表的不同位置，从而使对象可以添加成功，这就与Set集合的规则有些出入了。
     * 所以，我们要明确的是: equals()决定是否可以加入HashSet、而hashCode()决定存放的位置，它们两者必须同时满足才能允许一个新元素加入HashSet
     但是要注意的是: 如果两个对象的hashCode相同，但是它们的equlas返回值不同，HashSet会在这个位置用链式结构来保存多个对象。而HashSet访问集合元素时也是根据元素的HashCode值来快速定位的，这种链式结构会导致性能下降。

     所以如果需要把某个类的对象保存到HashSet集合中，我们在重写这个类的equlas()方法和hashCode()方法时，应该尽量保证两个对象通过equals()方法比较返回true时，它们的hashCode()方法返回值也相等
     */
    public static void main(String[] args){
        HashSet books = new HashSet();
        //分别向books集合中添加两个A对象，两个B对象，两个C对象
        books.add(new A());
        books.add(new A());

        books.add(new B());
        books.add(new B());

        books.add(new C());
        books.add(new C());
        System.out.println(books);
    }
}
