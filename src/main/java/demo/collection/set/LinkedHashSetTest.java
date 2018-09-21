package demo.collection.set;

import java.util.LinkedHashSet;

/**
 * LinkedHashSet集合也是根据元素的hashCode值来决定元素的存储位置，但和HashSet不同的是，它同时使用链表维护元素的次序，这样使得元素看起来是以插入的顺序保存的。
 　　　　　　　当遍历LinkedHashSet集合里的元素时，LinkedHashSet将会按元素的添加顺序来访问集合里的元素。
 LinkedHashSet需要维护元素的插入顺序，因此性能略低于HashSet的性能，但在迭代访问Set里的全部元素时(遍历)将有很好的性能(链表很适合进行遍历)
 */
public class LinkedHashSetTest {

    /**
     * 元素的顺序总是与添加顺序一致，同时要明白的是，LinkedHashSet是HashSet的子类，因此它不允许集合元素重复
     */
    public static void main(String[] args){
        LinkedHashSet<String> books = new LinkedHashSet<String>();
        books.add("Java");
        books.add("ES");
        books.add("ES");
        books.add("Redis");
        System.out.println(books);

        // 删除java
        books.remove("Java");
        // 重新添加java
        books.add("Java");
        System.out.println(books);
    }
}
