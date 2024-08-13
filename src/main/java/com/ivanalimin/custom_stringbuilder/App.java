package com.ivanalimin.custom_stringbuilder;

public class App {
    public static void main(String[] args) {
        MyCustomStringBuilder builder1 = new MyCustomStringBuilder("Helllo");
        builder1.append("World");
        builder1.append('!');
        System.out.println(builder1);
        System.out.println(builder1.length());
        builder1.delete(2, 3);
        System.out.println(builder1);
        builder1.insert(5, " ");
        System.out.println(builder1);
        builder1.replace(11, 12, "?");
        System.out.println(builder1);
        System.out.println(builder1.charAt(11));

        MyCustomStringBuilder builder2 = new MyCustomStringBuilder("Hello World!");
        System.out.println(builder1.equals(builder2));
        System.out.println(builder2.compareTo(builder1));
        builder1.replace(11, 12, "!");
        System.out.println(builder1);
        System.out.println(builder1.equals(builder2));
        System.out.println(builder2.delete(0, builder2.length()));
        if (builder2.toString().isEmpty()) {
            System.out.println("Пусто");
        }
        builder2.undo();
        System.out.println(builder2);
        builder1.undo();
        builder1.undo();
        builder1.undo();
        builder1.undo();
        builder1.undo();
        builder1.undo();
        System.out.println(builder1);
    }
}
