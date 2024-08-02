package he.chen.coroutinedemo.mytest;

public class Father {

    public Father(int a, boolean b) {
        this(a, b, 1f);
        System.out.println("fahter constructor 1 is invoked");
    }

    public Father(int a, boolean b, float c) {
        System.out.println("fahter constructor 2 is invoked");
    }
}
