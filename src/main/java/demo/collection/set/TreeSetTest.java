package demo.collection.set;

import java.util.Comparator;
import java.util.TreeSet;

/**
 * TreeSet是SortedSet接口的实现类，TreeSet可以确保集合元素处于排序状态
 * 与HashSet集合采用hash算法来决定元素的存储位置不同，TreeSet采用红黑树的数据结构来存储集合元素。
 * TreeSet支持两种排序方式: 自然排序、定制排序
 */
public class TreeSetTest {

    /**
     * 自然排序
     * TreeSet会调用集合元素的compareTo(Object obj)方法来比较元素之间的大小关系，然后将集合元素按升序排序，即自然排序。如果试图把一个对象添加到TreeSet时，则该对象的类必须实现Comparable接口，否则程序会抛出异常。

     当把一个对象加入TreeSet集合中时，TreeSet会调用该对象的compareTo(Object obj)方法与容器中的其他对象比较大小，然后根据红黑树结构找到它的存储位置。
     如果两个对象通过compareTo(Object obj)方法比较相等，新对象将无法添加到TreeSet集合中(牢记Set是不允许重复的概念)。

     注意: 当需要把一个对象放入TreeSet中，重写该对象对应类的equals()方法时，应该保证该方法与compareTo(Object obj)方法有一致的结果，即如果两个对象通过equals()方法比较返回true时，这两个对象通过compareTo(Object obj)方法比较结果应该也为0(即相等)
     */
    @SuppressWarnings("SpellCheckingInspection")
    public static void main(String[] args){

        TreeSet nums = new TreeSet();
        nums.add(5);
        nums.add(-3);
        nums.add(14);
        nums.add(0);

        // 输出集合元素，看到集合元素已经处于排序状态
        System.out.println(nums);

        // 输出集合里的第一个元素
        System.out.println(nums.first());

        // 输出集合里的最后一个元素
        System.out.println(nums.last());

        // 返回小于4的子集，不包含4
        System.out.println(nums.headSet(4));

        // 返回大于5的子集，如果Set中包含5，子集中还包含5
        System.out.println(nums.tailSet(5));

        // 返回大于等于-3，小于4的子集。
        System.out.println(nums.subSet(-3 , 4));

        System.out.println("============================================");

        TreeSet objs = new TreeSet();
        objs.add("爱国ss");
        objs.add("asdf");
        objs.add("噶啥");
        objs.add("123");
        System.out.println(objs);

        TreeSet objs2 = new TreeSet();
        objs2.add("asdf");
        objs2.add("噶啥");
        objs2.add("123");
        objs2.add("爱国ss");
        System.out.println(objs2);
    }


    static class M
    {
        int age;
        public M(int age)
        {
            this.age = age;
        }
        public String toString()
        {
            return "M[age:" + age + "]";
        }
    }

    /**
     * 定制排序
     * TreeSet的自然排序是根据集合元素的大小，TreeSet将它们以升序排序。
     * 如果我们需要实现定制排序，则可以通过Comparator接口的帮助(类似PHP中的array_map回调处理函数的思想)。该接口里包含一个int compare(T o1， T o2)方法，该方法用于比较大小
     */
    public static void main2(String[] args){
        TreeSet ts = new TreeSet(new Comparator()
        {
            //根据M对象的age属性来决定大小
            public int compare(Object o1, Object o2)
            {
                M m1 = (M)o1;
                M m2 = (M)o2;
                return m1.age > m2.age ? -1
                        : m1.age < m2.age ? 1 : 0;
            }
        });
        ts.add(new M(5));
        ts.add(new M(-3));
        ts.add(new M(9));
        System.out.println(ts);
    }
}
