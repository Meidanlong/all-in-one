package com.meidl.javabasic.hashMap;

import java.util.HashMap;
import java.util.Objects;

import static java.util.Objects.hash;

public  class OnlyOverrideEquals {
    private static class Person{
        int id;
        String name;

        public Person(int id, String name) {
            this.id = id;
            this.name = name;
        }
        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()){
                return false;
            }
            Person person = (Person) o;
            //两个对象是否等值，通过id来确定
            return this.id == person.id;
        }

        /*@Override
        public int hashCode() {
            return hash(id);
        }*/
    }
    public static void main(String []args){
        HashMap<Person,String> map = new HashMap<Person, String>();
        Person person = new Person(12,"梅丹隆");
        //put到hashmap中去
        //System.out.println(hash(person));
        System.out.println(Objects.hashCode(person));
        map.put(person,"我是想要输出的结果");
        //get取出，从逻辑上讲应该能输出“天龙八部”，但实际上返回为null
        Person person2 = new Person(12,"梅丹隆");
        System.out.println(hash(person2));
        System.out.println("结果:"+map.get(person2));




    }

}